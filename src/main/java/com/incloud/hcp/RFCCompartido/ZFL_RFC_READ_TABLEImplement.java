package com.incloud.hcp.RFCCompartido;

import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
    public class ZFL_RFC_READ_TABLEImplement {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public JCoTable BuscarData(String cond)throws Exception{

        logger.error("Paso la condicion: "+cond);
        logger.error("Paso");

        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        //JCo
        logger.error("_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("ZFL_RFC_READ_TABLE_Buscar_1");;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_READ_TABLE");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("QUERY_TABLE", "ZV_FLPL");
        importx.setValue("DELIMITER", "|");
        importx.setValue("P_USER", "FGARCIA");


        logger.error("ZFL_RFC_READ_TABLE_Buscar_2");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        JCoTable tableImport = tables.getTable("OPTIONS");
        tableImport.appendRow();
        logger.error("ZFL_RFC_READ_TABLE_Buscar_3");;

        tableImport.setValue("WA", "ESREG = '"+cond+"'");


        //Ejecutar Funcion
        stfcConnection.execute(destination);
        logger.error("ZFL_RFC_READ_TABLE_Buscar_4");
        //DestinationAcce

        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable("DATA");
        logger.error("ZFL_RFC_READ_TABLE_Buscar_5");
        return tableExport;
    }
}
