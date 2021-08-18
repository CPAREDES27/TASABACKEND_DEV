package com.incloud.hcp.util;

import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Metodos {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<HashMap<String, Object>> ListarObjetos(JCoTable tableExport)throws Exception{

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < tableExport.getNumRows(); i++)
        {
            tableExport.setRow(i);
            JCoFieldIterator iter = tableExport.getFieldIterator();
            HashMap<String, Object> newRecord = new HashMap<String, Object>();
            while(iter.hasNextField())
            {
                JCoField field = iter.nextField();
                String key=(String) field.getName();
                Object value=tableExport.getValue(field.getName());
                newRecord.put(key, value);
            }
            data.add(newRecord);
        }

        return data;
    }
}
