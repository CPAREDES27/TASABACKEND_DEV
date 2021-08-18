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



   public ST_CEPDto llenarSTCEP(JCoTable table){

       ST_CEPDto st_cepDto= new ST_CEPDto();
       st_cepDto.setCLSIS(table.getString("CLSIS"));
       st_cepDto.setINEIF(table.getString("INEIF"));
       st_cepDto.setBWART1(table.getString("BWART1"));
       st_cepDto.setMATNR(table.getString("MATNR"));
       st_cepDto.setCDTEZ(table.getString("CDTEZ"));
       st_cepDto.setCDTAP(table.getString("CDTAP"));
       st_cepDto.setCDTED(table.getString("CDTED"));
       st_cepDto.setBAUGR(table.getString("BAUGR"));
       st_cepDto.setHERKUNFT(table.getString("HERKUNFT"));
       st_cepDto.setBSART(table.getString("BSART"));
       st_cepDto.setEKORG(table.getString("EKORG"));
       st_cepDto.setEKGRP(table.getString("EKGRP"));
       st_cepDto.setAUART(table.getString("AUART"));
       st_cepDto.setVKORG(table.getString("VKORG"));
       st_cepDto.setVTWEG(table.getString("VTWEG"));
       st_cepDto.setBWART2(table.getString("BWART2"));
       st_cepDto.setMXESP(table.getString("MXESP"));
       st_cepDto.setMNESP(table.getString("MNESP"));
       st_cepDto.setNTESP(table.getString("NTESP"));
       st_cepDto.setQMART(table.getString("QMART"));
       st_cepDto.setMAKTX(table.getString("MAKTX"));
       st_cepDto.setBEZEI(table.getString("BEZEI"));
       st_cepDto.setEKNAM(table.getString("EKNAM"));
       st_cepDto.setLTEXT(table.getString("LTEXT"));
       st_cepDto.setUMPDS(table.getString("UMPDS"));
       st_cepDto.setUMPDC(table.getString("UMPDC"));
       st_cepDto.setPRCHI(table.getString("PRCHI"));
       st_cepDto.setDSSPC(table.getString("DSSPC"));
       st_cepDto.setTMXCA(table.getString("TMXCA"));
       st_cepDto.setUMTMX(table.getString("UMTMX"));
       st_cepDto.setTMICA(table.getString("TMICA"));
       st_cepDto.setUMTMI(table.getString("UMTMI"));
       st_cepDto.setHRCOR(table.getString("HRCOR"));






       return st_cepDto;
   }

   public EventosPescaExports ListarEventoPesca(EventosPescaImports imports)throws Exception{

       logger.error("ListarEventosPesca_1");;
       JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
       logger.error("ListarEventosPesca_2");;
       JCoRepository repo = destination.getRepository();
       logger.error("ListarEventosPesca_3");;
       JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_MAES_EVEN_PES);
       JCoParameterList importx = stfcConnection.getImportParameterList();
       importx.setValue("I_FLAG", imports.getI_flag());
       importx.setValue("P_USER", imports.getP_user());
       logger.error("ListarEventosPesca_4");;
       JCoParameterList tables = stfcConnection.getTableParameterList();
       stfcConnection.execute(destination);
       logger.error("ListarEventosPesca_5");

       JCoTable tableST_CCP = tables.getTable(Tablas.ST_CCP);
       logger.error("ListarEventosPesca_6");
       JCoTable tableST_CEP = tables.getTable(Tablas.ST_CEP);

       ST_CEPDto st_cepDto= llenarSTCEP(tableST_CEP);

       logger.error("ListarEventosPesca_7");

       Metodos metodo = new Metodos();
       List<HashMap<String, Object>> ListarST_CEP= metodo.ListarObjetos(tableST_CEP);
       List<HashMap<String, Object>> ListarST_CCP= metodo.ListarObjetos(tableST_CCP);

       EventosPescaExports dto= new EventosPescaExports();
       dto.setSt_cep(ListarST_CEP);
       dto.setSt_ccp(ListarST_CCP);
       dto.setMensaje("Ok");

       return dto;

   }

   public Mensaje EditarEventosPesca(EventosPescaEditImports impor)throws Exception{

       HashMap<String, Object> imports = new HashMap<String, Object>();
       imports.put("I_FLAG", impor.getI_flag());
       imports.put("P_USER", impor.getP_user());


       logger.error("ListarEventosPesca_1");;
       JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
       JCoRepository repo = destination.getRepository();
       JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_MAES_EVEN_PES);
       JCoParameterList jcoTables = function.getTableParameterList();

       logger.error("ListarEventosPesca_4");;

       List<HashMap<String, Object>> estcce=impor.getEstcce();
       List<HashMap<String, Object>> estcep=impor.getEstcep();

       for (Object o:estcce) {
        logger.error("RECORRER LISTA: "+o.toString());
       }
       EjecutarRFC exec= new EjecutarRFC();
       exec.setImports(function, imports);
       exec.setTable(jcoTables,Tablas.ESTCCE,estcce);
       exec.setTable(jcoTables,Tablas.ESTCEP,estcep);
       function.execute(destination);

      Mensaje msj= new Mensaje();
        msj.setMensaje("Ok");

        return msj;
   }


}
