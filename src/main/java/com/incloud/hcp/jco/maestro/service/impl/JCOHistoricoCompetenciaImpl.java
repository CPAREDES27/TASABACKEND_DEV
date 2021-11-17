package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.PescaCompCargaHistoricoExports;
import com.incloud.hcp.jco.maestro.dto.PescaCompCargaHistoricoImports;
import com.incloud.hcp.jco.maestro.service.JCOHistoricoCompetenciaService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class JCOHistoricoCompetenciaImpl implements JCOHistoricoCompetenciaService {
    @Override
    public PescaCompCargaHistoricoExports CargaHistorico(PescaCompCargaHistoricoImports imports) throws Exception {
        HashMap<String, Object> importParams = new HashMap<>();
        Metodos metodo = new Metodos();
        importParams.put("P_CDPCN", imports.getP_cdpcn());

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_PESC_COMP_CARGA_HIST);

        EjecutarRFC ejecutarRFC = new EjecutarRFC();
        ejecutarRFC.setImports(function, importParams);

        /**
         * Se refactorizará para añadir los exports
         */
        //JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);
        //JCoTable tblS_MAREA = tables.getTable(Tablas.S_MAREA);

        //List<HashMap<String, Object>> listS_MAREA = metodo.ListarObjetos(tblS_MAREA);
        PescaCompCargaHistoricoExports dto = new PescaCompCargaHistoricoExports();
        dto.setMensaje("OK");
        return dto;
    }
}
