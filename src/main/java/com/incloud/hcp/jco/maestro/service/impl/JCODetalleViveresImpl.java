package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.maestro.service.JCODetalleViveresService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JCODetalleViveresImpl implements JCODetalleViveresService {

    @Override
    public DetalleViveresExports DetalleImpresionViveres(DetalleViveresImports imports) throws Exception {

        DetalleViveresExports dto= new DetalleViveresExports();
        Metodos metodo = new Metodos();

        try {
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_LECT_POSI_VIVER);

            String usuario= metodo.ObtenerUsuario(imports.getP_user());

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", usuario);
            importx.setValue("P_CODE", imports.getP_code());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            stfcConnection.execute(destination);

            JCoTable S_POSICION = tables.getTable(Tablas.S_POSICION);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);


            List<HashMap<String, Object>> s_posicion = metodo.ObtenerListObjetos(S_POSICION, imports.getFields());
            List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetos(T_MENSAJE);




            dto.setS_posicion(s_posicion);
            dto.setT_mensaje(t_mensaje);
            dto.setMensaje("Ok");

        }catch (Exception e){

        }

        return dto;
    }

}
