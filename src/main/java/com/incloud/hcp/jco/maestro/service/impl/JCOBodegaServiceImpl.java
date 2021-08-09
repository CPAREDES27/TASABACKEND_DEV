package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.BalanzaDto;
import com.incloud.hcp.jco.maestro.dto.BodegaDto;
import com.incloud.hcp.jco.maestro.service.JCOBodegaService;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JCOBodegaServiceImpl implements JCOBodegaService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

   public List<BodegaDto> BuscarBodegas(BodegaDto bodegaDto)throws Exception{

       String CDBOD, DSBOD,ESREG;
        List<BodegaDto> ListaBodegas=new ArrayList<BodegaDto>();

        logger.error("listaBodega_1");;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        //JCo
        logger.error("listaBodega_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("listaBodega_3");;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_READ_TABLE");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("QUERY_TABLE", "ZFLBOD");
        importx.setValue("DELIMITER", "|");
        importx.setValue("P_USER", "FGARCIA");


        logger.error("listaBodega_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        JCoTable tableImport = tables.getTable("OPTIONS");

        logger.error("listaBodega_5");;

       CDBOD="CDBOD " + bodegaDto.getCDBOD();
       DSBOD="DSBOD " +bodegaDto.getDSBOD();
       ESREG="ESREG " +bodegaDto.getESREG();

       logger.error(CDBOD);;
       logger.error(DSBOD);;
       logger.error(ESREG);;

       if(bodegaDto.getCDBOD()!=null) {
           tableImport.appendRow();
           tableImport.setValue("WA", CDBOD);
       }
       if(bodegaDto.getDSBOD()!=null) {
           tableImport.appendRow();
           tableImport.setValue("WA", DSBOD);
       }
       if(bodegaDto.getESREG()!=null) {
           tableImport.appendRow();
           tableImport.setValue("WA", ESREG);
       }

        //Ejecutar Funcion
        stfcConnection.execute(destination);
        logger.error("listaBodega_6");
        //DestinationAcce

        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable("DATA");

        logger.error("listaBodega_7");

        String response;
        String[] ArrayResponse;


        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);

            logger.error("listaBodega_8");


            response=tableExport.getString();
            ArrayResponse= response.split("\\|");

            BodegaDto bodega = new BodegaDto();
            bodega.setCDBOD(ArrayResponse[1].trim());
            bodega.setDSBOD(ArrayResponse[2].trim());
            bodega.setESREG(ArrayResponse[3].trim());

            ListaBodegas.add(bodega);
        }


        logger.error("listaBodega_9");

        return ListaBodegas;
    }


}
