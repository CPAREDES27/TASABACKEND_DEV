package com.incloud.hcp.jco.controlLogistico.service.impl;

import com.incloud.hcp.jco.controlLogistico.dto.RepModifDatCombusExports;
import com.incloud.hcp.jco.controlLogistico.dto.RepModifDatCombusImports;
import com.incloud.hcp.jco.controlLogistico.service.JCORepModifDatCombusService;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JCORepModifDatCombusImpl implements JCORepModifDatCombusService {

    public RepModifDatCombusExports Listar(RepModifDatCombusImports imports)throws Exception{

        RepModifDatCombusExports rmdc= new RepModifDatCombusExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_COMB_CONS_MODIF_DATOS);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_FASE", imports.getP_fase());
            importx.setValue("P_CANT", imports.getP_cant());

            JCoParameterList tables = stfcConnection.getTableParameterList();



            stfcConnection.execute(destination);
            JCoParameterList export=stfcConnection.getExportParameterList();
           rmdc.setP_nmob(export.getValue("P_NMOB").toString());
            rmdc.setP_nmar(export.getValue("P_NMAR").toString());

            JCoTable T_FLOCC = tables.getTable(Tablas.T_FLOCC);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);
            JCoTable T_OPCIONES= tables.getTable(Tablas.T_OPCIONES);

            Metodos metodo = new Metodos();
            //List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetos(T_MENSAJE);
            String[] fieldsT_flocc=imports.getFieldsT_flocc();
            String[] fieldsT_opciones=imports.getFieldsT_opciones();
            String[] fieldsT_mensaje=imports.getFieldsT_mensaje();

            List<HashMap<String, Object>> t_flocc = metodo.ObtenerListObjetos(T_FLOCC, fieldsT_flocc);
            List<HashMap<String, Object>> t_opciones = metodo.ObtenerListObjetos(T_MENSAJE, fieldsT_opciones);
            List<HashMap<String, Object>> t_mensaje = metodo.ObtenerListObjetos(T_OPCIONES, fieldsT_mensaje);

            rmdc.setT_opciones(t_opciones);
            rmdc.setT_mensaje(t_mensaje);
            rmdc.setT_flocc(t_flocc);
            rmdc.setMensaje("Ok");
        }catch (Exception e){
            rmdc .setMensaje(e.getMessage());
        }

        return rmdc;
    }

}
