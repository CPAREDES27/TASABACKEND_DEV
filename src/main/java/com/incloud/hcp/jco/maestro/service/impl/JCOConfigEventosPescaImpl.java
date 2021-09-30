package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.maestro.service.JCOConfigEventosPesca;
import com.incloud.hcp.util.*;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JCOConfigEventosPescaImpl implements JCOConfigEventosPesca {
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

            dto.setSt_cep(ST_CEP);
            dto.setSt_ccp(ST_CCP);
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
            HashMap<String, Object> importParams = new HashMap<String, Object>();
            importParams.put("I_FLAG", "S");
            importParams.put("P_USER", imports.getP_user());

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_MAES_EVEN_PES);

            JCoParameterList jcoTables = function.getTableParameterList();

            List<HashMap<String, Object>> estcce = new ArrayList<>();
            List<HashMap<String, Object>> estcep = new ArrayList<>();
            estcce.add(imports.getEstcce());
            estcep.add(imports.getEstcep());

            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, importParams);
            exec.setTable(jcoTables, Tablas.ESTCCE, estcce);
            exec.setTable(jcoTables, Tablas.ESTCEP, estcep);
            function.execute(destination);
            msj.setMensaje("Ok");

        } catch (Exception e) {
            msj.setMensaje(e.getMessage());
        }

        return msj;
    }
}
