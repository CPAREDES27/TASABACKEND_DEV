package com.incloud.hcp.jco.tripulantes.service.impl;

import com.incloud.hcp.jco.tripulantes.dto.Options;
import com.incloud.hcp.jco.tripulantes.dto.SeguimientoTripuExports;
import com.incloud.hcp.jco.tripulantes.dto.SeguimientoTripuImports;
import com.incloud.hcp.jco.tripulantes.service.JCOSeguimientoTripuService;
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
public class JCOSeguimientoTripuImpl implements JCOSeguimientoTripuService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public SeguimientoTripuExports SeguimientoTripulantes(SeguimientoTripuImports imports) throws Exception {

        SeguimientoTripuExports st= new SeguimientoTripuExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZHR_RFC_FLOTA_REPORT_DES_TRIPU);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("IP_PERNR", imports.getIp_perner());
            importx.setValue("IP_CDPCN", imports.getIp_cdpcn());
            importx.setValue("IP_TIREP", imports.getIp_tired());

            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();

            HashMap<String, Object> record = new HashMap<String, Object>();
            record.put("DATA", imports.getFechaInicio());
            tmpOptions.add(record);
            record = new HashMap<String, Object>();
            record.put("DATA", imports.getFechaFin());
            tmpOptions.add(record);
            record = new HashMap<String, Object>();
            record.put("DATA", imports.getZona());
            tmpOptions.add(record);
            record = new HashMap<String, Object>();
            record.put("DATA", imports.getCargo());
            tmpOptions.add(record);
            record = new HashMap<String, Object>();
            record.put("DATA", imports.getRotantes());
            tmpOptions.add(record);
            // logger.error("DATA: "+imports.getFechaInicio());


            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC exec= new EjecutarRFC();
            exec.setTable(tables, Tablas.T_OPCION,tmpOptions);

            stfcConnection.execute(destination);

            JCoTable T_DSTRIP = tables.getTable(Tablas.T_DSTRIP);
            JCoTable T_MENSAJ = tables.getTable(Tablas.T_MENSAJ);

            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> t_dstrip = metodo.ObtenerListObjetos(T_DSTRIP, imports.getFieldst_dstrip());
            List<HashMap<String, Object>>  t_mensaj = metodo.ListarObjetos(T_MENSAJ);

            st.setT_dstrip(t_dstrip);
            st.setT_mensaje(t_mensaj);
            st.setMensaje("Ok");

        }catch (Exception e){
            st.setMensaje(e.getMessage());
        }

        return st;
    }
}
