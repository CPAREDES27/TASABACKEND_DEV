package com.incloud.hcp.jco.reportepesca.service.impl;

import com.incloud.hcp.jco.reportepesca.dto.MaestroOptionsMarea;
import com.incloud.hcp.jco.reportepesca.dto.MareaExports;
import com.incloud.hcp.jco.reportepesca.dto.MareaImports;
import com.incloud.hcp.jco.reportepesca.service.JCOMareasService;
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
public class JCOMareasServiceImpl implements JCOMareasService {
    @Override
    public MareaExports ObtenerMareas(MareaImports imports) throws Exception {
        HashMap<String, Object> importParams = new HashMap<>();
        importParams.put("P_USER", imports.getP_user());
        importParams.put("ROWCOUNT", imports.getRowcount());

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_GPES_CONS_MAREA);

        // Obtener los options
        List<HashMap<String, Object>> options = new ArrayList<HashMap<String, Object>>();
        for (MaestroOptionsMarea option : imports.getOptions()) {
            HashMap<String, Object> optionRecord = new HashMap<>();
            optionRecord.put("WA", option.getWa());
            options.add(optionRecord);
        }

        JCoParameterList paramsTable = function.getTableParameterList();

        EjecutarRFC ejecutarRFC = new EjecutarRFC();
        ejecutarRFC.setImports(function, importParams);
        ejecutarRFC.setTable(paramsTable, "OPTIONS", options);

        JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);
        JCoTable tblS_MAREA = tables.getTable(Tablas.S_MAREA);

        Metodos metodo = new Metodos();
        List<HashMap<String, Object>> listS_MAREA = metodo.ListarObjetos(tblS_MAREA);

        MareaExports dto = new MareaExports();
        dto.setS_marea(listS_MAREA);
        dto.setMensaje("Ok");

        return dto;
    }
}
