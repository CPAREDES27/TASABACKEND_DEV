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

            /*
            HashMap<String, Object> totales = new HashMap<>();

            //Obtener modelo de una fila y usarla para generar la fila de totales
            HashMap<String, Object> firstEntry = str_tp.get(0);
            for (Map.Entry<String, Object> mapEntry : firstEntry.entrySet()) {
                totales.put(mapEntry.getKey(), null);
            }

            int totGenEmbaAsig = 0;
            int totGenEmbaPesc = 0;
            int totGenEmbaInop = 0;
            int totGenEmbaOtro = 0;
            float totGenPescDesc = 0.00f;
            float totGenPescDecl = 0.00f;
            int totGenNumEmba = 0;
            int totGenPescDeclProp = 0;
            int totGenNumEmbaProp = 0;
            int totGenPescDeclTerc = 0;
            int totGenNumEmbaTerc = 0;
            float totGenPromProp = 0.00f;
            float totGenPromTerc = 0.00f;

            for (HashMap<String, Object> item : str_tp) {
                totGenEmbaAsig += Integer.parseInt(item.get("CEMBA").toString());
                totGenEmbaPesc += Integer.parseInt(item.get("CEMBP").toString());
                totGenEmbaInop += Integer.parseInt(item.get("CEMBI").toString());
                totGenEmbaOtro += Integer.parseInt(item.get("CEMBO").toString());
                totGenPescDesc += Float.parseFloat(item.get("CNPDS").toString());
                totGenPescDecl += Float.parseFloat(item.get("TOT_PESC_DECL").toString());
                totGenNumEmba += Integer.parseInt(item.get("TOT_NUM_EMBA").toString());
                totGenPescDeclProp += Integer.parseInt(item.get("CNPEP").toString());
                totGenNumEmbaProp += Integer.parseInt(item.get("NEMBP").toString());
                totGenPescDeclTerc += Integer.parseInt(item.get("CNPET").toString());
                totGenNumEmbaTerc += Integer.parseInt(item.get("NEMBT").toString());
                totGenPromProp += Float.parseFloat(item.get("PROM_PESC_PROP").toString());
                totGenPromTerc += Float.parseFloat(item.get("PROM_PESC_TERC").toString());
            }

            totales.replace("CEMBA",totGenEmbaAsig);
            totales.replace("CEMBP",totGenEmbaPesc);
            totales.replace("CEMBI",totGenEmbaInop);
            totales.replace("CEMBO",totGenEmbaOtro);
            totales.replace("CNPDS",totGenPescDesc);
            totales.replace("TOT_PESC_DECL",totGenPescDecl);
            totales.replace("TOT_NUM_EMBA",totGenNumEmba);
            totales.replace("CNPEP",totGenPescDeclProp);
            totales.replace("NEMBP",totGenNumEmbaProp);
            totales.replace("CNPET",totGenPescDeclTerc);
            totales.replace("NEMBT",totGenNumEmbaTerc);
            totales.replace("PROM_PESC_PROP",totGenPromProp);
            totales.replace("PROM_PESC_TERC",totGenPromTerc);

            str_tp.add(totales);

             */

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
             * Redondear a 2 dígitos los campos decimales
             *
             */
            str_dl.stream().map(s -> {
                double ppchi = Math.round(Double.parseDouble(s.get("PPCHI").toString()) * 100.00) / 100.00;
                double ptchi = Math.round(Double.parseDouble(s.get("PTCHI").toString()) * 100.00) / 100.00;
                double pspro = Math.round(Double.parseDouble(s.get("PSPRO").toString()) * 100.00) / 100.00;
                double pster = Math.round(Double.parseDouble(s.get("PSTER").toString()) * 100.00) / 100.00;
                double psdte = Math.round(Double.parseDouble(s.get("PSDTE").toString()) * 100.00) / 100.00;
                double pdech = Math.round(Double.parseDouble(s.get("PDECH").toString()) * 100.00) / 100.00;
                double pschd = Math.round(Double.parseDouble(s.get("PSCHD").toString()) * 100.00) / 100.00;

                s.replace("PPCHI", ppchi);
                s.replace("PTCHI", ptchi);
                s.replace("PSPRO", pspro);
                s.replace("PSTER", pster);
                s.replace("PSDTE", psdte);
                s.replace("PDECH", pdech);
                s.replace("PSCHD", pschd);

                return s;
            });

            /**
             * Cálculos de porcentajes y totales
             */
            str_dl.stream().map(s -> {
                double pescDeclChi = Double.parseDouble(s.get("PPCHI").toString()) + Double.parseDouble(s.get("PTCHI").toString());
                double pescDescChi = Double.parseDouble(s.get("PSPRO").toString()) + Double.parseDouble(s.get("PSTER").toString());
                double porcDeclChiProp = Double.parseDouble(s.get("PPCHI").toString()) * 100 / pescDeclChi;
                double porcDeclChiTerc = Double.parseDouble(s.get("PTCHI").toString()) * 100 / pescDeclChi;

                double eficProp = pescDeclChi / Double.parseDouble(s.get("CNEMP").toString());
                double eficTerc = pescDeclChi / Double.parseDouble(s.get("CNEMT").toString());

                double porcDifer = (pescDeclChi - pescDescChi) * 100 / (pescDeclChi);

                s.put("PESC_DECL_CHI", Math.round(pescDeclChi * 100.00) / 100.00);
                s.put("PESC_DESC_CHI", Math.round(pescDescChi * 100.00) / 100.00);

                if (!Double.isNaN(porcDeclChiProp)) {
                    s.put("PORC_DECL_CHI_PROP", Math.round(porcDeclChiProp * 100.00) / 100.00);
                } else {
                    s.put("PORC_DECL_CHI_PROP", 0);
                }

                if (!Double.isNaN(porcDeclChiTerc)) {
                    s.put("PORC_DECL_CHI_TERC", Math.round(porcDeclChiTerc * 100.00) / 100.00);
                } else {
                    s.put("PORC_DECL_CHI_TERC", 0);
                }

                if (!Double.isNaN(eficProp)) {
                    s.put("EFIC_PROP", Math.round(eficProp * 100.00) / 100.00);
                } else {
                    s.put("EFIC_PROP", 0);
                }

                if (!Double.isNaN(eficTerc)) {
                    s.put("EFIC_TERC", Math.round(eficTerc * 100.00) / 100.00);
                } else {
                    s.put("EFIC_TERC", 0);
                }

                if (!Double.isNaN(porcDifer)) {
                    s.put("PORC_DIFER", Math.round(porcDifer * 100.00) / 100.00);
                } else {
                    s.put("PORC_DIFER", 0);
                }

                return s;
            }).collect(Collectors.toList());

            HashMap<String, Object> totales = new HashMap<>();

            //Obtener modelo de una fila y usarla para generar la fila de totales
            HashMap<String, Object> firstPescDecl = str_dl.get(0);

            for (Map.Entry<String, Object> mapEntry : firstPescDecl.entrySet()) {
                totales.put(mapEntry.getKey(), null);
            }

            double totPescDeclChi = 0;
            double totPescDeclChd = 0;
            double totPescDeclChiProp = 0;
            double totPorcDeclChiProp = 0;
            double totPescDeclChiTerc = 0;
            double totPorcDeclChiTerc = 0;
            double totEficProp = 0;
            double totEficTerc = 0;
            int totNumEmbaProp = 0;
            int totNumEmbaTerc = 0;
            double totPescDescChiProp = 0;
            double totPescDescChiTerc = 0;
            double totPescDescChi = 0;
            double totPescDescPlantTerc = 0;
            double totPescDescChd = 0;
            double totPorcDifer = 0;

            for (HashMap<String, Object> item : str_dl) {
                totPescDeclChi += Double.parseDouble(item.get("PESC_DECL_CHI").toString());
                totPescDeclChd += Double.parseDouble(item.get("PDECH").toString());
                totPescDeclChiProp += Double.parseDouble(item.get("PPCHI").toString());
                totPorcDeclChiProp += Double.parseDouble(item.get("PORC_DECL_CHI_PROP").toString());
                totPescDeclChiTerc += Double.parseDouble(item.get("PTCHI").toString());
                totPorcDeclChiTerc += Double.parseDouble(item.get("PORC_DECL_CHI_TERC").toString());
                totEficProp += Double.parseDouble(item.get("EFIC_PROP").toString());
                totEficTerc += Double.parseDouble(item.get("EFIC_TERC").toString());
                totNumEmbaProp += Integer.parseInt(item.get("CNEMP").toString());
                totNumEmbaTerc += Integer.parseInt(item.get("CNEMT").toString());
                totPescDescChiProp += Double.parseDouble(item.get("PSPRO").toString());
                totPescDescChiTerc += Double.parseDouble(item.get("PSTER").toString());
                totPescDescChi += Double.parseDouble(item.get("PESC_DESC_CHI").toString());
                totPescDescPlantTerc += Double.parseDouble(item.get("PSDTE").toString());
                totPescDescChd += Double.parseDouble(item.get("PSCHD").toString());
                totPorcDifer += Double.parseDouble(item.get("PORC_DIFER").toString());
            }
            totales.replace("PESC_DECL_CHI", Math.round(totPescDeclChi * 100.00) / 100.00);
            totales.replace("PDECH", Math.round(totPescDeclChd * 100.00) / 100.00);
            totales.replace("PPCHI", Math.round(totPescDeclChiProp * 100.00) / 100.00);
            totales.replace("PORC_DECL_CHI_PROP", Math.round(totPorcDeclChiProp * 100.00) / 100.00);
            totales.replace("PTCHI", Math.round(totPescDeclChiTerc * 100.00) / 100.00);
            totales.replace("PORC_DECL_CHI_TERC", Math.round(totPorcDeclChiTerc * 100.00) / 100.00);
            totales.replace("EFIC_PROP", Math.round(totEficProp * 100.00) / 100.00);
            totales.replace("EFIC_TERC", Math.round(totEficTerc * 100.00) / 100.00);
            totales.replace("CNEMP", totNumEmbaProp);
            totales.replace("CNEMT", totNumEmbaTerc);
            totales.replace("PSPRO", Math.round(totPescDescChiProp * 100.00) / 100.00);
            totales.replace("PSTER", Math.round(totPescDescChiTerc * 100.00) / 100.00);
            totales.replace("PESC_DESC_CHI", Math.round(totPescDescChi * 100.00) / 100.00);
            totales.replace("PSDTE", Math.round(totPescDescPlantTerc * 100.00) / 100.00);
            totales.replace("PSCHD", Math.round(totPescDescChd * 100.00) / 100.00);
            totales.replace("PORC_DIFER", Math.round(totPorcDifer * 100.00) / 100.00);

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
