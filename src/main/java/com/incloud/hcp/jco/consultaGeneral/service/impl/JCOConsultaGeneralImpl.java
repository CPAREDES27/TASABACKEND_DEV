package com.incloud.hcp.jco.consultaGeneral.service.impl;

import com.incloud.hcp.jco.consultaGeneral.dto.*;
import com.incloud.hcp.jco.consultaGeneral.service.JCOConsultaGeneralService;
import com.incloud.hcp.jco.dominios.dto.*;
import com.incloud.hcp.jco.dominios.service.JCODominiosService;
import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.maestro.dto.MaestroImportsKey;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JCOConsultaGeneralImpl implements JCOConsultaGeneralService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private JCODominiosService jcoDominiosService;

    public ConsultaGeneralExports ConsultaGeneral(ConsultaGeneralImports importsParam) throws Exception {
        logger.error("CONSULTA GENERAL");
        ConsultaGeneralExports dto = new ConsultaGeneralExports();

        MaestroExport me;

        try {
            String tabla;
            String condicion;
            List<MaestroOptions> options = new ArrayList<>();
            MaestroOptions opt = new MaestroOptions();
            List<MaestroOptionsKey> optionsKeys = new ArrayList<>();
            String[] fields;
            ConsultaGeneralExports result1;
            HashMap<String, Object> data;
            List<HashMap<String, Object>> listResult;

            //Parámetros de consultas avanzadas
            String tabla2;
            String condicion2;
            List<MaestroOptions> options2 = new ArrayList<>();
            MaestroOptions opt2 = new MaestroOptions();
            String[] fields2;
            HashMap<String, Object> response;
            ConsultaGeneralExports result2;
            List<HashMap<String, Object>> listData2;

            switch (importsParam.getNombreConsulta()) {
                case "CONSGENDSTRFLOTA":
                    tabla = "ZV_FLDF";
                    condicion = "CDEMB LIKE '" + importsParam.getParametro1() + "'";
                    opt.setWa(condicion);
                    options.add(opt);
                    fields = new String[]{"CDPTA", "DESCR", "CDPTO", "DSPTO", "LTGEO", "LNGEO", "FEARR", "HEARR", "EMPLA", "WKSPT", "CDUPT", "MANDT"};

                    //Consulta en tabla ZV_FLDF
                    result1 = ConsultaGeneralReadTable("", tabla, importsParam.getP_user(), options, optionsKeys, fields);
                    if (result1.getData().size() > 0) {
                        data = result1.getData().get(0);
                        String empla = data.get("EMPLA").toString();

                        //Consulta en tabla ZV_FLMP
                        tabla2 = "ZV_FLMP";
                        condicion2 = "CDEMP LIKE '" + empla + "'";
                        opt2.setWa(condicion2);
                        options2.add(opt2);
                        fields2 = new String[]{"DSEMP", "INPRP", "MANDT"};

                        result2 = ConsultaGeneralReadTable("", tabla2, importsParam.getP_user(), options2, optionsKeys, fields2);
                        listData2 = result2.getData();

                        data.put("DSEMP", listData2.size() > 0 ? listData2.get(0).get("DSEMP") : null);
                        data.put("INPRP", listData2.size() > 0 ? listData2.get(0).get("INPRP") : null);

                        listResult = new ArrayList<>();
                        listResult.add(data);

                        dto.setData(listResult);
                    }

                    dto.setMensaje("Ok");

                    break;
                case "CONSGENPLANTADIST":
                    //Consulta a la tabla ZV_FLPL
                    tabla = "ZV_FLPL";
                    condicion = "CDPTA LIKE '" + importsParam.getParametro1() + "'";
                    opt.setWa(condicion);
                    options.add(opt);
                    fields = new String[]{"CDPTA", "DESCR", "CDPTO", "DSPTO", "LTGEO", "LNGEO", "CDEMP", "CDUPT", "MANDT"};

                    result1 = ConsultaGeneralReadTable("", tabla, importsParam.getP_user(), options, optionsKeys, fields);
                    if (result1.getData().size() > 0) {
                        data = result1.getData().get(0);
                        String emplaZvFlpl = data.get("CDEMP").toString();

                        //Consulta a la tabla ZV_FLMP
                        tabla2 = "ZV_FLMP";
                        condicion2 = "CDEMP LIKE '" + emplaZvFlpl + "'";
                        opt2.setWa(condicion2);
                        options2.add(opt2);
                        fields2 = new String[]{"DSEMP", "INPRP", "MANDT"};

                        result2 = ConsultaGeneralReadTable("", tabla2, importsParam.getP_user(), options2, optionsKeys, fields2);
                        listData2 = result2.getData();

                        data.put("DSEMP", listData2.size() > 0 ? listData2.get(0).get("DSEMP") : null);
                        data.put("INPRP", listData2.size() > 0 ? listData2.get(0).get("INPRP") : null);

                        listResult = new ArrayList<>();
                        listResult.add(data);
                        dto.setData(listResult);
                    }

                    dto.setMensaje("Ok");

                    break;
                default:
                    tabla = Buscartabla(importsParam.getNombreConsulta());
                    options = BuscarOption(importsParam.getNombreConsulta(), importsParam.getParametro1(), importsParam.getParametro2()
                            , importsParam.getParametro3(), importsParam.getParametro4(), importsParam.getParametro5());
                    optionsKeys = BuscarOptions(importsParam.getNombreConsulta(), importsParam.getParametro1(), importsParam.getParametro2()
                            , importsParam.getParametro3(), importsParam.getParametro4(), importsParam.getParametro5());
                    fields = BuscarFields(importsParam.getNombreConsulta());
                    dto = ConsultaGeneralReadTable(importsParam.getNombreConsulta(), tabla, importsParam.getP_user(), options, optionsKeys, fields);
                    break;
            }
        } catch (Exception e) {
            dto.setMensaje(e.getMessage());
        }

        return dto;
    }

    public ConsultaGeneralExports ConsultaGeneralReadTable(String nombreConsulta, String tabla, String user, List<MaestroOptions> option, List<MaestroOptionsKey> optionsKeys, String[] fields) throws Exception {
        logger.error("CONSULTA GENERAL");
        ConsultaGeneralExports dto = new ConsultaGeneralExports();

        MaestroExport me;

        try {
            //setear mapeo de parametros import
            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("QUERY_TABLE", tabla);
            imports.put("DELIMITER", "|");
            imports.put("NO_DATA", "");
            imports.put("ROWSKIPS", "");
            imports.put("ROWCOUNT", "200");
            imports.put("P_USER", user);
            imports.put("P_ORDER", "");
            //setear mapeo de tabla options

            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            logger.error("CONSULTA GENERAL: size options " + option.size());

            for (int i = 0; i < option.size(); i++) {
                MaestroOptions mo = option.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();
                record.put("WA", mo.getWa());
                logger.error("CONSULTA GENERAL: WA: " + mo.getWa());
                tmpOptions.add(record);
            }

            Metodos metodo = new Metodos();

            tmpOptions = metodo.ValidarOptions(option, optionsKeys);

            //ejecutar RFC ZFL_RFC_READ_TABLE
            EjecutarRFC exec = new EjecutarRFC();
            me = exec.Execute_ZFL_RFC_READ_TABLE(imports, tmpOptions, fields);

            if (nombreConsulta.equals("CONSGENPESDESC")) {
                List<HashMap<String, Object>> data = ListaDataConDominio(me, nombreConsulta);
                dto.setData(data);
            } else {
                dto.setData(me.getData());
            }

            dto.setMensaje("Ok");
        } catch (Exception e) {
            dto.setMensaje(e.getMessage());
        }

        return dto;
    }

    public String Buscartabla(String nombreConsultaG) {

        String tabla = "";

        switch (nombreConsultaG) {
            case "CONSGENCODTIPRE":
                tabla = ConsultaGeneralTablas.CONSGENCODTIPRE;
                break;
            case "CONSGENLISTEQUIP":
                tabla = ConsultaGeneralTablas.CONSGENLISTEQUIP;
                break;
            case "CONSGENCOOZONPES":
                tabla = ConsultaGeneralTablas.CONSGENCOOZONPES;
                break;
            case "CONSGENPESDECLA":
                tabla = ConsultaGeneralTablas.CONSGENPESDECLA;
                break;
            case "CONSGENLISTBODE":
                tabla = ConsultaGeneralTablas.CONSGENLISTBODE;
                break;
            case "CONSGENPESBODE":
                tabla = ConsultaGeneralTablas.CONSGENPESBODE;
                break;
            case "CONSGENPUNTDES":
                tabla = ConsultaGeneralTablas.CONSGENPUNTDES;
                break;
            case "CONSGENPESDESC":
                tabla = ConsultaGeneralTablas.CONSGENPESDESC;
                break;
            case "CONSGENCALENTEMP":
                tabla = ConsultaGeneralTablas.CONSGENCALENTEMP;
                break;
            case "CONSGENCLDRTEMPFECHA":
                tabla = ConsultaGeneralTablas.CONSGENCLDRTEMPFECHA;
                break;
            case "CONSGENCONSTLAT":
                tabla = ConsultaGeneralTablas.CONSGENCONSTLAT;
                break;
            case "CONSGENLISTDESCPP":
                tabla = ConsultaGeneralTablas.CONSGENLISTDESCPP;
                break;
            case "CONSGENDPTO":
                tabla = ConsultaGeneralTablas.CONSGENDPTO;
                break;
            case "CONSGENMAREAANT":
                tabla = ConsultaGeneralTablas.CONSGENMAREAANT;
                break;
            case "CONSGENEVENTANT":
                tabla = ConsultaGeneralTablas.CONSGENEVENTANT;
                break;
            case "CONSGENNRORESERVA":
                tabla = ConsultaGeneralTablas.CONSGENNRORESERVA;
                break;
            case "CONSGENLISTSINIESTROS":
                tabla = ConsultaGeneralTablas.CONSGENLISTSINIESTROS;
                break;
            case "CONSGENVERIFTEMP":
                tabla = ConsultaGeneralTablas.CONSGENVERIFTEMP;
                break;
            case "CONSGENTEMP":
                tabla = ConsultaGeneralTablas.CONSGENTEMP;
                break;
            case "CONSGENCAPATANBARCA":
                tabla = ConsultaGeneralTablas.CONSGENCAPATANBARCA;
                break;
            case "CONSGENMILLASLIT":
                tabla = ConsultaGeneralTablas.CONSGENMILLASLIT;
                break;
            case "CONSGENPERMISOZARPE":
                tabla = ConsultaGeneralTablas.CONSGENPERMISOZARPE;
                break;
            case "CONSGENARMADOR":
                tabla = ConsultaGeneralTablas.CONSGENARMADOR;
                break;
            case "CONSGENOBTTALLAMIN":
                tabla = ConsultaGeneralTablas.CONSGENOBTTALLAMIN;
                break;
        }
        logger.error("tabla= " + tabla);
        return tabla;
    }

    public String[] BuscarFields(String nombreConsultaG) {

        String[] fields = {};

        switch (nombreConsultaG) {
            case "CONSGENCODTIPRE":
                fields = ConsultaGeneralFields.CONSGENCODTIPRE;
                break;
            case "CONSGENLISTEQUIP":
                fields = ConsultaGeneralFields.CONSGENLISTEQUIP;
                break;
            case "CONSGENCOOZONPES":
                fields = ConsultaGeneralFields.CONSGENCOOZONPES;
                break;
            case "CONSGENPESDECLA":
                fields = ConsultaGeneralFields.CONSGENPESDECLA;
                break;
            case "CONSGENLISTBODE":
                fields = ConsultaGeneralFields.CONSGENLISTBODE;
                break;
            case "CONSGENPESBODE":
                fields = ConsultaGeneralFields.CONSGENPESBODE;
                break;
            case "CONSGENPUNTDES":
                fields = ConsultaGeneralFields.CONSGENPUNTDES;
                break;
            case "CONSGENPESDESC":
                fields = ConsultaGeneralFields.CONSGENPESDESC;
                break;
            case "CONSGENCALENTEMP":
                fields = ConsultaGeneralFields.CONSGENCALENTEMP;
                break;
            case "CONSGENCLDRTEMPFECHA":
                fields = ConsultaGeneralFields.CONSGENCLDRTEMPFECHA;
                break;
            case "CONSGENCONSTLAT":
                fields = ConsultaGeneralFields.CONSGENCONSTLAT;
                break;
            case "CONSGENLISTDESCPP":
                fields = ConsultaGeneralFields.CONSGENLISTDESCPP;
                break;
            case "CONSGENDPTO":
                fields = ConsultaGeneralFields.CONSGENDPTO;
                break;
            case "CONSGENMAREAANT":
                fields = ConsultaGeneralFields.CONSGENMAREAANT;
                break;
            case "CONSGENEVENTANT":
                fields = ConsultaGeneralFields.CONSGENEVENTANT;
                break;
            case "CONSGENNRORESERVA":
                fields = ConsultaGeneralFields.CONSGENNRORESERVA;
                break;
            case "CONSGENLISTSINIESTROS":
                fields = ConsultaGeneralFields.CONSGENLISTSINIESTROS;
                break;
            case "CONSGENVERIFTEMP":
                fields = ConsultaGeneralFields.CONSGENVERIFTEMP;
                break;
            case "CONSGENTEMP":
                fields = ConsultaGeneralFields.CONSGENTEMP;
                break;
            case "CONSGENCAPATANBARCA":
                fields = ConsultaGeneralFields.CONSGENCAPATANBARCA;
                break;
            case "CONSGENMILLASLIT":
                fields = ConsultaGeneralFields.CONSGENMILLASLIT;
                break;
            case "CONSGENPERMISOZARPE":
                fields = ConsultaGeneralFields.CONSGENPERMISOZARPE;
                break;
            case "CONSGENARMADOR":
                fields = ConsultaGeneralFields.CONSGENARMADOR;
                break;
            case "CONSGENOBTTALLAMIN":
                fields = ConsultaGeneralFields.CONSGENOBTTALLAMIN;
                break;
        }


        return fields;
    }

    public List<MaestroOptions> BuscarOption(String nombreAyuda, String parametro1, String parametro2,
                                             String parametro3, String parametro4, String parametro5) {

        List<MaestroOptions> options = new ArrayList<>();

        MaestroOptions opt = new MaestroOptions();

        boolean inList = true;

        String condicion = "";

        switch (nombreAyuda) {
            case "CONSGENCODTIPRE":
                condicion = ConsultaGeneralOptions.CONSGENCODTIPRE + parametro1 + "'";
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENLISTEQUIP":
                condicion = ConsultaGeneralOptions.CONSGENLISTEQUIP + parametro1 + ConsultaGeneralOptions.CONSGENLISTEQUIP_;
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENCOOZONPES":
                condicion = ConsultaGeneralOptions.CONSGENCOOZONPES + parametro1 + "'";
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENPESDECLA":
                condicion = ConsultaGeneralOptions.CONSGENPESDECLA + parametro1 + ConsultaGeneralOptions.CONSGENPESDECLA_ + parametro2 + "'";
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENLISTBODE":
                condicion = ConsultaGeneralOptions.CONSGENLISTBODE + parametro1 + ConsultaGeneralOptions.CONSGENLISTBODE_;
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENPESBODE":
                condicion = ConsultaGeneralOptions.CONSGENPESBODE + parametro1 + ConsultaGeneralOptions.CONSGENPESBODE_ + parametro2 + "'";
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENPUNTDES":
                condicion = ConsultaGeneralOptions.CONSGENPUNTDES + parametro1 + "'";
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENPESDESC":
                condicion = ConsultaGeneralOptions.CONSGENPESDESC + parametro1 + "'";
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENCALENTEMP":
                condicion = ConsultaGeneralOptions.CONSGENCALENTEMP + parametro2 + parametro1 + "%'";
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENCLDRTEMPFECHA":
                condicion = ConsultaGeneralOptions.CONSGENCLDRTEMPFECHA + parametro1 + "' AND '" + parametro2 + "'";
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENCONSTLAT":
                condicion = ConsultaGeneralOptions.CONSGENCONSTLAT + ConsultaGeneralOptions.CONSGENCONSTLAT2 + ConsultaGeneralOptions.CONSGENCONSTLAT3 + ConsultaGeneralOptions.CONSGENCONSTLAT4;
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENDPTO":
                condicion = ConsultaGeneralOptions.CONSGENDPTO;
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENMAREAANT":
                //Evaluando el número de marea
                int nrmar = Integer.parseInt(parametro1);
                condicion = nrmar > 0 ? ConsultaGeneralOptions.CONSGENMAREAANT_OPTION1 + parametro1 + ConsultaGeneralOptions.CONSGENMAREAANT2_OPTION1 + parametro2 + "'" : ConsultaGeneralOptions.CONSGENMAREAANT2_OPTION2 + parametro2 + "'";
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENEVENTANT":
                condicion = ConsultaGeneralOptions.CONSGENEVENTANT + parametro1;
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENNRORESERVA":
                condicion = ConsultaGeneralOptions.CONSGENNRORESERVA + parametro1 + ConsultaGeneralOptions.CONSGENNRORESERVA2;
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENLISTSINIESTROS":
                condicion = ConsultaGeneralOptions.CONSGENLISTSINIESTROS + parametro1 + ConsultaGeneralOptions.CONSGENLISTSINIESTROS2 + parametro2 + ConsultaGeneralOptions.CONSGENLISTSINIESTROS3;
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENVERIFTEMP":
                condicion = ConsultaGeneralOptions.CONSGENVERIFTEMP + parametro1 + "'" + ConsultaGeneralOptions.CONSGENVERIFTEMP2 + parametro2 + "'";
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENTEMP":
                condicion = ConsultaGeneralOptions.CONSGENTEMP + parametro1 + "'" + ConsultaGeneralOptions.CONSGENTEMP2 + parametro2 + "'";
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENCAPATANBARCA":
                condicion = ConsultaGeneralOptions.CONSGENCAPATANBARCA + parametro1 + "'";
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENMILLASLIT":
                int latiCalaM = Integer.parseInt(parametro2);
                condicion = ConsultaGeneralOptions.CONSGENMILLASLIT + parametro1 + "'" + ConsultaGeneralOptions.CONSGENMILLASLIT2 + parametro2 + "'" + ConsultaGeneralOptions.CONSGENMILLASLIT3 + (latiCalaM + 1) + "'";
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENPERMISOZARPE":
                condicion = ConsultaGeneralOptions.CONSGENPERMISOZARPE + parametro1 + "'" + ConsultaGeneralOptions.CONSGENPERMISOZARPE2;
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENARMADOR":
                condicion = ConsultaGeneralOptions.CONSGENARMADOR + parametro1 + "'" + ConsultaGeneralOptions.CONSGENARMADOR2;
                opt.setWa(condicion);
                options.add(opt);
                break;
            case "CONSGENOBTTALLAMIN":
                condicion = ConsultaGeneralOptions.CONSGENOBTTALLAMIN + parametro1 + "'";
                opt.setWa(condicion);
                options.add(opt);
                break;
            default:
                inList = false;
                break;
        }

        if (inList) {
            logger.error("option= " + opt.getWa());
        }

        return options;
    }

    public HashMap<String, Object> BuscarNombreDominio(String nomConsulta) {
        logger.error("BuscarNombreDominio");
        HashMap<String, Object> data = new HashMap<>();

        switch (nomConsulta) {
            case "CONSGENPESDESC":
                data.put("DOMINIO", "ZCDLDS");
                data.put("CAMPO", "CDLDS");
                break;
        }
        //  logger.error("data1: "+data.get(0).toString());

        return data;
    }

    public String BuscarDominio(String nomDomino, String valor) throws Exception {


        String descripcion = "";

        DominioParams dominioParams = new DominioParams();
        dominioParams.setDomname(nomDomino);
        dominioParams.setStatus("A");

        List<DominioParams> ListDominioParams = new ArrayList<>();
        ListDominioParams.add(dominioParams);

        DominiosImports dominiosImports = new DominiosImports();
        dominiosImports.setDominios(ListDominioParams);

        DominioDto dominioDto = jcoDominiosService.Listar(dominiosImports);

        List<DominiosExports> ListaDomExports = dominioDto.getData();

        for (int i = 0; i < ListaDomExports.size(); i++) {
            DominiosExports dominiosExports = ListaDomExports.get(i);
            List<DominioExportsData> ListaDomExportData = dominiosExports.getData();

            for (int j = 0; j < ListaDomExportData.size(); j++) {
                DominioExportsData dominioExportsData = ListaDomExportData.get(j);

                if (valor.equals(dominioExportsData.getId())) {
                    descripcion = dominioExportsData.getDescripcion();

                }
            }

        }

        return descripcion;
    }

    public List<HashMap<String, Object>> ListaDataConDominio(MaestroExport me, String nomConsulta) throws Exception {

        List<HashMap<String, Object>> ndata = new ArrayList<>();
        HashMap<String, Object> nombreDominio = BuscarNombreDominio(nomConsulta);
        String campo = "";
        String dom = "";
        for (Map.Entry<String, Object> entry : nombreDominio.entrySet()) {

            if (entry.getKey().equals("DOMINIO")) {
                dom = entry.getValue().toString();

            } else if (entry.getKey().equals("CAMPO")) {
                campo = entry.getValue().toString();

            }
        }

        String valor = "";

        List<HashMap<String, Object>> data = me.getData();

        for (int i = 0; i < data.size(); i++) {
            HashMap<String, Object> registro = data.get(i);

            for (Map.Entry<String, Object> entry : registro.entrySet()) {

                if (entry.getKey().equals(campo)) {

                    valor = entry.getValue().toString();

                }
            }

            String descripcion = "";
            if (!valor.equals("")) {
                descripcion = BuscarDominio(dom, valor);
            }


            String field = "DESC_" + campo;

            registro.put(field, descripcion);

            ndata.add(registro);
        }

        return ndata;
    }

    public List<MaestroOptionsKey> BuscarOptions(String nombreConsultaG, String parametro1, String parametro2,
                                                 String parametro3, String parametro4, String parametro5) {

        List<MaestroOptionsKey> ListOptions = new ArrayList<>();


        if (nombreConsultaG.equals("CONSGENLISTDESCPP")) {
            MaestroOptionsKey matricula = new MaestroOptionsKey();
            matricula.setCantidad("12");
            matricula.setControl("INPUT");
            matricula.setKey("MREMB");
            matricula.setValueHigh("");
            matricula.setValueLow(parametro1);
            ListOptions.add(matricula);

            MaestroOptionsKey nomEmbarcacion = new MaestroOptionsKey();
            nomEmbarcacion.setCantidad("60");
            nomEmbarcacion.setControl("INPUT");
            nomEmbarcacion.setKey("NMEMB");
            nomEmbarcacion.setValueHigh("");
            nomEmbarcacion.setValueLow(parametro2);
            ListOptions.add(nomEmbarcacion);

            MaestroOptionsKey codPlanta = new MaestroOptionsKey();
            codPlanta.setCantidad("4");
            codPlanta.setControl("INPUT");
            codPlanta.setKey("CDPTA");
            codPlanta.setValueHigh("");
            codPlanta.setValueLow(parametro3);
            ListOptions.add(codPlanta);

            MaestroOptionsKey nomPlanta = new MaestroOptionsKey();
            nomPlanta.setCantidad("60");
            nomPlanta.setControl("INPUT");
            nomPlanta.setKey("DSPTA");
            nomPlanta.setValueHigh("");
            nomPlanta.setValueLow(parametro4);
            ListOptions.add(nomPlanta);

            MaestroOptionsKey fechaInicio = new MaestroOptionsKey();
            fechaInicio.setCantidad("8");
            fechaInicio.setControl("INPUT");
            fechaInicio.setKey("FIDES");
            fechaInicio.setValueHigh("");
            fechaInicio.setValueLow(parametro5);
            ListOptions.add(fechaInicio);

        }


        return ListOptions;
    }

}
