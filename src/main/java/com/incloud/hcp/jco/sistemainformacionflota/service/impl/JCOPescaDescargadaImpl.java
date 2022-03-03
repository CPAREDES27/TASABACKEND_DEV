package com.incloud.hcp.jco.sistemainformacionflota.service.impl;

import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaDescargadaDiaResumExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaDescargadaDiaResumImports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaDescargadaExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaDescargadaImports;
import com.incloud.hcp.jco.sistemainformacionflota.service.JCOPescaDescargadaService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class JCOPescaDescargadaImpl implements JCOPescaDescargadaService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
            /*
            AtomicReference<Double> totalGenPescDesc = new AtomicReference<>((double) 0);
            AtomicInteger numDays = new AtomicInteger(1);
            ArrayList<HashMap<String, Object>> listDescSum = new ArrayList<>();*/

            /*
            str_dsd.stream().map(desc -> {
                double cnpds = Math.round(Double.parseDouble(desc.get("CNPDS").toString()) * 100.00) / 100.00;
                HashMap<String, Object> descargaDia = new HashMap<>();
                descargaDia.put("CNPDS", cnpds);
                descargaDia.put("FIDES", desc.get("FIDES").toString());

                return descargaDia;
            }).collect(Collectors.groupingBy(desc->desc.get("FIDES").toString(),Collectors.summarizingDouble(desc->Double.parseDouble(desc.get("CNPDS").toString())))).entrySet().forEach(desc->{
                logger.debug("----> Descarga: {}", desc.getKey());
                HashMap<String,Object> descargaDia=new HashMap<>();
                descargaDia.put("CNPDS",desc.getKey());
                descargaDia.put("FIDES",desc.getValue());
            });*/

            /*
            str_dsd.stream().forEach(dsd -> {
                totalGenPescDesc.updateAndGet(v -> new Double((double) (v + Double.parseDouble(dsd.get("CNPDS").toString()))));
                HashMap<String, Object> descDiaSum = listDescSum.stream().filter(descSum -> descSum.get("FIDES").toString().equals(dsd.get("FIDES").toString())).findFirst().orElse(null);
                if (descDiaSum == null) {
                    descDiaSum = str_dsd.stream().filter(dsdFilter -> dsdFilter.get("FIDES").toString().equals(dsd.get("FIDES").toString())).reduce((dsdPrev, dsdCurr) -> {
                        double cndpsSum = Double.parseDouble(dsdPrev.get("CNPDS").toString()) + Double.parseDouble(dsdCurr.get("CNPDS").toString());
                        double cndpsSumRound = Math.round(cndpsSum * 100.00) / 100.00;
                        HashMap<String, Object> desc = new HashMap<>();
                        desc.put("CNPDS", cndpsSumRound);
                        desc.put("FIDES", dsdCurr.get("FIDES").toString());

                        return desc;
                    }).orElse(null);

                    listDescSum.add(descDiaSum);
                }
            });*/

            /**
             * Adicionar días, promedios y asignar las cantidades de pesca descargada por planta, redondearlas a 2 decimales
             */
            /*
            List<HashMap<String, Object>> listPescaDescargadaSum = listDescSum.stream().map((desc) -> {
                str_dsd.stream().filter(d -> d.get("FIDES").toString().equals(desc.get("FIDES").toString())).forEach(d -> {
                    HashMap<String, Object> planta = str_pta.stream().filter(pta -> pta.get("CDPTA").toString().equals(d.get("CDPTA").toString())).findFirst().orElse(null);
                    if (planta != null) {
                        double cndps = Double.parseDouble(d.get("CNPDS").toString());
                        double cndpsRound = Math.round(cndps * 100.00) / 100.00;
                        String plantaDesc = "Planta" + planta.get("CDPTA");
                        desc.put(plantaDesc, cndpsRound);
                    }
                });

                double promedio = totalGenPescDesc.get() / numDays.get();
                double promedioRound = Math.round(promedio * 100.00) / 100.00;

                desc.put("DAYS", numDays);
                desc.put("PROM", promedioRound);

                numDays.getAndIncrement();

                return desc;
            }).collect(Collectors.toList());*/

            /**
             * Calcular los totales de las pescas descargadas y plantas
             */
            /*
            HashMap<String, Object> totales = listPescaDescargadaSum.stream().reduce((descAcum, desc) -> {
                double malabSur = desc.get("Planta0005") != null ? Double.parseDouble(desc.get("Planta0005").toString()) : 0;
                double chimb = desc.get("Planta0119") != null ? Double.parseDouble(desc.get("Planta0119").toString()) : 0;
                double samanco = desc.get("Planta0009") != null ? Double.parseDouble(desc.get("Planta0009").toString()) : 0;
                double supe = desc.get("Planta0010") != null ? Double.parseDouble(desc.get("Planta0010").toString()) : 0;
                double vegueta = desc.get("Planta0011") != null ? Double.parseDouble(desc.get("Planta0011").toString()) : 0;
                double callaoNor = desc.get("Planta0012") != null ? Double.parseDouble(desc.get("Planta0012").toString()) : 0;
                double piscoSur = desc.get("Planta0015") != null ? Double.parseDouble(desc.get("Planta0015").toString()) : 0;
                double matarani = desc.get("Planta0018") != null ? Double.parseDouble(desc.get("Planta0018").toString()) : 0;

                double totalMalabSur = descAcum.get("Planta0005") != null ? Double.parseDouble(descAcum.get("Planta0005").toString()) : 0;
                double totalChimb = descAcum.get("Planta0119") != null ? Double.parseDouble(descAcum.get("Planta0119").toString()) : 0;
                double totalSamanco = descAcum.get("Planta0009") != null ? Double.parseDouble(descAcum.get("Planta0009").toString()) : 0;
                double totalSupe = descAcum.get("Planta0010") != null ? Double.parseDouble(descAcum.get("Planta0010").toString()) : 0;
                double totalVegueta = descAcum.get("Planta0011") != null ? Double.parseDouble(descAcum.get("Planta0011").toString()) : 0;
                double totalCallaoNor = descAcum.get("Planta0012") != null ? Double.parseDouble(descAcum.get("Planta0012").toString()) : 0;
                double totalPiscoSur = descAcum.get("Planta0015") != null ? Double.parseDouble(descAcum.get("Planta0015").toString()) : 0;
                double totalMatarani = descAcum.get("Planta0018") != null ? Double.parseDouble(descAcum.get("Planta0018").toString()) : 0;

                HashMap<String, Object> total = new HashMap<>();
                total.put("Planta0005", totalMalabSur + malabSur);
                total.put("Planta0119", totalChimb + chimb);
                total.put("Planta0009", totalSamanco + samanco);
                total.put("Planta0010", totalSupe + supe);
                total.put("Planta0011", totalVegueta + vegueta);
                total.put("Planta0012", totalCallaoNor + callaoNor);
                total.put("Planta0015", totalPiscoSur + piscoSur);
                total.put("Planta0018", totalMatarani + matarani);

                return total;
            }).orElse(new HashMap<>());

            totales.put("CNPDS", totalGenPescDesc.get());

            for (Map.Entry<String, Object> entry : totales.entrySet()) {
                double data = Double.parseDouble(totales.get(entry.getKey()).toString());
                totales.replace(entry.getKey(), Math.round(data * 100.00) / 100.00);
            }

            listPescaDescargadaSum.add(totales);*/

            pd.setStr_pta(str_pta);
            pd.setStr_dsd(str_dsd);
            pd.setT_mensaje(t_mensaje);
            pd.setMensaje("Ok");

        } catch (Exception e) {
            pd.setMensaje(e.getMessage());
        }

        return pd;
    }

    @Override
    public PescaDescargadaDiaResumExports PescaDescargadaDiaResum(PescaDescargadaDiaResumImports imports) throws Exception {
        PescaDescargadaDiaResumExports pd = new PescaDescargadaDiaResumExports();
        try {
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_PESCA_DESC_DIA_RESUM);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            importx.setValue("P_FIDES", imports.getP_fides());
            importx.setValue("P_FFDES", imports.getP_ffdes());
            JCoParameterList tables = stfcConnection.getTableParameterList();
            stfcConnection.execute(destination);
            JCoTable STR_PTA = tables.getTable(Tablas.STR_PTA);
            JCoTable STR_DSD = tables.getTable(Tablas.STR_DSD);
            JCoTable STR_DSDDIA = tables.getTable(Tablas.STR_DSDDIA);
            JCoTable STR_DSDTOT = tables.getTable(Tablas.STR_DSDTOT);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);
            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> str_pta = metodo.ListarObjetosLazy(STR_PTA);
            List<HashMap<String, Object>> str_dsd = metodo.ListarObjetosLazy(STR_DSD);
            List<HashMap<String, Object>> str_dsddia = metodo.ListarObjetosLazy(STR_DSDDIA);
            List<HashMap<String, Object>> str_dsdtot = metodo.ListarObjetosLazy(STR_DSDTOT);
            List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetosLazy(T_MENSAJE);
            pd.setStr_pta(str_pta);
            pd.setStr_dsd(str_dsd);
            pd.setStr_dsddia(str_dsddia);
            pd.setStr_dsdtot(str_dsdtot);
            pd.setT_mensaje(t_mensaje);
            pd.setMensaje("Ok");
        } catch (Exception e) {
            pd.setMensaje(e.getMessage());
        }
        return pd;
    }
}
