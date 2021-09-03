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
                if(key.equals("ESMAR")){
                    if(value.equals("A")){
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
                    }
                }

                newRecord.put(key, value);
            }
            if(newRecord.containsKey("ESMAR")){
                 if(newRecord.containsValue("C")){
                    if(newRecord.containsKey("ESCMA")){
                        if(newRecord.containsValue("P")){
                            data.add(newRecord);
                        }
                    }

                }
            }


        }

        return data;
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
}
