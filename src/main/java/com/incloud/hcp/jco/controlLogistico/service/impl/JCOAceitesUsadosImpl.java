package com.incloud.hcp.jco.controlLogistico.service.impl;

import com.incloud.hcp.jco.controlLogistico.dto.AceitesUsadosExports;
import com.incloud.hcp.jco.controlLogistico.dto.AceitesUsadosImports;
import com.incloud.hcp.jco.controlLogistico.service.JCOAceitesUsadosService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class JCOAceitesUsadosImpl implements JCOAceitesUsadosService {

    public AceitesUsadosExports Listar(AceitesUsadosImports imports)throws Exception{

        AceitesUsadosExports au=new AceitesUsadosExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_GEST_ACEITES);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("IP_TOPE", imports.getIp_tope());
            importx.setValue("IP_NRRNV", imports.getIp_nrrnv());
            importx.setValue("IP_CDEMB", imports.getIp_cdemb());
            importx.setValue("IP_FHRNVI", imports.getIp_fhrnvi());
            importx.setValue("IP_FHRNVF", imports.getIp_fhrnvf());
            importx.setValue("IP_CDALM", imports.getIp_cdalm());
            importx.setValue("IP_ESRNV", imports.getIp_esrnv());

            stfcConnection.execute(destination);
            Metodos metodo = new Metodos();

            JCoParameterList export=stfcConnection.getExportParameterList();
            au.setEp_nrrnv(export.getValue("EP_NRRNV").toString());
            JCoTable ET_MENSJ= export.getTable(Tablas.ET_MENSJ);
            List<HashMap<String, Object>> et_mnsaj=metodo.ObtenerListObjetos(ET_MENSJ, imports.getFieldsEt_mensj());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            JCoTable T_RNV = tables.getTable(Tablas.T_RNV);
            JCoTable T_RPN = tables.getTable(Tablas.T_RPN);

            List<HashMap<String, Object>> t_rnv = metodo.ObtenerListObjetos(T_RNV, imports.getFieldsT_rnv());
            List<HashMap<String, Object>> t_rpn = metodo.ObtenerListObjetos(T_RPN, imports.getFieldsT_rpn());

            au.setT_rnv(t_rnv);
            au.setT_rpn(t_rpn);
            au.setEt_mensj(et_mnsaj);
            au.setMensaje("Ok");
        }catch (Exception e){
            au .setMensaje(e.getMessage());
        }

        return au;
    }
}