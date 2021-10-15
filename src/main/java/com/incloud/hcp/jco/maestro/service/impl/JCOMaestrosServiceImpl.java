package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.maestro.service.JCOMaestrosService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.lang.reflect.Array;
import java.util.*;

@Service
public class JCOMaestrosServiceImpl implements JCOMaestrosService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


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
        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        ;
        JCoRepository repo = destination.getRepository();
        ;
        JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_READ_TABLE);
        JCoParameterList importx = stfcConnection.getImportParameterList();

        importx.setValue("DELIMITER","|");
        importx.setValue("QUERY_TABLE","LFA1");
        importx.setValue("ROWCOUNT",codigo.getCodigo());


        JCoParameterList tables = stfcConnection.getTableParameterList();

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

    public MensajeDto editarMaestro (MaestroEditImports importsParam) throws Exception{

        //DESPUES
        MensajeDto msj= new MensajeDto();
        try {
            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("I_TABLE", importsParam.getTabla());
            imports.put("P_FLAG", importsParam.getFlag());
            imports.put("P_CASE", importsParam.getP_case());
            imports.put("P_USER", importsParam.getP_user());
            EjecutarRFC exec = new EjecutarRFC();

            msj = exec.Execute_ZFL_RFC_UPDATE_TABLE(imports, importsParam.getData());


        }catch (Exception e){

            msj.setMANDT("00");
            msj.setCMIN("Error");
            msj.setCDMIN("Exception");
            msj.setDSMIN(e.getMessage());
        }
        return msj;


    }
    public MensajeDto editarMaestro2 (MaestroEditImport importsParam) throws Exception{

        //DESPUES
        MaestroExport me= new MaestroExport();
        MensajeDto msj= new MensajeDto();
        try {

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
               valor = update.get(i).getValor();
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

        }catch (Exception e){
            msj.setMANDT("00");
            msj.setCMIN("Error");
            msj.setCDMIN("Exception");
            msj.setDSMIN(e.getMessage());
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
            String tabla=(Buscartabla(importsParam.getNombreAyuda()));

            logger.error("AyudasBusqueda TABLA= "+tabla);
            //setear mapeo de parametros import
            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("QUERY_TABLE", tabla);
            imports.put("DELIMITER", "|");
            imports.put("NO_DATA", "");
            imports.put("ROWSKIPS", "");
            imports.put("ROWCOUNT", "");
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
                logger.error("AyudasBusqueda options= "+mo.getWa());
                record.put("WA", mo.getWa());
                tmpOptions.add(record);
            }
            logger.error("AyudasBusqueda_4");

            String []fields=BuscarFields(importsParam.getNombreAyuda());
            logger.error("AyudasBusqueda_5");
            //ejecutar RFC ZFL_RFC_READ_TABLE
            EjecutarRFC exec = new EjecutarRFC();
            me = exec.Execute_ZFL_RFC_READ_TABLE(imports, tmpOptions, fields);
            logger.error("AyudasBusqueda_6");

            dto.setData(me.getData());
            dto.setMensaje("Ok");
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
        }
        logger.error("AyudasBusqueda fields= "+fields[0]);
        return fields;
    }

    public List<MaestroOptions> BuscarOptions(String nombreAyuda){

        List<MaestroOptions> options= new ArrayList<>();

        MaestroOptions opt= new MaestroOptions();


        if(nombreAyuda.equals("BSQPLANTAS") || nombreAyuda.equals("BSQMAT") || nombreAyuda.equals("BSQESPEC") || nombreAyuda.equals("BSQPUERTO") ||
                nombreAyuda.equals("BSQUNDEXT") ||nombreAyuda.equals("BSQUSR") ||nombreAyuda.equals("BSQPEDCOMP") ||nombreAyuda.equals("BSQCLSDOC") ){
            logger.error("ENTRO AL IF QUE EVALUA 1");

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
            }
            options.add(opt);
        }




        return options;
    }


}
