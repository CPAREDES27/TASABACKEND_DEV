package com.incloud.hcp.jco.tripulantes.service.impl;


import com.incloud.hcp.jco.dominios.dto.DominioExportsData;
import com.incloud.hcp.jco.dominios.dto.DominiosExports;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.reportepesca.dto.DominiosHelper;
import com.incloud.hcp.jco.tripulantes.dto.Options;
import com.incloud.hcp.jco.tripulantes.dto.ProtestoNuevoImport;
import com.incloud.hcp.jco.tripulantes.dto.ProtestosExports;
import com.incloud.hcp.jco.tripulantes.dto.ProtestosImports;
import com.incloud.hcp.jco.tripulantes.service.JCOProtestosService;
import com.incloud.hcp.util.*;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JCOProtestosImpl implements JCOProtestosService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ProtestosExports Protestos(ProtestosImports imports) throws Exception {

        ProtestosExports pe=new ProtestosExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_REGPRT_ADM_REGPRT);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("IP_TOPE", imports.getIp_tope());
            importx.setValue("IP_CDPRT", imports.getIp_cdprt());
            importx.setValue("IP_CANTI", imports.getIp_canti());
            importx.setValue("IP_PERNR", imports.getIp_pernr());

            List<MaestroOptions> options = imports.getT_opcion();
            List<HashMap<String, Object>> listOptions = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < options.size(); i++) {
                MaestroOptions o = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();

                record.put("DATA", o.getWa());
                logger.error(o.getWa());
                listOptions.add(record);
            }

            Metodos me=new Metodos();
            List<HashMap<String, Object>> tmpOptions =me.ValidarOptions(imports.getT_opcion(),imports.getOpcionkeys(),"DATA");

            JCoParameterList export = stfcConnection.getExportParameterList();
            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC exec= new EjecutarRFC();

            exec.setTable(tables, Tablas.T_OPCION,tmpOptions);
            if(imports.getIp_tope().equals("C")){
                exec.setTable(tables, Tablas.T_BAPRT,imports.getT_baprt());
                exec.setTable(tables, Tablas.T_TEXTOS,imports.getT_textos());
            }
            stfcConnection.execute(destination);

            pe.setEp_cdprt(export.getValue(Tablas.EP_CDPRT).toString());
            pe.setEp_drpta(export.getValue(Tablas.EP_DRPTA).toString());

            JCoTable T_BAPRT = tables.getTable(Tablas.T_BAPRT);
            JCoTable T_TEXTOS = tables.getTable(Tablas.T_TEXTOS);
            JCoTable T_MENSAJ = tables.getTable(Tablas.T_MENSAJ);

            Metodos metodo = new Metodos();
            //List<HashMap<String, Object>>  t_baprt = metodo.ObtenerListObjetos(T_BAPRT, imports.getFieldst_baprt());
            //List<HashMap<String, Object>>  t_textos = metodo.ObtenerListObjetos(T_TEXTOS, imports.getFieldst_textos());
            //List<HashMap<String, Object>>  t_mensaj = metodo.ListarObjetos(T_MENSAJ);

            List<HashMap<String, Object>>  t_baprt = metodo.ListarObjetosLazy(T_BAPRT);
            List<HashMap<String, Object>>  t_textos = metodo.ListarObjetosLazy(T_TEXTOS);
            List<HashMap<String, Object>>  t_mensaj = metodo.ListarObjetosLazy(T_MENSAJ);

            ArrayList<String> listDomNames = new ArrayList<>();
            listDomNames.add(Dominios.CLASEPROTESTO);
            listDomNames.add(Dominios.TIPOPROTESTO);
            listDomNames.add(Dominios.ZINEIF);

            DominiosHelper helper = new DominiosHelper();
            ArrayList<DominiosExports> listDescipciones = helper.listarDominios(listDomNames);

            DominiosExports claseProtesto = listDescipciones.stream().filter(d -> d.getDominio().equals(Dominios.CLASEPROTESTO)).findFirst().orElse(null);
            DominiosExports tipoProtestos = listDescipciones.stream().filter(d -> d.getDominio().equals(Dominios.TIPOPROTESTO)).findFirst().orElse(null);
            DominiosExports zineif = listDescipciones.stream().filter(d -> d.getDominio().equals(Dominios.ZINEIF)).findFirst().orElse(null);


            t_baprt.stream().map(m -> {
                String clprt = m.get("CLPRT").toString()!=null ? m.get("CLPRT").toString() : "";
                String tpptr = m.get("TPPRT").toString()!=null ? m.get("TPPRT").toString() : "";
                String amplc = m.get("AMPLC").toString()!=null ? m.get("AMPLC").toString() : "";


                DominioExportsData dataCLPRT = claseProtesto.getData().stream().filter(d -> d.getId().equals(clprt)).findFirst().orElse(null);
                DominioExportsData dataTPPTR = tipoProtestos.getData().stream().filter(d -> d.getId().equals(tpptr)).findFirst().orElse(null);
                DominioExportsData dataAMPLC = zineif.getData().stream().filter(d -> d.getId().equals(amplc)).findFirst().orElse(null);

                m.put("DESC_CLPRT", dataCLPRT != null ? dataCLPRT.getDescripcion() : "");
                m.put("DESC_TPPRT", dataTPPTR != null ? dataTPPTR.getDescripcion() : "");
                m.put("DESC_AMPLC", dataAMPLC != null ? dataAMPLC.getDescripcion() : "");




                return m;
            }).collect(Collectors.toList());

            pe.setT_baprt(t_baprt);
            pe.setT_textos(t_textos);
            pe.setT_mensaj(t_mensaj);
            pe.setMensaje("Ok");

        }catch (Exception e){
            pe.setMensaje(e.getMessage());
        }

        return pe;
    }

    @Override
    public ProtestosExports ProtestosNuevo(ProtestoNuevoImport imports) throws Exception {

        ProtestosExports pe=new ProtestosExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_REGPRT_ADM_REGPRT);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("IP_TOPE", imports.getIp_tope());
            importx.setValue("IP_CDPRT", imports.getIp_cdprt());
            importx.setValue("IP_CANTI", imports.getIp_canti());
            importx.setValue("IP_PERNR", imports.getIp_pernr());

            JCoParameterList export = stfcConnection.getExportParameterList();
            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC exec= new EjecutarRFC();
            JCoTable T_BAPRT = tables.getTable(Tablas.T_BAPRT);
            JCoTable T_TEXTOS = tables.getTable(Tablas.T_TEXTOS);
            exec.setTable(tables, Tablas.T_BAPRT,imports.getT_baprt());
            exec.setTable(tables, Tablas.T_TEXTOS,imports.getT_textos());
            stfcConnection.execute(destination);


            pe.setEp_cdprt(export.getValue(Tablas.EP_CDPRT).toString());
            pe.setEp_drpta(export.getValue(Tablas.EP_DRPTA).toString());
            Metodos metodo = new Metodos();

            pe.setMensaje("Ok");

        }catch (Exception e){
            pe.setMensaje(e.getMessage());
        }

        return pe;
    }
}
