package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.EventosPescaDto;
import com.incloud.hcp.jco.maestro.dto.EventosPescaImports;
import com.incloud.hcp.jco.maestro.dto.ST_CCPDto;
import com.incloud.hcp.jco.maestro.dto.ST_CEPDto;
import com.incloud.hcp.jco.maestro.service.JCOEventosPescaService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JCOEventosPescaImpl implements JCOEventosPescaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public EventosPescaDto ListarEventosPesca(EventosPescaImports imports)throws Exception{

       logger.error("ListarEventosPesca_1");;
       JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
       //JCo
       logger.error("ListarEventosPesca_2");;
       JCoRepository repo = destination.getRepository();
       logger.error("ListarEventosPesca_3");;
       JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_MAES_EVEN_PES);
       JCoParameterList importx = stfcConnection.getImportParameterList();
       importx.setValue("I_FLAG", imports.getI_flag());
       importx.setValue("P_USER", imports.getP_user());
       logger.error("ListarEventosPesca_4");;
       JCoParameterList tables = stfcConnection.getTableParameterList();
       ;
       //Ejecutar Funcion
       stfcConnection.execute(destination);
       logger.error("ListarEventosPesca_5");

       JCoTable tableST_CCP = tables.getTable(Tablas.ST_CCP);

        List<ST_CCPDto> ListST_CCDto = new ArrayList<ST_CCPDto>();

       for (int i = 0; i < tableST_CCP.getNumRows(); i++) {
           tableST_CCP.setRow(i);
           ST_CCPDto st_ccpDto= new ST_CCPDto();

           st_ccpDto.setCLSIS(tableST_CCP.getString("CLSIS"));
           st_ccpDto.setCDTEV(tableST_CCP.getString("CDTEV"));
           st_ccpDto.setBWART(tableST_CCP.getString("BWART"));
           st_ccpDto.setMATNR(tableST_CCP.getString("MATNR"));
           st_ccpDto.setUNICB(tableST_CCP.getString("UNICB"));
           st_ccpDto.setATLHO(tableST_CCP.getString("ATLHO"));
           st_ccpDto.setMAKTX(tableST_CCP.getString("MAKTX"));

           ListST_CCDto.add(st_ccpDto);
           //lista.add(param);
       }

        logger.error("ListarEventosPesca_6");
       JCoTable tableST_CEP = tables.getTable(Tablas.ST_CEP);
        ST_CEPDto st_cepDto= llenarSTCEP(tableST_CEP);

        logger.error("ListarEventosPesca_7");
       EventosPescaDto dto= new EventosPescaDto();
       dto.setLista_st_cpp(ListST_CCDto);
       dto.setSt_cep(st_cepDto);
       dto.setMensaje("Ok");

        logger.error("ListarEventosPesca_8");
       return dto;
   }

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
}
