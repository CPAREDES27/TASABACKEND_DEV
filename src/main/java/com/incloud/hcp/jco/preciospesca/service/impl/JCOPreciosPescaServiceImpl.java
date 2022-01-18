package com.incloud.hcp.jco.preciospesca.service.impl;

import com.incloud.hcp.CallBAPI;
import com.incloud.hcp.jco.dominios.dto.DominioExportsData;
import com.incloud.hcp.jco.dominios.dto.DominiosExports;
import com.incloud.hcp.jco.maestro.dto.ListaWA;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.jco.preciospesca.PrecioPonderadoExport;
import com.incloud.hcp.jco.preciospesca.dto.*;
import com.incloud.hcp.jco.preciospesca.service.JCOPreciosPescaService;
import com.incloud.hcp.jco.reportepesca.dto.DominiosHelper;
import com.incloud.hcp.util.*;
import com.sap.conn.jco.*;
import org.apache.poi.hpsf.Decimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JCOPreciosPescaServiceImpl implements JCOPreciosPescaService {
    private Logger logger = LoggerFactory.getLogger(CallBAPI.class);
    @Override
    public PrecioProbPescaExports ObtenerPrecioProbPesca(PrecioProbPescaImports imports) throws Exception {
        Metodos metodos = new Metodos();
        HashMap<String, Object> importParams = new HashMap<>();
        importParams.put("P_USER", imports.getP_user());
        logger.error("PRECIOS PESCA PROB");
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
        logger.error("PRECIOS PESCA PROB2");


        //List<HashMap<String, Object>> listSTR_APP = metodos.ListarObjetos(tblSTR_APP);
        List<HashMap<String, Object>> listSTR_APP = metodos.ListarObjetosLazy(tblSTR_APP);

        ArrayList<String> listDomNames = new ArrayList<>();
        listDomNames.add(Dominios.ESCSG);
        listDomNames.add(Dominios.ESPRC);
        listDomNames.add(Dominios.ZDOMMONEDA);


        DominiosHelper helper = new DominiosHelper();
        ArrayList<DominiosExports> listDescipciones = helper.listarDominios(listDomNames);

        DominiosExports estadoCastigo = listDescipciones.stream().filter(d -> d.getDominio().equals(Dominios.ESCSG)).findFirst().orElse(null);
        DominiosExports estadoPrecio = listDescipciones.stream().filter(d -> d.getDominio().equals(Dominios.ESPRC)).findFirst().orElse(null);
        DominiosExports moneda = listDescipciones.stream().filter(d -> d.getDominio().equals(Dominios.ZDOMMONEDA)).findFirst().orElse(null);

        listSTR_APP.stream().map(m -> {
            String escsg = m.get("ESCSG").toString();
            String esprc = m.get("ESPRC").toString();
            String waers = m.get("WAERS").toString();


            // Buscar los detalles
            DominioExportsData dataESCSG = estadoCastigo.getData().stream().filter(d -> d.getId().equals(escsg)).findFirst().orElse(null);
            DominioExportsData dataESPRC = estadoPrecio.getData().stream().filter(d -> d.getId().equals(esprc)).findFirst().orElse(null);
            DominioExportsData dataWAERS = moneda.getData().stream().filter(d -> d.getId().equals(waers)).findFirst().orElse(null);

            if (dataESCSG != null) {
                String descESCSG = dataESCSG.getDescripcion();
                m.put("DESC_ESCSG", descESCSG);
            } else {
                m.put("DESC_ESCSG", "");
            }

            if (dataESPRC != null) {
                String descESPRC = dataESPRC.getDescripcion();
                m.put("DESC_ESPRC", descESPRC);
            } else {
                m.put("DESC_ESPRC", "");
            }

            if (dataWAERS != null) {
                String descWAERS = dataWAERS.getDescripcion();
                m.put("DESC_WAERS", descWAERS);
            } else {
                m.put("DESC_WAERS", "");
            }

            return m;
        }).collect(Collectors.toList());

        //List<HashMap<String, Object>>listaConPromedio=CalcularPromedio(listSTR_APP);

        logger.error("PRECIOS PESCA PROB_3");
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


        List<HashMap<String, Object>> tmpOptions =metodo.ValidarOptions(option,options2);

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
        List<EstadosPrecioDto> arrayLista = new ArrayList<EstadosPrecioDto>();
            for(Map<String,Object> datas: listT_PREPES){
                EstadosPrecioDto obj = new EstadosPrecioDto();
            for(Map.Entry<String,Object> entry: datas.entrySet()){


                String key = entry.getKey();
                Object value= entry.getValue();
                if(key.equals("WERKS")){
                    obj.setWERKS(value);
                }
                if(key.equals("MREMB")){
                    obj.setMREMB(value);
                }
                if(key.equals("NMEMB")){
                    obj.setNMEMB(value);
                }
                if(key.equals("NAME1")){
                    obj.setNAME1(value);
                }
                if(key.equals("FECCONMOV")){
                    obj.setFECCONMOV(value);
                }
                if(key.equals("CNPDS")){
                    obj.setCNPDS(value);
                }
                if(key.equals("PRCOM")){
                    obj.setPRCOM(new BigDecimal(value.toString()));
                }
                if(key.equals("EBELN")){
                    obj.setEBELN(value);
                }
                if(key.equals("BELNR")){
                    obj.setBELNR(value);
                }
                if(key.equals("XBLNR")){
                    obj.setXBLNR(value);
                }
                if(key.equals("PRCTP")){
                    obj.setPRCTP(new BigDecimal(value.toString()));
                }
                if(key.equals("PRCMX")){
                    obj.setPRCMX(new BigDecimal(value.toString()));
                }
                if(key.equals("ESPRC")){
                    obj.setESPRC(value.toString());
                }
                if(key.equals("PCSPP")){
                    obj.setPCSPP(new BigDecimal(value.toString()));
                }
                if(key.equals("ESCSG")){
                    obj.setESCSG(value.toString());
                }
                logger.error("ENTRY KEY"+ key);
                logger.error("ENTRY value"+ value.toString());
            }
            arrayLista.add(obj);
        }
            for(int k=0;k<arrayLista.size();k++){
                if((arrayLista.get(k).getPRCOM().compareTo(new BigDecimal("0"))==0) && arrayLista.get(k).getPRCTP().compareTo(new BigDecimal("0"))==0){
                    arrayLista.get(k).setIconoSemana("sap-icon://circle-task-2");
                    arrayLista.get(k).setColorSemana("Error");
                    arrayLista.get(k).setIconoPactado("sap-icon://circle-task-2");
                    arrayLista.get(k).setColorPactado("Error");
                    arrayLista.get(k).setIconoAprobado("sap-icon://circle-task-2");
                    arrayLista.get(k).setColorAprobado("Error");
                }else{
                    arrayLista.get(k).setIconoSemana("sap-icon://circle-task-2");
                    arrayLista.get(k).setColorSemana("Success");
                    if(arrayLista.get(k).getPRCOM().equals("") || arrayLista.get(k).getPRCOM().compareTo(new BigDecimal("0"))==0){
                        arrayLista.get(k).setIconoPactado("sap-icon://circle-task-2");
                        arrayLista.get(k).setColorPactado("Error");
                        arrayLista.get(k).setIconoAprobado("sap-icon://circle-task-2");
                        arrayLista.get(k).setColorAprobado("Error");
                    }else{
                        if((arrayLista.get(k).getPRCOM().compareTo(arrayLista.get(k).getPRCMX())==-1) || (arrayLista.get(k).getPRCOM().compareTo(arrayLista.get(k).getPRCMX())==0)){
                            arrayLista.get(k).setIconoPactado("sap-icon://circle-task-2");
                            arrayLista.get(k).setColorPactado("Success");
                            arrayLista.get(k).setIconoAprobado("sap-icon://circle-task-2");
                            arrayLista.get(k).setColorAprobado("Success");
                        }else{
                            if((arrayLista.get(k).getPRCOM().compareTo(arrayLista.get(k).getPRCTP())==-1) ||
                                arrayLista.get(k).getPRCOM().compareTo(arrayLista.get(k).getPRCTP())==0){
                                arrayLista.get(k).setIconoPactado("sap-icon://circle-task-2");
                                arrayLista.get(k).setColorPactado("Warning");
                                if((arrayLista.get(k).getESPRC().equals("") || arrayLista.get(k).getESPRC().equals(null)) && arrayLista.get(k).getESPRC().equalsIgnoreCase("L")){
                                    arrayLista.get(k).setIconoAprobado("sap-icon://circle-task-2");
                                    arrayLista.get(k).setColorAprobado("Success");
                                }else{
                                    arrayLista.get(k).setIconoAprobado("sap-icon://circle-task-2");
                                    arrayLista.get(k).setColorAprobado("Error");
                                }
                            }
                        }
                    }
                }
                /*if((arrayLista.get(k).getPCSPP().compareTo(new BigDecimal("0"))==-1) || (arrayLista.get(k).getPCSPP().compareTo(new BigDecimal("0"))==1)){
                    if((arrayLista.get(k).getESCSG().equals("") || arrayLista.get(k).getESCSG().equals(null)) && arrayLista.get(k).getESCSG().equalsIgnoreCase("L")){

                    }
                }*/
            }
        ConsPrecioPescaExports dto = new ConsPrecioPescaExports();
        dto.setT_prepes(arrayLista);
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

    public List<HashMap<String, Object>>  CalcularPromedio(List<HashMap<String, Object>> str_app){

        logger.error("CALCULAR PROMEDIO");
        List<HashMap<String, Object>> newList= str_app;
        double sum=0;
        for(HashMap<String,Object> datas: newList){

            for(Map.Entry<String,Object> entry: datas.entrySet()){
                String key= entry.getKey();
                Object value= entry.getValue();

                if(key.equals("PRCOM")){
                    sum=sum+Double.parseDouble(value.toString());
                    logger.error("PRCOM SUMA=  "+sum);
                }

            }

        }


        double promedio=sum/str_app.size();
       promedio= Math.round(promedio*100.0)/100.0;

        HashMap<String, Object> data= new HashMap<>();
        data.put("AUGBL","");
        data.put("AUGBL2","");
        data.put("AUGBL3","");
        data.put("BELNR","");
        data.put("BELNR2","");
        data.put("BELNR3","");
        data.put("BONIF","");
        data.put("CALIDAD","");
        data.put("CDEMB","");
        data.put("CDPTA","");
        data.put("CHARG","");
        data.put("CNPDS","");
        data.put("DESC_ESCSG","");
        data.put("DESC_ESPRC","");
        data.put("DESC_WAERS","");
        data.put("DOCFI","");
        data.put("DOCFI2","");
        data.put("DOCFI3","");
        data.put("EBELN","");
        data.put("ESCSG","");
        data.put("ESPRC","");
        data.put("FECCONMOV","");
        data.put("FHAPR","");
        data.put("FHCDF","");
        data.put("FHCPP","");
        data.put("FHRPR","");
        data.put("HRAPR","");
        data.put("HRCDF","");
        data.put("HRCPP","");
        data.put("HRRPR","");
        data.put("JUVEN","");
        data.put("LIFNR","");
        data.put("MREMB","");
        data.put("NAME1","PONDERADO");
        data.put("NMEMB","");
        data.put("NRDES","");
        data.put("OBCDF","");
        data.put("OBCPP","");
        data.put("PCCSG","");
        data.put("PCSPP","");
        data.put("PRCOM",String.valueOf(promedio));
        data.put("PRUEFBEMKT","");
        data.put("PRUEFLOS","");
        data.put("PRVEN","");
        data.put("STBLG","");
        data.put("TDC","");
        data.put("TVN","");
        data.put("USAPR","");
        data.put("USCDF","");
        data.put("USCPP","");
        data.put("USRPR","");
        data.put("WAERS","");
        data.put("WERKS","");
        data.put("XBLNR","");
        data.put("XBLNR2","");
        data.put("XBLNR3","");

        newList.add(data);

        return str_app;
    }


}
