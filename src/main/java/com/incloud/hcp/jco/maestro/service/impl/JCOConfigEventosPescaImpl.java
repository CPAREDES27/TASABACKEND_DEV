package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.maestro.service.JCOConfigEventosPesca;
import com.incloud.hcp.util.*;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JCOConfigEventosPescaImpl implements JCOConfigEventosPesca {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public EventosPesca2Exports ListarEventoPesca(EventosPesca2Imports imports) throws Exception {
        EventosPesca2Exports dto = new EventosPesca2Exports();
        HashMap<String, Object> ST_CCP = new HashMap<>();
        HashMap<String, Object> ST_CEP = new HashMap<>();

        try {
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_MAES_EVEN_PES);
            JCoParameterList params = stfcConnection.getImportParameterList();

            params.setValue("I_FLAG", "R");
            params.setValue("P_USER", imports.getP_user());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            stfcConnection.execute(destination);

            JCoTable tableST_CCP = tables.getTable(Tablas.ST_CCP);
            JCoTable tableST_CEP = tables.getTable(Tablas.ST_CEP);

            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> ListarST_CEP = metodo.ListarObjetos(tableST_CEP);
            List<HashMap<String, Object>> ListarST_CCP = metodo.ListarObjetos(tableST_CCP);

            /**
             * Mapeado de CCP
             */
            Optional<HashMap<String, Object>> ccpZarpe = ListarST_CCP.stream().filter(c -> c.get("CDTEV").toString().equals("1")).findFirst();
            Optional<HashMap<String, Object>> ccpArrbpto = ListarST_CCP.stream().filter(c -> c.get("CDTEV").toString().equals("5")).findFirst();
            Optional<HashMap<String, Object>> ccpCala = ListarST_CCP.stream().filter(c -> c.get("CDTEV").toString().equals("6")).findFirst();

            ccpZarpe.ifPresent(c -> {
                Iterator iterator = c.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    ST_CCP.put(entry.getKey().toString() + "_ZARPE", entry.getValue());
                }
            });

            ccpArrbpto.ifPresent(c -> {
                Iterator iterator = c.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    ST_CCP.put(entry.getKey().toString() + "_ARRBPTO", entry.getValue());
                }
            });

            ccpCala.ifPresent(c -> {
                Iterator iterator = c.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    ST_CCP.put(entry.getKey().toString() + "_DSCG", entry.getValue());
                }
            });

            /*
             * Mapeado de CEP
             */
            ST_CEP = ListarST_CEP.get(0);

            HashMap<String, Object> ST_CMAP = new HashMap<>();
            for (Map.Entry<String, Object> entry: ST_CCP.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                ST_CMAP.put(key, value);
            }

            for (Map.Entry<String, Object> entry: ST_CEP.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                ST_CMAP.put(key, value);
            }

            dto.setSt_cmap(ST_CMAP);
            dto.setMensaje("Ok");

        } catch (Exception e) {
            dto.setMensaje(e.getMessage());
        }

        return dto;
    }

    @Override
    public Mensaje EditarEventosPesca(EventosPescaEdit2Imports imports) throws Exception {
        Mensaje msj = new Mensaje();
        try {
            Metodos metodos=new Metodos();
            HashMap<String, Object> importParams = new HashMap<String, Object>();
            importParams.put("I_FLAG", "S");
            importParams.put("P_USER", imports.getP_user());

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_MAES_EVEN_PES);

            JCoParameterList jcoTables = function.getTableParameterList();

            List<HashMap<String, Object>> estcce = new ArrayList<>();
            List<HashMap<String, Object>> estcep = new ArrayList<>();

            HashMap<String, Object> valuesEstcceZarpe = new HashMap<>();
            HashMap<String, Object> valuesEstcceArrbpto = new HashMap<>();
            HashMap<String, Object> valuesEstcceDesc = new HashMap<>();
            HashMap<String, Object> valuesEstcep = new HashMap<>();

            // Mapeao de parámetros ZFLCEP y ZFLCCE
            for (Map.Entry<String, Object> entry: imports.getEstcmap().entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                int indexSuffix = 0;
                String suffix = "";

                if (key.endsWith("_ZARPE")) {
                    suffix = "_ZARPE";
                } else if (key.endsWith("_ARRBPTO")) {
                    suffix = "_ARRBPTO";
                } else if (key.endsWith("_DSCG")) {
                    suffix = "_DSCG";
                }

                indexSuffix = key.indexOf(suffix);
                String fieldName = key.substring(0, indexSuffix);

                switch (suffix) {
                    case "_ZARPE":
                        valuesEstcceZarpe.put(fieldName, value);
                        break;
                    case "_ARRBPTO":
                        valuesEstcceArrbpto.put(fieldName, value);
                        break;
                    case "_DSCG":
                        valuesEstcceDesc.put(fieldName, value);
                        break;
                    default:
                        valuesEstcep.put(key, value);
                        break;
                }
            }

            // Eliminación de cmmpos
            String[] fieldsToRemoveCCP = {"MAKTX", "DESC_CDTEV"};
            String[] fieldsToRemoveCEP = {"DSSPC", "LTEXT", "EKNAM", "MAKTX", "BEZEI"};

            for (String fieldCCP: fieldsToRemoveCCP) {
                if (valuesEstcceArrbpto.containsKey(fieldCCP)) {
                     valuesEstcceArrbpto.remove(fieldCCP);
                }
                if (valuesEstcceZarpe.containsKey(fieldCCP)) {
                    valuesEstcceZarpe.remove(fieldCCP);
                }
                if (valuesEstcceDesc.containsKey(fieldCCP)) {
                    valuesEstcceDesc.remove(fieldCCP);
                }
            }

            for (String fieldCEP: fieldsToRemoveCEP) {
                valuesEstcep.remove(fieldCEP);
            }

            estcce.add(valuesEstcceZarpe);
            estcce.add(valuesEstcceArrbpto);
            estcce.add(valuesEstcceDesc);
            estcep.add(valuesEstcep);

            String infoArrbpto="";
            String infoZarpe="";
            String infoDesc="";
            String infocep="";
            for (Map.Entry<String, Object> entry: valuesEstcceArrbpto.entrySet()) {
                infoArrbpto+=entry.getKey()+": "+entry.getValue().toString() + "\n";
            }
            for (Map.Entry<String, Object> entry: valuesEstcceZarpe.entrySet()) {
                infoZarpe+=entry.getKey()+": "+entry.getValue().toString() + "\n";;
            }
            for (Map.Entry<String, Object> entry: valuesEstcceDesc.entrySet()) {
                infoDesc+=entry.getKey()+": "+entry.getValue().toString() + "\n";;
            }
            for (Map.Entry<String, Object> entry: valuesEstcep.entrySet()) {
                infocep+=entry.getKey()+": "+entry.getValue().toString() + "\n";;
            }
            logger.info("ARRIBO ESTCCE: "+infoArrbpto);
            logger.info("ZARPE ESTCCE: "+infoZarpe);
            logger.info("DESCARGA ESTCCE: "+infoDesc);
            logger.info("ESTCEP:"+infocep);

            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, importParams);
            exec.setTable(jcoTables, Tablas.ESTCCE, estcce);
            exec.setTable(jcoTables, Tablas.ESTCEP, estcep);
            function.execute(destination);

            /*
            JCoTable T_MENSAJE = jcoTables.getTable(Tablas.T_MENSAJE);
            List<HashMap<String, Object>> tMensaje = metodos.ListarObjetos(T_MENSAJE);
            logger.info("Mensaje del RFC: " + tMensaje.get(0).get("DSMIN") + ", Clase de mensaje: " + tMensaje.get(0).get("CMIN") + ", Código de mensaje: " + tMensaje.get(0).get("CDMIN"));
             */

            msj.setMensaje("Ok");

        } catch (Exception e) {
            msj.setMensaje(e.getMessage());
        }

        return msj;
    }
}
