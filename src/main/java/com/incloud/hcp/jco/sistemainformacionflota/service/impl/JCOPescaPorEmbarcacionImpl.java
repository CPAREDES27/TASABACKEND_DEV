package com.incloud.hcp.jco.sistemainformacionflota.service.impl;

import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaPorEmbarcaExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaPorEmbarcaImports;
import com.incloud.hcp.jco.sistemainformacionflota.service.JCOPescaPorEmbarcacionService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class JCOPescaPorEmbarcacionImpl implements JCOPescaPorEmbarcacionService {

    @Override
    public PescaPorEmbarcaExports PescaPorEmbarcacion(PescaPorEmbarcaImports imports) throws Exception {

        PescaPorEmbarcaExports ppe= new PescaPorEmbarcaExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_PESCA_EMBARCA_BTP);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user ());
            importx.setValue("P_FCINI", imports.getP_fcini());
            importx.setValue("P_FCFIN", imports.getP_fcfin());
            importx.setValue("P_CDTEM", imports.getP_cdtem());
            importx.setValue("P_CDPCN", imports.getP_cdpcn());
            importx.setValue("P_CDEMB", imports.getP_cdemb());
            importx.setValue("P_PAG", imports.getP_pag());


            JCoParameterList tables = stfcConnection.getTableParameterList();
            JCoParameterList export    = stfcConnection.getExportParameterList();

            stfcConnection.execute(destination);

            JCoTable STR_PEM = tables.getTable(Tablas.STR_PEM);
            ppe.setP_totalpag(export.getString("P_TOTALPAG"));

            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> str_pem = metodo.ListarObjetosLazy(STR_PEM);

            ppe.setStr_pem(str_pem);
            ppe.setMensaje("Ok");

        }catch (Exception e){
            ppe.setMensaje(e.getMessage());
        }

        return ppe;
    }
}
