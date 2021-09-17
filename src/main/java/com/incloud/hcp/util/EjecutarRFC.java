package com.incloud.hcp.util;

import com.incloud.hcp.jco.maestro.dto.*;
import com.sap.conn.jco.*;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.text.SimpleDateFormat;
import java.util.*;

public class EjecutarRFC {

    private JCoDestination destination;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public EjecutarRFC(){
    }

    public MaestroExport Execute_ZFL_RFC_READ_TABLE(HashMap<String, Object> imports, List<HashMap<String, Object>> optionsParam, String[] fields) throws Exception{
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
        data=ObtenerListObj(DATA, FIELDS, fields);
        //data=ObtenerListObjetos(DATA, fields);

        logger.error("Execute_ZFL_RFC_READ_TABLE_4");;


        MaestroExport me = new MaestroExport();
        me.setData(data);
        me.setMensaje(mensaje);
        return me;
    }

    public MaestroExport Execute_ZFL_RFC_READ_TABLE2(HashMap<String, Object> imports, List<HashMap<String, Object>> optionsParam, String[] fields) throws Exception{
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
        data=ObtenerListObj(DATA, FIELDS, fields);
        //data=ObtenerListObjetos(DATA, fields);

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

    public MensajeDto Execute_ZFL_RFC_UPDATE_TABLE(HashMap<String, Object> imports, String data) throws Exception{

        JCoFunction function = getFunction(Constantes.ZFL_RFC_UPDATE_TABLE);
        logger.error("Execute_ZFL_RFC_UPDATE_TABLE_1");
        setImports(function, imports);
        logger.error("Execute_ZFL_RFC_UPDATE_TABLE_2");
        JCoParameterList jcoTables = function.getTableParameterList();
        JCoTable tableImport = jcoTables.getTable(Tablas.T_DATA);
        tableImport.appendRow();
        tableImport.setValue("DATA", data);
        logger.error("Execute_ZFL_RFC_UPDATE_TABLE_3");
        function.execute(destination);
        JCoTable tableExport = jcoTables.getTable(Tablas.T_MENSAJE);

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

    /*
    public List<HashMap<String, Object>> ObtenerListObj(JCoTable jcoTable, JCoTable jcoFields, String[] fields)throws Exception{

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

        if(fields.length>=1) {

            for (int i = 0; i < jcoTable.getNumRows(); i++) {
                jcoTable.setRow(i);
                logger.error("jcoTable.getNumRows:  "+jcoTable.getNumRows());;
                logger.error("jcotable.getString: "+jcoTable.getString());;

                String ArrayResponse[] = jcoTable.getString().split("\\|");
                String x=jcoTable.getString();
                int con=0;

                logger.error("contador: "+con);
                HashMap<String, Object> newRecord = new HashMap<String, Object>();
                for(int j=0; j<=jcoFields.getNumRows(); j++){

                    jcoFields.setRow(j);
                    String key = (String) jcoFields.getValue("FIELDNAME");
                    Object value="";
                    if(j<=ArrayResponse.length){
                        value = ArrayResponse[j].trim();
                    }
                    else{
                        value="";
                    }
                    for (int k = 0; k < fields.length; k++) {


                        if (fields[k].trim().equals(key.trim())) {

                            if (field.getTypeAsString().equals("TIME")) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                                value = dateFormat.format(value);
                            }
                            if (key.equals("FEMAR") || key.equals("FITVS") || key.equals("FCVVI") || key.equals("FFTVS")) {

                                String date = String.valueOf(value);
                                SimpleDateFormat dia = new SimpleDateFormat("dd/MM/yyyy");
                                String fecha = dia.format(value);
                                value = fecha;
                            }
                            newRecord.put(key, value);

                        }
                    }
                }

                data.add(newRecord);
            }
        }else {
            for (int i = 0; i < jcoTable.getNumRows(); i++) {
                jcoTable.setRow(i);
                logger.error("jcoTable.getNumRows:  "+jcoTable.getNumRows());;
                logger.error("jcotable.getString: "+jcoTable.getString());;

                String ArrayResponse[] = jcoTable.getString().split("\\|");
                String x=jcoTable.getString();
                int con=15;
                logger.error("contador: "+con);
                logger.error("numero de fields: "+ jcoFields.getNumRows());
                logger.error("numero de strings: "+ ArrayResponse.length);
                 HashMap<String, Object> newRecord = new HashMap<String, Object>();
                 for(int j=0; j<jcoFields.getNumRows(); j++){

                        jcoFields.setRow(j);
                        String key = (String) jcoFields.getValue("FIELDNAME");
                        Object value;
                        if(j<=ArrayResponse.length){
                             value = ArrayResponse[j].trim();
                        }
                        else{
                            value="";
                        }
                        newRecord.put(key, value);
                    }

                data.add(newRecord);
            }
        }



        return data;
    }*/
    public List<HashMap<String, Object>>    ObtenerListObj(JCoTable jcoTable, JCoTable jcoFields, String[] fields)throws Exception{

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();


        if(fields.length>=1) {
            for (int i = 0; i < jcoTable.getNumRows(); i++) {
                jcoTable.setRow(i);
                String ArrayResponse[] = jcoTable.getString().split("\\|");
                HashMap<String, Object> newRecord = new HashMap<String, Object>();
                for (int j = 0; j < jcoFields.getNumRows(); j++) {
                    jcoFields.setRow(j);
                    String key = (String) jcoFields.getValue("FIELDNAME");
                        Object   value="";
                    try{
                        value = ArrayResponse[j].trim();
                    }catch (Exception e){
                        value="";

                    }
                    for (int k = 0; k < fields.length; k++) {
                        logger.error("key: " + key + " k: " + fields[k]);
                        ;

                        if (fields[k].trim().equals(key.trim())) {

                           /* if (field.getTypeAsString().equals("TIME")) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                                value = dateFormat.format(value);
                            }*/
                            if (key.equals("FEMAR") || key.equals("FITVS") || key.equals("FCVVI") || key.equals("FFTVS")) {

                                String date = String.valueOf(value);
                                SimpleDateFormat dia = new SimpleDateFormat("dd/MM/yyyy");
                                String fecha = dia.format(value);
                                value = fecha;
                            }
                            newRecord.put(key, value);

                        }
                    }


                }
                ;
                data.add(newRecord);
            }
        }else {
            for (int i = 0; i < jcoTable.getNumRows(); i++) {
                jcoTable.setRow(i);
                String ArrayResponse[] = jcoTable.getString().split("\\|");
                HashMap<String, Object> newRecord = new HashMap<String, Object>();
                for (int j = 0; j < jcoFields.getNumRows(); j++) {
                    jcoFields.setRow(j);
                    String key = (String) jcoFields.getValue("FIELDNAME");
                    String contador ="00"+j+"_";
                    String key2 = contador +key;
                    Object value = "";
                    try{
                        value = ArrayResponse[j].trim();
                    }catch (Exception e){
                        value="";

                    }

                    newRecord.put(key2, value);


                }
                ;
                data.add(newRecord);
            }
        }



        return data;
    }

    public List<HashMap<String, Object>> ObtenerListObj2(JCoTable jcoTable, JCoTable jcoFields, String[] fields)throws Exception{

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();


        if(fields.length>=1) {
            for (int i = 0; i < jcoTable.getNumRows(); i++) {
                jcoTable.setRow(i);
                String ArrayResponse[] = jcoTable.getString().split("\\|");
                HashMap<String, Object> newRecord = new HashMap<String, Object>();
                for (int j = 0; j < jcoFields.getNumRows(); j++) {
                    jcoFields.setRow(j);
                    String key = (String) jcoFields.getValue("FIELDNAME");
                    Object   value="";
                    try{
                        value = ArrayResponse[j].trim();
                    }catch (Exception e){
                        value="";

                    }
                    for (int k = 0; k < fields.length; k++) {
                        logger.error("key: " + key + " k: " + fields[k]);
                        ;

                        if (fields[k].trim().equals(key.trim())) {

                           /* if (field.getTypeAsString().equals("TIME")) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                                value = dateFormat.format(value);
                            }*/
                            if (key.equals("FEMAR") || key.equals("FITVS") || key.equals("FCVVI") || key.equals("FFTVS")) {

                                String date = String.valueOf(value);
                                SimpleDateFormat dia = new SimpleDateFormat("dd/MM/yyyy");
                                String fecha = dia.format(value);
                                value = fecha;
                            }
                            newRecord.put(key, value);

                        }
                    }


                }
                ;
                data.add(newRecord);
            }
        }else {
            for (int i = 0; i < jcoTable.getNumRows(); i++) {
                jcoTable.setRow(i);
                String ArrayResponse[] = jcoTable.getString().split("\\|");
                HashMap<String, Object> newRecord = new HashMap<String, Object>();
                for (int j = 0; j < jcoFields.getNumRows(); j++) {
                    jcoFields.setRow(j);
                    String key = (String) jcoFields.getValue("FIELDNAME");

                    Object value = "";
                    try{
                        value = ArrayResponse[j].trim();
                    }catch (Exception e){
                        value="";

                    }

                    newRecord.put(key, value);


                }
                ;
                data.add(newRecord);
            }
        }



        return data;
    }
}





