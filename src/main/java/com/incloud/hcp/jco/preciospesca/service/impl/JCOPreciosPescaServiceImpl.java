package com.incloud.hcp.jco.preciospesca.service.impl;

import com.incloud.hcp.CallBAPI;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.jco.preciospesca.PrecioPonderadoExport;
import com.incloud.hcp.jco.preciospesca.dto.*;
import com.incloud.hcp.jco.preciospesca.service.JCOPreciosPescaService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JCOPreciosPescaServiceImpl implements JCOPreciosPescaService {
    private Logger logger = LoggerFactory.getLogger(CallBAPI.class);
    @Override
    public PrecioProbPescaExports ObtenerPrecioProbPesca(PrecioProbPescaImports imports) throws Exception {
        Metodos metodos = new Metodos();
        HashMap<String, Object> importParams = new HashMap<>();
        importParams.put("P_USER", imports.getP_user());

        List<MaestroOptions> option = imports.getOption();
        List<MaestroOptionsKey> options2 = imports.getOptions();


        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
        tmpOptions=metodos.ValidarOptions(option,options2);

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_PROB_PED_PESCA);

        JCoParameterList paramsTable = function.getTableParameterList();

        EjecutarRFC executeRFC = new EjecutarRFC();
        executeRFC.setImports(function, importParams);
        executeRFC.setTable(paramsTable, "P_OPTIONS", tmpOptions);

        JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);
        JCoTable tblSTR_APP = tables.getTable(Tablas.STR_APP);

        List<HashMap<String, Object>> listSTR_APP = metodos.ListarObjetos(tblSTR_APP);

        PrecioProbPescaExports dto = new PrecioProbPescaExports();
        dto.setStr_app(listSTR_APP);
        dto.setMensaje("OK");

        return dto;
    }

    @Override
    public PrecioPescaExports ObtenerPrecioPesca(PrecioPescaImports imports) throws Exception {
        Metodos metodo = new Metodos();
        HashMap<String, Object> importParams = new HashMap<>();
        importParams.put("P_USER", imports.getP_user());

        List<MaestroOptions> option = imports.getOption();
        List<MaestroOptionsKey> options2 = imports.getOptions();


        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
        tmpOptions=metodo.ValidarOptions(option,options2);

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_LECT_PREC_PESC);

        JCoParameterList paramsTable = function.getTableParameterList();

        EjecutarRFC executeRFC = new EjecutarRFC();
        executeRFC.setImports(function, importParams);
        executeRFC.setTable(paramsTable, "P_OPTIONS", tmpOptions);

        //Exports
        JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);
        JCoTable tblSTR_PPC = tables.getTable(Tablas.STR_PPC);


        List<HashMap<String, Object>> listSTR_PPC = metodo.ListarObjetos(tblSTR_PPC);

        PrecioPescaExports dto = new PrecioPescaExports();
        dto.setStr_ppc(listSTR_PPC);
        dto.setMensaje("OK");

        return dto;
    }

    @Override
    public PrecioPescaMantExports MantPrecioPesca(PrecioPescaMantImports imports) throws Exception {
        HashMap<String, Object> importParams = new HashMap<>();
        importParams.put("P_USER", imports.getP_user());
        importParams.put("P_INDTR", imports.getP_indtr());
        importParams.put("P_CAMPO", imports.getP_campo());

        // Obtener los parámetros del PPC
        List<HashMap<String, Object>> str_ppcs = new ArrayList<>();
        for (PoliticaPrecios politicaPrecios : imports.getStr_ppc()) {
            HashMap<String, Object> ppcRecord = new HashMap<>();
            ppcRecord.put("MANDT", politicaPrecios.getMandt());
            ppcRecord.put("CDPPC", politicaPrecios.getCdppc());
            ppcRecord.put("CDZLT", politicaPrecios.getCdzlt());
            ppcRecord.put("CDPTO", politicaPrecios.getCdpto());
            ppcRecord.put("CDPTA", politicaPrecios.getCdpta());
            ppcRecord.put("CDEMP", politicaPrecios.getCdemp());
            ppcRecord.put("CDSPC", politicaPrecios.getCdspc());
            ppcRecord.put("FFVIG", politicaPrecios.getFfvig());
            ppcRecord.put("FIVIG", politicaPrecios.getFivig());
            ppcRecord.put("PRCMX", politicaPrecios.getPrcmx());
            ppcRecord.put("PRVMN", politicaPrecios.getPrvmn());
            ppcRecord.put("PRCTP", politicaPrecios.getPrctp());
            ppcRecord.put("PRVTP", politicaPrecios.getPrvtp());
            ppcRecord.put("WAERS", politicaPrecios.getWaers());
            ppcRecord.put("ESPMR", politicaPrecios.getEspmr());
            ppcRecord.put("FHCRN", politicaPrecios.getFhcrn());
            ppcRecord.put("HRCRN", politicaPrecios.getHrcrn());
            ppcRecord.put("ATCRN", politicaPrecios.getAtcrn());

            str_ppcs.add(ppcRecord);
        }

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_MANT_PRECIO_PESC);

        JCoParameterList paramsTable = function.getTableParameterList();

        EjecutarRFC executeRFC = new EjecutarRFC();
        executeRFC.setImports(function, importParams);
        executeRFC.setTable(paramsTable, "STR_PPC", str_ppcs);

        //Exports
        JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);


        JCoTable tblT_Mensaje = tables.getTable(Tablas.T_MENSAJE);

        Metodos metodos = new Metodos();

       List<HashMap<String, Object>> listT_MENSAJE = metodos.ListarObjetos(tblT_Mensaje);
        PrecioPescaMantExports dto = new PrecioPescaMantExports();
        dto.setT_mensaje(listT_MENSAJE);
        dto.setMensaje("OK");

        return dto;
    }

    @Override
    public ConsPrecioPescaExports ConsultarPrecioPesca(ConsPrecioPescaImports imports) throws Exception {
        Metodos metodos = new Metodos();
        HashMap<String, Object> importParams = new HashMap<>();
        importParams.put("IP_CANTI", imports.getIp_canti());

        List<MaestroOptions> option = imports.getP_option();
        List<MaestroOptionsKey> options2 = imports.getP_options();


        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
        tmpOptions=metodos.ValidarOptions(option,options2);

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_CONS_PREC_PESC);

        JCoParameterList paramsTable = function.getTableParameterList();

        EjecutarRFC executeRFC = new EjecutarRFC();
        executeRFC.setImports(function, importParams);
        executeRFC.setTable(paramsTable, "T_OPCION", tmpOptions);

        //Exports
        JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);
        JCoTable tblT_PREPES = tables.getTable(Tablas.T_PREPES);


        List<HashMap<String, Object>> listT_PREPES = metodos.ListarObjetos(tblT_PREPES);

        ConsPrecioPescaExports dto = new ConsPrecioPescaExports();
        dto.setT_prepes(listT_PREPES);
        dto.setMensaje("OK");

        return dto;
    }

    @Override
    public PrecioPonderadoExport ObtenerPrecioPond(PrecioPonderadoImport imports) throws Exception {

        Metodos metodos = new Metodos();
        HashMap<String, Object> importParams = new HashMap<>();
        importParams.put("IP_CANTI", 0);

        List<MaestroOptions> option = imports.getOption();
        List<MaestroOptionsKey> options = imports.getOptions();


        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
        tmpOptions=metodos.ValidarOptions(option,options);

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_CONS_PREC_PESC_PLTA);

        JCoParameterList paramsTable = function.getTableParameterList();

        EjecutarRFC executeRFC = new EjecutarRFC();
        executeRFC.setImports(function, importParams);
        executeRFC.setTable(paramsTable, "T_OPCION", tmpOptions);

        //Exports
        JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);
        JCoTable T_PRCPESCPTA = tables.getTable(Tablas.T_PRCPESCPTA);


        List<HashMap<String, Object>> listT_PREPES = metodos.ListarObjetos(T_PRCPESCPTA);
        LinkedHashMap<String,Object> newRecord = new LinkedHashMap<String,Object>();
        PrecioPonderadoExport dto = new PrecioPonderadoExport();
        int contador=0;
        double ponderado=0.0;
        for(Map<String,Object> datas: listT_PREPES){

            for(Map.Entry<String,Object> entry: datas.entrySet()){
                String key= entry.getKey();
                Object value= entry.getValue();
                if(key.equals("PRCPOND")){
                    ponderado+=dto.getTotal()+ Double.valueOf(value.toString());
                }
            }
            contador++;
        }

        dto.setT_PRCPESCPTA(listT_PREPES);
        dto.setTotal(ponderado/contador);
        dto.setMensaje("OK");
        return dto;
    }
}
