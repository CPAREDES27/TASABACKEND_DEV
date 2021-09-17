package com.incloud.hcp.jco.tripulantes.service.impl;

import com.incloud.hcp.jco.tripulantes.dto.ImpresFormatosProduceExports;
import com.incloud.hcp.jco.tripulantes.dto.ImpresFormatosProduceImports;
import com.incloud.hcp.jco.tripulantes.service.JCOImpresFormatosProduceService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;


@Service
public class JCOImpresFormatosProduceImpl implements JCOImpresFormatosProduceService {


    @Override
    public ImpresFormatosProduceExports ImpresionFormatosProduce(ImpresFormatosProduceImports imports) throws Exception {

        ImpresFormatosProduceExports ifp=new ImpresFormatosProduceExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_IMPR_FORM_PRODUCE);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("IP_TOPE", imports.getIp_tope ());
            importx.setValue("IP_WERKS", imports.getIp_werks());
            importx.setValue("IP_FINI", imports.getIp_fini());
            importx.setValue("IP_FFIN", imports.getIp_ffin());

            JCoParameterList tables = stfcConnection.getTableParameterList();

            stfcConnection.execute(destination);

            JCoTable T_RSPRCE = tables.getTable(Tablas.T_RSPRCE);
            JCoTable T_DTPRCE = tables.getTable(Tablas.T_DTPRCE);

            Metodos metodo = new Metodos();
            List<HashMap<String, Object>>  t_rsprce = metodo.ObtenerListObjetos(T_RSPRCE, imports.getFieldst_rsprce());
            List<HashMap<String, Object>>  t_dtprce = metodo.ObtenerListObjetos(T_DTPRCE, imports.getFieldst_dtprce());

            ifp.setT_rsprce(t_rsprce);
            ifp.setT_dtprce(t_dtprce);
            ifp.setMensaje("Ok");

        }catch (Exception e){
            ifp.setMensaje(e.getMessage());
        }
        return ifp;
    }
}
