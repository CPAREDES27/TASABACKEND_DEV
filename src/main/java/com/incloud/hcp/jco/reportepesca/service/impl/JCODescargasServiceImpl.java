package com.incloud.hcp.jco.reportepesca.service.impl;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.jco.reportepesca.dto.*;
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
        Metodos metodo = new Metodos();
        HashMap<String, Object> importParams = new HashMap<>();
        importParams.put("P_USER", imports.getP_user());
        importParams.put("P_ROWS", imports.getP_rows());

        // Obtener los options
        List<MaestroOptionsDescarga> optionData = imports.getP_options();
        List<MaestroOptions> option = metodo.convertMaestroOptions(optionData);

        List<MaestroOptionsKey> options2 = imports.getOptions();

        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
        tmpOptions = metodo.ValidarOptions(option, options2, "DATA");

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_CONS_DESCA);


        JCoParameterList paramsTable = function.getTableParameterList();

        EjecutarRFC executeRFC = new EjecutarRFC();
        executeRFC.setImports(function, importParams);
        executeRFC.setTable(paramsTable, "P_OPTIONS", tmpOptions);

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

    @Override
    public InterlocutorExports AgregarInterlocutor(InterlocutorImports imports) throws Exception {
        HashMap<String, Object> importParams = new HashMap<>();
        importParams.put("P_USER", imports.getP_user());
        importParams.put("P_NRMAR", imports.getP_nrmar());
        importParams.put("P_LIFNR", imports.getP_lifnr());

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_AGREGA_INTER);

        EjecutarRFC executeRFC = new EjecutarRFC();
        executeRFC.setImports(function, importParams);

        JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);
        JCoTable tblT_MENSAJE = tables.getTable(Tablas.T_MENSAJE);

        Metodos metodos = new Metodos();
        List<HashMap<String, Object>> listT_MENSAJE = metodos.ListarObjetos(tblT_MENSAJE);

        InterlocutorExports dto = new InterlocutorExports();
        dto.setT_mensaje(listT_MENSAJE);
        dto.setMensaje("OK");

        return dto;
    }
}
