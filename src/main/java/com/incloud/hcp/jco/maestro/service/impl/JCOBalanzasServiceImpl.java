package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.BalanzaDto;
import com.incloud.hcp.jco.maestro.dto.UnidadMedidaDto;
import com.incloud.hcp.jco.maestro.service.JCOBalanzasService;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JCOBalanzasServiceImpl implements JCOBalanzasService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<UnidadMedidaDto> ListarUnidadMedida(String esreg) throws Exception {

        String ESREG;

        List<UnidadMedidaDto> ListaUmd=new ArrayList<UnidadMedidaDto>();

        logger.error("listaUnidadMedida_1");;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        //JCo
        logger.error("listaUnidadMedida_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("listaUnidadMedida_3");;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_READ_TABLE");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("QUERY_TABLE", "ZFLUMD");
        importx.setValue("DELIMITER", "|");
        importx.setValue("P_USER", "FGARCIA");


        logger.error("listaUnidadMedida_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        JCoTable tableImport = tables.getTable("OPTIONS");

        logger.error("listaUnidadMedida_5");;

        ESREG="ESREG "+esreg;
        tableImport.appendRow();
        tableImport.setValue("WA", ESREG);

        //Ejecutar Funcion
        stfcConnection.execute(destination);
        logger.error("listaUnidadMedida_6");
        //DestinationAcce

        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable("DATA");

        logger.error("listaUnidadMedida_7");

        String response;
        String[] ArrayResponse;


        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);

            logger.error("listaUnidadMedida_8");


            response = tableExport.getString();
            ArrayResponse = response.split("\\|");

            UnidadMedidaDto umd = new UnidadMedidaDto();

            umd.setCDUMD(ArrayResponse[1].trim());
            umd.setDSUMD(ArrayResponse[2].trim());
            //umd.setDSUMD(ArrayResponse[2].trim());




            ListaUmd.add(umd);
            //lista.add(param);
        }


        logger.error("listaUnidadMedida_9");

        return ListaUmd;

    }

    public List<BalanzaDto> ListarBalanzas()throws Exception{


        List<BalanzaDto> ListaBalanza= new ArrayList<BalanzaDto>();

        logger.error("listaBalanza_1");;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        //JCo
        logger.error("listaBalanza_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("listaBalanza_3");;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_READ_TABLE");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("QUERY_TABLE", "ZV_FLBA");
        importx.setValue("DELIMITER", "|");
        importx.setValue("P_USER", "FGARCIA");


        logger.error("listaBalanza_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        //JCoTable tableImport = tables.getTable("OPTIONS");
        //tableImport.appendRow();
        logger.error("listaBalanza_5");;


        //Ejecutar Funcion
        stfcConnection.execute(destination);
        logger.error("listaBalanza_6");
        //DestinationAcce

        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable("DATA");

        logger.error("listaBalanza_7");

        String response;
        String[] ArrayResponse;


        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);

            logger.error("listaBalanza_8");


            response=tableExport.getString();
            ArrayResponse= response.split("\\|");

            BalanzaDto balanza = new BalanzaDto();

            balanza.setCDBAL(ArrayResponse[1].trim());
             balanza.setDSBAL(ArrayResponse[2].trim());
            balanza.setCDPTA(ArrayResponse[3].trim());
            balanza.setDESCR(ArrayResponse[4].trim());
            balanza.setCDTBA(ArrayResponse[5].trim());
            balanza.setTABAL(ArrayResponse[6].trim());
            balanza.setCDUMD(ArrayResponse[7].trim());
            balanza.setNMSER(ArrayResponse[9].trim());




            ListaBalanza.add(balanza);
            //lista.add(param);
        }


        logger.error("listaBalanza_9");


        return ListaBalanza;
    }
}
