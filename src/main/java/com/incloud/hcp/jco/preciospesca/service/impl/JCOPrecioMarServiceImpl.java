package com.incloud.hcp.jco.preciospesca.service.impl;

import com.incloud.hcp.jco.dominios.dto.DominioExportsData;
import com.incloud.hcp.jco.dominios.dto.DominiosExports;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.jco.preciospesca.dto.PrecioMarExports;
import com.incloud.hcp.jco.preciospesca.dto.PrecioMarImports;
import com.incloud.hcp.jco.preciospesca.service.JCOPrecioMarService;
import com.incloud.hcp.jco.reportepesca.dto.DominiosHelper;
import com.incloud.hcp.util.*;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JCOPrecioMarServiceImpl implements JCOPrecioMarService {
    @Override
    public PrecioMarExports ObtenerPrecioMar(PrecioMarImports imports) throws Exception {
        Metodos metodos = new Metodos();
        HashMap<String, Object> importParams = new HashMap<>();
        importParams.put("P_USER", imports.getP_user());
        importParams.put("P_INDPR", imports.getP_indpr());
        importParams.put("P_ROWS", imports.getP_rows());
        importParams.put("P_CALIDAD", imports.getP_calidad());
        importParams.put("P_FLAG", imports.getP_flag());

        List<MaestroOptions> option = imports.getP_option();
        List<MaestroOptionsKey> options2 = imports.getP_options();

        /**
         * Añadir el options de acuerdo a la aplicación
         */
        String optionWaApplications=null;
        switch (imports.getNum_application()){
            case 1: //Aprobacion Castigos Propuestos
                if(imports.getId_estado().equalsIgnoreCase("PC")){
                    optionWaApplications="(USCPP NE '' OR USCPP IS NOT NULL) AND ESCSG EQ 'N'";
                }
                break;
            case 2: //Aprobacion Precios
                optionWaApplications="(ESPRC EQ 'N' AND (ESCSG EQ 'L' OR ESCSG EQ '' OR ESCSG EQ ' ' OR ESCSG IS NULL)) AND PRCOM > '0.00'";
                break;
            case 3: //Castigos Propuestos
                if(imports.getId_estado().equalsIgnoreCase("PC")){
                    optionWaApplications="(USCPP EQ '' OR USCPP IS NULL) AND ESCSG NE 'L'";
                }
                break;
            case 4: //Precios acopio
                switch (imports.getId_estado()){
                    case "C":
                        optionWaApplications="(PRCOM IS NOT NULL AND PRCOM > 0)";
                        break;
                    case "S":
                        optionWaApplications="(PRCOM IS NULL OR PRCOM = 0)";
                        break;
                }
                break;
        }

        if(optionWaApplications!=null){
            MaestroOptions optionApplication=new MaestroOptions();
            optionApplication.setWa(optionWaApplications);
            option.add(optionApplication);
        }


        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
        tmpOptions=metodos.ValidarOptions(option,options2);


        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_LECT_PRECIO_MAR_BTP);

        JCoParameterList paramsTable = function.getTableParameterList();

        EjecutarRFC executeRFC = new EjecutarRFC();
        executeRFC.setImports(function, importParams);
        executeRFC.setTable(paramsTable, "P_OPTIONS", tmpOptions);

        JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);
        JCoTable tblT_MENSAJE = tables.getTable(Tablas.T_MENSAJE);
        JCoTable tblSTR_PM = tables.getTable(Tablas.STR_PM);


       // List<HashMap<String, Object>> listSTR_PM = metodos.ListarObjetos(tblSTR_PM);
       // List<HashMap<String, Object>> listT_MENSAJE = metodos.ListarObjetos(tblT_MENSAJE);


        List<HashMap<String, Object>> listSTR_PM = metodos.ListarObjetosLazy(tblSTR_PM);
        List<HashMap<String, Object>> listT_MENSAJE = metodos.ListarObjetosLazy(tblT_MENSAJE);


        /*PROCESO MAPEAR DOMINIOS - INICIO*/
        ArrayList<String> listDomNames = new ArrayList<>();
        listDomNames.add(Dominios.ZINPRP);
        listDomNames.add(Dominios.ESCSG);
        listDomNames.add(Dominios.ESPRC);
        listDomNames.add(Dominios.ZDOMMONEDA);
        //listDomNames.add(Dominios.CALIDA);


        DominiosHelper helper = new DominiosHelper();
        ArrayList<DominiosExports> listDescipciones = helper.listarDominios(listDomNames);

        DominiosExports estadoCastigo = listDescipciones.stream().filter(d -> d.getDominio().equals(Dominios.ESCSG)).findFirst().orElse(null);
        DominiosExports estadoPrecio = listDescipciones.stream().filter(d -> d.getDominio().equals(Dominios.ESPRC)).findFirst().orElse(null);
        DominiosExports moneda = listDescipciones.stream().filter(d -> d.getDominio().equals(Dominios.ZDOMMONEDA)).findFirst().orElse(null);
        DominiosExports indicadorPropiedad = listDescipciones.stream().filter(d -> d.getDominio().equals(Dominios.ZINPRP)).findFirst().orElse(null);
        //DominiosExports calidad = listDescipciones.stream().filter(d -> d.getDominio().equals(Dominios.CALIDA)).findFirst().orElse(null);


        listSTR_PM.stream().map(m -> {
            String escsg = m.get("ESCSG").toString();
            String esprc = m.get("ESPRC").toString();
            String waers = m.get("WAERS").toString();
            String inprp = m.get("INPRP").toString();
            //String calida = m.get("CALIDA").toString();


            // Buscar los detalles
            DominioExportsData dataESCSG = estadoCastigo.getData().stream().filter(d -> d.getId().equals(escsg)).findFirst().orElse(null);
            DominioExportsData dataESPRC = estadoPrecio.getData().stream().filter(d -> d.getId().equals(esprc)).findFirst().orElse(null);
            DominioExportsData dataWAERS = moneda.getData().stream().filter(d -> d.getId().equals(waers)).findFirst().orElse(null);
            DominioExportsData dataINPRP = indicadorPropiedad.getData().stream().filter(d -> d.getId().equals(inprp)).findFirst().orElse(null);
         //   DominioExportsData dataCALIDA = calidad.getData().stream().filter(d -> d.getId().equals(calida)).findFirst().orElse(null);

            if (dataESCSG != null) {
                String descESCSG = dataESCSG.getDescripcion();
                m.put("DESC_ESCSG", descESCSG);
            } else {
                m.put("DESC_ESCSG", "");
            }

            if (dataESPRC != null) {
                String descESPRC = dataESPRC.getDescripcion();
                m.put("DESC_ESPRC", descESPRC);
            } else {
                m.put("DESC_ESPRC", "");
            }

            if (dataWAERS != null) {
                String descWAERS = dataWAERS.getDescripcion();
                m.put("DESC_WAERS", descWAERS);
            } else {
                m.put("DESC_WAERS", "");
            }
            if (dataINPRP != null) {
                String descINPRP = dataINPRP.getDescripcion();
                m.put("DESC_INPRP", descINPRP);
            } else {
                m.put("DESC_INPRP", "");
            }
          /*  if (dataCALIDA != null) {
                String descCALIDA = dataCALIDA.getDescripcion();
                m.put("DESC_CALIDA", descCALIDA);
            } else {
                m.put("DESC_CALIDA", "");
            }*/

            return m;
        }).collect(Collectors.toList());
        /*PROCESO MAPEAR DOMINIOS - FIN*/


        PrecioMarExports dto = new PrecioMarExports();
        dto.setStr_pm(listSTR_PM);
        dto.setT_mensaje(listT_MENSAJE);
        dto.setMensaje("OK");

        return dto;
    }
}
