package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.CalendarioTemporadaExports;
import com.incloud.hcp.jco.maestro.dto.CalendarioTemporadaImports;
import com.incloud.hcp.jco.maestro.service.JCOCalendarioTemporadaService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class JCOCalendarioTemporadaImpl implements JCOCalendarioTemporadaService {

    public CalendarioTemporadaExports Guadar(CalendarioTemporadaImports imports)throws Exception{

        CalendarioTemporadaExports dto= new CalendarioTemporadaExports();


        try{
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_CALEN_TEMP_PESC);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());

            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC exec= new EjecutarRFC();
            exec.setTable(tables, Tablas.T_CAL,imports.getT_cal());

            stfcConnection.execute(destination);

            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);

            Metodos me=new Metodos();
            List<HashMap<String, Object>> t_mensaje=me.ListarObjetos(T_MENSAJE);

            dto.setT_mensaje(t_mensaje);
            dto.setMensaje("Ok");

        }catch (Exception e){

            dto.setMensaje(e.getMessage());
        }
        return dto;

    }
}
