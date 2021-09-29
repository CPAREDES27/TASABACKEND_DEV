package com.incloud.hcp.jco.reportepesca.service.impl;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
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
        Metodos metodo = new Metodos();
        importParams.put("P_USER", imports.getP_user());
        importParams.put("ROWCOUNT", imports.getRowcount());

        List<MaestroOptions> option = imports.getOption();
        List<MaestroOptionsKey> options2 = imports.getOptions();

        List<HashMap<String, Object>> tmpOptions = metodo.ValidarOptions(option, options2);

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_GPES_CONS_MAREA);

        JCoParameterList paramsTable = function.getTableParameterList();

        EjecutarRFC ejecutarRFC = new EjecutarRFC();
        ejecutarRFC.setImports(function, importParams);
        ejecutarRFC.setTable(paramsTable, "OPTIONS", tmpOptions);

        JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);
        JCoTable tblS_MAREA = tables.getTable(Tablas.S_MAREA);

        List<HashMap<String, Object>> listS_MAREA = metodo.ListarObjetos(tblS_MAREA);

        MareaExports dto = new MareaExports();
        dto.setS_marea(listS_MAREA);
        dto.setMensaje("Ok");

        return dto;
    }
}
