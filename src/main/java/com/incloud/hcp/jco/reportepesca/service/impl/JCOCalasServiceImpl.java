package com.incloud.hcp.jco.reportepesca.service.impl;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.jco.reportepesca.dto.*;
import com.incloud.hcp.jco.reportepesca.service.JCOCalasService;
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
public class JCOCalasServiceImpl implements JCOCalasService {
    @Override
    public CalaExports ObtenerCalas(CalaImports imports) throws Exception {
        Metodos metodo = new Metodos();
        HashMap<String, Object> importParams = new HashMap<>();
        importParams.put("P_USER", imports.getP_user());
        importParams.put("ROWCOUNT", imports.getRowcount());

        List<MaestroOptions> option = imports.getOption();
        List<MaestroOptionsKey> options2 = imports.getOptions();


        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
        tmpOptions=metodo.ValidarOptions(option,options2);

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_GPES_CONS_CALAS);

        JCoParameterList paramsTable = function.getTableParameterList();

        EjecutarRFC executeRFC = new EjecutarRFC();
        executeRFC.setImports(function, importParams);
        executeRFC.setTable(paramsTable, "OPTIONS", tmpOptions);

        JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);
        JCoTable tblS_CALA = tables.getTable(Tablas.S_CALA);

        Metodos metodos = new Metodos();
        List<HashMap<String, Object>> listS_CALA = metodos.ListarObjetos(tblS_CALA);

        CalaExports dto = new CalaExports();
        dto.setS_cala(listS_CALA);
        dto.setMensaje("OK");

        return dto;
    }
}
