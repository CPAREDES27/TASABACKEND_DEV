package com.incloud.hcp.jco.tolvas.service.impl;

import com.incloud.hcp.jco.tolvas.dto.PescaCompetenciaRExports;
import com.incloud.hcp.jco.tolvas.dto.PescaCompetenciaRImports;
import com.incloud.hcp.jco.tolvas.service.JCOPescaCompetenciaRService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class JCOPescaCompetenciaRImpl implements JCOPescaCompetenciaRService {

    @Override
    public PescaCompetenciaRExports PescaCompetencia(PescaCompetenciaRImports imports) throws Exception {

        PescaCompetenciaRExports pc=new PescaCompetenciaRExports();


        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_PESC_DECLA_COMPE);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            importx.setValue("P_FECHA", imports.getP_fecha());
            importx.setValue("P_CDTPC", imports.getP_cdtpc());
            importx.setValue("P_TIPO", imports.getP_tipo());

            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC exec= new EjecutarRFC();
            exec.setTable(tables, Tablas.STR_ZLT,imports.getStr_zlt());
            exec.setTable(tables, Tablas.STR_PTO,imports.getStr_pto());
            exec.setTable(tables, Tablas.STR_PCP,imports.getStr_pcp());

            stfcConnection.execute(destination);


            JCoTable STR_ZLT = tables.getTable(Tablas.STR_ZLT);
            JCoTable STR_PTO = tables.getTable(Tablas.STR_PTO);
            JCoTable STR_PCP = tables.getTable(Tablas.STR_PCP);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);

            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> str_ztl = metodo.ObtenerListObjetos(STR_ZLT, imports.getFieldStr_pcp());
            List<HashMap<String, Object>> str_pto = metodo.ObtenerListObjetos(STR_PTO, imports.getFieldStr_pto());
            List<HashMap<String, Object>> str_pcp = metodo.ObtenerListObjetos(STR_PCP, imports.getFieldStr_pcp());
            List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetos(T_MENSAJE);

            pc.setStr_zlt(str_ztl);
            pc.setStr_pto(str_pto);
            pc.setStr_pcp(str_pcp);
            pc.setT_mensaje(t_mensaje);
            pc.setMensaje("Ok");
        }catch (Exception e){
            pc.setMensaje(e.getMessage());
        }

        return pc;

    }
}
