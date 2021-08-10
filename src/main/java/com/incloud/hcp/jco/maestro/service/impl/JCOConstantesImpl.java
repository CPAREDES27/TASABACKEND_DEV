package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.ConstantesDto;
import com.incloud.hcp.jco.maestro.service.JCOConstantesSevice;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JCOConstantesImpl implements JCOConstantesSevice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<ConstantesDto> ListarConstantes()throws Exception{

        List<ConstantesDto> ListaConstantes= new ArrayList<ConstantesDto>();

        logger.error("listaConstantes_1");;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        //JCo
        logger.error("listaConstantes_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("listaConstantes_3");;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_READ_TABLE");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("QUERY_TABLE", "ZFLCNS");
        importx.setValue("DELIMITER", "|");
        importx.setValue("P_USER", "FGARCIA");


        logger.error("listaConstantes_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        //JCoTable tableImport = tables.getTable("OPTIONS");

        logger.error("listaConstantes_5");;


        //Ejecutar Funcion
        stfcConnection.execute(destination);
        logger.error("listaConstantes_6");
        //DestinationAcce

        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable("DATA");

        logger.error("listaConstantes_7");

        String response;
        String[] ArrayResponse;


        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);

            logger.error("listaConstantes_8");


            response=tableExport.getString();
            ArrayResponse= response.split("\\|");

            ConstantesDto constantes = new ConstantesDto();

            constantes.setCDCNS(ArrayResponse[1].trim());
            constantes.setDESCR(ArrayResponse[2].replaceAll(" ",""));
            constantes.setVAL01(ArrayResponse[3].trim());
            constantes.setVAL02(ArrayResponse[4].trim());
            constantes.setVAL03(ArrayResponse[5].trim());
            constantes.setVAL04(ArrayResponse[6].trim());


            ListaConstantes.add(constantes);
        }


        logger.error("listaConstantes_9");

        return ListaConstantes;

    }

}
