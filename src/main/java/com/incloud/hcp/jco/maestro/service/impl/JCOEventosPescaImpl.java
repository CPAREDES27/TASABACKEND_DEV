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


}
