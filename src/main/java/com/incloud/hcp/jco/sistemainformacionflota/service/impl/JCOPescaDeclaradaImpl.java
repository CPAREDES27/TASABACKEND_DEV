package com.incloud.hcp.jco.sistemainformacionflota.service.impl;

import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaDeclaradaExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaDeclaradaImports;
import com.incloud.hcp.jco.sistemainformacionflota.service.JCOPescaDeclaradaService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class JCOPescaDeclaradaImpl implements JCOPescaDeclaradaService {


    @Override
    public PescaDeclaradaExports PescaDeclarada(PescaDeclaradaImports imports) throws Exception {

        PescaDeclaradaExports pd=new PescaDeclaradaExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_PESCA_DECLA);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user ());
            importx.setValue("P_FECON", imports.getP_fecon());
            importx.setValue("P_CDMMA", imports.getP_cdmma());


            JCoParameterList tables = stfcConnection.getTableParameterList();

            stfcConnection.execute(destination);

            JCoTable STR_TP = tables.getTable(Tablas.STR_TP);
            JCoTable STR_TE = tables.getTable(Tablas.STR_TE);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);


            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> str_tp = metodo.ObtenerListObjetos(STR_TP, imports.getFieldstr_tp());
            List<HashMap<String, Object>>  str_te = metodo.ObtenerListObjetos(STR_TE, imports.getFieldstr_te());
            List<HashMap<String, Object>>  t_mensaje = metodo.ObtenerListObjetos(T_MENSAJE, imports.getFieldstr_te());

            pd.setT_mensaje(t_mensaje);
            pd.setStr_tp(str_tp);
            pd.setStr_te(str_te);
            pd.setMensaje("Ok");

        }catch (Exception e){
            pd.setMensaje(e.getMessage());
        }

        return pd;
    }
}
