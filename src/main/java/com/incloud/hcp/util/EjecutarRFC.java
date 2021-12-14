package com.incloud.hcp.util;

import com.incloud.hcp.jco.maestro.dto.*;
import com.sap.conn.jco.*;
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
        JCoFunction function = getFunction(Constantes.ZFL_RFC_READ_TABLE_BTP);
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
        List<HashMap<String, Object>> Fields = ObtenerFields(FIELDS, fields);
        logger.error("Execute_ZFL_RFC_READ_TABLE_4");;


        MaestroExport me = new MaestroExport();
        me.setFields(Fields);
        me.setData(data);
        me.setMensaje(mensaje);
        return me;
    }

    public MaestroExport Execute_ZFL_RFC_READ_TABLE2(HashMap<String, Object> imports, List<ListaWA> optionsParam, String[] fields) throws Exception{
        JCoFunction function = getFunction(Constantes.ZFL_RFC_READ_TABLE_BTP);
        setImports(function, imports);
        logger.error("Execute_ZFL_RFC_READ_TABLE_1");;
        JCoParameterList jcoTables = function.getTableParameterList();
        setTable2(jcoTables, "OPTIONS", optionsParam);
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
    public void setTable2(JCoParameterList listTables, String tableName, List<ListaWA> optionsParam){

        logger.error("Lista de parametros: " +listTables);
        JCoTable tableImport = listTables.getTable(tableName);
        logger.error("setTable_1");
        for (int i = 0; i < optionsParam.size(); i++){
            tableImport.appendRow();
            tableImport.setValue(optionsParam.get(i).getClave(),optionsParam.get(i).getValor());
        }
        logger.error("setTable_2");

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

    public UpdateTableExports Execute_ZFL_RFC_UPDATE_TABLE(HashMap<String, Object> imports, String data) throws Exception{

        JCoFunction function = getFunction(Constantes.ZFL_RFC_UPDATE_TABLE);
        logger.error("Execute_ZFL_RFC_UPDATE_TABLE_1");
        setImports(function, imports);
        logger.error("Execute_ZFL_RFC_UPDATE_TABLE_2");
        JCoParameterList jcoTables = function.getTableParameterList();
        JCoTable tableImport = jcoTables.getTable(Tablas.T_DATA);
        tableImport.appendRow();
        logger.error("Execute_ZFL_RFC_UPDATE_TABLE DATA: "+data);
        tableImport.setValue("DATA", data);
        logger.error("Execute_ZFL_RFC_UPDATE_TABLE_3");
        function.execute(destination);
        JCoTable tableExport = jcoTables.getTable(Tablas.T_MENSAJE);

        Metodos me = new Metodos();

        List<HashMap<String, Object>> t_mensaje=me.ListarObjetos(tableExport);
        UpdateTableExports dto = new UpdateTableExports();

        dto.setT_mensaje(t_mensaje);

        logger.error("Execute_ZFL_RFC_UPDATE_TABLE_4");


        return dto;
    }

    public MensajeDto Execute_ZFL_RFC_UPDATE_TABLE2(HashMap<String, Object> imports, String data) throws Exception{

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

    public MensajeDto2 Execute_ZFL_RFC_UPDATE_TABLE3(HashMap<String, Object> imports, String data) throws Exception{

        JCoFunction function = getFunction(Constantes.ZFL_RFC_UPDATE_TABLE);
        logger.error("Execute_ZFL_RFC_UPDATE_TABLE_1");
        setImports(function, imports);
        logger.error("Execute_ZFL_RFC_UPDATE_TABLE_2");
        JCoParameterList jcoTables = function.getTableParameterList();
        JCoTable tableImport = jcoTables.getTable(Tablas.T_DATA);
        tableImport.appendRow();
        logger.error("Execute_ZFL_RFC_UPDATE_TABLE DATA: "+data);
        tableImport.setValue("DATA", data);
        logger.error("Execute_ZFL_RFC_UPDATE_TABLE_3");
        function.execute(destination);
        JCoTable tableExport = jcoTables.getTable(Tablas.T_MENSAJE);

        MensajeDto2 dto = new MensajeDto2();
        ArrayList<MensajeDtoItem> mensajes=new ArrayList<>();
        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);
            MensajeDtoItem mensaje=new MensajeDtoItem();
            mensaje.setMANDT(tableExport.getString("MANDT"));
            mensaje.setCMIN(tableExport.getString("CMIN"));
            mensaje.setCDMIN(tableExport.getString("CDMIN"));
            mensaje.setDSMIN(tableExport.getString("DSMIN"));

        }
        dto.setMensajes(mensajes);
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
    public List<HashMap<String, Object>>   ObtenerListObj(JCoTable jcoTable, JCoTable jcoFields, String[] fields)throws Exception{

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        Metodos exec= new Metodos();

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



                        if (fields[k].trim().equals(key.trim())) {


                           try {
                               if(key.equals("LNMAX") || key.equals("LNMIN") ||key.equals("LTMAX") ||key.equals("LTMIN")
                                       ||key.equals("LGFIN") || key.equals("LNINI") ||key.equals("LTFIN") ||key.equals("LTINI")){
                                   String field="";
                                   if(key.equals("LNMAX")){
                                       field=key+"_S";
                                   }else if  (key.equals("LNMIN")){
                                       field=key+"_S";
                                   }else if  (key.equals("LTMAX")){
                                       field=key+"_S";
                                   }else if  (key.equals("LTMIN")){
                                       field=key+"_S";
                                   }
                                   newRecord.put(field, value);
                                   String valor=value.toString();
                                   logger.error("valor= "+valor);
                                   valor=valor.substring(0,3)+"°"+valor.substring(3,valor.length());
                                   logger.error("valor= "+valor);
                                   value=valor.substring(0,6)+"'";
                                   logger.error("value= "+value);
                               }

                               if (key.equals("HFDES") || key.equals("HIDES") || key.equals("HAMAR") || key.equals("HFMAR") || key.equals("HIMAR") || key.equals("HXMAR") ||
                                       key.equals("HFEVN") || key.equals("HIEVN")) {

                                   if(value.toString().equals("000000")){
                                       value="";
                                   }else {
                                       SimpleDateFormat parseador = new SimpleDateFormat("hhmmss");
                                       SimpleDateFormat formateador = new SimpleDateFormat("HH:mm", Locale.UK);
                                       Date hora = parseador.parse(value.toString());
                                       value = formateador.format(hora);
                                   }

                               }

                               if (key.equals("FEMAR") || key.equals("FITVS") || key.equals("FCVVI") || key.equals("FFTVS")|| key.equals("FHFVG")|| key.equals("FHIVG") ||
                                       key.equals("FFDES") || key.equals("FIDES") || key.equals("FFMAR") || key.equals("FIMAR") || key.equals("FXMAR") || key.equals("FFEVN") ||
                                       key.equals("FIEVN")|| key.equals("FHFTM")|| key.equals("FHITM")){
                                    if(value.toString().equals("00000000")){
                                        value="";
                                    }else{
                                        SimpleDateFormat parseador = new SimpleDateFormat("yyyyMMdd");
                                        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                                        Date fecha = parseador.parse(value.toString());
                                        value=formateador.format(fecha);
                                    }

                               }
                           }catch (Exception e){
                               value=String.valueOf(value);
                           }

                            newRecord.put(key, value);
                           Metodos me=new Metodos();
                            if(key.equals("INPRP") || key.equals("ESREG") || key.equals("CDMMA") || key.equals("CDTEV")|| key.equals("STELL")){
                                HashMap<String, Object>dominio=me.BuscarNombreDominio(key, value.toString());
                                for (Map.Entry<String, Object> entry:dominio.entrySet() ){
                                    String campo=entry.getKey();
                                    Object valor=entry.getValue();
                                    newRecord.put(campo, valor);
                                }
                            }


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
                    //String contador ="00"+j+"_";
                    //String key2 = contador +key;
                    Object value = "";
                    try{
                        value = ArrayResponse[j].trim();

                        if(key.equals("LNMAX") || key.equals("LNMIN") ||key.equals("LTMAX") ||key.equals("LTMIN")
                                ||key.equals("LGFIN") || key.equals("LNINI") ||key.equals("LTFIN") ||key.equals("LTINI")){
                            String field="";
                            if(key.equals("LNMAX")){
                                field=key+"_S";
                            }else if  (key.equals("LNMIN")){
                                field=key+"_S";
                            }else if  (key.equals("LTMAX")){
                                field=key+"_S";
                            }else if  (key.equals("LTMIN")){
                                field=key+"_S";
                            }
                            newRecord.put(field, value);
                            String valor=value.toString();
                            logger.error("valor= "+valor);
                            valor=valor.substring(0,3)+"°"+valor.substring(3,valor.length());
                            logger.error("valor= "+valor);
                            value=valor.substring(0,6)+"'";
                            logger.error("value= "+value);
                        }

                        if (key.equals("HRCRN") || key.equals("HRMOD")|| key.equals("HRREQ")) {

                            if(value.toString().equals("000000")){
                                value="";
                            }else {
                                SimpleDateFormat parseador = new SimpleDateFormat("hhmmss");
                                SimpleDateFormat formateador = new SimpleDateFormat("HH:mm", Locale.UK);
                                Date hora = parseador.parse(value.toString());
                                value = formateador.format(hora);
                            }

                        }
                        if (key.equals("FEMAR") || key.equals("FITVS") || key.equals("FCVVI") || key.equals("FFTVS") ||
                                key.equals("FHREQ") || key.equals("FHCRN")|| key.equals("FHMOD")|| key.equals("FHFVG")|| key.equals("FHIVG")
                                || key.equals("FHFTM")|| key.equals("FHITM")) {

                            if(value.toString().equals("00000000")){
                               value="";
                            }else {
                                SimpleDateFormat parseador = new SimpleDateFormat("yyyyMMdd");
                                SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                                Date fecha = parseador.parse(value.toString());
                                value = formateador.format(fecha);
                            }

                        }
                    }catch (Exception e){

                        value="";

                    }

                    newRecord.put(key, value);
                    Metodos me=new Metodos();
                    if(key.equals("INPRP") || key.equals("ESREG") || key.equals("STELL")){
                        HashMap<String, Object>dominio=me.BuscarNombreDominio(key, value.toString());
                        for (Map.Entry<String, Object> entry:dominio.entrySet() ){
                            String campo=entry.getKey();
                            Object valor=entry.getValue();
                            newRecord.put(campo, valor);
                        }
                    }

                }

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

    public List<HashMap<String, Object>> ObtenerFields(JCoTable jcoTable, String[] fields)throws Exception{

        List<HashMap<String, Object>> data= new ArrayList<HashMap<String, Object>>();
        int con=1;
        if(fields.length>=1) {
            for (int i = 0; i < jcoTable.getNumRows(); i++) {
                jcoTable.setRow(i);
                JCoFieldIterator iter = jcoTable.getFieldIterator();
                HashMap<String, Object> newRecord = new HashMap<String, Object>();

                while (iter.hasNextField()) {
                    JCoField field = iter.nextField();
                    String key = (String) field.getName();
                    Object value = jcoTable.getValue(key);

                    for (int k = 0; k < fields.length; k++) {


                        if (fields[k].trim().equals(value.toString().trim())) {
                            newRecord.put(key, value);
                        }
                    }
                }
                if(newRecord.size()>0) {
                    newRecord.put("ORDEN", con);
                    data.add(newRecord);
                }

                con++;
            }
        }else {
            for (int i = 0; i < jcoTable.getNumRows(); i++) {
                jcoTable.setRow(i);
                JCoFieldIterator iter = jcoTable.getFieldIterator();
                HashMap<String, Object> newRecord = new HashMap<String, Object>();

                while (iter.hasNextField()) {
                    JCoField field = iter.nextField();
                    String key = (String) field.getName();
                    Object value = jcoTable.getValue(key);

                    newRecord.put(key, value);

                }
                newRecord.put("ORDEN", con);

                data.add(newRecord);
                con++;
            }
        }
        return data;
    }
}





