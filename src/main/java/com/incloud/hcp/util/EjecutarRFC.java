package com.incloud.hcp.util;

import com.incloud.hcp.jco.maestro.dto.*;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class EjecutarRFC {

    private JCoDestination destination;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public EjecutarRFC(){
    }

    public MaestroExport Execute_ZFL_RFC_READ_TABLE(HashMap<String, Object> imports, List<HashMap<String, Object>> optionsParam) throws Exception{
        JCoFunction function = getFunction(Constantes.ZFL_RFC_READ_TABLE);
        setImports(function, imports);
        logger.error("Execute_ZFL_RFC_READ_TABLE_1");;
        JCoParameterList jcoTables = function.getTableParameterList();
        setTable(jcoTables, "OPTIONS", optionsParam);
        function.execute(destination);
        logger.error("Execute_ZFL_RFC_READ_TABLE_2");;


        JCoTable DATA = jcoTables.getTable("DATA");
        JCoTable FIELDS = jcoTables.getTable("FIELDS");
        //JCoTable T_MENSAJE = jcoTables.getTable("FIELDS");
        //T_MENSAJE.setRow(0);
        String mensaje = "Ok";
        logger.error("Execute_ZFL_RFC_READ_TABLE_3");;

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < DATA.getNumRows(); i++) {
            DATA.setRow(i);
            String ArrayResponse[] = DATA.getString().split("\\|");
            HashMap<String, Object> newRecord = new HashMap<String, Object>();
            for (int j = 0; j < FIELDS.getNumRows(); j++){
                FIELDS.setRow(j);
                String key = (String) FIELDS.getValue("FIELDNAME");
                Object value = ArrayResponse[j].trim();
                newRecord.put(key, value);
            };
            data.add(newRecord);
        }
        logger.error("Execute_ZFL_RFC_READ_TABLE_4");;



        MaestroExport me = new MaestroExport();
        me.setData(data);
        me.setMensaje(mensaje);
        return me;
    }

    public void setImports(JCoFunction function, HashMap<String, Object> imports){

        JCoParameterList jcoImport = function.getImportParameterList();
        Iterator iterator = imports.entrySet().iterator();
        logger.error("setImports_1");
        while (iterator.hasNext()) {
            Map.Entry tmpImport = (Map.Entry) iterator.next();
            String key = tmpImport.getKey().toString();
            Object value = tmpImport.getValue();
            jcoImport.setValue(key, value);
        }
        logger.error("setImports_2");
    }

    public void setTable(JCoParameterList listTables, String tableName, List<HashMap<String, Object>> data){

        logger.error("Lista de parametros: " +listTables);
        JCoTable tableImport = listTables.getTable(tableName);
        logger.error("setTable_1");
        for (int i = 0; i < data.size(); i++){
            tableImport.appendRow();
            HashMap<String, Object> record = data.get(i);
            Iterator iterator = record.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry tmpImport = (Map.Entry) iterator.next();
                String key = tmpImport.getKey().toString();
                Object value = tmpImport.getValue();
                tableImport.setValue(key, value);

            }
        }
        logger.error("setTable_2");

    }

    public JCoFunction getFunction (String FunctionName) throws Exception{
        destination = getDestination();
        JCoRepository repo = destination.getRepository();
        logger.error("getFunction_1");
        return repo.getFunction(FunctionName);
    }

    public JCoDestination getDestination() throws Exception{
        return JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
    }



    public Mensaje Exec_ZFL_RFC_ACT_CAMP_TAB(STR_SETDto str_setDto, String Tabla)throws Exception{

        logger.error("EditarCaptanques_1");;
        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        //JCo
        logger.error("EditarCaptanques_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("EditarCaptanques_3");;
        JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_ACT_CAMP_TAB);
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("P_USER", str_setDto.getP_USER());
        logger.error("EditarCaptanques_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        JCoTable tableImport = tables.getTable(Tablas.STR_SET);
        tableImport.appendRow();
        logger.error("EditarCaptanques_5");;
        tableImport.setValue("NMTAB", Tabla);
        tableImport.setValue("CMSET", str_setDto.getCMSET());
        tableImport.setValue("CMOPT", str_setDto.getCMOPT());
        //Ejecutar Funcion
        logger.error("NMTAB " +  Tabla);
        logger.error("CMSET", str_setDto.getCMSET());
        logger.error("CMOPT", str_setDto.getCMOPT());
        stfcConnection.execute(destination);
        logger.error("EditarCaptanques_6");

        Mensaje msj= new Mensaje();
        msj.setMensaje("Ok");

        //Recuperar Datos de SAP
        /*
        JCoTable tableExport = tables.getTable(Tablas.T_MENSAJE);
        tableExport.setRow(0);
        mensaje=tableExport.getString("DSMIN");

        if(mensaje==""){
            mensaje="Ok";
        }*/


        return msj;
    }

    public MensajeDto Execute_ZFL_RFC_UPDATE_TABLE(HashMap<String, Object> imports, String data) throws Exception{

        JCoFunction function = getFunction(Constantes.ZFL_RFC_UPDATE_TABLE);
        logger.error("Execute_ZFL_RFC_UPDATE_TABLE_1");
        setImports(function, imports);
        logger.error("Execute_ZFL_RFC_UPDATE_TABLE_2");
        JCoParameterList jcoTables = function.getTableParameterList();
        JCoTable tableImport = jcoTables.getTable("T_DATA");
        tableImport.appendRow();
        tableImport.setValue("DATA", data);
        logger.error("Execute_ZFL_RFC_UPDATE_TABLE_3");
        function.execute(destination);
        JCoTable tableExport = jcoTables.getTable("T_MENSAJE");

        MensajeDto dto = new MensajeDto();
        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);


            dto.setMANDT(tableExport.getString("MANDT"));
            dto.setCMIN(tableExport.getString("CMIN"));
            dto.setCDMIN(tableExport.getString("CDMIN"));
            dto.setDSMIN(tableExport.getString("DSMIN"));

        }
        logger.error("Execute_ZFL_RFC_UPDATE_TABLE_4");


        return dto;
    }


}
