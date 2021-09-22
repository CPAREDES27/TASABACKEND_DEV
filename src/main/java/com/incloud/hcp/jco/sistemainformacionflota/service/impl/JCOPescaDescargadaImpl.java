package com.incloud.hcp.jco.sistemainformacionflota.service.impl;

import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaDescargadaExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaDescargadaImports;
import com.incloud.hcp.jco.sistemainformacionflota.service.JCOPescaDescargadaService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class JCOPescaDescargadaImpl implements JCOPescaDescargadaService {

    public PescaDescargadaExports PescaDescargada(PescaDescargadaImports imports)throws Exception{

        PescaDescargadaExports pd= new PescaDescargadaExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_PESCA_DESC_DIA);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user ());
            importx.setValue("P_FIDES", imports.getP_fides());
            importx.setValue("P_FFDES", imports.getP_ffdes());


            JCoParameterList tables = stfcConnection.getTableParameterList();

            stfcConnection.execute(destination);

            JCoTable STR_PTA = tables.getTable(Tablas.STR_PTA);
            JCoTable STR_PTR = tables.getTable(Tablas.STR_DSD);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);

            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> str_pta = metodo.ObtenerListObjetos(STR_PTA, imports.getFieldstr_pta());
            List<HashMap<String, Object>> str_dsd = metodo.ObtenerListObjetos(STR_PTR, imports.getFielstr_dsd());
            List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetos(T_MENSAJE);

            pd.setStr_pta(str_pta);
            pd.setStr_dsd(str_dsd);
            pd.setT_mensaje(t_mensaje);
            pd.setMensaje("Ok");

        }catch (Exception e){
            pd.setMensaje(e.getMessage());
        }

        return pd;
    }
}
