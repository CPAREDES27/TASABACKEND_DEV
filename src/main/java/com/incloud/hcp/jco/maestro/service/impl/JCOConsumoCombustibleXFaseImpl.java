package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.ConsumoCombustibleXFaseDto;
import com.incloud.hcp.jco.maestro.service.JCOConsumoCombustibleXFaseService;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JCOConsumoCombustibleXFaseImpl implements JCOConsumoCombustibleXFaseService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public List<ConsumoCombustibleXFaseDto> ListarConsumoxFase() throws Exception {

        List<ConsumoCombustibleXFaseDto> ListaConsumoxFase= new ArrayList<ConsumoCombustibleXFaseDto>();

        logger.error("listaConsumoxFase_1");;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        //JCo
        logger.error("listaConsumoxFase_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("listaConsumoxFase_3");;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_READ_TABLE");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("QUERY_TABLE", "ZFLCCF");
        importx.setValue("DELIMITER", "|");
        importx.setValue("P_USER", "FGARCIA");


        logger.error("listaConsumoxFase_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        //JCoTable tableImport = tables.getTable("OPTIONS");

        logger.error("listaConsumoxFase_5");;


        //Ejecutar Funcion
        stfcConnection.execute(destination);
        logger.error("listaConsumoxFase_6");
        //DestinationAcce

        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable("DATA");

        logger.error("listaConsumoxFase_7");

        String response;
        String[] ArrayResponse;


        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);

            logger.error("listaConsumoxFase_8");


            response=tableExport.getString();
            ArrayResponse= response.split("\\|");

            ConsumoCombustibleXFaseDto ccxf = new ConsumoCombustibleXFaseDto();
            ccxf.setCDFAS(ArrayResponse[1].trim());
            ccxf.setCSTEO(ArrayResponse[2].trim());
            ccxf.setCSMAX(ArrayResponse[3].trim());

            ListaConsumoxFase.add(ccxf);
        }


        logger.error("listaConsumoxFase_9");


        return ListaConsumoxFase;


    }
}
