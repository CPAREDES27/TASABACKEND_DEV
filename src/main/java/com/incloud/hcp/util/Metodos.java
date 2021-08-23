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

                newRecord.put(key, value);
            }
            data.add(newRecord);
        }

        return data;
    }
}
