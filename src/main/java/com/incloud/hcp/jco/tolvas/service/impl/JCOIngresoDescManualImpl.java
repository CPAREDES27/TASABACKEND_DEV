package com.incloud.hcp.jco.tolvas.service.impl;

import com.incloud.hcp.jco.tolvas.dto.IngresoDesManualExports;
import com.incloud.hcp.jco.tolvas.dto.IngresoDescManualImports;
import com.incloud.hcp.jco.tolvas.service.JCOIngresoDescManualService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class JCOIngresoDescManualImpl implements JCOIngresoDescManualService {

    public IngresoDesManualExports Guardar(IngresoDescManualImports imports)throws Exception{

        IngresoDesManualExports idm= new IngresoDesManualExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_SAVE_DESC_MANUAL);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());


            JCoParameterList tables = stfcConnection.getTableParameterList();
            EjecutarRFC exec=new EjecutarRFC();
            exec.setTable(tables, Tablas.STR_DES, imports.getStr_des());
            stfcConnection.execute(destination);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);

            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> t_mensaje = metodo.ObtenerListObjetos(T_MENSAJE, imports.getFieldst_mensaje());

            idm.setT_mensaje(t_mensaje);
            idm.setMensaje("Ok");

        }catch (Exception e){
            idm .setMensaje(e.getMessage());
        }
        return idm;
    }

}