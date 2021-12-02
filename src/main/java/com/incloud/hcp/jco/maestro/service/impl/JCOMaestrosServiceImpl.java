package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.dominios.dto.*;
import com.incloud.hcp.jco.dominios.service.JCODominiosService;
import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.maestro.service.JCOCampoTablaService;
import com.incloud.hcp.jco.maestro.service.JCOMaestrosService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class JCOMaestrosServiceImpl implements JCOMaestrosService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JCODominiosService jcoDominiosService;
    @Autowired
    private JCOEmbarcacionImpl jcoEmbarcacion;
    @Autowired
    private JCOCampoTablaService jcoCampoTablaService;

    @Override
    public MaestroExport obtenerMaestro (MaestroImports importsParam) throws Exception {

        MaestroExport me=new MaestroExport();
        try {
            //setear mapeo de parametros import
            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("QUERY_TABLE", importsParam.getTabla());
            imports.put("DELIMITER", importsParam.getDelimitador());
            imports.put("NO_DATA", importsParam.getNo_data());
            imports.put("ROWSKIPS", importsParam.getRowskips());
            imports.put("ROWCOUNT", importsParam.getRowcount());
            imports.put("P_USER", importsParam.getP_user());
            imports.put("P_ORDER", importsParam.getOrder());
            logger.error("obtenerMaestro_1");
            //setear mapeo de tabla options

            List<MaestroOptions> options = importsParam.getOptions();
            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < options.size(); i++) {
                MaestroOptions mo = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();

                record.put("WA", mo.getWa());
                tmpOptions.add(record);
            }
            logger.error("obtenerMaestro_2");

            String []fields=importsParam.getFields();
            //ejecutar RFC ZFL_RFC_READ_TABLE
            EjecutarRFC exec = new EjecutarRFC();
            me = exec.Execute_ZFL_RFC_READ_TABLE(imports, tmpOptions, fields);

            HashMap<String, Object> d=me.getData().get(0);
            for (Map.Entry<String, Object> entry : d.entrySet()) {
                String key=entry.getKey();
                logger.error("NOMBRE CAMPO: "+key);
                if(key.equals("INPRP")||key.equals("ESREG")){
                    List<HashMap<String, Object >>dataConDominio=ListaDataConDominio(me,key);
                    me.setData(dataConDominio);
                }
            }
        }catch (Exception e){
            me.setMensaje(e.getMessage());
        }
        return me;
    }

    public MaestroExport obtenerMaestro3 (MaestroImportsKey imports) throws Exception {

        Metodos metodo= new Metodos();
        MaestroExport me=new MaestroExport();
        MaestroOptionsKey me2 = new MaestroOptionsKey();

        List<MaestroOptions> option = imports.getOption();
        logger.error("TAMAÑO import: "+option.size());
        List<MaestroOptionsKey> options = imports.getOptions();
        logger.error("TAMAÑO import: "+options.size());
            /*for(int j =0;j<option.size();j++){
                MaestroOptions mop= option.get(j);
                logger.error("GET IMPORT: "+mop.getWa());
            }*/
        HashMap<String, Object> importz = new HashMap<String, Object>();
        importz.put("QUERY_TABLE", imports.getTabla());
        importz.put("DELIMITER", imports.getDelimitador());
        importz.put("NO_DATA", imports.getNo_data());
        importz.put("ROWSKIPS", imports.getRowskips());
        importz.put("ROWCOUNT", imports.getRowcount());
        importz.put("P_USER", imports.getP_user());
        importz.put("P_ORDER", imports.getOrder());
        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
        logger.error("cas METODO GENERAR");
        List<ListaWA> tmpOption=metodo.GeneraCadena(option,options,"WA");
        logger.error("ENTRE METODO GENERAR");
        logger.error("FIN");
        String []fields=imports.getFields();
        EjecutarRFC exec = new EjecutarRFC();
        me = exec.Execute_ZFL_RFC_READ_TABLE2(importz, tmpOption, fields);
        return me;
    }
    public MaestroExport obtenerMaestro2 (MaestroImportsKey imports) throws Exception {
        logger.error("ARMADOR 1");
        Metodos metodo= new Metodos();
        MaestroExport me=new MaestroExport();
        MaestroOptionsKey me2 = new MaestroOptionsKey();
        logger.error("ARMADOR 2");
        List<MaestroOptions> option = imports.getOption();
        logger.error("TAMAÑO import: "+option.size());
        List<MaestroOptionsKey> options = imports.getOptions();
        logger.error("TAMAÑO import: "+options.size());
            /*for(int j =0;j<option.size();j++){
                MaestroOptions mop= option.get(j);
                logger.error("GET IMPORT: "+mop.getWa());
            }*/
        logger.error("ARMADOR 3");
        HashMap<String, Object> importz = new HashMap<String, Object>();
        logger.error("ARMADOR 4");
        importz.put("QUERY_TABLE", imports.getTabla());
        importz.put("DELIMITER", imports.getDelimitador());
        importz.put("NO_DATA", imports.getNo_data());
        importz.put("ROWSKIPS", imports.getRowskips());
        importz.put("ROWCOUNT", imports.getRowcount());
        importz.put("P_USER", imports.getP_user());
        importz.put("P_ORDER", imports.getOrder());
        logger.error("ARMADOR 5");
        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
        tmpOptions=metodo.ValidarOptions(option,options);
        logger.error("ARMADOR 6");
        String []fields=imports.getFields();
        EjecutarRFC exec = new EjecutarRFC();
        me = exec.Execute_ZFL_RFC_READ_TABLE(importz, tmpOptions, fields);




        return me;
    }

    @Override
    public MaestroExport obtenerArmador(BusquedaArmadorDTO codigo) throws Exception {
        Metodos metodos = new Metodos();
        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        ;
        JCoRepository repo = destination.getRepository();
        ;
        JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_READ_TABLE);
        JCoParameterList importx = stfcConnection.getImportParameterList();
        importx.setValue("DELIMITER","|");
        importx.setValue("QUERY_TABLE","LFA1");
        importx.setValue("ROWCOUNT",codigo.getCodigo());
        List<MaestroOptions> option = codigo.getOption();
        List<MaestroOptionsKey> options = codigo.getOptions2();


        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
        tmpOptions=metodos.ValidarOptions(option,options);

        JCoParameterList tables = stfcConnection.getTableParameterList();
        EjecutarRFC executeRFC = new EjecutarRFC();

        executeRFC.setTable(tables, "OPTIONS", tmpOptions);
        JCoTable tableExport = tables.getTable("DATA");
        JCoTable FIELDS = tables.getTable("FIELDS");
        stfcConnection.execute(destination);
        String hola = tableExport.getString();
        String[] fields= {"LIFNR","NAME1","STCD1"};
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        String campo="";

        for(int i=0;i<tableExport.getNumRows();i++){
            tableExport.setRow(i);
            String ArrayResponse[] = tableExport.getString().split("\\|");
            logger.error("LLEGUE 4");
            HashMap<String, Object> newRecord = new HashMap<String, Object>();
            logger.error("LLEGUE 5");
            for(int j=0;j<FIELDS.getNumRows();j++){
                FIELDS.setRow(j);
                String key=(String) FIELDS.getValue("FIELDNAME");
                for(int k=0;k<fields.length;k++){
                    if(key.equals(fields[k])){
                        Object value = ArrayResponse[j].trim();

                        newRecord.put(key, value.toString());
                    }
                }
                logger.error("LLEGUE 10");
            }
            data.add(newRecord);
            logger.error("LLEGUE 11");
        }
        logger.error("LLEGUE 12");



        MaestroExport obj = new MaestroExport();
        obj.setData(data);
        obj.setMensaje("OK");
        return obj;
    }

    public UpdateTableExports editarMaestro (MaestroEditImports importsParam) throws Exception{

        //DESPUES
        UpdateTableExports msj= new UpdateTableExports();
        try {
            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("I_TABLE", importsParam.getTabla());
            imports.put("P_FLAG", importsParam.getFlag());
            imports.put("P_CASE", importsParam.getP_case());
            imports.put("P_USER", importsParam.getP_user());
            EjecutarRFC exec = new EjecutarRFC();

            msj = exec.Execute_ZFL_RFC_UPDATE_TABLE(imports, importsParam.getData().toUpperCase().trim());
            msj.setMensaje("Ok");

        }catch (Exception e){

            msj.setMensaje(e.getMessage());
        }
        return msj;


    }
    public UpdateTableExports editarMaestro2 (MaestroEditImport importsParam) throws Exception{

        //DESPUES
        MaestroExport me= new MaestroExport();
        UpdateTableExports msj= new UpdateTableExports();
        try {
            logger.error("editarMaestro2= FIELDWHERE: "+importsParam.getFieldWhere()+", KEYWHERE= "+importsParam.getKeyWhere()+", TABLA:"+importsParam.getTabla()+", P_USER: "+importsParam.getP_user());
            me= ConsultaReadTable(importsParam.getFieldWhere(),importsParam.getKeyWhere(),importsParam.getTabla(),importsParam.getP_user());
            logger.error("VERIFICAR DATA"+me.getData().toString());
            List<MaestroUpdate> update = importsParam.getOpcion();
            List<HashMap<String, Object>> data= me.getData();
            LinkedHashMap<String,Object> newRecord = new LinkedHashMap<String,Object>();
            LinkedHashMap<String,Object> setDAta = new LinkedHashMap<String,Object>();
            logger.error("VERIFICAR DATA PASO2");
            int contador=0;
            for(Map<String,Object> datas: data){
                for(Map.Entry<String,Object> entry: datas.entrySet()){
                       String key= entry.getKey();
                       Object value= entry.getValue();
                       newRecord.put(key, value);
                }

            }
            logger.error("VERIFICAR DATA PASO3");
            String mensajito="";
            String valor="";
           for(int i=0;i<update.size();i++){
               mensajito=update.get(i).getField();
               valor = update.get(i).getValor().toUpperCase().trim();
               for(Map.Entry<String,Object> entry:newRecord.entrySet()){
                   if(entry.getKey().contains(mensajito)){
                       entry.setValue(valor);
                   }
               }
           }
            logger.error("VERIFICAR DATA PASO4");
            String cadena="";
            if(importsParam.getKeyWhere() == null || importsParam.getKeyWhere() == "")
            {
                cadena ="||";
                for(Map.Entry<String,Object> entry:newRecord.entrySet()){

                    if(contador >1) {
                        cadena += entry.getValue();
                        cadena += "|";
                    }
                    contador++;
                }
            }else {
                 cadena = "|";
                for(Map.Entry<String,Object> entry:newRecord.entrySet()){

                    if(contador >0) {
                        cadena += entry.getValue();
                        cadena += "|";
                    }
                    contador++;
                }
            }
            logger.error("VERIFICAR DATA PASO_ cadena"+cadena);

            /*for(Map.Entry<String,Object> entry:newRecord.entrySet()){

                if(contador >1) {
                    cadena += entry.getValue();
                    cadena += "|";
                }
                contador++;
            }*/
            logger.error("VERIFICAR DATA PASO5");
            cadena = cadena.substring(0,cadena.length()-1);


            logger.error("CADENA: " +cadena);
            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("I_TABLE", importsParam.getTabla());
            imports.put("P_FLAG", importsParam.getFlag());
            imports.put("P_CASE", importsParam.getP_case());
            imports.put("P_USER", importsParam.getP_user());



            EjecutarRFC exec = new EjecutarRFC();


            msj = exec.Execute_ZFL_RFC_UPDATE_TABLE(imports, cadena);
            msj.setMensaje("Ok");

        }catch (Exception e){

            msj.setMensaje(e.getMessage());
        }
        return msj;


    }

    public MensajeDto2 editarMaestro3 (MaestroEditImport importsParam) throws Exception{

        //DESPUES
        MaestroExport me= new MaestroExport();
        MensajeDto2 msj= new MensajeDto2();
        try {
            logger.error("editarMaestro2= FIELDWHERE: "+importsParam.getFieldWhere()+", KEYWHERE= "+importsParam.getKeyWhere()+", TABLA:"+importsParam.getTabla()+", P_USER: "+importsParam.getP_user());
            me= ConsultaReadTable(importsParam.getFieldWhere(),importsParam.getKeyWhere(),importsParam.getTabla(),importsParam.getP_user());
            logger.error("VERIFICAR DATA"+me.getData().toString());
            List<MaestroUpdate> update = importsParam.getOpcion();
            List<HashMap<String, Object>> data= me.getData();
            LinkedHashMap<String,Object> newRecord = new LinkedHashMap<String,Object>();
            LinkedHashMap<String,Object> setDAta = new LinkedHashMap<String,Object>();
            logger.error("VERIFICAR DATA PASO2");
            int contador=0;
            for(Map<String,Object> datas: data){
                for(Map.Entry<String,Object> entry: datas.entrySet()){
                    String key= entry.getKey();
                    Object value= entry.getValue();
                    newRecord.put(key, value);
                }

            }
            logger.error("VERIFICAR DATA PASO3");
            String mensajito="";
            String valor="";
            for(int i=0;i<update.size();i++){
                mensajito=update.get(i).getField();
                valor = update.get(i).getValor().toUpperCase().trim();
                for(Map.Entry<String,Object> entry:newRecord.entrySet()){
                    if(entry.getKey().contains(mensajito)){
                        entry.setValue(valor);
                    }
                }
            }
            logger.error("VERIFICAR DATA PASO4");
            String cadena="";
            if(importsParam.getKeyWhere() == null || importsParam.getKeyWhere() == "")
            {
                cadena ="||";
                for(Map.Entry<String,Object> entry:newRecord.entrySet()){

                    if(contador >1) {
                        cadena += entry.getValue();
                        cadena += "|";
                    }
                    contador++;
                }
            }else {
                cadena = "|";
                for(Map.Entry<String,Object> entry:newRecord.entrySet()){

                    if(contador >0) {
                        cadena += entry.getValue();
                        cadena += "|";
                    }
                    contador++;
                }
            }
            logger.error("VERIFICAR DATA PASO_ cadena"+cadena);

            /*for(Map.Entry<String,Object> entry:newRecord.entrySet()){

                if(contador >1) {
                    cadena += entry.getValue();
                    cadena += "|";
                }
                contador++;
            }*/
            logger.error("VERIFICAR DATA PASO5");
            cadena = cadena.substring(0,cadena.length()-1);


            logger.error("CADENA: " +cadena);
            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("I_TABLE", importsParam.getTabla());
            imports.put("P_FLAG", importsParam.getFlag());
            imports.put("P_CASE", importsParam.getP_case());
            imports.put("P_USER", importsParam.getP_user());



            EjecutarRFC exec = new EjecutarRFC();


            msj = exec.Execute_ZFL_RFC_UPDATE_TABLE3(imports, cadena);

        }catch (Exception e){
            ArrayList<MensajeDtoItem> mensajes=new ArrayList<>();
            MensajeDtoItem msjItem=new MensajeDtoItem();
            msjItem.setMANDT("00");
            msjItem.setCMIN("Error");
            msjItem.setCDMIN("Exception");
            msjItem.setDSMIN(e.getMessage());

            mensajes.add(msjItem);

            msj.setMensajes(mensajes);
        }
        return msj;


    }

    public MaestroExport ConsultaReadTable(String key, String value, String table, String usuario) throws Exception{

        logger.error("STEP 1");
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        JCoRepository repo = destination.getRepository();
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_READ_TABLE");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        importx.setValue("P_USER", usuario);
        importx.setValue("QUERY_TABLE", table);
        importx.setValue("DELIMITER", "|");
        logger.error("STEP 2");

        logger.error("STEP 3");
        JCoParameterList tables = stfcConnection.getTableParameterList();

        if(!key.equals("")  && !value.equals("")) {
            JCoTable tableImport = tables.getTable("OPTIONS");
            tableImport.appendRow();
            tableImport.setValue("WA", key + " = " + "'" + value + "'");
            logger.error("WA", key + " = " + "'" + value + "'");
        }
        logger.error("STEP 4");


        JCoTable tableExport = tables.getTable("DATA");
        JCoTable FIELDS = tables.getTable("FIELDS");
        stfcConnection.execute(destination);
        List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();

        logger.error("STEP 5");
        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);
            String ArrayResponse[] = tableExport.getString().split("\\|");
            LinkedHashMap<String, Object> newRecord = new LinkedHashMap<String, Object>();
            for (int j = 0; j < FIELDS.getNumRows(); j++) {
                FIELDS.setRow(j);
                String keys = (String) FIELDS.getValue("FIELDNAME");
                Object values = "";
                try {
                    values = ArrayResponse[j].trim();
                } catch (Exception e) {
                    values = "";

                }

                newRecord.put(keys, values);
            }
            datas.add(newRecord);
        }
        logger.error("STEP 6");

            MaestroExport me = new MaestroExport();
            me.setData(datas);
        return me;
    }
    public AppMaestrosExports appMaestros(AppMaestrosImports imports) {

        AppMaestrosExports ame=new AppMaestrosExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_APP_MAESTROS);
            logger.error("stfcConnection: "+stfcConnection.toString());
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_ROL", imports.getP_rol());
            importx.setValue("P_APP", imports.getP_app());
            importx.setValue("P_TIPO", imports.getP_tipo());


            JCoParameterList tables = stfcConnection.getExportParameterList();
            stfcConnection.execute(destination);

            stfcConnection.execute(destination);

            JCoTable t_tabapp = tables.getTable(Tablas.T_TABAPP);
            JCoTable t_tabfield = tables.getTable(Tablas.T_TABFIELD);
            JCoTable t_tabservice = tables.getTable(Tablas.T_TABSERVICE);



            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> List_t_tabapp = metodo.ListarObjetos(t_tabapp);
            List<HashMap<String, Object>> List_t_tabfield = metodo.ListarObjetos(t_tabfield);
            List<HashMap<String, Object>> List_t_tabservice = metodo.ListarObjetos(t_tabservice);

            ame.setT_tabapp(List_t_tabapp);
            ame.setT_tabfield(List_t_tabfield);
            ame.setT_tabservice(List_t_tabservice);
            ame.setMensaje("Ok");

        }catch (Exception e){
            ame.setMensaje(e.getMessage());
        }

        return ame;
    }


    public AyudaBusquedaExports AyudasBusqueda(AyudaBusquedaImports importsParam)throws Exception{

        AyudaBusquedaExports dto=new AyudaBusquedaExports();
        MaestroExport me;

        try {
            if(importsParam.getNombreAyuda().equals("BSQEMBHORO")){
                dto=BuscarEmbaHorometro();
            }else {
                String tabla = (Buscartabla(importsParam.getNombreAyuda()));

                String rowcount="200";
                if(importsParam.getNombreAyuda().equals("BSQCOCINERO")){
                    rowcount="";
                }
                logger.error("AyudasBusqueda TABLA= " + tabla);
                //setear mapeo de parametros import
                HashMap<String, Object> imports = new HashMap<String, Object>();
                imports.put("QUERY_TABLE", tabla);
                imports.put("DELIMITER", "|");
                imports.put("NO_DATA", "");
                imports.put("ROWSKIPS", "");
                imports.put("ROWCOUNT", rowcount);
                imports.put("P_USER", importsParam.getP_user());
                imports.put("P_ORDER", "");
                logger.error("AyudasBusqueda_2");
                //setear mapeo de tabla options

                List<MaestroOptions> options = BuscarOptions(importsParam.getNombreAyuda());
                logger.error("AyudasBusqueda_3");
                List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
                for (int i = 0; i < options.size(); i++) {
                    MaestroOptions mo = options.get(i);
                    HashMap<String, Object> record = new HashMap<String, Object>();
                    logger.error("AyudasBusqueda options= " + mo.getWa());
                    record.put("WA", mo.getWa());
                    tmpOptions.add(record);
                }
                logger.error("AyudasBusqueda_4");

                String[] fields = BuscarFields(importsParam.getNombreAyuda());
                logger.error("AyudasBusqueda_5");
                //ejecutar RFC ZFL_RFC_READ_TABLE
                EjecutarRFC exec = new EjecutarRFC();
                me = exec.Execute_ZFL_RFC_READ_TABLE(imports, tmpOptions, fields);
                logger.error("AyudasBusqueda_6");

                dto.setData(me.getData());
                dto.setMensaje("Ok");
            }

        }catch (Exception e){
            dto.setMensaje(e.getMessage());
        }
        return dto;
    }

    public String Buscartabla(String nombreAyuda){

        String tabla="";

        switch (nombreAyuda){
            case "BSQPLANTAS":
                tabla=AyudaBusquedaTablas.BSQPLANTAS;
                break;
            case "BSQALMACENES":
                tabla=AyudaBusquedaTablas.BSQALMACENES;
                break;
            case "BSQEMPRESA":
                tabla=AyudaBusquedaTablas.BSQEMPRESA;
                break;
            case "BSQCENTRO":
                tabla=AyudaBusquedaTablas.BSQCENTRO;
                break;
            case "BSQUBICTNC":
                tabla=AyudaBusquedaTablas.BSQUBICTNC;
                break;
            case "BSQEQPMT":
                tabla=AyudaBusquedaTablas.BSQEQPMT;
                break;
            case "BSQPROV":
                tabla=AyudaBusquedaTablas.BSQPROV;
                break;
            case "BSQCLTE":
                tabla=AyudaBusquedaTablas.BSQCLTE;
                break;
            case "BSQMAT":
                tabla=AyudaBusquedaTablas.BSQMAT;
                break;
            case "BSQESPEC":
                tabla=AyudaBusquedaTablas.BSQESPEC;
                break;
            case "BSQCIRCTCN":
                tabla=AyudaBusquedaTablas.BSQCIRCTCN;
                break;
            case "BSQPUERTO":
                tabla=AyudaBusquedaTablas.BSQPUERTO;
                break;
            case "BSQUNDEXT":
                tabla=AyudaBusquedaTablas.BSQUNDEXT;
                break;
            case "BSQUSR":
                tabla=AyudaBusquedaTablas.BSQUSR;
                break;
            case "BSQPEDCOMP":
                tabla=AyudaBusquedaTablas.BSQPEDCOMP;
                break;
            case "BSQCLSDOC":
                tabla=AyudaBusquedaTablas.BSQCLSDOC;
                break;
            case "BSQGPOCOMP":
                tabla=AyudaBusquedaTablas.BSQGPOCOMP;
                break;
            case "BSQARMCOM":
                tabla=AyudaBusquedaTablas.BSQARMCOM;
                break;
            case "BSQEMPLANTA":
                tabla=AyudaBusquedaTablas.BSQEMPLANTA;
                break;
            case "BSQTEMPORADA":
                tabla=AyudaBusquedaTablas.BSQTEMPORADA;
                break;
            case "BSQEQUIPO":
                tabla=AyudaBusquedaTablas.BSQEQUIPO;
                break;
            case "BSQALMACEN":
                tabla=AyudaBusquedaTablas.BSQALMACEN;
                break;
            case "BSQCOCINERO":
                tabla=AyudaBusquedaTablas.BSQCOCINERO;
                break;
        }

        return tabla;
    }

    public String[] BuscarFields(String nombreAyuda){

        String[]fields={};

        switch (nombreAyuda) {
            case "BSQPLANTAS":
                fields = AyudaBusquedaFields.BSQPLANTAS;
                break;
            case "BSQALMACENES":
                fields = AyudaBusquedaFields.BSQALMACENES;
                break;
            case "BSQEMPRESA":
                fields = AyudaBusquedaFields.BSQEMPRESA;
                break;
            case "BSQCENTRO":
                fields = AyudaBusquedaFields.BSQCENTRO;
                break;
            case "BSQUBICTNC":
                fields = AyudaBusquedaFields.BSQUBICTNC;
                break;
            case "BSQEQPMT":
                fields = AyudaBusquedaFields.BSQEQPMT;
                break;
            case "BSQPROV":
                fields = AyudaBusquedaFields.BSQPROV;
                break;
            case "BSQCLTE":
                fields = AyudaBusquedaFields.BSQCLTE;
                break;
            case "BSQMAT":
                fields = AyudaBusquedaFields.BSQMAT;
                break;
            case "BSQESPEC":
                fields = AyudaBusquedaFields.BSQESPEC;
                break;
            case "BSQCIRCTCN":
                fields = AyudaBusquedaFields.BSQCIRCTCN;
                break;
            case "BSQPUERTO":
                fields = AyudaBusquedaFields.BSQPUERTO;
                break;
            case "BSQUNDEXT":
                fields = AyudaBusquedaFields.BSQUNDEXT;
                break;
            case "BSQUSR":
                fields = AyudaBusquedaFields.BSQUSR;
                break;
            case "BSQPEDCOMP":
                fields = AyudaBusquedaFields.BSQPEDCOMP;
                break;
            case "BSQCLSDOC":
                fields = AyudaBusquedaFields.BSQCLSDOC;
                break;
            case "BSQGPOCOMP":
                fields = AyudaBusquedaFields.BSQGPOCOMP;
                break;
            case "BSQARMCOM":
                fields = AyudaBusquedaFields.BSQARMCOM;
                break;
            case "BSQEMPLANTA":
                fields = AyudaBusquedaFields.BSQEMPLANTA;
                break;
            case "BSQTEMPORADA":
                fields = AyudaBusquedaFields.BSQTEMPORADA;
                break;
            case "BSQEQUIPO":
                fields = AyudaBusquedaFields.BSQEQUIPO;
                break;
            case "BSQALMACEN":
                fields = AyudaBusquedaFields.BSQALMACEN;
                break;
            case "BSQCOCINERO":
                fields = AyudaBusquedaFields.BSQCOCINERO;
                break;
        }
        logger.error("AyudasBusqueda fields= "+fields[0]);
        return fields;
    }

    public List<MaestroOptions> BuscarOptions(String nombreAyuda){

        List<MaestroOptions> options= new ArrayList<>();

        MaestroOptions opt= new MaestroOptions();
        boolean noExists=false;

        switch (nombreAyuda){
            case "BSQPLANTAS":
                opt.setWa(AyudaBusquedaOptions.BSQPLANTAS);
                break;
            case "BSQMAT":
                opt.setWa(AyudaBusquedaOptions.BSQMAT);
                break;
            case "BSQESPEC":
                opt.setWa(AyudaBusquedaOptions.BSQESPEC);
                break;
            case "BSQPUERTO":
                opt.setWa(AyudaBusquedaOptions.BSQPUERTO);
                break;
            case "BSQUNDEXT":
                opt.setWa(AyudaBusquedaOptions.BSQUNDEXT);
                break;
            case "BSQUSR":
                opt.setWa(AyudaBusquedaOptions.BSQUSR);
                break;
            case "BSQPEDCOMP":
                opt.setWa(AyudaBusquedaOptions.BSQPEDCOMP);
                break;
            case "BSQCLSDOC":
                opt.setWa(AyudaBusquedaOptions.BSQCLSDOC);
                break;
            case "BSQCENTRO":
                opt.setWa(AyudaBusquedaOptions.BSQCENTRO);
                break;
            case "BSQTEMPORADA":
                opt.setWa(AyudaBusquedaOptions.BSQTEMPORADA);
                break;
            case "BSQALMACEN":
                opt.setWa(AyudaBusquedaOptions.BSQPLANTAS);
                break;
            case "BSQCOCINERO":
                opt.setWa(AyudaBusquedaOptions.BSQCOCINERO);
                break;
            default:
                noExists=true;
                break;
        }

        if(!noExists){
            options.add(opt);
        }

        return options;
    }

    public AyudaBusquedaExports BuscarEmbaHorometro(){

        AyudaBusquedaExports dto= new AyudaBusquedaExports();
        try{


            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_CONS_EMBARCA_BTP);
            JCoParameterList importx = stfcConnection.getImportParameterList();

            importx.setValue("P_USER", "FGARCIA");


            JCoParameterList tables = stfcConnection.getTableParameterList();
            JCoTable tableImport = tables.getTable(Tablas.P_OPTIONS);
            tableImport.appendRow();
            tableImport.setValue("WA", AyudaBusquedaOptions.BSQEMBHORO);
            tableImport.appendRow();
            tableImport.setValue("WA", AyudaBusquedaOptions.BSQEMBHORO1);

            stfcConnection.execute(destination);

            JCoTable STR_EMB = tables.getTable(Tablas.STR_EMB);

            Metodos exec= new Metodos();
            List<HashMap<String, Object>> data=exec.ObtenerListObjetos(STR_EMB,AyudaBusquedaFields.BSQEMBHORO) ;

            dto.setData(data);
            dto.setMensaje("Ok");
        }catch (Exception e){
            dto.setMensaje(e.getMessage());
        }

        return dto;
    }

    public HashMap<String, Object> BuscarNombreDominio(String nomCampo){
        logger.error("BuscarNombreDominio");
        HashMap<String, Object>data= new HashMap<>();

        switch (nomCampo){
            case "INPRP":
                data.put("DOMINIO", "ZINPRP");
                data.put("CAMPO", nomCampo);
                break;
            case "ESREG":
                data.put("DOMINIO", "ZESREG");
                data.put("CAMPO", nomCampo);
                break;
        }
        //  logger.error("data1: "+data.get(0).toString());

        return data;
    }

    public String BuscarDominio(String nomDomino, String valor)throws Exception{


        String descripcion="";

        DominioParams dominioParams= new DominioParams();
        dominioParams.setDomname(nomDomino);
        dominioParams.setStatus("A");

        List<DominioParams> ListDominioParams= new ArrayList<>();
        ListDominioParams.add(dominioParams);

        DominiosImports dominiosImports=new DominiosImports();
        dominiosImports.setDominios(ListDominioParams);

        DominioDto dominioDto=jcoDominiosService.Listar(dominiosImports);

        List<DominiosExports>ListaDomExports=dominioDto.getData();

        for(int i=0; i<ListaDomExports.size();i++){
            DominiosExports dominiosExports=ListaDomExports.get(i);
            List<DominioExportsData> ListaDomExportData=dominiosExports.getData();

            for(int j=0; j<ListaDomExportData.size();j++){
                DominioExportsData dominioExportsData=ListaDomExportData.get(j);

                if(valor.equals(dominioExportsData.getId())){
                    descripcion=dominioExportsData.getDescripcion();

                }
            }

        }

        return descripcion;
    }

    public List<HashMap<String, Object>> ListaDataConDominio(MaestroExport me, String nomTabla)throws Exception{

        List<HashMap<String, Object>> ndata= new ArrayList<>();
        HashMap<String, Object> nombreDominio=BuscarNombreDominio(nomTabla);
        String campo="";
        String dom="";
        for (Map.Entry<String, Object> entry :nombreDominio.entrySet()) {

            if(entry.getKey().equals("DOMINIO")){
                dom=entry.getValue().toString();

            }else if(entry.getKey().equals("CAMPO")){
                campo=entry.getValue().toString();

            }
        }

        String valor="";

        List<HashMap<String, Object>> data= me.getData();

        for(int i=0; i<data.size();i++){
            HashMap<String, Object> registro=data.get(i);

            for (Map.Entry<String, Object> entry :registro.entrySet()) {

                if(entry.getKey().equals(campo)){

                    valor=entry.getValue().toString();

                }
            }

            String descripcion="";
            if(!valor.equals("")){
                descripcion=BuscarDominio(dom, valor);
            }


            String field="DESC_"+campo;

            registro.put(field, descripcion);

            ndata.add(registro);
        }

        return ndata;
    }

    public AyudaBusquedaExports BuscarEmbarcacion(String p_user)throws Exception{

        AyudaBusquedaExports dto= new AyudaBusquedaExports();

        EmbarcacionImports ei=new EmbarcacionImports();

        try {
            List<MaestroOptions> options = new ArrayList<>();

            MaestroOptions option = new MaestroOptions();
            option.setWa(AyudaBusquedaOptions.BSQEMBARCA);

            options.add(option);
            ei.setOption(options);
            ei.setP_pag("");
            ei.setP_user(p_user);
            EmbarcacionExports ee = jcoEmbarcacion.ListarEmbarcaciones(ei);

            dto.setData(ee.getData());
            dto.setMensaje(ee.getMensaje());

        }catch (Exception e){

            dto.setMensaje(e.getMessage());
        }

        return dto;
    }

    public CampoTablaExports UpdateEmbarcacionMasivo(UpdateEmbarcaMasivoImports imports)throws Exception{

        CampoTablaExports dto= new CampoTablaExports();

        try {
            CampoTablaImports cti = new CampoTablaImports();

            List<SetDto> ListSetDto = new ArrayList<>();


            String tabla = "ZFLEMB";

            if (imports.getId().equals("CAPTANQ")) {

                for (int i = 0; i < imports.getStr_set().size(); i++) {
                    SetDto set = new SetDto();
                    set.setCmopt("CDEMB = '" + imports.getStr_set().get(i).getCDEMB() + "'");
                    set.setCmset("CDTAN = '" + imports.getStr_set().get(i).getCDTAN() + "'");
                    set.setNmtab(tabla);
                    ListSetDto.add(set);
                    logger.error("OPT = " + set.getCmopt());
                    logger.error("OPT = " + set.getCmset());
                }


            } else if (imports.getId().equals("CUOTAEMB")) {

                for (int i = 0; i < imports.getStr_set().size(); i++) {
                    SetDto set = new SetDto();
                    set.setCmopt("CDEMB = '" + imports.getStr_set().get(i).getCDEMB() + "'");
                    set.setCmset("CPNCN = '" + imports.getStr_set().get(i).getCPNCN() + "' CPNSR" + imports.getStr_set().get(i).getCPNSR() + "'");
                    set.setNmtab(tabla);
                    ListSetDto.add(set);
                    logger.error("OPT = " + set.getCmopt());
                    logger.error("OPT = " + set.getCmset());
                }

            } else if (imports.getId().equals("EMBNOM")) {

                for (int i = 0; i < imports.getStr_set().size(); i++) {
                    SetDto set = new SetDto();
                    set.setCmopt("CDEMB = '" + imports.getStr_set().get(i).getCDEMB() + "'");
                    set.setCmset("CNVPS = '" + imports.getStr_set().get(i).getCNVPS() + "'");
                    set.setNmtab(tabla);
                    ListSetDto.add(set);
                    logger.error("OPT = " + set.getCmopt());
                    logger.error("OPT = " + set.getCmset());
                }
            } else if (imports.getId().equals("TRIPEMB")) {

                for (int i = 0; i < imports.getStr_set().size(); i++) {
                    SetDto set = new SetDto();
                    set.setCmopt("CDEMB = '" + imports.getStr_set().get(i).getCDEMB() + "'");
                    set.setCmset("NRTRI = '" + imports.getStr_set().get(i).getNRTRI() + "'");
                    set.setNmtab(tabla);
                    ListSetDto.add(set);
                    logger.error("OPT = " + set.getCmopt());
                    logger.error("OPT = " + set.getCmset());
                }
            }


            cti.setP_user(imports.getP_user());
            cti.setStr_set(ListSetDto);

            for (int i = 0; i < cti.getStr_set().size(); i++) {
                logger.error("cti opt= " + cti.getStr_set().get(i).getCmopt());
                logger.error("cti set= " + cti.getStr_set().get(i).getCmset());
                logger.error("cti tab= " + cti.getStr_set().get(i).getNmtab());
            }

            dto = jcoCampoTablaService.Actualizar(cti);

        }catch (Exception e){
            dto.setMensaje(e.getMessage());
        }
        return dto;
    }

    public CampoTablaExports UpdateTripulantesMasivo(UpdateTripuMasivoImports imports)throws Exception{

        CampoTablaExports dto= new CampoTablaExports();
        try {

            CampoTablaImports cti = new CampoTablaImports();

            List<SetDto> ListSetDto = new ArrayList<>();


            String tabla = "ZTFL_TRIOBS";

            for (int i = 0; i < imports.getStr_set().size(); i++) {

                SetDto set = new SetDto();
                set.setCmopt("BUKRS = '" + imports.getStr_set().get(i).getBUKRS() + "' AND PERNR = '" + imports.getStr_set().get(i).getPERNR() + "'");
                set.setCmset("INDAPT = '" + imports.getStr_set().get(i).getINDAPT() + "' FEOBS ='" + imports.getStr_set().get(i).getFEOBS() + "' OBSER ='" + imports.getStr_set().get(i).getOBSER() + "'");
                set.setNmtab(tabla);
                ListSetDto.add(set);
                logger.error("OPT = " + set.getCmopt());
                logger.error("OPT = " + set.getCmset());
            }


            cti.setP_user(imports.getP_user());
            cti.setStr_set(ListSetDto);

            for (int i = 0; i < cti.getStr_set().size(); i++) {
                logger.error("cti opt= " + cti.getStr_set().get(i).getCmopt());
                logger.error("cti set= " + cti.getStr_set().get(i).getCmset());
                logger.error("cti tab= " + cti.getStr_set().get(i).getNmtab());
            }

            dto = jcoCampoTablaService.Actualizar(cti);
        }catch(Exception ex) {
            dto.setMensaje(ex.getMessage());
        }

        return dto;
    }

    public CampoTablaExports UpdateMasivo(UpdateMasivoImports imports)throws Exception{

        CampoTablaExports dto= new CampoTablaExports();
        try {

            CampoTablaImports cti = new CampoTablaImports();

            List<SetDto> ListSetDto = new ArrayList<>();

            for(int i=0; i<imports.getStr_set().size(); i++){

                UpdateMasivoDto um= imports.getStr_set().get(i);

                List<HashMap<String, Object>> options= um.getOptions();

                String cmopt="";
                int tam= options.get(i).size();
                int con=0;
                for (Map.Entry<String, Object>entry:options.get(i).entrySet()){

                    String key=entry.getKey();
                    String value=entry.getValue().toString();

                    if(tam-1==con){
                        cmopt+=key+" = '"+value+"'";
                    }else{
                        cmopt+=key+" = '"+value +"' AND ";
                    }
                    con++;
                }

                List<HashMap<String, Object>> values= um.getValues();

                String cmset="";
                tam= values.get(i).size();
                con=0;
                for (Map.Entry<String, Object>entry:values.get(i).entrySet()){

                    String key=entry.getKey();
                    String value=entry.getValue().toString();

                    if(tam-1==con){
                        cmset += key + " = '" + value + "' ";
                    }else {
                        cmset += key + " = '" + value + "'";
                    }
                    con++;
                }

                SetDto set = new SetDto();
                set.setCmopt(cmopt);
                set.setCmset(cmset);
                set.setNmtab(imports.getTabla());
                ListSetDto.add(set);
            }






            cti.setP_user(imports.getP_user());
            cti.setStr_set(ListSetDto);

            for (int i = 0; i < cti.getStr_set().size(); i++) {
                logger.error("cti "+i+" opt ="  + cti.getStr_set().get(i).getCmopt());
                logger.error("cti "+i+" set = " + cti.getStr_set().get(i).getCmset());
                logger.error("cti "+i+" tab  = " + cti.getStr_set().get(i).getNmtab());
            }

            dto.setMensaje("prueba");
            //dto = jcoCampoTablaService.Actualizar(cti);
        }catch(Exception ex) {
            dto.setMensaje(ex.getMessage());
        }

        return dto;
    }

    @Override
    public UpdateTableExports Update_Table_Maestro(HiscomDTOImport imports) throws Exception {
        Metodos metodos = new Metodos();
        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        ;
        JCoRepository repo = destination.getRepository();
        ;
        JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_UPDATE_TABLE);
        JCoParameterList importx = stfcConnection.getImportParameterList();
        importx.setValue("I_TABLE","ZTFL_HISCOM");
        importx.setValue("P_FLAG","X");
        importx.setValue("P_CASE","E");
        importx.setValue("P_USER",imports.getUser());



        JCoParameterList jcoTables = stfcConnection.getTableParameterList();
        JCoTable tableImport = jcoTables.getTable(Tablas.T_DATA);
        tableImport.appendRow();
        String cadena= "|"+imports.getCdpcn()+"|"+imports.getIebpt()+"|"+"|"+imports.getCdgre()+"|"+imports.getCdemp()+"|"+imports.getCdemb()+"|"+imports.getCdpta()+"|";
        logger.error("Execute_ZFL_RFC_UPDATE_TABLE DATA: "+cadena);
        tableImport.setValue("DATA", cadena);
        logger.error("Execute_ZFL_RFC_UPDATE_TABLE_3");
        stfcConnection.execute(destination);
        JCoTable tableExport = jcoTables.getTable(Tablas.T_MENSAJE);
        Metodos me = new Metodos();

        List<HashMap<String, Object>> t_mensaje=me.ListarObjetos(tableExport);
        UpdateTableExports dto = new UpdateTableExports();

        dto.setT_mensaje(t_mensaje);

        return  dto;

    }

    @Override
    public ArrayList<EstructurasRfc> obtenerEstructurasRfc(String funcion)throws Exception{
        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction rfc = repo.getFunction(funcion);
        JCoParameterList jcoTables = rfc.getTableParameterList();
        JCoParameterFieldIterator iterator = jcoTables.getParameterFieldIterator();
        ArrayList<EstructurasRfc> eRfcLista = new ArrayList<EstructurasRfc>();
        while (iterator.hasNextField()){
            EstructurasRfc eRfc = new EstructurasRfc();
            JCoParameterField field = iterator.nextParameterField();
            String NombreTabla = field.getName();
            eRfc.setNombreTabla(NombreTabla);
            JCoTable tabla = jcoTables.getTable(NombreTabla);
            JCoFieldIterator fields = tabla.getFieldIterator();
            ArrayList<CamposEstructuraRfc> ceRfcLista = new ArrayList<CamposEstructuraRfc>();
            while (fields.hasNextField()){
                JCoField fieldTable = fields.nextField();
                CamposEstructuraRfc  ceRfc = new CamposEstructuraRfc();
                ceRfc.setNombreCampo(fieldTable.getName());
                ceRfc.setDescripcion(fieldTable.getDescription());
                ceRfc.setTipoDato(fieldTable.getTypeAsString());
                ceRfc.setDecimales(fieldTable.getDecimals());
                ceRfc.setLongitud(fieldTable.getLength());
                ceRfcLista.add(ceRfc);
            }
            eRfc.setCampos(ceRfcLista);
            eRfcLista.add(eRfc);
        }
        return  eRfcLista;
    }


}
