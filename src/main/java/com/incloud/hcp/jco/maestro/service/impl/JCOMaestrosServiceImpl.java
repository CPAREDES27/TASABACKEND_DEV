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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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


    public MaestroExport obtenerMaestro2 (MaestroImports importsParam) throws Exception {

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
            me = exec.Execute_ZFL_RFC_READ_TABLE2(imports, tmpOptions, fields);

        }catch (Exception e){
            me.setMensaje(e.getMessage());
        }

        return me;
    }

    public MensajeDto editarMaestro (MaestroEditImports importsParam) throws Exception{

        MensajeDto msj= new MensajeDto();
        try {
            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("I_TABLE", importsParam.getTabla());
            imports.put("P_FLAG", importsParam.getFlag());
            imports.put("P_CASE", importsParam.getP_case());
            imports.put("P_USER", importsParam.getP_user());

            logger.error("EditarMaestro_1");
            //ejecutar RFC ZFL_RFC_READ_TABLE
            EjecutarRFC exec = new EjecutarRFC();
            logger.error("EditarMaestro_2");
            msj = exec.Execute_ZFL_RFC_UPDATE_TABLE(imports, importsParam.getData());
            logger.error("EditarMaestro_3");

        }catch (Exception e){

            msj.setMANDT("00");
            msj.setCMIN("Error");
            msj.setCDMIN("Exception");
            msj.setDSMIN(e.getMessage());
        }
        return msj;

    }

    public AppMaestrosExports appMaestros(AppMaestrosImports imports)throws Exception{

        AppMaestrosExports ame=new AppMaestrosExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_APP_MAESTROS);
            logger.error("stfcConnection: "+stfcConnection.toString());
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_ROL", imports.getP_rol());
            importx.setValue("P_APP", imports.getP_app());


            JCoParameterList tables = stfcConnection.getExportParameterList();
            stfcConnection.execute(destination);

            stfcConnection.execute(destination);

            JCoTable t_approles = tables.getTable(Tablas.T_APPROLES);
            JCoTable t_tabcolumna = tables.getTable(Tablas.T_TABCOLUMNA);
            JCoTable t_tabselec = tables.getTable(Tablas.T_TABSELEC);
            JCoTable t_tabform = tables.getTable(Tablas.T_TABFORM);


            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> List_t_approles = metodo.ListarObjetos(t_approles);
            List<HashMap<String, Object>> List_t_tabcolumna = metodo.ListarObjetos(t_tabcolumna);
            List<HashMap<String, Object>> List_t_tabselec = metodo.ListarObjetos(t_tabselec);
            List<HashMap<String, Object>> List_t_tabform = metodo.ListarObjetos(t_tabform);

            ame.setT_approles(List_t_approles);
            ame.setT_tabcolumna(List_t_tabcolumna);
            ame.setT_tabselec(List_t_tabselec);
            ame.setT_tabform(List_t_tabform);
            ame.setMensaje("Ok");

        }catch (Exception e){
            ame.setMensaje(e.getMessage());
        }

        return ame;
    }



}
