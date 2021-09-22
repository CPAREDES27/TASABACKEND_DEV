package com.incloud.hcp.jco.sistemainformacionflota.service.impl;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaCompetenciaProduceExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaCompetenciaProduceImports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaCompetenciaRadialExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaCompetenciaRadialImports;
import com.incloud.hcp.jco.sistemainformacionflota.service.JCOPescaCompetenciaService;
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
public class JCOPescaCompetenciaImpl implements JCOPescaCompetenciaService {

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
            EjecutarRFC exec = new EjecutarRFC();
            exec.setTable(tables, Tablas.P_OPTIONS, tmpOptions);



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

    @Override
    public PescaCompetenciaProduceExports PescaCompetenciaProduce(PescaCompetenciaProduceImports imports) throws Exception {

        PescaCompetenciaProduceExports pcp=new PescaCompetenciaProduceExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_PESC_COMP_GNRAL);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("CDUSR", imports.getCdusr ());
            importx.setValue("P_TCONS", imports.getP_tcons());
            importx.setValue("P_FEINI", imports.getP_feini());
            importx.setValue("P_FEFIN", imports.getP_fefin());
            importx.setValue("P_CDGRE", imports.getP_cdgre());
            importx.setValue("P_GRUEB", imports.getP_grueb());
            importx.setValue("P_CTGRA", imports.getP_ctgra());
            importx.setValue("P_CDPCN", imports.getP_cdpcn());
            importx.setValue("P_ZCDZAR", imports.getP_zcdzar());
            importx.setValue("P_EMBA", imports.getP_emba());
            importx.setValue("P_EMPEB", imports.getP_empeb());

            List<MaestroOptions> options = imports.getP_options();
            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < options.size(); i++) {
                MaestroOptions mo = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();

                record.put("WA", mo.getWa());
                tmpOptions.add(record);
            }

            JCoParameterList tables = stfcConnection.getTableParameterList();
            EjecutarRFC exec = new EjecutarRFC();
            exec.setTable(tables, Tablas.P_OPTIONS, tmpOptions);
            stfcConnection.execute(destination);

            JCoTable STR_ZLT = tables.getTable(Tablas.STR_ZLT);
            JCoTable STR_PTO = tables.getTable(Tablas.STR_PTO);
            JCoTable STR_GRE = tables.getTable(Tablas.STR_GRE);
            JCoTable STR_PGE = tables.getTable(Tablas.STR_PGE);
            JCoTable STR_GZP = tables.getTable(Tablas.STR_GZP);
            JCoTable STR_EPP = tables.getTable(Tablas.STR_EPP);
            JCoTable STR_ZPL = tables.getTable(Tablas.STR_ZPL);
            JCoTable STR_EMP = tables.getTable(Tablas.STR_EMP);
            JCoTable STR_PEM = tables.getTable(Tablas.STR_PEM);
            JCoTable STR_PED = tables.getTable(Tablas.STR_PED);

            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> str_zlt = metodo.ObtenerListObjetos(STR_ZLT, imports.getFieldstr_zlt());
            List<HashMap<String, Object>> str_pto = metodo.ObtenerListObjetos(STR_PTO, imports.getFieldstr_pto());
            List<HashMap<String, Object>> str_gre = metodo.ObtenerListObjetos(STR_GRE, imports.getFieldstr_gre());
            List<HashMap<String, Object>> str_pge = metodo.ObtenerListObjetos(STR_PGE, imports.getFieldstr_pge());
            List<HashMap<String, Object>> str_gzp = metodo.ObtenerListObjetos(STR_GZP, imports.getFieldstr_gzp());
            List<HashMap<String, Object>> str_epp = metodo.ObtenerListObjetos(STR_EPP, imports.getFieldstr_epp());
            List<HashMap<String, Object>> str_zpl = metodo.ObtenerListObjetos(STR_ZPL, imports.getFieldstr_zpl());
            List<HashMap<String, Object>> str_emp = metodo.ObtenerListObjetos(STR_EMP, imports.getFieldstr_emp());
            List<HashMap<String, Object>> str_pem = metodo.ObtenerListObjetos(STR_PEM, imports.getFieldstr_pem());
            List<HashMap<String, Object>> str_ped = metodo.ObtenerListObjetos(STR_PED, imports.getFieldstr_ped());

            pcp.setStr_zlt(str_zlt);
            pcp.setStr_pto(str_pto);
            pcp.setStr_gre(str_gre);
            pcp.setStr_pge(str_pge);
            pcp.setStr_gzp(str_gzp);
            pcp.setStr_epp(str_epp);
            pcp.setStr_zpl(str_zpl);
            pcp.setStr_emp(str_emp);
            pcp.setStr_pem(str_pem);
            pcp.setStr_ped(str_ped);
            pcp.setMensaje("Ok");

        }catch (Exception e){
            pcp.setMensaje(e.getMessage());
        }

        return pcp;
    }
}
