package com.incloud.hcp.jco.politicaprecios.service.impl;

import com.incloud.hcp.jco.politicaprecios.dto.MaestroOptionsPrecioPesca;
import com.incloud.hcp.jco.politicaprecios.dto.PrecioPescaExports;
import com.incloud.hcp.jco.politicaprecios.dto.PrecioPescaImports;
import com.incloud.hcp.jco.politicaprecios.service.JCOPrecioPescaService;
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
public class JCOPrecioPescaServiceImpl implements JCOPrecioPescaService {

    @Override
    public PrecioPescaExports ObtenerPrecioPesca(PrecioPescaImports imports) throws Exception {
        HashMap<String,Object> importParams=new HashMap<>();
        importParams.put("P_USER",imports.getP_user());

        //Obtener los options
        List<HashMap<String,Object>> options=new ArrayList<HashMap<String, Object>>();
        for (MaestroOptionsPrecioPesca option: imports.getP_options()) {
            HashMap<String, Object> optionRecord = new HashMap<>();
            optionRecord.put("WA", option.getWa());
            options.add(optionRecord);
        }

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_LECT_PREC_PESC);

        JCoParameterList paramsTable = function.getTableParameterList();

        EjecutarRFC executeRFC = new EjecutarRFC();
        executeRFC.setImports(function, importParams);
        executeRFC.setTable(paramsTable, "P_OPTIONS", options);

        JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);
        JCoTable tblSTR_PPC = tables.getTable(Tablas.STR_PPC);

        Metodos metodos = new Metodos();
        List<HashMap<String, Object>> listSTR_PPC = metodos.ListarObjetos(tblSTR_PPC);

        PrecioPescaExports dto = new PrecioPescaExports();
        dto.setStr_ppc(listSTR_PPC);
        dto.setMensaje("OK");

        return dto;
    }
}
