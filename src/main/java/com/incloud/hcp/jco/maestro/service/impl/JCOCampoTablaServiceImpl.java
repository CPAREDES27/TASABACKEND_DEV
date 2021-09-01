package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.CampoTablaExports;
import com.incloud.hcp.jco.maestro.dto.CampoTablaImports;
import com.incloud.hcp.jco.maestro.dto.SetDto;
import com.incloud.hcp.jco.maestro.service.JCOCampoTablaService;
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
public class JCOCampoTablaServiceImpl implements JCOCampoTablaService {
    @Override
    public CampoTablaExports Actualizar(CampoTablaImports imports) throws Exception {
        HashMap<String, Object> importParams = new HashMap<>();
        importParams.put("P_USER", imports.getP_user());

        List<HashMap<String, Object>> str_set = new ArrayList<>();
        for (SetDto setDto : imports.getStr_set()) {
            HashMap<String, Object> setRecord = new HashMap<>();
            setRecord.put("NMTAB", setDto.getNmtab());
            setRecord.put("CMSET", setDto.getCmset());
            setRecord.put("CMOPT", setDto.getCmopt());

            str_set.add(setRecord);
        }

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_ACT_CAMP_TAB);

        JCoParameterList paramsTable = function.getTableParameterList();

        EjecutarRFC executeRFC = new EjecutarRFC();
        executeRFC.setImports(function, importParams);
        executeRFC.setTable(paramsTable, "STR_SET", str_set);

        //Exports
        JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);
        JCoTable tblT_Mensaje = tables.getTable(Tablas.T_MENSAJE);

        Metodos metodos = new Metodos();
        List<HashMap<String, Object>> listT_MENSAJE = metodos.ListarObjetos(tblT_Mensaje);

        CampoTablaExports dto = new CampoTablaExports();
        dto.setT_mensaje(listT_MENSAJE);
        dto.setMensaje("OK");

        return dto;
    }
}
