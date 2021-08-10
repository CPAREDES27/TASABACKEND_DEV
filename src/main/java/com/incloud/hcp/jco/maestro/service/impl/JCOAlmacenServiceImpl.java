package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.RFCCompartido.ZFL_RFC_READ_TABLEImplement;
import com.incloud.hcp.jco.maestro.dto.AlmacenDto;
import com.incloud.hcp.jco.maestro.dto.AlmacenExtDto;
import com.incloud.hcp.jco.maestro.dto.PlantaDto;
import com.incloud.hcp.jco.maestro.service.JCOAlmacenService;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JCOAlmacenServiceImpl implements JCOAlmacenService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ZFL_RFC_READ_TABLEImplement zfl_rfc_read_tableImplement;

    @Override
    public List<PlantaDto> BuscarPlantas(PlantaDto plantaDto) throws Exception {

        String  ESREG, WERKS, CDPTA, DESCR, STCD1, NAME1, CDPTO, DSPTO, INPRP;

        //JCoTable tableExport= zfl_rfc_read_tableImplement.BuscarData(cond);
        logger.error("listaBalanza_1");;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        //JCo
        logger.error("listaBalanza_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("listaBalanza_3");;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_READ_TABLE");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("QUERY_TABLE", "ZV_FLPL");
        importx.setValue("DELIMITER", "|");
        importx.setValue("P_USER", "FGARCIA");


        logger.error("listaBalanza_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        JCoTable tableImport = tables.getTable("OPTIONS");

        logger.error("listaBalanza_5");;

        ESREG="ESREG " + plantaDto.getESREG();
        WERKS="WERKS " + plantaDto.getWERKS();
        CDPTA="CDPTA " + plantaDto.getCDPTA();
        DESCR="DESCR " + plantaDto.getDESCR();
        STCD1="STCD1 " + plantaDto.getSTCD1();
        NAME1="NAME1 " + plantaDto.getNAME1();
        CDPTO="CDPTO " + plantaDto.getCDPTO();
        DSPTO="DSPTO " + plantaDto.getDSPTO();
        INPRP="INPRP " + plantaDto.getINPRP();

        if(plantaDto.getESREG()!=null){
            tableImport.appendRow();
            tableImport.setValue("WA",ESREG);
        } if(plantaDto.getWERKS()!=null){
            tableImport.appendRow();
            tableImport.setValue("WA",WERKS);
        } if(plantaDto.getCDPTA()!=null){
            tableImport.appendRow();
            tableImport.setValue("WA",CDPTA);
        } if(plantaDto.getDESCR()!=null){
            tableImport.appendRow();
            tableImport.setValue("WA",DESCR);
        }if(plantaDto.getCDPTA()!=null){
            tableImport.appendRow();
            tableImport.setValue("WA",ESREG);
        }if(plantaDto.getSTCD1()!=null){
            tableImport.appendRow();
            tableImport.setValue("WA",STCD1);
        }if(plantaDto.getCDPTA()!=null){
            tableImport.appendRow();
            tableImport.setValue("WA",ESREG);
        }if(plantaDto.getNAME1()!=null){
            tableImport.appendRow();
            tableImport.setValue("WA",NAME1);
        }if(plantaDto.getCDPTO()!=null){
            tableImport.appendRow();
            tableImport.setValue("WA",CDPTO);
        }if(plantaDto.getDSPTO()!=null){
            tableImport.appendRow();
            tableImport.setValue("WA",DSPTO);
        }if(plantaDto.getINPRP()!=null){
            tableImport.appendRow();
            tableImport.setValue("WA",INPRP);
        }

        //Ejecutar Funcion
        stfcConnection.execute(destination);
        logger.error("listaBalanza_6");
        //DestinationAcce

        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable("DATA");

        logger.error("listaBalanza_7");


        List<PlantaDto> ListaPlantas=new ArrayList<PlantaDto>();

        String response;
        String[] ArrayResponse;

        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);
            logger.error("listaPlanta_8");

            PlantaDto planta = new PlantaDto();
            response=tableExport.getString();
            ArrayResponse=response.split("\\|");

            planta.setCDPTA(tableExport.getString());/*
            planta.setCDPTA(ArrayResponse[2].trim());
            planta.setWERKS(ArrayResponse[2].trim());
            planta.setCDEMP(ArrayResponse[3].trim());
            planta.setCDPTO(ArrayResponse[4].trim());
            planta.setNAME1(ArrayResponse[5].trim());
            planta.setSTCD1(ArrayResponse[6].trim());
            planta.setDESCR(ArrayResponse[7].trim());
            planta.setINPRP(ArrayResponse[8].trim());*/

            ListaPlantas.add(planta);
            //lista.add(param);
        }


        return ListaPlantas;
    }

    @Override
    public List<AlmacenExtDto> BuscarAlmacenExterno(AlmacenExtDto almacenExtDto) throws Exception {

        String WERKS, LGORT,LGOBE;

        List<AlmacenExtDto> ListaAlmacenExt=new ArrayList<AlmacenExtDto>();

        logger.error("listaAlmacenExt_1");;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        //JCo
        logger.error("listaAlmacenExt_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("listaAlmacenExt_3");;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_READ_TABLE");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("QUERY_TABLE", "T001L");
        importx.setValue("DELIMITER", "|");
        importx.setValue("P_USER", "FGARCIA");


        logger.error("listaAlmacenExt_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        JCoTable tableImport = tables.getTable("OPTIONS");
        tableImport.appendRow();
        logger.error("listaAlmacenExt_5");;

        if(almacenExtDto.getWERKS()!=null){
            WERKS="WERKS "+almacenExtDto.getWERKS();
            tableImport.setValue("WA", WERKS);
        }

         if(almacenExtDto.getLGORT()!=null){
            tableImport.appendRow();
            LGORT="LGORT "+almacenExtDto.getWERKS();
            tableImport.setValue("WA", LGORT);
        }

        if(almacenExtDto.getLGOBE()!=null){
            tableImport.appendRow();
            LGOBE="LGOBE "+almacenExtDto.getWERKS();
            tableImport.setValue("WA", LGOBE);
        }



        //Ejecutar Funcion
        stfcConnection.execute(destination);
        logger.error("listaAlmacenExt_6");
        //DestinationAcce

        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable("DATA");

        logger.error("listaAlmacenExt_7");
        String response;
        String[] ArrayResponse;


        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);

            logger.error("listaAlmacenExt_8");


            response=tableExport.getString();
            ArrayResponse= response.split("\\|");

            AlmacenExtDto almacen = new AlmacenExtDto();
            almacen.setWERKS(ArrayResponse[1].trim());
            almacen.setLGOBE(ArrayResponse[2].trim());
            almacen.setLGORT(ArrayResponse[3].trim());

            ListaAlmacenExt.add(almacen);
            //lista.add(param);
        }


        logger.error("listaAlmacenExt_9");

        return ListaAlmacenExt;

    }

    public List<AlmacenDto> BuscarAlmacen(AlmacenDto almacenDto) throws Exception {

        String CDALM, CDPTA, DSALM, ESREG;

        List<AlmacenDto> ListaAlmacen=new ArrayList<AlmacenDto>();

        logger.error("listaAlmacen_1");;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        //JCo
        logger.error("listaAlmacen_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("listaAlmacen_3");;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_READ_TABLE");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("QUERY_TABLE", "ZV_FLAL");
        importx.setValue("DELIMITER", "|");
        importx.setValue("P_USER", "FGARCIA");


        logger.error("listaAlmacen_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        JCoTable tableImport = tables.getTable("OPTIONS");
        tableImport.appendRow();
        logger.error("listaAlmacen_5");;
        logger.error(almacenDto.getCDPTA());;
        logger.error(almacenDto.getCDALM());;
        logger.error(almacenDto.getDSALM());;
        logger.error(almacenDto.getESREG());;


            if (almacenDto.getCDPTA() != null) {
                CDPTA = "CDPTA " + almacenDto.getCDPTA();
                tableImport.setValue("WA", CDPTA);
            }
            if (almacenDto.getCDALM() != null) {
                tableImport.appendRow();
                CDALM = "CDALM " + almacenDto.getCDALM();
                tableImport.setValue("WA", CDALM);
            }
            if (almacenDto.getDSALM() != null) {
                tableImport.appendRow();
                DSALM = "DSALM " + almacenDto.getDSALM();
                tableImport.setValue("WA", DSALM);
            }
            if (almacenDto.getESREG() != null) {
                tableImport.appendRow();
                ESREG = "ESREG " + almacenDto.getESREG();
                tableImport.setValue("WA", ESREG);
            }

        //Ejecutar Funcion
        stfcConnection.execute(destination);
        logger.error("listaAlmacen_6");
        //DestinationAcce

        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable("DATA");

        logger.error("listaAlmacen_7");

        String response;
        String[] ArrayResponse;


        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);

            logger.error("listaAlmacen_8");


            response=tableExport.getString();
            ArrayResponse= response.split("\\|");

            AlmacenDto almacen = new AlmacenDto();
            almacen.setCDALM(ArrayResponse[1].trim());
            almacen.setCDPTA(ArrayResponse[2].trim());
            almacen.setDSALM(ArrayResponse[3].trim());
            almacen.setWERKS(ArrayResponse[4].trim());
            almacen.setNEWKO(ArrayResponse[5].trim());
            almacen.setCDALE(ArrayResponse[7].trim());
            almacen.setESREG(ArrayResponse[6].trim());

            ListaAlmacen.add(almacen);
            //lista.add(param);
        }


        logger.error("listaAlmacen_9");

        return ListaAlmacen;





    }

}

