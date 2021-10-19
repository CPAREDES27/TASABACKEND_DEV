package com.incloud.hcp.jco.controlLogistico.service.impl;

import com.incloud.hcp.jco.controlLogistico.dto.*;
import com.incloud.hcp.jco.controlLogistico.service.JCOValeVivereService;
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
public class JCOValeViveresImpl implements JCOValeVivereService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public ValeViveresExports ListarValeViveres(ValeViveresImports imports)throws Exception{

        ValeViveresExports vve= new ValeViveresExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_LECT_MAES_VIVER);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            importx.setValue("ROWCOUNT", imports.getRowcount());

            List<Options> options = imports.getOptions();
            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < options.size(); i++) {
                Options o = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();

                record.put("TEXT", o.getText());
                tmpOptions.add(record);
            }

            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC exec= new EjecutarRFC();
            exec.setTable(tables, Tablas.OPTIONS,tmpOptions);

            stfcConnection.execute(destination);

            JCoTable S_DATA = tables.getTable(Tablas.S_DATA);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);

             Metodos metodo = new Metodos();
            String [] fields=imports.getFields();
            List<HashMap<String, Object>> s_data = metodo.ObtenerListObjetos(S_DATA, fields);
            List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetos(T_MENSAJE);

            vve.setT_mensaje(t_mensaje);
            vve.setData(s_data);
            vve.setMensaje("Ok");
        }catch (Exception e){
            vve .setMensaje(e.getMessage());
        }

        return vve;
    }
    public VvGuardaExports GuardarValeViveres(VvGuardaImports imports)throws Exception{

        VvGuardaExports vve = new VvGuardaExports();

        try{

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_VALE_VIVER);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            JCoParameterList export = stfcConnection.getExportParameterList();

            EjecutarRFC exe = new EjecutarRFC();
            exe.setTable(tables, Tablas.ST_VVI, imports.getSt_vvi());
            exe.setTable(tables, Tablas.ST_PVA, imports.getSt_pva());

            stfcConnection.execute(destination);

            vve.setP_orden(export.getValue("P_ORDEN").toString());
            vve.setP_merc(export.getValue("P_MERC").toString());
            vve.setP_vale(export.getValue("P_VALE").toString());

            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);
            JCoTable ST_VVI = tables.getTable(Tablas.ST_VVI);

            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> st_vvi = metodo.ListarObjetos(ST_VVI);
            List<HashMap<String, Object>> t_mensaje = metodo.ObtenerListObjetos(T_MENSAJE, imports.getFieldsT_mensaje());

            vve.setT_mensaje(t_mensaje);
            vve.setMensaje("Ok");

        }catch (Exception e){
        vve .setMensaje(e.getMessage());
        }
        return vve;
    }
    public CostoRacionValevExports CostoRacionValev(CostoRacionValevImports imports)throws Exception{

        CostoRacionValevExports dto= new CostoRacionValevExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_COST_RACI_VALEV);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            importx.setValue("P_CENTRO", imports.getP_centro());
            importx.setValue("P_PROVEEDOR", imports.getP_proveedor());
            importx.setValue("P_CODE", imports.getP_code());

            List<Options> options = imports.getOptions();
            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < options.size(); i++) {
                Options o = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();

                record.put("TEXT", o.getText());
                tmpOptions.add(record);
            }

            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC exec= new EjecutarRFC();
            exec.setTable(tables, Tablas.OPTIONS,tmpOptions);

            stfcConnection.execute(destination);

            JCoTable S_DATA = tables.getTable(Tablas.S_DATA);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);

            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> s_data = metodo.ObtenerListObjetos(S_DATA, imports.getFieldS_data());
            List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetos(T_MENSAJE);

            dto.setT_mensaje(t_mensaje);
            dto.setS_data(s_data);
            dto.setMensaje("Ok");
        }catch (Exception e){
            dto .setMensaje(e.getMessage());
        }

        return dto;
    }
    public AnularValevExports AnularValev(AnularValevImports imports) throws Exception {

        AnularValevExports dto= new AnularValevExports();
        try {
            logger.error("ANULAR VALE VIVERES");
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            logger.error("ANULAR VALE VIVERES_1");
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_DELETE_PO);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            importx.setValue("P_VALE", imports.getP_vale());
            importx.setValue("P_ANULA", imports.getP_anula());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            logger.error("ANULAR VALE VIVERES_2");
            stfcConnection.execute(destination);

            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);
            logger.error("ANULAR VALE VIVERES_3");
            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetos(T_MENSAJE);
            logger.error("ANULAR VALE VIVERES_4");
            dto.setT_mensaje(t_mensaje);
            dto.setMensaje("Ok");
            logger.error("ANULAR VALE VIVERES_5");
        }catch (Exception e){
            logger.error("catch ");
            dto .setMensaje(e.getMessage());

        }
        return dto;
    }


}
