package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.AlmacenExtDto;
import com.incloud.hcp.jco.maestro.dto.PlantaDto;
import com.incloud.hcp.jco.maestro.service.JCOAlmacenService;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JCOAlmacenServiceImpl implements JCOAlmacenService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<PlantaDto> BuscarPlantas(PlantaDto plantaDto) throws Exception {

        /*
        String ESREG="ESREG LIKE '%"+plantaDto.getEstadoReg()+"%'";
        String CDPTA="CDPTA LIKE '%"+plantaDto.getCodPlanta()+"%'";
        String WERKS="WERKS LIKE '%"+plantaDto.getCentro()+"%'";
        String DESCR="DESCR LIKE '%"+plantaDto.getDescripcionPlanta()+"%'";
        String STCD1="STCD1 LIKE '%"+plantaDto.getRucEmp()+"%'";
        String NAME1="NAME1 LIKE '%"+plantaDto.getDescripcionEmp()+"%'";
        String CDPTO="CDPTO LIKE '%"+plantaDto.getCodigoPuerto()+"%'";
        String DSPTO="DSPTO LIKE '%"+plantaDto.getDescripcionPuerto()+"%'";
        String INPRP="INPRP LIKE '%"+plantaDto.getIndPropiedad()+"%'";}*/

        String ESREG="ESREG = '"+plantaDto.getEstadoReg()+"'";

        List<PlantaDto> ListaPlantas=new ArrayList<PlantaDto>();

        logger.error("listaPlanta_1");;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        //JCo
        logger.error("listaPlanta_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("listaPlanta_3");;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_READ_TABLE");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("QUERY_TABLE", "ZV_FLPL");
        importx.setValue("DELIMITER", "|");
        importx.setValue("P_USER", "FGARCIA");


        logger.error("listaPlanta_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        JCoTable tableImport = tables.getTable("OPTIONS");
        tableImport.appendRow();
        logger.error("listaPlanta_5");;

        tableImport.setValue("WA", ESREG);
        /*
        if(plantaDto.getEstadoReg()!=null) {
            tableImport.appendRow();
            tableImport.setValue("WA", ESREG);
        }
        if(plantaDto.getCodPlanta()!=null){
            tableImport.appendRow();
            tableImport.setValue("WA", CDPTA);
        }*/


        //Ejecutar Funcion
        stfcConnection.execute(destination);
        logger.error("listaPlanta_6");
        //DestinationAcce

        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable("DATA");
        logger.error("listaPlanta_7");
        String response;
        String[] ArrayResponse;

        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);
            logger.error("listaPlanta_8");

            PlantaDto planta = new PlantaDto();
            response=tableExport.getString();
            ArrayResponse=response.split("|");

            planta.setCodPlanta(ArrayResponse[1].trim());
            planta.setCodPlanta(ArrayResponse[2].trim());
            planta.setCodigoEmpresa(ArrayResponse[3].trim());
            planta.setCodigoPuerto(ArrayResponse[4].trim());
            planta.setDescripcionEmp(ArrayResponse[5].trim());
            planta.setRucEmp(ArrayResponse[6].trim());
            planta.setDescripcionPlanta(ArrayResponse[7].trim());
            planta.setIndPropiedad(ArrayResponse[8].trim());

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

        if(almacenExtDto.getCentro()=="*") {
            WERKS="WERKS LIKE '%'";
            tableImport.setValue("WA", WERKS);
        }else if(almacenExtDto.getCentro()!=null){
            WERKS="WERKS = '"+almacenExtDto.getCentro()+"'";
            tableImport.setValue("WA", WERKS);
        }

        if(almacenExtDto.getNombreAlmacen()=="*") {
            tableImport.appendRow();
            LGORT="LGORT LIKE '%'";
            tableImport.setValue("WA", LGORT);
        }else if(almacenExtDto.getCentro()!=null){
            tableImport.appendRow();
            LGORT="LGORT = '"+almacenExtDto.getCentro()+"'";
            tableImport.setValue("WA", LGORT);
        }

        if(almacenExtDto.getDenominacionAlmacen()=="*") {
            tableImport.appendRow();
            LGOBE="LGOBE LIKE '%'";
            tableImport.setValue("WA", LGOBE);
        }else if(almacenExtDto.getCentro()!=null){
            tableImport.appendRow();
            LGOBE="LGOBE = '"+almacenExtDto.getCentro()+"'";
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

            AlmacenExtDto almacen = new AlmacenExtDto();
            response=tableExport.getString();
            ArrayResponse=response.split("|");

            almacenExtDto.setCentro(ArrayResponse[0].trim());
            almacenExtDto.setDenominacionAlmacen(ArrayResponse[1].trim());
            almacenExtDto.setNombreAlmacen(ArrayResponse[2].trim());
            ListaAlmacenExt.add(almacen);
            //lista.add(param);
        }
        logger.error("listaAlmacenExt_9");

        return ListaAlmacenExt;

        



    }
}

