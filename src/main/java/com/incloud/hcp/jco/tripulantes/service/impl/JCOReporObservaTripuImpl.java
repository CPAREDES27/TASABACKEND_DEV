package com.incloud.hcp.jco.tripulantes.service.impl;

import com.incloud.hcp.jco.tripulantes.dto.Options;
import com.incloud.hcp.jco.tripulantes.dto.ReporObservaTripuExports;
import com.incloud.hcp.jco.tripulantes.dto.ReporObservaTripuImports;
import com.incloud.hcp.jco.tripulantes.service.JCOReporObservaTripuService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.apache.poi.ss.formula.functions.T;
//import org.omg.CORBA.ObjectHelper;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JCOReporObservaTripuImpl implements JCOReporObservaTripuService {

    @Override
    public ReporObservaTripuExports ReporteObservacionesTripulantes(ReporObservaTripuImports imports) throws Exception {

        ReporObservaTripuExports rot=new ReporObservaTripuExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_REG_SALUD_TRIP);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("IP_TOPE", imports.getIp_tope());
            importx.setValue("IP_PERNR", imports.getIp_pernr());
            importx.setValue("IP_VORNA", imports.getIp_vorna());
            importx.setValue("IP_NACHN", imports.getIp_nachn());
            importx.setValue("IP_NACH2", imports.getIp_nach2());
            importx.setValue("IP_INDAT", imports.getIp_indat());
            importx.setValue("IP_CDEMB", imports.getIp_cdemb());
            importx.setValue("IP_CDGFL", imports.getIp_cdgfl());


            JCoParameterList tables = stfcConnection.getTableParameterList();
            JCoParameterList export = stfcConnection.getExportParameterList();
            stfcConnection.execute(destination);

            JCoTable T_TRIOBS = tables.getTable(Tablas.T_TRIOBS);
            JCoStructure T_MENSAJE = export.getStructure(Tablas.T_MENSAJE);

            Metodos metodo = new Metodos();
            List<HashMap<String, Object>>  t_triobs = metodo.ObtenerListObjetos(T_TRIOBS, imports.getFieldst_triobs());

            List<HashMap<String, Object>> t_mensaje=new ArrayList<HashMap<String, Object>>();
            HashMap<String, Object> record=new HashMap<>();
            for(int i=0; i<T_MENSAJE.getFieldCount();i++){
                record.put(T_MENSAJE.getField(i).toString(), T_MENSAJE.getValue(i).toString());
            }

            rot.setT_triobs(t_triobs);
            rot.setT_mensaje(t_mensaje);
            rot.setMensaje("Ok");

        }catch (Exception e){
            rot.setMensaje(e.getMessage());
        }

        return rot;
    }
}
