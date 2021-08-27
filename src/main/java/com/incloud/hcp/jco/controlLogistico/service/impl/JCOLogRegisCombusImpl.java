package com.incloud.hcp.jco.controlLogistico.service.impl;

import com.incloud.hcp.jco.controlLogistico.dto.LogRegCombusExports;
import com.incloud.hcp.jco.controlLogistico.dto.LogRegCombusImports;
import com.incloud.hcp.jco.controlLogistico.service.JCOLogRegisCombusService;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
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
public class JCOLogRegisCombusImpl implements JCOLogRegisCombusService {

    public LogRegCombusExports Listar(LogRegCombusImports imports)throws Exception{

        LogRegCombusExports lrce= new LogRegCombusExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_REG_COMB_MARE_SAP);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            importx.setValue("P_TOPE", imports.getP_tope());
            importx.setValue("P_LCCO", imports.getP_lcco());
            importx.setValue("P_CANTI", imports.getP_canti());

            List<MaestroOptions> options = imports.getOptions();
            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < options.size(); i++) {
                MaestroOptions mo = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();
                record.put("WA", mo.getWa());
                tmpOptions.add(record);
            }
            JCoParameterList tables = stfcConnection.getTableParameterList();
            EjecutarRFC exec=new EjecutarRFC();
            exec.setTable(tables, Tablas.P_OPTIONS, tmpOptions);

            stfcConnection.execute(destination);

            JCoTable STR_LGCCO = tables.getTable(Tablas.STR_LGCCO);
            JCoTable STR_CSMAJ = tables.getTable(Tablas.STR_CSMAJ);
            JCoTable STR_CSMAR = tables.getTable(Tablas.STR_CSMAR);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);

            Metodos metodo = new Metodos();

            List<HashMap<String, Object>> str_lgcco = metodo.ObtenerListObjetos(STR_LGCCO, imports.getFieldsStr_lgcco());
            List<HashMap<String, Object>> str_csmaj = metodo.ObtenerListObjetos(STR_CSMAJ, imports.getFieldsStr_csmaj());
            List<HashMap<String, Object>> str_csmar = metodo.ObtenerListObjetos(STR_CSMAR, imports.getFieldsStr_csmar());
            List<HashMap<String, Object>> t_mensaje = metodo.ObtenerListObjetos(T_MENSAJE, imports.getFieldsT_mensaje());


            lrce.setStr_lgcco(str_lgcco);
            lrce.setStr_csmaj(str_csmaj);
            lrce.setStr_csmar(str_csmar);
            lrce.setT_mensaje(t_mensaje);
            lrce.setMensaje("Ok");
        }catch (Exception e){
            lrce .setMensaje(e.getMessage());
        }

        return lrce;

    }

}
