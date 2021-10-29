package com.incloud.hcp.jco.controlLogistico.service.impl;

import com.incloud.hcp.jco.controlLogistico.dto.AceitesUsadosExports;
import com.incloud.hcp.jco.controlLogistico.dto.AceitesUsadosImports;
import com.incloud.hcp.jco.controlLogistico.dto.RegistroAceiteDto;
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
            if(imports.getIp_tope().equals("NR")){
                for(int i=0; i<imports.getT_rpn().size();i++) {
                    RegistroAceiteDto  dto = imports.getT_rpn().get(i);
                    T_RPN.appendRow();
                    T_RPN.setValue("MANDT",dto.getMANDT());
                    T_RPN.setValue("NRRNV",dto.getNRRNV());
                    T_RPN.setValue("NRPOS",dto.getNRPOS());
                    T_RPN.setValue("FHRNV;",dto.getFHRNV());
                    T_RPN.setValue("ESRNV",dto.getESRNV());
                    T_RPN.setValue("DSEST",dto.getDSEST());
                    T_RPN.setValue("CDSUM",dto.getCDSUM());
                    T_RPN.setValue("CNSUM",dto.getCNSUM());
                    T_RPN.setValue("CDUMD",dto.getCDUMD());
                    T_RPN.setValue("MSEHL",dto.getMSEHL());
                    T_RPN.setValue("CDPTA",dto.getCDPTA());
                    T_RPN.setValue("DSWKS",dto.getDSWKS());
                    T_RPN.setValue("CDALM",dto.getCDALM());
                    T_RPN.setValue("LGOBE",dto.getLGOBE());
                    T_RPN.setValue("CDEMB",dto.getCDEMB());
                    T_RPN.setValue("NMEMB",dto.getNMEMB());
                    T_RPN.setValue("NRGRM",dto.getNRGRM());
                    T_RPN.setValue("CNBAR",dto.getCNBAR());
                    T_RPN.setValue("NRTKP",dto.getNRTKP());
                    T_RPN.setValue("NRTGA",dto.getNRTGA());
                    T_RPN.setValue("DEMAT",dto.getDEMAT());
                    T_RPN.setValue("FHCRN",dto.getFHCRN());
                    T_RPN.setValue("HRCRN",dto.getHRCRN());
                }
                T_RNV.appendRow();
                T_RNV.setValue("MANDT","");
                T_RNV.setValue("NRRNV","0000000000");
                T_RNV.setValue("FHRNV",imports.getFHRNV());
                T_RNV.setValue("ESRNV","");
                T_RNV.setValue("FHCRN","");
                T_RNV.setValue("HRCRN","00:00:00");
                T_RNV.setValue("ATCRN","");
                T_RNV.setValue("FHMFN","");
                T_RNV.setValue("HRMFN","00:00:00");
                T_RNV.setValue("ATMFN","");

            }

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