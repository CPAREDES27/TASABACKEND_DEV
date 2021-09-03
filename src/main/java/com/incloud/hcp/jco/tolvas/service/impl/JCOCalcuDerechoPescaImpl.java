package com.incloud.hcp.jco.tolvas.service.impl;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.tolvas.dto.CalcuDerechoPescaExports;
import com.incloud.hcp.jco.tolvas.dto.CalcuDerechoPescaImports;
import com.incloud.hcp.jco.tolvas.service.JCOCalcuDerechoPescaService;
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
public class JCOCalcuDerechoPescaImpl implements JCOCalcuDerechoPescaService {

    public CalcuDerechoPescaExports Listar(CalcuDerechoPescaImports imports)throws Exception{

        CalcuDerechoPescaExports cd= new CalcuDerechoPescaExports();
        Metodos metodo = new Metodos();
        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_GPES_DERE_PESCA);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            importx.setValue("ROWCOUNT", imports.getRowcount());
            importx.setValue("P_INDTR", imports.getP_indtr());
            importx.setValue("P_MONED", imports.getP_moned());

            List<MaestroOptions> options = imports.getOptions();
            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < options.size(); i++) {
                MaestroOptions mo = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();
                record.put("DATA", mo.getWa());
                tmpOptions.add(record);
            }


            JCoParameterList tables = stfcConnection.getTableParameterList();
            EjecutarRFC exec=new EjecutarRFC();
            if(imports.getOptions().size()>=1) {
                exec.setTable(tables, Tablas.OPTIONS, tmpOptions);
            }

            if(imports.getStr_dps().size()>=1 && imports.getS_derecho().size()>=1 ){
                exec.setTable(tables, Tablas.S_DERECHO, imports.getS_derecho());
                exec.setTable(tables, Tablas.STR_DPS, imports.getStr_dps());
                stfcConnection.execute(destination);
                JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);

                List<HashMap<String, Object>> t_mensaje = metodo.ObtenerListObjetos(T_MENSAJE, imports.getFieldt_mensaje());

                cd.setT_mensaje(t_mensaje);
            }
            if(imports.getS_derecho().size()>=1 && imports.getStr_dps().size()<1 ){
                exec.setTable(tables, Tablas.S_DERECHO, imports.getS_derecho());
                stfcConnection.execute(destination);
                JCoTable STR_DPS = tables.getTable(Tablas.STR_DPS);
                JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);

                List<HashMap<String, Object>> str_dps = metodo.ObtenerListObjetos(STR_DPS, imports.getFieldstr_dps());
                List<HashMap<String, Object>> t_mensaje = metodo.ObtenerListObjetos(T_MENSAJE, imports.getFieldt_mensaje());

                cd.setStr_dps(str_dps);
                cd.setT_mensaje(t_mensaje);
            }
            if(imports.getStr_dps().size()>=1 && imports.getS_derecho().size()<1 ){
                exec.setTable(tables, Tablas.STR_DPS, imports.getStr_dps());
                stfcConnection.execute(destination);
                JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);

                List<HashMap<String, Object>> t_mensaje = metodo.ObtenerListObjetos(T_MENSAJE, imports.getFieldt_mensaje());

                cd.setT_mensaje(t_mensaje);
            }
            cd.setMensaje("Ok");
        }catch (Exception e){
            cd .setMensaje(e.getMessage());
        }

        return cd;
    }

}
