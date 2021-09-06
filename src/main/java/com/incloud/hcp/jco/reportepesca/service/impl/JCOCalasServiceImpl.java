package com.incloud.hcp.jco.reportepesca.service.impl;

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
        HashMap<String, Object> importParams = new HashMap<>();
        importParams.put("P_USER", imports.getP_user());
        importParams.put("ROWCOUNT", imports.getRowcount());

        // Obtener los options
        List<HashMap<String, Object>> options = new ArrayList<HashMap<String, Object>>();
        for (MaestroOptionsMarea option : imports.getOptions()) {
            HashMap<String, Object> optionRecord = new HashMap<>();
            optionRecord.put("WA", option.getWa());
            options.add(optionRecord);
        }

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_GPES_CONS_CALAS);

        JCoParameterList paramsTable = function.getTableParameterList();

        EjecutarRFC executeRFC = new EjecutarRFC();
        executeRFC.setImports(function, importParams);
        executeRFC.setTable(paramsTable, "OPTIONS", options);

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

    @Override
    public BiomasaExports ConsultarBiomasa(BiomasaImports imports) throws Exception {
        HashMap<String, Object> importParams = new HashMap<>();
        importParams.put("IP_OPER", imports.getIp_oper());
        importParams.put("IP_CDMMA", imports.getIp_cdmma());

        // Obtener los options
        List<HashMap<String, Object>> mareas = new ArrayList<HashMap<String, Object>>();
        for (BiomasaMarea biomasaMarea : imports.getIt_marea()) {
            HashMap<String, Object> mareaRecord = new HashMap<>();
            mareaRecord.put("NRMAR", biomasaMarea.getNrmar());
            mareaRecord.put("CDPTA", biomasaMarea.getCdpta());
            mareaRecord.put("DSEMP", biomasaMarea.getDsemp());
            mareaRecord.put("CDEMB", biomasaMarea.getCdemb());
            mareaRecord.put("NMEMB", biomasaMarea.getNmemb());
            mareaRecord.put("CDSPE", biomasaMarea.getCdspe());
            mareaRecord.put("DSSPE", biomasaMarea.getDsspe());
            mareaRecord.put("INPRP", biomasaMarea.getInprp());
            mareaRecord.put("CDMMA", biomasaMarea.getCdmma());
            mareaRecord.put("FEMAR", biomasaMarea.getFemar());
            mareaRecord.put("HAMAR", biomasaMarea.getHamar());
            mareas.add(mareaRecord);
        }

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_GPES_CONS_BIOM);

        JCoParameterList paramsTable = function.getTableParameterList();

        EjecutarRFC executeRFC = new EjecutarRFC();
        executeRFC.setImports(function, importParams);
        executeRFC.setTable(paramsTable, "IT_MAREA", mareas);

        JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);
        JCoTable tblET_BIOM = tables.getTable(Tablas.ET_BIOM);
        JCoTable tblET_ESPE = tables.getTable(Tablas.ET_ESPE);
        JCoTable tblET_PSCINC = tables.getTable(Tablas.ET_PSCINC);

        Metodos metodos = new Metodos();
        List<HashMap<String, Object>> listET_BIOM = metodos.ListarObjetos(tblET_BIOM);
        List<HashMap<String, Object>> listET_ESPE = metodos.ListarObjetos(tblET_ESPE);
        List<HashMap<String, Object>> listET_PSCINC = metodos.ListarObjetos(tblET_PSCINC);

        JCoParameterList exports = function.getExportParameterList();
        String ep_mmax = exports.getValue("EP_MMAX").toString();
        String ep_mmin = exports.getValue("EP_MMIN").toString();

        BiomasaExports dto = new BiomasaExports();
        dto.setEt_biom(listET_BIOM);
        dto.setEt_espe(listET_ESPE);
        dto.setEt_pscinc(listET_PSCINC);
        dto.setEp_mmax(ep_mmax);
        dto.setEp_mmin(ep_mmin);
        dto.setMensaje("OK");

        return dto;
    }
}
