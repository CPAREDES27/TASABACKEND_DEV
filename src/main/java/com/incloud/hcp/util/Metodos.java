package com.incloud.hcp.util;

import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Metodos {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<HashMap<String, Object>> ListarObjetos(JCoTable tableExport) throws Exception {

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);
            JCoFieldIterator iter = tableExport.getFieldIterator();
            HashMap<String, Object> newRecord = new HashMap<String, Object>();
            while (iter.hasNextField()) {
                JCoField field = iter.nextField();
                String key = (String) field.getName();
                Object value = tableExport.getValue(key);

                if (field.getTypeAsString().equals("TIME")) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                    value = dateFormat.format(value);
                }
                /*if (field.getTypeAsString().equals("DATE")) {
                    Date dateValue = tableExport.getDate(key);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    value = dateFormat.format(dateValue);
                }*/
                if (field.getTypeAsString().equals("DATE") && key.equals("FEMAR")) {
                    /* String date="Sat Jun 01 12:53:10 UTC 2013";
                    SimpleDateFormat sdf=new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
                    Date currentdate=sdf.parse(date);
                    SimpleDateFormat sdf2=new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
                    value= sdf2.format(currentdate);*/
                    String date=String.valueOf(value);
                    SimpleDateFormat dia=new SimpleDateFormat("dd/MM/yyyy");
                    // SimpleDateFormat mes=new SimpleDateFormat("MM");
                    //SimpleDateFormat anio=new SimpleDateFormat("yyyy");
                    String fecha= dia.format(value);
                    //String month= mes.format(value);
                    //String year= anio.format(value);

                    value= fecha;

                }
                if (field.getTypeAsString().equals("DATE") && key.equals("FITVS") ||
                        field.getTypeAsString().equals("DATE") && key.equals("FCVVI") ||
                        field.getTypeAsString().equals("DATE") && key.equals("FFTVS")) {

                    String date=String.valueOf(value);
                    SimpleDateFormat dia=new SimpleDateFormat("dd/MM/yyyy");
                    String fecha= dia.format(value);
                    value= fecha;
                }

                newRecord.put(key, value);
            }
            data.add(newRecord);
        }

        return data;
    }

    public List<HashMap<String, Object>> ListarObjetosDIR(JCoTable tableExport) throws Exception {

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);
            JCoFieldIterator iter = tableExport.getFieldIterator();
            HashMap<String, Object> newRecord = new HashMap<String, Object>();
            while (iter.hasNextField()) {
                JCoField field = iter.nextField();

                String key = (String) field.getName();
                Object value = tableExport.getValue(key);

                        if (field.getTypeAsString().equals("TIME")) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                            value = dateFormat.format(value);
                        }

                       if (field.getTypeAsString().equals("DATE") && key.equals("FEARR") ) {
                            try{
                                String date=String.valueOf(value);
                                SimpleDateFormat dia=new SimpleDateFormat("dd/MM/yyyy");
                                String fecha= dia.format(value);
                                value= fecha;
                            }catch(Exception e){
                                value="";
                             }

                        }



                newRecord.put(key, value);
            }
            data.add(newRecord);
            if(newRecord.containsKey("ESMAR")){
                 if(newRecord.containsValue("C") || newRecord.containsValue("A")){

                     data.add(newRecord);
                }
            }



        }

        return data;
    }

    public String returnTable(String table){
        String tablita="";
        if(table.equals("MONEDA")){
            tablita="ZFLMND";
        }
        if(table.equals("UBICPLANTA")){
            tablita="ZFLUPT";
        }
        if(table.equals("LITORAL")){
            tablita="ZFLZLT";
        }
        return tablita;
    }
    public String returnWA(String table){
        String wa="";
        if(table.equals("MONEDA") || table.equals("UBICPLANTA") || table.equals("LITORAL")){
            wa="ESREG = 'S'";
        }

        return wa;
    }
    public String[] returnField(String table){

        String[] fields= null;
        if(table.equals("UBICPLANTA")){
           fields= new  String[]{"CDUPT","DSUPT"};
        }
        if(table.equals("MONEDA")){
            fields= new String[]{"CDMND","DSMND"};
        }
        if(table.equals("LITORAL")){
            fields= new String[]{"CDZLT","DSZLT"};
        }
        return fields;
    }

    public List<HashMap<String, Object>> ObtenerListObjetos(JCoTable jcoTable,  String[] fields)throws Exception{

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

        if(fields.length>=1){
            for (int i = 0; i < jcoTable.getNumRows(); i++) {
                jcoTable.setRow(i);
                JCoFieldIterator iter = jcoTable.getFieldIterator();
                HashMap<String, Object> newRecord = new HashMap<String, Object>();
                while (iter.hasNextField()) {
                    JCoField field = iter.nextField();
                    String key = (String) field.getName();
                    Object value = jcoTable.getValue(key);

                    for (int k = 0; k < fields.length; k++) {
                        logger.error("key: " + key + " k: " + fields[k]);
                        ;

                        if (fields[k].trim().equals(key.trim())) {

                            if (field.getTypeAsString().equals("TIME")) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                                value = dateFormat.format(value);
                            }
                            try {
                                if (field.getTypeAsString().equals("DATE")) {

                                    String date = String.valueOf(value);
                                    SimpleDateFormat dia = new SimpleDateFormat("dd/MM/yyyy");
                                    String fecha = dia.format(value);
                                    value = fecha;
                                }
                            }catch (Exception e){
                                value=String.valueOf(value);
                            }

                            newRecord.put(key, value);

                        }
                    }


                }
                data.add(newRecord);
            }
        }else {
            data=ObtenerListObjetosSinField(jcoTable);
        }
        return data;

    }

    public List<HashMap<String, Object>> ObtenerListObjetosSinField(JCoTable jcoTable)throws Exception{

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < jcoTable.getNumRows(); i++) {
            jcoTable.setRow(i);
            JCoFieldIterator iter = jcoTable.getFieldIterator();
            HashMap<String, Object> newRecord = new HashMap<String, Object>();
            while (iter.hasNextField()) {
                JCoField field = iter.nextField();
                String key = (String) field.getName();
                Object value = jcoTable.getValue(key);

                if (field.getTypeAsString().equals("TIME")) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                    value = dateFormat.format(value);
                }
                try {
                    if (field.getTypeAsString().equals("DATE")) {

                        String date = String.valueOf(value);
                        SimpleDateFormat dia = new SimpleDateFormat("dd/MM/yyyy");
                        String fecha = dia.format(value);
                        value = fecha;
                    }
                }catch (Exception e){
                    value=String.valueOf(value);
                }

                newRecord.put(key, value);
            }
            data.add(newRecord);
        }
        return data;

    }

    public String ObtenerCampo(JCoTable tableExport, JCoTable FIELDS){
        String campo="";
        for(int i=0;i<tableExport.getNumRows();i++){
            tableExport.setRow(i);
            String ArrayResponse[] = tableExport.getString().split("\\|");
            for(int j=0;j<FIELDS.getNumRows();j++){
                FIELDS.setRow(j);
                Object value="";
                String key=(String) FIELDS.getValue("FIELDNAME");
                if(key.equals("CDPTA")){
                    value=ArrayResponse[j].trim();
                    campo=value.toString();
                    logger.error("CDPTA: "+campo);
                }
            }

        }
        return campo;
    }
}
