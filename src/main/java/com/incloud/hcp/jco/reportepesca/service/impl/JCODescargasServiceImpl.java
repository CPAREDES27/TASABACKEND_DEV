package com.incloud.hcp.jco.reportepesca.service.impl;

import com.incloud.hcp.jco.reportepesca.dto.DescargasExports;
import com.incloud.hcp.jco.reportepesca.dto.DescargasImports;
import com.incloud.hcp.jco.reportepesca.dto.MaestroOptionsDescarga;
import com.incloud.hcp.jco.reportepesca.service.JCODescargasService;
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
public class JCODescargasServiceImpl implements JCODescargasService {
    @Override
    public DescargasExports ObtenerDescargas(DescargasImports imports) throws Exception {
        HashMap<String, Object> importParams = new HashMap<>();
        importParams.put("P_USER", imports.getP_user());
        importParams.put("P_ROWS", imports.getP_rows());

        // Obtener los options
        List<HashMap<String, Object>> options = new ArrayList<HashMap<String, Object>>();
        for (MaestroOptionsDescarga option : imports.getP_options()) {
            HashMap<String, Object> optionRecord = new HashMap<>();
            optionRecord.put("DATA", option.getData());
            options.add(optionRecord);
        }

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_CONS_DESCA);

        JCoParameterList paramsTable = function.getTableParameterList();

        EjecutarRFC executeRFC = new EjecutarRFC();
        executeRFC.setImports(function, importParams);
        executeRFC.setTable(paramsTable, "P_OPTIONS", options);

        JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);
        JCoTable tblSTR_DES = tables.getTable(Tablas.STR_DES);

        Metodos metodos = new Metodos();
        List<HashMap<String, Object>> listSTR_DES = metodos.ListarObjetos(tblSTR_DES);

        DescargasExports dto = new DescargasExports();
        dto.setStr_des(listSTR_DES);
        dto.setMensaje("OK");

        return dto;
    }
}
