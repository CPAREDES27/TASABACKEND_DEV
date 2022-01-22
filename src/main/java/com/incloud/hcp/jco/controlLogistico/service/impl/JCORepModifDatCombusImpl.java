package com.incloud.hcp.jco.controlLogistico.service.impl;

import com.incloud.hcp.CallBAPI;
import com.incloud.hcp.jco.controlLogistico.dto.RepModifDatCombusExports;
import com.incloud.hcp.jco.controlLogistico.dto.RepModifDatCombusImports;
import com.incloud.hcp.jco.controlLogistico.service.JCORepModifDatCombusService;
import com.incloud.hcp.jco.dominios.dto.DominioExportsData;
import com.incloud.hcp.jco.dominios.dto.DominiosExports;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.jco.reportepesca.dto.DominiosHelper;
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
public class JCORepModifDatCombusImpl implements JCORepModifDatCombusService {
    private Logger logger = LoggerFactory.getLogger(CallBAPI.class);
    public RepModifDatCombusExports Listar(RepModifDatCombusImports imports)throws Exception{

        RepModifDatCombusExports rmdc= new RepModifDatCombusExports();

        try {
            Metodos metodos = new Metodos();
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            List<MaestroOptions> option = imports.getOption();
            List<MaestroOptionsKey> options2 = imports.getOptions();
            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            tmpOptions=metodos.ValidarOptions(option,options2,"DATA");
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_COMB_CONS_MODIF_DATOS);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_FASE", imports.getP_fase());
            importx.setValue("P_CANT", imports.getP_cant());

            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC ejecutarRFC = new EjecutarRFC();

            ejecutarRFC.setTable(tables, "T_OPCIONES", tmpOptions);

            stfcConnection.execute(destination);
            JCoParameterList export=stfcConnection.getExportParameterList();
            rmdc.setP_nmob(export.getValue("P_NMOB").toString());
            rmdc.setP_nmar(export.getValue("P_NMAR").toString());

            double p_nmob= Double.parseDouble(rmdc.getP_nmob());
            double p_mar= Double.parseDouble(rmdc.getP_nmar());
            double total = Math.round((p_nmob/p_mar)*100.0)/100.0;
            total = total*100;
            logger.error("TOTAL"+ total);
            logger.error("P_NMOB"+p_nmob);
            logger.error("p_mar"+p_mar);
            logger.error("P_NMOB", rmdc.getP_nmob());
            logger.error("P_NMOB", rmdc.getP_nmar());
            JCoTable T_FLOCC = tables.getTable(Tablas.T_FLOCC);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);
            JCoTable T_OPCIONES= tables.getTable(Tablas.T_OPCIONES);



            Metodos metodo = new Metodos();
            //List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetos(T_MENSAJE);
            String[] fieldsT_flocc=imports.getFieldsT_flocc();

            String[] fieldsT_mensaje=imports.getFieldsT_mensaje();

            List<HashMap<String, Object>> t_flocc = metodo.ListarObjetosLazy(T_FLOCC);
            List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetosLazy(T_OPCIONES);

            //Dominios
            ArrayList<String> listDomNames = new ArrayList<>();
            listDomNames.add(Dominios.CDFAS);
            listDomNames.add(Dominios.CDMMA);

            DominiosHelper helper = new DominiosHelper();
            ArrayList<DominiosExports> listDescipciones = helper.listarDominios(listDomNames);

            DominiosExports cdfasDom = listDescipciones.stream().filter(d -> d.getDominio().equals(Dominios.CDFAS)).findFirst().orElse(null);
            DominiosExports cdmmaDom = listDescipciones.stream().filter(d -> d.getDominio().equals(Dominios.CDMMA)).findFirst().orElse(null);

            t_flocc.stream().map(s -> {
                String cdfas = s.get("CDFAS").toString();
                String cdmma = s.get("CDMMA").toString();

                DominioExportsData dataCdfas = cdfasDom.getData().stream().filter(d -> d.getId().equals(cdfas)).findFirst().orElse(null);
                DominioExportsData dataCdmma = cdmmaDom.getData().stream().filter(d -> d.getId().equals(cdmma)).findFirst().orElse(null);

                s.put("DESC_CDFAS", dataCdfas != null ? dataCdfas.getDescripcion() : "");
                s.put("DESC_CDMMA", dataCdmma != null ? dataCdmma.getDescripcion() : "");

                return s;
            }).collect(Collectors.toList());

            rmdc.setT_mensaje(t_mensaje);
            rmdc.setIndicadorPorc(total);
            rmdc.setT_flocc(t_flocc);
            rmdc.setMensaje("Ok");
        }catch (Exception e){
            rmdc .setMensaje(e.getMessage());
        }

        return rmdc;
    }

}
