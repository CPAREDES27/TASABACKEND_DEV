package com.incloud.hcp.jco.tripulantes.service.impl;

import com.incloud.hcp.jco.tripulantes.dto.Options;
import com.incloud.hcp.jco.tripulantes.dto.TrabajoFueraFaenaExports;
import com.incloud.hcp.jco.tripulantes.dto.TrabajoFueraFaenaImports;
import com.incloud.hcp.jco.tripulantes.service.JCOTrabajoFueraFaenaService;
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
public class JCOTrabajoFueraFaenaImpl implements JCOTrabajoFueraFaenaService {


    @Override
    public TrabajoFueraFaenaExports TrabajoFueraFaenaTransporte(TrabajoFueraFaenaImports imports) throws Exception {

        TrabajoFueraFaenaExports tff=new TrabajoFueraFaenaExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_TFF_CON_TRA_FUE_FAE);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("IP_NRTFF", imports.getIp_nrtff());
            importx.setValue("IP_CDGFL", imports.getIp_cdgfl());
            importx.setValue("IP_WERKS", imports.getIp_werks());
            importx.setValue("IP_TOPE", imports.getIp_tope());
            importx.setValue("IP_CANTI", imports.getIp_canti());
            importx.setValue("IP_TIPTR", imports.getIp_tiptr());
            importx.setValue("IP_SEPES", imports.getIp_sepes());
            importx.setValue("IP_ESREG", imports.getIp_esreg());
            importx.setValue("IP_FECIN", imports.getIp_fecin());
            importx.setValue("IP_FECFN", imports.getIp_fecfn());

            List<Options> options = imports.getT_opcion();
            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < options.size(); i++) {
                Options o = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();

                record.put("DATA", o.getData());
                tmpOptions.add(record);
            }

            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC exec= new EjecutarRFC();
            exec.setTable(tables, Tablas.T_OPCION,tmpOptions);

            stfcConnection.execute(destination);

            JCoTable T_TRABFF = tables.getTable(Tablas.T_TRABFF);
            JCoTable T_TRABAJ = tables.getTable(Tablas.T_TRABAJ);
            JCoTable T_FECHAS = tables.getTable(Tablas.T_FECHAS);
            JCoTable T_TEXTOS = tables.getTable(Tablas.T_TEXTOS);
            JCoTable T_MENSAJES = tables.getTable(Tablas.T_MENSAJES);

            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> t_trabff = metodo.ObtenerListObjetos(T_TRABFF, imports.getFieldst_trabff());
            List<HashMap<String, Object>> t_trabaj = metodo.ObtenerListObjetos(T_TRABAJ, imports.getFieldst_trabaj());
            List<HashMap<String, Object>> t_fechas = metodo.ObtenerListObjetos(T_FECHAS, imports.getFieldst_fechas());
            List<HashMap<String, Object>> t_textos = metodo.ObtenerListObjetos(T_TEXTOS, imports.getFieldst_textos());
            List<HashMap<String, Object>>  t_mensajes = metodo.ListarObjetos(T_MENSAJES);

            tff.setT_trabff(t_trabff);
            tff.setT_trabaj(t_trabaj);
            tff.setT_fechas(t_fechas);
            tff.setT_textos(t_textos);
            tff.setT_mensajes(t_mensajes);
            tff.setMensaje("Ok");

        }catch (Exception e){
            tff.setMensaje(e.getMessage());
        }

        return tff;
    }
}
