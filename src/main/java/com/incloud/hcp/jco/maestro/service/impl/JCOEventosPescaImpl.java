package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.maestro.service.JCOEventosPescaService;
import com.incloud.hcp.util.*;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JCOEventosPescaImpl implements JCOEventosPescaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());




   public EventosPescaExports ListarEventoPesca(EventosPescaImports imports)throws Exception{
       EventosPescaExports dto= new EventosPescaExports();
       try {
           logger.error("ListarEventosPesca_1");
           ;
           JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
           logger.error("ListarEventosPesca_2");
           ;
           JCoRepository repo = destination.getRepository();
           logger.error("ListarEventosPesca_3");
           ;
           JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_MAES_EVEN_PES);
           JCoParameterList importx = stfcConnection.getImportParameterList();
           importx.setValue("I_FLAG", imports.getI_flag());
           importx.setValue("P_USER", imports.getP_user());
           logger.error("ListarEventosPesca_4");
           ;
           JCoParameterList tables = stfcConnection.getTableParameterList();
           stfcConnection.execute(destination);
           logger.error("ListarEventosPesca_5");

           JCoTable tableST_CCP = tables.getTable(Tablas.ST_CCP);
           logger.error("ListarEventosPesca_6");
           JCoTable tableST_CEP = tables.getTable(Tablas.ST_CEP);

           logger.error("ListarEventosPesca_7");

           Metodos metodo = new Metodos();
           List<HashMap<String, Object>> ListarST_CEP = metodo.ListarObjetos(tableST_CEP);
           List<HashMap<String, Object>> ListarST_CCP = metodo.ListarObjetos(tableST_CCP);


           dto.setSt_cep(ListarST_CEP);
           dto.setSt_ccp(ListarST_CCP);
           dto.setMensaje("Ok");
       }catch (Exception e){
           dto.setMensaje(e.getMessage());
       }

       return dto;
   }

   public Mensaje EditarEventosPesca(EventosPescaEditImports impor)throws Exception{
        Mensaje msj= new Mensaje();
        try {
            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("I_FLAG", impor.getI_flag());
            imports.put("P_USER", impor.getP_user());


            logger.error("ListarEventosPesca_1");
            ;
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_MAES_EVEN_PES);
            JCoParameterList jcoTables = function.getTableParameterList();

            logger.error("ListarEventosPesca_4");
            ;

            List<HashMap<String, Object>> estcce = impor.getEstcce();
            List<HashMap<String, Object>> estcep = impor.getEstcep();

            for (Object o : estcce) {
                logger.error("RECORRER LISTA: " + o.toString());
            }
            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, imports);
            exec.setTable(jcoTables, Tablas.ESTCCE, estcce);
            exec.setTable(jcoTables, Tablas.ESTCEP, estcep);
            function.execute(destination);
            msj.setMensaje("Ok");

        }catch (Exception e){
            msj.setMensaje(e.getMessage());
        }



        return msj;
    }

    @Override
    public ConfiguracionEventoPescaExports ObtenerConfEventosPesca() throws Exception {

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        ;
        JCoRepository repo = destination.getRepository();
        ;
        JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_READ_TABLE);
        JCoParameterList importx = stfcConnection.getImportParameterList();

        importx.setValue("DELIMITER","|");
        importx.setValue("QUERY_TABLE",Tablas.ZFLCEP);

        JCoParameterList tables = stfcConnection.getTableParameterList();
        JCoTable tableImport = tables.getTable("OPTIONS");
        tableImport.appendRow();

        tableImport.setValue("WA", "CLSIS = '01'");
        String[] field = {"QMART", "MNESP", "MXESP", "NTESP", "UMPDC", "PRCHI", "TMXCA", "UMTMX", "TMICA", "UMTMI",
                "UMPDS", "BAUGR", "HERKUNFT", "BSART", "EKORG", "EKGRP", "AUART", "VKORG", "BWART1", "VTWEG", "HRCOR"};
        stfcConnection.execute(destination);


        JCoTable tableExport = tables.getTable("DATA");
        JCoTable FIELDS = tables.getTable("FIELDS");
        Metodos me = new Metodos();
        List<HashMap<String, Object>> ListarST_CEP =me.obtenerDataEventosPesca(tableExport,FIELDS,field);

        ConfiguracionEventoPescaExports list = new ConfiguracionEventoPescaExports();
        list.setList_EventosPesca(ListarST_CEP);

        //SEGUNDO

        JCoDestination destinations = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        ;
        JCoRepository repos = destinations.getRepository();
        ;
        JCoFunction stfcConnections = repos.getFunction(Constantes.ZFL_RFC_READ_TABLE);
        JCoParameterList importxs = stfcConnections.getImportParameterList();

        importxs.setValue("DELIMITER","|");
        importxs.setValue("QUERY_TABLE",Tablas.ZFLCCE);

        JCoParameterList tabless = stfcConnections.getTableParameterList();
        JCoTable tableImports = tabless.getTable("OPTIONS");
        String[] fieldz = {"CDTEV", "BWART", "MATNR", "UNICB", "ATLHO"};
        stfcConnections.execute(destination);


        JCoTable tableExports = tabless.getTable("DATA");
        JCoTable FIELDSs = tabless.getTable("FIELDS");
        List<HashMap<String, Object>> Listar_ConsCombEve =me.obtenerDataEventosPesca2(tableExports,FIELDSs,fieldz);

        int contador=0;


        for(Map<String,Object> datas: ListarST_CEP){
            for(Map.Entry<String,Object> entry: datas.entrySet()){
                contador++;
            }
        }
        String[] data=new String[contador];
        logger.error("TAMANIO DATA:" +data.length);
        logger.error("TAMANIO LISTA:" + contador);

        int contador2=0;
        for(Map<String,Object> datas: ListarST_CEP){
            for(Map.Entry<String,Object> entry: datas.entrySet()){
                String key= entry.getKey();
                Object value= entry.getValue();
                data[contador2]=value.toString();
                contador2++;
            }

        }

        for(int j=0;j<data.length;j++){
            logger.error(data[j]);
        }
        list.setListar_ConsCombEve(Listar_ConsCombEve);


        //tercero
        JCoDestination destinations3 = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        ;
        JCoRepository repos3 = destinations3.getRepository();
        ;
        JCoFunction stfcConnections3 = repos3.getFunction(Constantes.ZFL_RFC_READ_TABLE);
        JCoParameterList importxs3 = stfcConnections3.getImportParameterList();

        importxs3.setValue("DELIMITER","|");
        importxs3.setValue("QUERY_TABLE",Tablas.ZFLCCE);

        JCoParameterList tabless3 = stfcConnections.getTableParameterList();
        JCoTable tableImports3 = tabless.getTable("OPTIONS");
        String fieldz3 = "DSSPC";
        stfcConnections3.execute(destination);


        JCoTable tableExports3 = tabless.getTable("DATA");
        JCoTable FIELDSs3 = tabless.getTable("FIELDS");

        String CalaDescEspecieCHI = me.obtenerDataEventosPescaCadena(tableExports3,FIELDSs3,fieldz3);

        return list;
    }

}
