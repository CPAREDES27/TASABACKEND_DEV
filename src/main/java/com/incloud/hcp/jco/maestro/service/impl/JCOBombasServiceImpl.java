package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.BombasDto;
import com.incloud.hcp.jco.maestro.service.JCOBombasService;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JCOBombasServiceImpl implements JCOBombasService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public List<BombasDto> ListarBombas() throws Exception {

        List<BombasDto> ListaBombas= new ArrayList<BombasDto>();
        logger.error("listaBombas_1");;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        //JCo
        logger.error("listaBombas_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("listaBombas_3");;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_READ_TABLE");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("QUERY_TABLE", "ZFLBOM");
        importx.setValue("DELIMITER", "|");
        importx.setValue("P_USER", "FGARCIA");


        logger.error("listaBombas_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        //JCoTable tableImport = tables.getTable("OPTIONS");

        logger.error("listaBombas_5");;


        //Ejecutar Funcion
        stfcConnection.execute(destination);
        logger.error("listaBombas_6");
        //DestinationAcce

        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable("DATA");

        logger.error("listaBombas_7");

        String response;
        String[] ArrayResponse;


        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);

            logger.error("listaBombas_8");


            response=tableExport.getString();
            ArrayResponse= response.split("\\|");

            BombasDto bombas = new BombasDto();
            bombas.setCDBOM(ArrayResponse[1].trim());
            bombas.setDSBOM(ArrayResponse[2].trim());
            bombas.setESREG(ArrayResponse[3].trim());

            ListaBombas.add(bombas);
        }


        logger.error("listaBombas_9");

        return ListaBombas;
    }
}
