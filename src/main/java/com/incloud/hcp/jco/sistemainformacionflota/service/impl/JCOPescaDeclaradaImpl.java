package com.incloud.hcp.jco.sistemainformacionflota.service.impl;

import com.incloud.hcp.jco.sistemainformacionflota.dto.*;
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

    @Override
    public PescaDeclaradaDiariaExports PescaDeclaradaDiaria(PescaDeclaradaDiariaImports imports) throws Exception {

       PescaDeclaradaDiariaExports pdd=new PescaDeclaradaDiariaExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_PESCA_DECLARADA);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user ());
            importx.setValue("P_FEINI", imports.getP_feini());
            importx.setValue("P_FEFIN", imports.getP_fefin());


            JCoParameterList tables = stfcConnection.getTableParameterList();

            stfcConnection.execute(destination);

            JCoTable STR_DL = tables.getTable(Tablas.STR_DL);


            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> str_dl = metodo.ObtenerListObjetos(STR_DL, imports.getFieldstr_dl());

            pdd.setStr_dl(str_dl);
            pdd.setMensaje("Ok");

        }catch (Exception e){
            pdd.setMensaje(e.getMessage());
        }

        return pdd;
    }

    @Override
    public PescaDeclaradaDifeExports PescaDeclaradaDife(PescaDeclaradaDifeImports imports) throws Exception {

        PescaDeclaradaDifeExports pdd= new PescaDeclaradaDifeExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_PESC_DECLA_DIFE2);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user ());
            importx.setValue("P_FECHA", imports.getP_fecha());
            importx.setValue("P_FIDES", imports.getP_fides());
            importx.setValue("P_FFDES", imports.getP_ffdes());


            JCoParameterList tables = stfcConnection.getTableParameterList();

            stfcConnection.execute(destination);

            JCoTable STR_PTD = tables.getTable(Tablas.STR_PTD);
            JCoTable STR_PTR = tables.getTable(Tablas.STR_PTR);
            JCoTable STR_EMD = tables.getTable(Tablas.STR_EMD);
            JCoTable STR_EMR = tables.getTable(Tablas.STR_EMR);


            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> str_ptd = metodo.ObtenerListObjetos(STR_PTD, imports.getFieldstr_ptd());
            List<HashMap<String, Object>> str_ptr = metodo.ObtenerListObjetos(STR_PTR, imports.getFieldstr_ptr());
            List<HashMap<String, Object>> str_emd = metodo.ObtenerListObjetos(STR_EMD, imports.getFieldstr_emd());
            List<HashMap<String, Object>> str_emr = metodo.ObtenerListObjetos(STR_EMR, imports.getFieldstr_emr());

            pdd.setStr_ptd(str_ptd);
            pdd.setStr_ptr(str_ptr);
            pdd.setStr_emd(str_emd);
            pdd.setStr_emr(str_emr);
            pdd.setMensaje("Ok");

        }catch (Exception e){
            pdd.setMensaje(e.getMessage());
        }

        return pdd;
    }


}
