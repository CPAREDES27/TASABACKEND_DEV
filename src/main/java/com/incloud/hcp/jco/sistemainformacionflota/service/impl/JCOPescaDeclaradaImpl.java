package com.incloud.hcp.jco.sistemainformacionflota.service.impl;

import com.incloud.hcp.jco.sistemainformacionflota.dto.*;
import com.incloud.hcp.jco.sistemainformacionflota.service.JCOPescaDeclaradaService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JCOPescaDeclaradaImpl implements JCOPescaDeclaradaService {


    @Override
    public PescaDeclaradaExports PescaDeclarada(PescaDeclaradaImports imports) throws Exception {

        PescaDeclaradaExports pd = new PescaDeclaradaExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_PESCA_DECLA);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            importx.setValue("P_FECON", imports.getP_fecon());
            importx.setValue("P_CDMMA", imports.getP_cdmma());


            JCoParameterList tables = stfcConnection.getTableParameterList();

            stfcConnection.execute(destination);

            JCoTable STR_TP = tables.getTable(Tablas.STR_TP);
            JCoTable STR_TE = tables.getTable(Tablas.STR_TE);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);


            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> str_tp = metodo.ObtenerListObjetos(STR_TP, imports.getFieldstr_tp());
            List<HashMap<String, Object>> str_te = metodo.ObtenerListObjetos(STR_TE, imports.getFieldstr_te());
            List<HashMap<String, Object>> t_mensaje = metodo.ObtenerListObjetos(T_MENSAJE, imports.getFieldstr_te());

            /**
             * Cálculo de porcentajes
             */
            float totalCbod = 0.00f;
            float totalCbodOper = 0.00f;
            float totalGenPescDecl = 0.00f;
            for (HashMap<String, Object> str_tpItem : str_tp) {
                totalCbod += Float.parseFloat(str_tpItem.get("CPPMS").toString());
                totalCbodOper += Float.parseFloat(str_tpItem.get("CPBOP").toString());
                totalGenPescDecl += Float.parseFloat(str_tpItem.get("CNPEP").toString()) + Float.parseFloat(str_tpItem.get("CNPET").toString());
            }
            float finalTotalCbod = totalCbod;
            float finalTotalCbodOper = totalCbodOper;
            float finalTotalGenPescDecl = totalGenPescDecl;

            str_tp.stream().map(s -> {
                float porcCbod = Float.parseFloat(s.get("CPPMS").toString()) * 100 / finalTotalCbod;
                float porcCbodOper = Float.parseFloat(s.get("CPBOP").toString()) * 100 / finalTotalCbodOper;
                float totalPescDecl = Float.parseFloat(s.get("CNPEP").toString()) + Float.parseFloat(s.get("CNPET").toString());
                float porcPescDecl = totalPescDecl * 100 / finalTotalGenPescDecl;
                int totalNumEmba = Integer.parseInt(s.get("NEMBP").toString()) + Integer.parseInt(s.get("NEMBT").toString());

                int nembp = Integer.parseInt(s.get("NEMBP").toString());
                int nembt = Integer.parseInt(s.get("NEMBT").toString());

                if (Float.isNaN(porcCbod)) {
                    s.put("PORC_CBOD", 0.00f);
                } else {
                    s.put("PORC_CBOD", Math.round(porcCbod * 100.00) / 100.00);
                }

                if (Float.isNaN(porcCbodOper)) {
                    s.put("PORC_CBOD_OPER", 0.00f);
                } else {
                    s.put("PORC_CBOD_OPER", Math.round(porcCbodOper * 100.00) / 100.00);
                }

                if (Float.isNaN(porcPescDecl)) {
                    s.put("PORC_PESC_DECL", 0.00f);
                } else {
                    s.put("PORC_PESC_DECL", Math.round(porcPescDecl * 100.00) / 100.00);
                }

                if (nembp > 0) {
                    float promPescProp = Float.parseFloat(s.get("CNPEP").toString()) / nembp;
                    s.put("PROM_PESC_PROP", Math.round(promPescProp * 100.00) / 100.00);
                } else {
                    s.put("PROM_PESC_PROP", 0.00f);
                }

                if (nembt > 0) {
                    float promPescTerc = Float.parseFloat(s.get("CNPET").toString()) / nembt;
                    s.put("PROM_PESC_TERC", promPescTerc);
                } else {
                    s.put("PROM_PESC_TERC", 0.00f);
                }

                s.put("TOT_PESC_DECL", totalPescDecl);
                s.put("TOT_NUM_EMBA", totalNumEmba);

                return s;
            }).collect(Collectors.toList());

            pd.setT_mensaje(t_mensaje);
            pd.setStr_tp(str_tp);
            pd.setStr_te(str_te);
            pd.setMensaje("Ok");

        } catch (Exception e) {
            pd.setMensaje(e.getMessage());
        }

        return pd;
    }

    @Override
    public PescaDeclaradaDiariaExports PescaDeclaradaDiaria(PescaDeclaradaDiariaImports imports) throws Exception {

        PescaDeclaradaDiariaExports pdd = new PescaDeclaradaDiariaExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_PESCA_DECLARADA);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            importx.setValue("P_FEINI", imports.getP_feini());
            importx.setValue("P_FEFIN", imports.getP_fefin());


            JCoParameterList tables = stfcConnection.getTableParameterList();

            stfcConnection.execute(destination);

            JCoTable STR_DL = tables.getTable(Tablas.STR_DL);


            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> str_dl = metodo.ObtenerListObjetos(STR_DL, imports.getFieldstr_dl());

            /**
             * Cálculos de porcentajes y totales
             */
            str_dl.stream().map(s -> {
                float pescDeclChi = Float.parseFloat(s.get("PPCHI").toString()) + Float.parseFloat(s.get("PTCHI").toString());
                float pescDescChi = Float.parseFloat(s.get("PSPRO").toString()) + Float.parseFloat(s.get("PSTER").toString());
                float porcDeclChiProp = Float.parseFloat(s.get("PPCHI").toString()) * 100 / pescDeclChi;
                float porcDeclChiTerc = Float.parseFloat(s.get("PTCHI").toString()) * 100 / pescDeclChi;

                float eficProp = pescDeclChi / Float.parseFloat(s.get("CNEMP").toString());
                float eficTerc = pescDeclChi / Float.parseFloat(s.get("CNEMT").toString());

                float porcDifer = (pescDeclChi - pescDescChi) * 100 / (pescDeclChi);

                s.put("PESC_DECL_CHI", pescDeclChi);
                s.put("PESC_DESC_CHI", pescDescChi);

                if (!Float.isNaN(porcDeclChiProp)) {
                    s.put("PORC_DECL_CHI_PROP", porcDeclChiProp);
                } else {
                    s.put("PORC_DECL_CHI_PROP", 0.00f);
                }

                if (!Float.isNaN(porcDeclChiTerc)) {
                    s.put("PORC_DECL_CHI_TERC", porcDeclChiTerc);
                } else {
                    s.put("PORC_DECL_CHI_TERC", 0.00f);
                }

                if (!Float.isNaN(eficProp)) {
                    s.put("EFIC_PROP", eficProp);
                } else {
                    s.put("EFIC_PROP", 0.00f);
                }

                if (!Float.isNaN(eficTerc)) {
                    s.put("EFIC_TERC", eficTerc);
                } else {
                    s.put("EFIC_TERC", 0.00f);
                }

                if (!Float.isNaN(porcDifer)) {
                    s.put("PORC_DIFER", porcDifer);
                } else {
                    s.put("PORC_DIFER", 0.00f);
                }

                return s;
            }).collect(Collectors.toList());

            HashMap<String, Object> totales = new HashMap<>();

            //Obtener modelo de una fila y usarla para generar la fila de totales
            HashMap<String, Object> firstPescDecl = str_dl.get(0);

            for (Map.Entry<String, Object> mapEntry : firstPescDecl.entrySet()) {
                totales.put(mapEntry.getKey(), null);
            }

            float totPescDeclChi = 0.00f;
            float totPescDeclChd = 0.00f;
            float totPescDeclChiProp = 0.00f;
            float totPorcDeclChiProp = 0.00f;
            float totPescDeclChiTerc = 0.00f;
            float totPorcDeclChiTerc = 0.00f;
            float totEficProp = 0.00f;
            float totEficTerc = 0.00f;
            float totNumEmbaProp = 0;
            float totNumEmbaTerc = 0;
            float totPescDescChiProp = 0.00f;
            float totPescDescChiTerc = 0.00f;
            float totPescDescChi = 0.00f;
            float totPescDescPlantTerc = 0.00f;
            float totPescDescChd = 0.00f;
            float totPorcDifer = 0.00f;

            for (HashMap<String, Object> item : str_dl) {
                totPescDeclChi += Float.parseFloat(item.get("PPCHI").toString());
                totPescDeclChd += Float.parseFloat(item.get("PDECH").toString());
                totPescDeclChiProp += Float.parseFloat(item.get("PPCHI").toString());
                totPorcDeclChiProp += Float.parseFloat(item.get("PORC_DECL_CHI_PROP").toString());
                totPescDeclChiTerc += Float.parseFloat(item.get("PTCHI").toString());
                totPorcDeclChiTerc += Float.parseFloat(item.get("PORC_DECL_CHI_TERC").toString());
                totEficProp += Float.parseFloat(item.get("EFIC_PROP").toString());
                totEficTerc += Float.parseFloat(item.get("EFIC_TERC").toString());
                totNumEmbaProp += Integer.parseInt(item.get("CNEMP").toString());
                totNumEmbaTerc += Integer.parseInt(item.get("CNEMT").toString());
                totPescDescChiProp += Float.parseFloat(item.get("PSPRO").toString());
                totPescDescChiTerc += Float.parseFloat(item.get("PSTER").toString());
                totPescDescChi += Float.parseFloat(item.get("PESC_DESC_CHI").toString());
                totPescDescPlantTerc += Float.parseFloat(item.get("PSDTE").toString());
                totPescDescChd += Float.parseFloat(item.get("PSCHD").toString());
                totPorcDifer += Float.parseFloat(item.get("PORC_DIFER").toString());
            }
            totales.replace("PPCHI", totPescDeclChi);
            totales.replace("PDECH", totPescDeclChd);
            totales.replace("PPCHI", totPescDeclChiProp);
            totales.replace("PORC_DECL_CHI_PROP", totPorcDeclChiProp);
            totales.replace("PTCHI", totPescDeclChiTerc);
            totales.replace("PORC_DECL_CHI_TERC", totPorcDeclChiTerc);
            totales.replace("EFIC_PROP", totEficProp);
            totales.replace("EFIC_TERC", totEficTerc);
            totales.replace("CNEMP", totNumEmbaProp);
            totales.replace("CNEMT", totNumEmbaTerc);
            totales.replace("PSPRO", totPescDescChiProp);
            totales.replace("PSTER", totPescDescChiTerc);
            totales.replace("PESC_DESC_CHI", totPescDescChi);
            totales.replace("PSDTE", totPescDescPlantTerc);
            totales.replace("PSCHD", totPescDescChd);
            totales.replace("PORC_DIFER", totPorcDifer);

            str_dl.add(totales);

            pdd.setStr_dl(str_dl);
            pdd.setMensaje("Ok");

        } catch (Exception e) {
            pdd.setMensaje(e.getMessage());
        }

        return pdd;
    }

    @Override
    public PescaDeclaradaDifeExports PescaDeclaradaDife(PescaDeclaradaDifeImports imports) throws Exception {

        PescaDeclaradaDifeExports pdd = new PescaDeclaradaDifeExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_PESC_DECLA_DIFE2);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            importx.setValue("P_FECHA", imports.getP_fecha());
            importx.setValue("P_FIDES", imports.getP_fides());
            importx.setValue("P_FFDES", imports.getP_ffdes());


            JCoParameterList tables = stfcConnection.getTableParameterList();

            stfcConnection.execute(destination);

            JCoTable STR_PTD = tables.getTable(Tablas.STR_PTD);
            JCoTable STR_PTR = tables.getTable(Tablas.STR_PTR);
            JCoTable STR_EMD = tables.getTable(Tablas.STR_EMD);
            JCoTable STR_EMR = tables.getTable(Tablas.STR_EMR);


            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> str_ptd = metodo.ObtenerListObjetos(STR_PTD, imports.getFieldstr_ptd());
            List<HashMap<String, Object>> str_ptr = metodo.ObtenerListObjetos(STR_PTR, imports.getFieldstr_ptr());
            List<HashMap<String, Object>> str_emd = metodo.ObtenerListObjetos(STR_EMD, imports.getFieldstr_emd());
            List<HashMap<String, Object>> str_emr = metodo.ObtenerListObjetos(STR_EMR, imports.getFieldstr_emr());

            pdd.setStr_ptd(str_ptd);
            pdd.setStr_ptr(str_ptr);
            pdd.setStr_emd(str_emd);
            pdd.setStr_emr(str_emr);
            pdd.setMensaje("Ok");

        } catch (Exception e) {
            pdd.setMensaje(e.getMessage());
        }

        return pdd;
    }


}
