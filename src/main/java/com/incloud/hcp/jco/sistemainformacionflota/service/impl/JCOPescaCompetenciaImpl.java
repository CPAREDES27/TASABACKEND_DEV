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
            Metodos metodo = new Metodos();
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

            List<HashMap<String, Object>> tmpOptions = metodo.ValidarOptions(imports.getP_option(),imports.getP_options());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            EjecutarRFC exec = new EjecutarRFC();
            exec.setTable(tables, Tablas.P_OPTIONS, tmpOptions);
            stfcConnection.execute(destination);

            JCoTable tblSTR_ZLT = tables.getTable(Tablas.STR_ZLT);
            JCoTable tblSTR_PTO = tables.getTable(Tablas.STR_PTO);
            JCoTable tblSTR_GRE = tables.getTable(Tablas.STR_GRE);
            JCoTable tblSTR_PGE = tables.getTable(Tablas.STR_PGE);
            JCoTable tblSTR_GZP = tables.getTable(Tablas.STR_GZP);
            JCoTable tblSTR_EPP = tables.getTable(Tablas.STR_EPP);
            JCoTable tblSTR_ZPL = tables.getTable(Tablas.STR_ZPL);
            JCoTable tblSTR_EMP = tables.getTable(Tablas.STR_EMP);
            JCoTable tblSTR_PEM = tables.getTable(Tablas.STR_PEM);
            JCoTable tblSTR_PED = tables.getTable(Tablas.STR_PED);
            JCoTable tblSTR_GRP = tables.getTable(Tablas.STR_GRP);
            JCoTable tblSTR_PLM = tables.getTable(Tablas.STR_PLM);

            List<HashMap<String, Object>> str_zlt = metodo.ListarObjetosLazy(tblSTR_ZLT);
            List<HashMap<String, Object>> str_pto = metodo.ListarObjetosLazy(tblSTR_PTO);
            List<HashMap<String, Object>> str_gre = metodo.ListarObjetosLazy(tblSTR_GRE);
            List<HashMap<String, Object>> str_pge = metodo.ListarObjetosLazy(tblSTR_PGE);
            List<HashMap<String, Object>> str_gzp = metodo.ListarObjetosLazy(tblSTR_GZP);
            List<HashMap<String, Object>> str_epp = metodo.ListarObjetosLazy(tblSTR_EPP);
            List<HashMap<String, Object>> str_zpl = metodo.ListarObjetosLazy(tblSTR_ZPL);
            List<HashMap<String, Object>> str_emp = metodo.ListarObjetosLazy(tblSTR_EMP);
            List<HashMap<String, Object>> str_pem = metodo.ListarObjetosLazy(tblSTR_PEM);
            List<HashMap<String, Object>> str_ped = metodo.ListarObjetosLazy(tblSTR_PED);
            List<HashMap<String, Object>> str_grp = metodo.ListarObjetosLazy(tblSTR_GRP);
            List<HashMap<String, Object>> str_plm = metodo.ListarObjetosLazy(tblSTR_PLM);

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
            pcp.setStr_grp(str_grp);
            pcp.setStr_plm(str_plm);
            pcp.setMensaje("Ok");

        }catch (Exception e){
            pcp.setMensaje(e.getMessage());
        }

        return pcp;
    }
}
