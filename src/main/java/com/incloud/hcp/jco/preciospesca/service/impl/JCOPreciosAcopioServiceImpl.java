package com.incloud.hcp.jco.preciospesca.service.impl;

import com.incloud.hcp.jco.preciospesca.dto.*;
import com.incloud.hcp.jco.preciospesca.service.JCOPreciosAcopioService;
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
public class JCOPreciosAcopioServiceImpl implements JCOPreciosAcopioService {

    @Override
    public PrecioMarExports ObtenerPrecioMar(PrecioMarImports imports) throws Exception {
        HashMap<String, Object> importParams = new HashMap<>();
        importParams.put("P_USER", imports.getP_user());
        importParams.put("P_INDPR", imports.getP_indpr());
        importParams.put("P_ROWS", imports.getP_rows());

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
        JCoTable tblSTR_PM = tables.getTable(Tablas.STR_PM);

        Metodos metodos = new Metodos();
        List<HashMap<String, Object>> listSTR_PM = metodos.ListarObjetos(tblSTR_PM);

        PrecioMarExports dto = new PrecioMarExports();
        dto.setStr_pm(listSTR_PM);
        dto.setMensaje("OK");

        return dto;
    }

    @Override
    public BonoExport AgregarBonos(BonoImport imports) throws Exception {
        HashMap<String, Object> importParams = new HashMap<>();
        importParams.put("P_USER", imports.getP_user());
        importParams.put("P_TCONS", imports.getP_tcons());
        importParams.put("P_NRMAR", imports.getP_nrmar());
        importParams.put("P_NRDES", imports.getP_nrdes());
        importParams.put("P_CDSPC", imports.getP_cdspc());

        List<HashMap<String, Object>> str_bpm = new ArrayList<>();
        List<HashMap<String, Object>> str_act = new ArrayList<>();
        for (Bpm bpm : imports.getStr_bpm()) {
            HashMap<String, Object> bpmRecord = new HashMap<>();
            bpmRecord.put("INDEJ", bpm.getIndej());
            bpmRecord.put("NRMAR", bpm.getNrmar());
            bpmRecord.put("NRDES", bpm.getNrdes());
            bpmRecord.put("CDSPC", bpm.getCdspc());
            bpmRecord.put("NRBON", bpm.getNrbon());
            bpmRecord.put("EBELP", bpm.getEbelp());
            bpmRecord.put("BONIF", bpm.getBonif());
            bpmRecord.put("ESBON", bpm.getEsbon());
            bpmRecord.put("USBON", bpm.getUsbon());
            bpmRecord.put("FHRBO", bpm.getFhrbo());
            bpmRecord.put("HRRBO", bpm.getHrrbo());
            bpmRecord.put("USABO", bpm.getUsabo());
            bpmRecord.put("FHABO", bpm.getFhabo());
            bpmRecord.put("HRABO", bpm.getHrabo());

            str_bpm.add(bpmRecord);
        }

        for (Act act : imports.getStr_act()) {
            HashMap<String, Object> actRecord = new HashMap<>();
            actRecord.put("NMTAB", act.getNmtab());
            actRecord.put("CMSET", act.getCmset());
            actRecord.put("CMOPT", act.getCmopt());

            str_act.add(actRecord);
        }

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_AGREGA_BONOS);

        JCoParameterList paramsTable = function.getTableParameterList();

        EjecutarRFC executeRFC = new EjecutarRFC();
        executeRFC.setImports(function, importParams);
        executeRFC.setTable(paramsTable, "STR_BPM", str_bpm);
        executeRFC.setTable(paramsTable, "STR_ACT", str_act);

        //Exports
        JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);
        JCoTable tblT_Mensaje = tables.getTable(Tablas.T_MENSAJE);

        Metodos metodos = new Metodos();
        List<HashMap<String, Object>> listT_MENSAJE = metodos.ListarObjetos(tblT_Mensaje);

        BonoExport dto = new BonoExport();
        dto.setT_mensaje(listT_MENSAJE);
        dto.setMensaje("OK");

        return dto;
    }
}
