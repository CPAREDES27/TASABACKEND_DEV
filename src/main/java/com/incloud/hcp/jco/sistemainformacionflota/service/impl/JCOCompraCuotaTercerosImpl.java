package com.incloud.hcp.jco.sistemainformacionflota.service.impl;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.sistemainformacionflota.dto.CompraCuotaTercerosExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.CompraCuotaTercerosImports;
import com.incloud.hcp.jco.sistemainformacionflota.service.JCOCompraCuotaTercerosService;
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
public class JCOCompraCuotaTercerosImpl implements JCOCompraCuotaTercerosService {

    @Override
    public CompraCuotaTercerosExports CompraCuotaTerceros(CompraCuotaTercerosImports imports) throws Exception {

        CompraCuotaTercerosExports cct=new CompraCuotaTercerosExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_CUOTA_EMB_TER);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_TCONS", imports.getP_tcons());
            importx.setValue("P_CDUSR", imports.getP_cdusr());


            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC exec=new EjecutarRFC();
            exec.setTable(tables,Tablas.STR_CET, imports.getStr_cet());

            stfcConnection.execute(destination);

            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);


            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetos(T_MENSAJE);

           cct.setT_mensaje(t_mensaje);
            cct.setMensaje("Ok");

        }catch (Exception e){
            cct.setMensaje(e.getMessage());
        }
        return cct;
    }
}
