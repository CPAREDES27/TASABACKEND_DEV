package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.CargosDto;
import com.incloud.hcp.jco.maestro.service.JCOCargosService;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JCOCargosImpl implements JCOCargosService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<CargosDto> ListarCargos() throws Exception {

        List<CargosDto> ListaCargos = new ArrayList<CargosDto>();

        logger.error("listaCargos_1");;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        //JCo
        logger.error("listaCargos_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("listaCargos_3");;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_READ_TABLE");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("QUERY_TABLE", "ZFLCRG");
        importx.setValue("DELIMITER", "|");
        importx.setValue("P_USER", "FGARCIA");


        logger.error("listaCargos_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        //JCoTable tableImport = tables.getTable("OPTIONS");

        logger.error("listaCargos_5");;


        //Ejecutar Funcion
        stfcConnection.execute(destination);
        logger.error("listaCargos_6");
        //DestinationAcce

        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable("DATA");

        logger.error("listaCargos_7");

        String response;
        String[] ArrayResponse;


        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);

            logger.error("listaCargos_8");


            response=tableExport.getString();
            ArrayResponse= response.split("\\|");

            CargosDto cargos = new CargosDto();
            cargos.setCDCRG(ArrayResponse[1].trim());
            cargos.setDSCRG(ArrayResponse[2].trim());
            cargos.setESREG(ArrayResponse[3].trim());

            ListaCargos.add(cargos);
        }


        logger.error("listaCargos_9");


        return ListaCargos;
    }
}
