package com.incloud.hcp.util;

import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.sap.conn.jco.*;

import java.util.*;

public class EjecutarRFC {

    private JCoDestination destination;

    public EjecutarRFC(){
    }

    public MaestroExport Execute_ZFL_RFC_READ_TABLE(HashMap<String, Object> imports, List<HashMap<String, Object>> optionsParam) throws Exception{
        JCoFunction function = getFunction(Constantes.ZFL_RFC_READ_TABLE);
        setImports(function, imports);
        JCoParameterList jcoTables = function.getTableParameterList();
        setTable(jcoTables, "OPTIONS", optionsParam);
        function.execute(destination);
        JCoTable DATA = jcoTables.getTable("DATA");
        JCoTable FIELDS = jcoTables.getTable("FIELDS");
        //JCoTable T_MENSAJE = jcoTables.getTable("FIELDS");
        //T_MENSAJE.setRow(0);
        String mensaje = "Ok";
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < DATA.getNumRows(); i++) {
            DATA.setRow(i);
            String ArrayResponse[] = DATA.getString().split("\\|");
            HashMap<String, Object> newRecord = new HashMap<String, Object>();
            for (int j = 0; j < FIELDS.getNumRows(); j++){
                FIELDS.setRow(j);
                String key = (String) FIELDS.getValue("FIELDNAME");
                Object value = ArrayResponse[j];
                newRecord.put(key, value);
            };
            data.add(newRecord);
        }
        MaestroExport me = new MaestroExport();
        me.setData(data);
        me.setMensaje(mensaje);
        return me;
    }

    public void setImports(JCoFunction function, HashMap<String, Object> imports){
        JCoParameterList jcoImport = function.getImportParameterList();
        Iterator iterator = imports.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry tmpImport = (Map.Entry) iterator.next();
            String key = tmpImport.getKey().toString();
            Object value = tmpImport.getValue();
            jcoImport.setValue(key, value);
        }
    }

    public void setTable(JCoParameterList listTables, String tableName, List<HashMap<String, Object>> data){
        JCoTable tableImport = listTables.getTable(tableName);
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
    }

    public JCoFunction getFunction (String FunctionName) throws Exception{
        destination = getDestination();
        JCoRepository repo = destination.getRepository();
        return repo.getFunction(FunctionName);
    }

    public JCoDestination getDestination() throws Exception{
        return JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
    }

}
