package com.incloud.hcp.jco.sistemainformacionflota.service.impl;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaCompetenciaRadialExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaCompetenciaRadialImports;
import com.incloud.hcp.jco.sistemainformacionflota.service.JCOPescaCompetenciaRadialService;
import com.incloud.hcp.jco.tripulantes.dto.Options;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JCOPescaCompetenciaRadialImpl implements JCOPescaCompetenciaRadialService {

    @Override
    public PescaCompetenciaRadialExports PescaCompetenciaRadial(PescaCompetenciaRadialImports imports) throws Exception {

        PescaCompetenciaRadialExports pcr=new PescaCompetenciaRadialExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_DECLA_COMPE_RADIO);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user ());
            importx.setValue("P_FEINI", imports.getP_feini());
            importx.setValue("P_FEFIN", imports.getP_fefin());
            importx.setValue("P_CDGRE", imports.getP_cdgre());
            importx.setValue("P_CTGRA", imports.getP_ctgra());
            importx.setValue("P_CDTPC", imports.getP_cdtpc());

            List<MaestroOptions> options = imports.getP_options();
            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < options.size(); i++) {
                MaestroOptions mo = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();

                record.put("WA", mo.getWa());
                tmpOptions.add(record);
            }

            JCoParameterList tables = stfcConnection.getTableParameterList();

            stfcConnection.execute(destination);

            JCoTable STR_ZLT = tables.getTable(Tablas.STR_ZLT);
            JCoTable STR_PTO = tables.getTable(Tablas.STR_PTO);
            JCoTable STR_GRE = tables.getTable(Tablas.STR_GRE);
            JCoTable STR_EMP = tables.getTable(Tablas.STR_EMP);
            JCoTable STR_PGE = tables.getTable(Tablas.STR_PGE);
            JCoTable STR_EPP = tables.getTable(Tablas.STR_EPP);


            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> str_zlt = metodo.ObtenerListObjetos(STR_ZLT, imports.getFieldstr_zlt());
            List<HashMap<String, Object>> str_pto = metodo.ObtenerListObjetos(STR_PTO, imports.getFieldstr_pto());
            List<HashMap<String, Object>> str_gre = metodo.ObtenerListObjetos(STR_GRE, imports.getFieldstr_gre());
            List<HashMap<String, Object>> str_emp = metodo.ObtenerListObjetos(STR_EMP, imports.getFieldstr_emp());
            List<HashMap<String, Object>> str_pge = metodo.ObtenerListObjetos(STR_PGE, imports.getFieldstr_pge());
            List<HashMap<String, Object>> str_epp = metodo.ObtenerListObjetos(STR_EPP, imports.getFieldstr_epp());

            pcr.setStr_zlt(str_zlt);
            pcr.setStr_pto(str_pto);
            pcr.setStr_gre(str_gre);
            pcr.setStr_emp(str_emp);
            pcr.setStr_pge(str_pge);
            pcr.setStr_epp(str_epp);
            pcr.setMensaje("Ok");

        }catch (Exception e){
            pcr.setMensaje(e.getMessage());
        }

        return pcr;
    }
}
