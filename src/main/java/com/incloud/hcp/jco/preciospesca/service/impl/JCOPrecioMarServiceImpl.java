package com.incloud.hcp.jco.preciospesca.service.impl;

import com.incloud.hcp.jco.preciospesca.dto.MaestroOptionsPrecioMar;
import com.incloud.hcp.jco.preciospesca.dto.PrecioMarExports;
import com.incloud.hcp.jco.preciospesca.dto.PrecioMarImports;
import com.incloud.hcp.jco.preciospesca.service.JCOPrecioMarService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JCOPrecioMarServiceImpl implements JCOPrecioMarService {
    @Override
    public PrecioMarExports ObtenerPrecioMar(PrecioMarImports imports) throws Exception {
        HashMap<String, Object> importParams = new HashMap<>();
        importParams.put("P_USER", imports.getP_user());
        importParams.put("P_INDPR", imports.getP_indpr());
        importParams.put("P_ROWS", imports.getP_rows());
        importParams.put("P_CALIDAD", imports.getP_calidad());
        importParams.put("P_FLAG", imports.getP_flag());

        // Options parameters
        List<HashMap<String, Object>> options = new ArrayList<>();
        for (MaestroOptionsPrecioMar option : imports.getP_options()) {
            HashMap<String, Object> optionRecord = new HashMap<>();
            optionRecord.put("WA", option.getWa());
            options.add(optionRecord);
        }

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_LECT_PRECIO_MAR);

        JCoParameterList paramsTable = function.getTableParameterList();

        EjecutarRFC executeRFC = new EjecutarRFC();
        executeRFC.setImports(function, importParams);
        executeRFC.setTable(paramsTable, "P_OPTIONS", options);

        JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);
        JCoTable tblT_MENSAJE = tables.getTable(Tablas.T_MENSAJE);
        JCoTable tblSTR_PM = tables.getTable(Tablas.STR_PM);

        Metodos metodos = new Metodos();
        List<HashMap<String, Object>> listSTR_PM = metodos.ListarObjetos(tblSTR_PM);
        List<HashMap<String, Object>> listT_MENSAJE = metodos.ListarObjetos(tblT_MENSAJE);

        PrecioMarExports dto = new PrecioMarExports();
        dto.setStr_pm(listSTR_PM);
        dto.setT_mensaje(listT_MENSAJE);
        dto.setMensaje("OK");

        return dto;
    }
}
