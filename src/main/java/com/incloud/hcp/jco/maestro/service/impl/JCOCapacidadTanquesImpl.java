package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.CapacidadTanquesDto;
import com.incloud.hcp.jco.maestro.service.JCOCapacidadTanquesService;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JCOCapacidadTanquesImpl implements JCOCapacidadTanquesService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<CapacidadTanquesDto> BuscarCapacidadTanques(String tipoEmbarcacion, String nombreEmbarcacion)throws Exception{

        String CDTEM, NMEMB;
        List<CapacidadTanquesDto> ListaCapTan= new ArrayList<CapacidadTanquesDto>();

        logger.error("listaCapacidadTanques_1");;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        //JCo
        logger.error("listaCapacidadTanques_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("listaCapacidadTanques_3");;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_READ_TABLE");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("QUERY_TABLE", "ZV_FLTE");
        importx.setValue("DELIMITER", "|");
        importx.setValue("P_USER", "FGARCIA");


        logger.error("listaCapacidadTanques_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        JCoTable tableImport = tables.getTable("OPTIONS");

        logger.error("listaCapacidadTanques_5");;

        CDTEM="CDTEM " + tipoEmbarcacion;
        NMEMB ="DSBOD " + nombreEmbarcacion;


        logger.error(CDTEM);;
        logger.error(NMEMB);;

        if(tipoEmbarcacion!=null) {
            tableImport.appendRow();
            tableImport.setValue("WA", CDTEM);
        }
        if(nombreEmbarcacion!=null) {
            tableImport.appendRow();
            tableImport.setValue("WA", NMEMB);
        }


        //Ejecutar Funcion
        stfcConnection.execute(destination);
        logger.error("listaCapacidadTanques_6");
        //DestinationAcce

        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable("DATA");

        logger.error("listaCapacidadTanques_7");

        String response;
        String[] ArrayResponse;


        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);

            logger.error("listaCapacidadTanques_8");


            response=tableExport.getString();
            ArrayResponse= response.split("\\|");

            CapacidadTanquesDto captan = new CapacidadTanquesDto();

            captan.setCDEMB(ArrayResponse[1].trim());
            captan.setNMEMB(ArrayResponse[4].trim());
            captan.setWERKS(ArrayResponse[3].trim());
            captan.setMREMB(ArrayResponse[2].trim());
            captan.setCDTEM(ArrayResponse[5].trim());
            captan.setDESCR(ArrayResponse[6].trim());
            captan.setCDTAN(ArrayResponse[13].trim());

            ListaCapTan.add(captan);
        }


        logger.error("listaCapacidadTanques_9");

        return ListaCapTan;
    }

}
