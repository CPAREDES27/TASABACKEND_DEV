package com.incloud.hcp.jco.sistemainformacionflota.service.impl;

import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaDescargadaExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaDescargadaImports;
import com.incloud.hcp.jco.sistemainformacionflota.service.JCOPescaDescargadaService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class JCOPescaDescargadaImpl implements JCOPescaDescargadaService {

    public PescaDescargadaExports PescaDescargada(PescaDescargadaImports imports) throws Exception {

        PescaDescargadaExports pd = new PescaDescargadaExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_PESCA_DESC_DIA);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            importx.setValue("P_FIDES", imports.getP_fides());
            importx.setValue("P_FFDES", imports.getP_ffdes());


            JCoParameterList tables = stfcConnection.getTableParameterList();

            stfcConnection.execute(destination);

            JCoTable STR_PTA = tables.getTable(Tablas.STR_PTA);
            JCoTable STR_PTR = tables.getTable(Tablas.STR_DSD);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);

            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> str_pta = metodo.ObtenerListObjetos(STR_PTA, imports.getFieldstr_pta());
            List<HashMap<String, Object>> str_dsd = metodo.ObtenerListObjetos(STR_PTR, imports.getFielstr_dsd());
            List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetos(T_MENSAJE);

            /**
             * Agrupar items por fecha y suma de cantidades de pesca descargadas por fecha (2 decimales) y total general
             */
            AtomicReference<Double> totalGenPescDesc = new AtomicReference<>((double) 0);
            int numDays = 1;
            ArrayList<HashMap<String, Object>> listDescSum = new ArrayList<>();
            str_dsd.stream().forEach(dsd -> {
                totalGenPescDesc.updateAndGet(v -> new Double((double) (v + Double.parseDouble(dsd.get("CNPDS").toString()))));
                HashMap<String, Object> descDiaSum = listDescSum.stream().filter(descSum -> descSum.get("FIDES").toString().equals(dsd.get("FIDES").toString())).findFirst().orElse(null);
                if (descDiaSum != null) {
                    descDiaSum = str_dsd.stream().filter(dsdFilter -> dsdFilter.get("FIDES").toString().equals(dsd.get("FIDES").toString())).reduce((dsdPrev, dsdCurr) -> {
                        double cndpsSum = Double.parseDouble(dsdPrev.get("CNPDS").toString()) + Double.parseDouble(dsdCurr.get("CNPDS").toString());
                        double cndpsSumRound = Math.round(cndpsSum * 100) / 100;
                        HashMap<String, Object> desc = new HashMap<>();
                        desc.put("CNPDS", cndpsSumRound);
                        desc.put("FIDES", dsdCurr.get("FIDES").toString());

                        return desc;
                    }).orElse(null);

                    if (descDiaSum != null) {
                        listDescSum.add(descDiaSum);
                    }

                }
            });

            /**
             * Adicionar días, promedios y asignar las cantidades de pesca descargada por planta, redondearlas a 2 decimales
             */
            List<HashMap<String, Object>> listPescaDescargadaSum = listDescSum.stream().map((desc) -> {
                str_dsd.stream().filter(d -> d.get("FIDES").toString().equals(desc.get("FIDES").toString())).forEach(d -> {
                    HashMap<String, Object> planta = str_pta.stream().filter(pta -> pta.get("CDPTA").toString().equals(d.get("CDPTA").toString())).findFirst().orElse(null);
                    if (planta != null) {
                        double cndps = Double.parseDouble(d.get("CNPDS").toString());
                        double cndpsRound = Math.round(cndps * 100) / 100;
                        String plantaDesc = "Planta" + planta.get("CDPTA");
                        desc.put(plantaDesc, cndpsRound);
                    }
                });

                double promedio = totalGenPescDesc.get() / numDays;
                double promedioRound = Math.round(promedio * 100) / 100;

                desc.put("DAYS", numDays);
                desc.put("PROM", promedioRound);

                return desc;
            }).collect(Collectors.toList());

            pd.setStr_pta(str_pta);
            pd.setStr_dsd(listPescaDescargadaSum);
            pd.setT_mensaje(t_mensaje);
            pd.setMensaje("Ok");

        } catch (Exception e) {
            pd.setMensaje(e.getMessage());
        }

        return pd;
    }
}
