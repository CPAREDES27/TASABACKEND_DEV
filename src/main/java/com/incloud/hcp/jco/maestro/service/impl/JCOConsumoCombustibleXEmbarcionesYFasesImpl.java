package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.ZonaAreaDto;
import com.incloud.hcp.jco.maestro.service.JCOConsumoCombustibleXEmbarcionesYFasesServices;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JCOConsumoCombustibleXEmbarcionesYFasesImpl implements JCOConsumoCombustibleXEmbarcionesYFasesServices {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public List<ZonaAreaDto> ListarZonaArea()throws Exception{

       List<ZonaAreaDto> ListaZonaArea=new ArrayList<ZonaAreaDto>();

       logger.error("listaZonaArea_1");;
       JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
       //JCo
       logger.error("listaZonaArea_2");;
       JCoRepository repo = destination.getRepository();
       logger.error("listaZonaArea_3");;
       JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_READ_TABLE");
       JCoParameterList importx = stfcConnection.getImportParameterList();
       //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
       importx.setValue("QUERY_TABLE", "ZFLZAR");
       importx.setValue("DELIMITER", "|");
       importx.setValue("P_USER", "FGARCIA");


       logger.error("listaZonaArea_4");;
       JCoParameterList tables = stfcConnection.getTableParameterList();
       //JCoTable tableImport = tables.getTable("OPTIONS");

       logger.error("listaZonaArea_5");;


       //Ejecutar Funcion
       stfcConnection.execute(destination);
       logger.error("listaZonaArea_6");
       //DestinationAcce

       //Recuperar Datos de SAP

       JCoTable tableExport = tables.getTable("DATA");

       logger.error("listaZonaArea_7");

       String response;
       String[] ArrayResponse;


       for (int i = 0; i < tableExport.getNumRows(); i++) {
           tableExport.setRow(i);

           logger.error("listaZonaArea_8");


           response=tableExport.getString();
           ArrayResponse= response.split("\\|");

           ZonaAreaDto zonaArea = new ZonaAreaDto();
           zonaArea.setZCDZAR(ArrayResponse[1].trim());
           zonaArea.setZDSZAR(ArrayResponse[2].trim());

           ListaZonaArea.add(zonaArea);
       }


       logger.error("listaZonaArea_9");

       return ListaZonaArea;
   }

}
