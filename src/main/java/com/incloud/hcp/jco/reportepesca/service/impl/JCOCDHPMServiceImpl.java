package com.incloud.hcp.jco.reportepesca.service.impl;

import com.incloud.hcp.jco.reportepesca.dto.CHDPMExports;
import com.incloud.hcp.jco.reportepesca.dto.CHDPMImports;
import com.incloud.hcp.jco.reportepesca.service.JCOCDHPMService;
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
public class JCOCDHPMServiceImpl implements JCOCDHPMService {

    @Override
    public CHDPMExports ObtenerCHDPM(CHDPMImports imports) throws Exception {
        HashMap<String, Object> importParams = new HashMap<>();
        importParams.put("IP_CDMAR", imports.getIp_cdmar());
        importParams.put("IP_CANTI", imports.getIp_canti());

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_GESP_CONSULTA_CHDPM);

        EjecutarRFC executeRFC = new EjecutarRFC();
        executeRFC.setImports(function, importParams);

        JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);
        JCoTable tbl_MCHPM = tables.getTable(Tablas.T_MCHPM);
        JCoTable tbl_DCHPM = tables.getTable(Tablas.T_DCHPM);
        JCoTable tbl_BODEG = tables.getTable(Tablas.T_BODEG);

        Metodos metodos = new Metodos();
        List<HashMap<String, Object>> listT_MCHPM = metodos.ListarObjetos(tbl_MCHPM);
        List<HashMap<String, Object>> listT_DCHPM = metodos.ListarObjetos(tbl_DCHPM);
        List<HashMap<String, Object>> listT_BODEG = metodos.ListarObjetos(tbl_BODEG);

        //Unir listT_MCHPM y listT_DCHPM
        List<HashMap<String, Object>> list_DetallReport = new ArrayList<>();
        for (int i = 0; i < listT_MCHPM.size(); i++) {
            HashMap<String, Object> mchpm = listT_MCHPM.get(i);
            HashMap<String, Object> dchpm = listT_DCHPM.get(i);
            HashMap<String, Object> detailReport = new HashMap<>(mchpm);

            detailReport.putAll(dchpm);
            list_DetallReport.add(detailReport);
        }

        CHDPMExports dto = new CHDPMExports();
        dto.setT_DetailsReport(list_DetallReport);
        dto.setT_bodeg(listT_BODEG);
        dto.setMensaje("OK");

        return dto;
    }
}
