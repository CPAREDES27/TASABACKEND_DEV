package com.incloud.hcp.jco.controlLogistico.service.impl;

import com.incloud.hcp.jco.controlLogistico.dto.ConsultaHorometroExports;
import com.incloud.hcp.jco.controlLogistico.dto.ConsultaHorometroImports;
import com.incloud.hcp.jco.controlLogistico.service.JCOConsultaHorometroService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class JCOConsultaHorometroImpl implements JCOConsultaHorometroService {

    public ConsultaHorometroExports Listar(ConsultaHorometroImports imports)throws Exception{

        ConsultaHorometroExports ch= new ConsultaHorometroExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_CONS_HORO);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            importx.setValue("P_FIEVN", imports.getP_fievn());
            importx.setValue("P_FFEVN", imports.getP_ffevn());
            importx.setValue("P_CDEMB", imports.getP_cdemb());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            stfcConnection.execute(destination);


            JCoTable STR_EMB = tables.getTable(Tablas.STR_EMB);
            JCoTable STR_EVN = tables.getTable(Tablas.STR_EVN);
            JCoTable STR_LHO= tables.getTable(Tablas.STR_LHO);
            JCoTable T_MENSAJE= tables.getTable(Tablas.T_MENSAJE);

            Metodos metodo = new Metodos();

            List<HashMap<String, Object>> str_emb = metodo.ObtenerListObjetos(STR_EMB, imports.getFieldStr_emb());
            List<HashMap<String, Object>> str_evn = metodo.ObtenerListObjetos(STR_EVN, imports.getFieldStr_evn());
            List<HashMap<String, Object>> str_lho = metodo.ObtenerListObjetos(STR_LHO, imports.getFieldStr_lho());
            List<HashMap<String, Object>> t_mensaje = metodo.ObtenerListObjetos(T_MENSAJE, imports.getFieldT_mensaje());

            ch.setStr_emb(str_emb);
            ch.setStr_evn(str_evn);
            ch.setStr_lho(str_lho);
            ch.setT_mensaje(t_mensaje);
            ch.setMensaje("Ok");
        }catch (Exception e){
            ch .setMensaje(e.getMessage());
        }

        return ch;
    }

}
