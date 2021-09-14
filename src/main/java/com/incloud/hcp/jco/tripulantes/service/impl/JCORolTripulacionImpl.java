package com.incloud.hcp.jco.tripulantes.service.impl;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.tripulantes.dto.Options;
import com.incloud.hcp.jco.tripulantes.dto.RolTripulacionExports;
import com.incloud.hcp.jco.tripulantes.dto.RolTripulacionImports;
import com.incloud.hcp.jco.tripulantes.service.JCORolTripulacionService;
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
public class JCORolTripulacionImpl implements JCORolTripulacionService {

    @Override
    public RolTripulacionExports RolTripulacion(RolTripulacionImports imports) throws Exception {

       RolTripulacionExports rt=new RolTripulacionExports();


        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_REGZAR_ADM_REGZAR);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_TOPE", imports.getP_tope());
            importx.setValue("P_CDRTR", imports.getP_cdrtr());
            importx.setValue("P_CANTI", imports.getP_canti());

            List<Options> options = imports.getT_opciones();
            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < options.size(); i++) {
                Options o = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();

                record.put("DATA", o.getData());
                tmpOptions.add(record);
            }

            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC exec= new EjecutarRFC();
            exec.setTable(tables, Tablas.T_OPCIONES,tmpOptions);

            stfcConnection.execute(destination);

            JCoTable T_ZARTR = tables.getTable(Tablas.T_ZARTR);
            JCoTable T_DZART = tables.getTable(Tablas.T_DZART);


            Metodos metodo = new Metodos();
            List<HashMap<String, Object>>  t_zartr = metodo.ObtenerListObjetos(T_ZARTR, imports.getFieldsT_zartr());
            List<HashMap<String, Object>>  t_dzart = metodo.ObtenerListObjetos(T_DZART, imports.getFieldsT_dzart());


            rt.setT_dzart(t_zartr);
            rt.setT_zartr(t_dzart);
            rt.setMensaje("Ok");

        }catch (Exception e){
            rt.setMensaje(e.getMessage());
        }


        return rt;
    }
}
