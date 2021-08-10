package com.incloud.hcp.jco.maestro.service.RFCCompartidos;

import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ZFL_RFC_READ_TEABLEImplement {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public JCoTable Buscar() throws Exception{

        logger.error("TIPO_1111");;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        //JCo
        logger.error("TIPO_12");;
        JCoRepository repo = destination.getRepository();
        logger.error("TIPO_3");;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_READ_TABLE");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("P_USER", "FGARCIA");
        importx.setValue("QUERY_TABLE","ZFLTEM");
        importx.setValue("DELIMITER","|");
        logger.error("TIPO_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        JCoTable tableImport = tables.getTable("OPTIONS");
        tableImport.appendRow();
        logger.error("TIPO_5");;
        tableImport.setValue("WA", "ESREG = 'S'");
        //Ejecutar Funcion
        stfcConnection.execute(destination);
        logger.error("TIPO_6");
        //DestinationAcce

        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable("DATA");
        return tableExport;
    }
}
