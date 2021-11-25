package com.incloud.hcp.jco.tripulantes.service.impl;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
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

        Metodos metodo = new Metodos();
        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_REGROL_ADM_REGROL);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_TOPE", imports.getP_tope());
            importx.setValue("P_CDRTR", imports.getP_cdrtr());
            importx.setValue("P_CANTI", imports.getP_canti());

            List<MaestroOptions>  options = imports.getP_option();
            MaestroOptions me = new MaestroOptions();
            me.setWa("CDRTR NE 0");
            options.add(me);
            List<MaestroOptionsKey>  options2 = imports.getP_options();
            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            tmpOptions=metodo.ValidarOptions(options,options2,"DATA");

            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC exec= new EjecutarRFC();
            exec.setTable(tables, Tablas.T_OPCIONES,tmpOptions);

            if(imports.getP_tope().equals("C")){

                exec.setTable(tables, Tablas.T_ZARTR,imports.getT_zartr());
                exec.setTable(tables, Tablas.T_DZART,imports.getT_dzart());
            }

            stfcConnection.execute(destination);
            if(imports.getP_tope().equals("L")) {

                JCoTable T_ZARTR = tables.getTable(Tablas.T_ZARTR);
                JCoTable T_DZART = tables.getTable(Tablas.T_DZART);


                List<HashMap<String, Object>> t_zartr = metodo.ObtenerListObjetos(T_ZARTR, imports.getFieldsT_zartr());
                List<HashMap<String, Object>> t_dzart = metodo.ObtenerListObjetos(T_DZART, imports.getFieldsT_dzart());

                rt.setT_dzart(t_dzart);
                rt.setT_zartr(t_zartr);
            }

            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);
            List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetos(T_MENSAJE);
            rt.setT_mensaje(t_mensaje);

            rt.setMensaje("Ok");

        }catch (Exception e){
            rt.setMensaje(e.getMessage());
        }


        return rt;
    }
}
