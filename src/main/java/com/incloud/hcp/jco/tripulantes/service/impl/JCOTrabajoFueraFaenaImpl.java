package com.incloud.hcp.jco.tripulantes.service.impl;

import com.incloud.hcp.jco.dominios.dto.DominioExportsData;
import com.incloud.hcp.jco.dominios.dto.DominiosExports;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.jco.reportepesca.dto.DominiosHelper;
import com.incloud.hcp.jco.tripulantes.dto.*;
import com.incloud.hcp.jco.tripulantes.service.JCOTrabajoFueraFaenaService;
import com.incloud.hcp.util.*;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JCOTrabajoFueraFaenaImpl implements JCOTrabajoFueraFaenaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public TrabajoFueraFaenaExports TrabajoFueraFaenaTransporte(TrabajoFueraFaenaImports imports) throws Exception {

        logger.error("tfft");
        TrabajoFueraFaenaExports tff=new TrabajoFueraFaenaExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_TFF_CON_TRA_FUE_FAE);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("IP_NRTFF", imports.getIp_nrtff());
            importx.setValue("IP_CDGFL", imports.getIp_cdgfl());
            importx.setValue("IP_WERKS", imports.getIp_werks());
            importx.setValue("IP_TOPE", imports.getIp_tope());
            importx.setValue("IP_CANTI", imports.getIp_canti());
            importx.setValue("IP_TIPTR", imports.getIp_tiptr());
            importx.setValue("IP_SEPES", imports.getIp_sepes());
            importx.setValue("IP_ESREG", imports.getIp_esreg());
            importx.setValue("IP_FECIN", imports.getIp_fecin());
            importx.setValue("IP_FECFN", imports.getIp_fecfn());
            Metodos metodo= new Metodos();
            List<MaestroOptions> option = imports.getOption();
            List<MaestroOptionsKey> options2 = imports.getOptions();
            List<HashMap<String, Object>> tmpOptions =metodo.ValidarOptions(option,options2,"DATA");

            logger.error("tfft1");

            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC exec= new EjecutarRFC();
            exec.setTable(tables, Tablas.T_OPCION,tmpOptions);

            if(imports.getIp_tope().equals("A")){
                exec.setTable(tables, Tablas.T_FECHAS,imports.getT_fechas());

            }
            logger.error("tfft2");
            stfcConnection.execute(destination);
            logger.error("tfft2");

            JCoTable T_TRABFF = tables.getTable(Tablas.T_TRABFF);

            JCoTable T_TRABAJ = tables.getTable(Tablas.T_TRABAJ);
            JCoTable T_FECHAS = tables.getTable(Tablas.T_FECHAS);
            JCoTable T_TEXTOS = tables.getTable(Tablas.T_TEXTOS);
            JCoTable T_MENSAJES = tables.getTable(Tablas.T_MENSAJES);

            //if(imports.getIp_tope().equals("A")){
            //    exec.setTable(tables, Tablas.T_FECHAS,imports.getT_fechas());
            //}

            //List<HashMap<String, Object>> t_trabff = metodo.ObtenerListObjetos2(T_TRABFF, imports.getFieldst_trabff());
            //List<HashMap<String, Object>> t_trabaj = metodo.ObtenerListObjetos(T_TRABAJ, imports.getFieldst_trabaj());
            //List<HashMap<String, Object>> t_fechas = metodo.ObtenerListObjetos(T_FECHAS, imports.getFieldst_fechas());
            //List<HashMap<String, Object>> t_textos = metodo.ObtenerListObjetos(T_TEXTOS, imports.getFieldst_textos());
            //List<HashMap<String, Object>>  t_mensajes = metodo.ListarObjetos(T_MENSAJES);
            List<HashMap<String, Object>> t_trabff = metodo.ListarObjetosLazy(T_TRABFF);
            logger.error("trabajo ff T_TRABFF");

            List<HashMap<String, Object>> t_trabaj = metodo.ListarObjetosLazy(T_TRABAJ);
            logger.error("trabajo ff T_TRABAJ");

            List<HashMap<String, Object>> t_fechas = metodo.ListarObjetosLazy(T_FECHAS);
            logger.error("trabajo ff T_FECHAS");

            List<HashMap<String, Object>> t_textos = metodo.ListarObjetosLazy(T_TEXTOS);
            logger.error("trabajo ff T_TEXTOS");

            List<HashMap<String, Object>> t_mensajes = metodo.ListarObjetosLazy(T_MENSAJES);
            logger.error("trabajo ff T_MENSAJES");

            logger.error("tff3");

            ArrayList<String> listDomNames = new ArrayList<>();
            listDomNames.add(Dominios.ZDO_ESREGTFF);
            listDomNames.add(Dominios.ZDO_TIPOTRABAJO);

            DominiosHelper helper = new DominiosHelper();
            ArrayList<DominiosExports> listDescipciones = helper.listarDominios(listDomNames);

            DominiosExports zdo_esregff = listDescipciones.stream().filter(d -> d.getDominio().equals(Dominios.ZDO_ESREGTFF)).findFirst().orElse(null);
            DominiosExports zdo_tipotrabajo = listDescipciones.stream().filter(d -> d.getDominio().equals(Dominios.ZDO_TIPOTRABAJO)).findFirst().orElse(null);

            t_trabff.stream().map(m -> {
                String esreg = m.get("ESREG").toString()!=null ? m.get("ESREG").toString() : "";
                String tiptr = m.get("TIPTR").toString()!=null ? m.get("TIPTR").toString() : "";

                // Buscar los detalles
                DominioExportsData dataESREG = zdo_esregff.getData().stream().filter(d -> d.getId().equals(esreg)).findFirst().orElse(null);
                DominioExportsData dataTIPTR = zdo_tipotrabajo.getData().stream().filter(d -> d.getId().equals(tiptr)).findFirst().orElse(null);

                m.put("DESC_ESREG", dataESREG != null ? dataESREG.getDescripcion() : "");
                m.put("DESC_TIPTR", dataTIPTR != null ? dataTIPTR.getDescripcion() : "");


                return m;
            }).collect(Collectors.toList());

            List<HashMap<String, Object>> listaTrabajadores= new ArrayList<>();

            for(int i=0; i<t_trabaj.size();i++) {

                boolean employee=false;
                for (Map.Entry<String, Object> entry: t_trabaj.get(i).entrySet()) {

                    String key=entry.getKey();
                    String value=entry.getValue().toString();

                    logger.error("tripulantes ESREG");
                    logger.error(key+":"+value);
                    if(key.equals("ESREG") && value.equals("A") || value.equals("P") || value.equals("L")){

                            employee=true;
                    }
                }
                if(employee){
                    listaTrabajadores.add( t_trabaj.get(i));
                }
            }

            tff.setT_trabff(t_trabff);
            tff.setT_trabaj(listaTrabajadores);
            tff.setT_fechas(t_fechas);
            tff.setT_textos(t_textos);
            tff.setT_mensajes(t_mensajes);
            tff.setMensaje("Ok");

        }catch (Exception e){
            tff.setMensaje(e.getMessage());
            logger.error("trabajoff: "+e.getMessage());
        }

        return tff;
    }

    @Override
    public GuardaTrabajoExports GuardaTrabajo(GuardaTrabajoImports imports) throws Exception {

        GuardaTrabajoExports gt=new GuardaTrabajoExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_TFF_ACT_TRA_FUE_FAE);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("IP_TOPE", imports.getIp_tope());
            importx.setValue("IP_NRTFF", imports.getIp_nrtff());


            JCoParameterList export = stfcConnection.getExportParameterList();


            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC exe = new EjecutarRFC();
            exe.setTable(tables, Tablas.T_TRABFF, imports.getT_trabff());
            exe.setTable(tables, Tablas.T_TRABAJ, imports.getT_trabaj());
            exe.setTable(tables, Tablas.T_FECHAS, imports.getT_fechas());
            exe.setTable(tables, Tablas.T_ACTCAM, imports.getT_actcam());
            exe.setTable(tables, Tablas.T_TEXTOS, imports.getT_textos());


            JCoTable T_MENSAJ = tables.getTable(Tablas.T_MENSAJ);

            stfcConnection.execute(destination);
            gt.setEp_nrtff(export.getValue(Tablas.EP_NRTFF).toString());

            Metodos metodo = new Metodos();

            List<HashMap<String, Object>>  t_mensaj = metodo.ListarObjetos(T_MENSAJ);


            gt.setT_mensaj(t_mensaj);
            gt.setMensaje("Ok");

        }catch (Exception e){
            gt.setMensaje(e.getMessage());
        }


        return gt;
    }

    public TrabajoFueraFaenaDetalleExports DetalleTrabajoFueraFaenaTransporte(TrabajoFueraFaenaDetalleImports imports)throws Exception{

        TrabajoFueraFaenaExports tffde= new TrabajoFueraFaenaExports();

        TrabajoFueraFaenaDetalleExports dto= new TrabajoFueraFaenaDetalleExports();

        logger.error("detalle ff_1");
        try{
            TrabajoFueraFaenaImports tfi=new TrabajoFueraFaenaImports();

            String[] fieldfechas= {"WERKS","PERNR","FETRA"};
            String[] fieldtextos= {"TDLINE","TDFORMAT"};
            String[] fieldtrabaj= {"PERNR","NOMBR","STELL"};
            String[] fieldtrabaff= {"NRTFF","FEFIN","FEINI","TIPTR","SEPES","USCRE","HOCRE","FECRE","USMOD","FEMOD","HOMOD","ESREG","DSWKS","WERKS"};
            tfi.setIp_nrtff(imports.getNroTrabajo());
            tfi.setIp_tope("");
            tfi.setFieldst_trabff(fieldtrabaff);
            tfi.setFieldst_fechas(fieldfechas);
            tfi.setFieldst_textos(fieldtextos);
            tfi.setFieldst_trabaj(fieldtrabaj);
            List<Options>options= new ArrayList<>();
            List<MaestroOptions> optionz = new ArrayList<>();
            List<MaestroOptionsKey> options2 = new ArrayList<>();
            tfi.setOption(optionz);
            tfi.setOptions(options2);
            tfi.setT_opcion(options);

            tffde=TrabajoFueraFaenaTransporte(tfi);
            logger.error("detalle ff_2");

            if(tffde.getT_textos().size()>0){
                String descripcion="";
                String observacion="";
                for (int i=0; i<tffde.getT_textos().size();i++){
                    String format="";
                    String line="";

                    for(Map.Entry<String, Object> entry:tffde.getT_textos().get(i).entrySet()){
                        String key=entry.getKey();
                        String valor=entry.getValue().toString();
                        if(key.equals("TDLINE")){
                            line=valor;
                        }else if(key.equals("TDFORMAT")){
                            format=valor;
                        }
                    }
                    dto.setObservacion("");
                    if(format.equals("D")){
                        descripcion=line;
                    }else if(format.equals("O")){
                        observacion=line;
                    }


                }
                dto.setDescripcionTrabajo(descripcion);
                dto.setObservacion(observacion);
            }else {
                dto.setDescripcionTrabajo("");
                dto.setObservacion("");
            }
            logger.error("detalle ff_3");


            String fechaCrea="";
            String horaCrea="";
            String fechaMod="";
            String horaMod="";

            Metodos me=new Metodos();

            for(Map.Entry<String, Object> entry:tffde.getT_trabff().get(0).entrySet()){
                String key=entry.getKey();
                String valor=entry.getValue().toString();
                if(key.equals("NRTFF")){
                    dto.setNrTrabajo(valor);
                }else if(key.equals("FEINI")){
                    dto.setFechaInicio(valor);
                }else if(key.equals("FEFIN")){
                    dto.setFechaFin(valor);
                }else if(key.equals("TIPTR")){
                    String dom=me.ObtenerDominio("ZDO_TIPOTRABAJO",valor);
                    dto.setTipoTrabajo(dom);
                }else if(key.equals("SEPES")){
                    dto.setSemana(valor);
                }else if(key.equals("USCRE")){
                    dto.setUsuarioCreacion(valor);
                }else if(key.equals("FECRE")){
                   fechaCrea=valor;
                }else if(key.equals("HOCRE")){
                    if(valor==null || valor.isEmpty() || valor.equals(null)) {
                        horaCrea="";
                    }else{
                        horaCrea=valor;
                    }
                }else if(key.equals("USMOD")){
                    dto.setUsuarioModif(valor);
                }else if(key.equals("ESREG")){
                    dto.setEstado(valor);
                }else if(key.equals("FEMOD")){
                    fechaMod=valor;
                }else if(key.equals("HOMOD")){
                    if(valor==null || valor.isEmpty() || valor.equals(null)) {
                        horaMod="";
                    }else{
                        //SimpleDateFormat parseador = new SimpleDateFormat("HH:mm:ss");
                        //SimpleDateFormat formateador = new SimpleDateFormat("HH:mm");
                        //Date date = parseador.parse(valor);
                        // horaCrea = formateador.format(date);
                        horaMod=valor;
                    }
                } else if(key.equals("DSWKS")){
                dto.setEmbarcacion(valor);
                 }else if(key.equals("WERKS")){
                dto.setCodEmbarcacion(valor);
            }
            }
            dto.setFechaHoraCreacion(fechaCrea+" "+horaCrea);
            if(!dto.getUsuarioModif().equals("")) {
                dto.setFechaHoraModif(fechaMod + " " + horaMod);
            }else{
                dto.setFechaHoraModif("");
            }
            logger.error("detalle ff_4");
            String[] Listfechas=Obtenerfechas(dto.getFechaInicio(), dto.getFechaFin());

            for(String f:Listfechas){
                logger.error("fecha= "+f);
            }

            dto.setFechas(Listfechas);
            List<TrabajoFFDetalleDto> ListDetalle=new ArrayList<>();
            logger.error("detalle ff_5");

            for (int i=0; i<tffde.getT_trabaj().size(); i++){
                    TrabajoFFDetalleDto detalle=new TrabajoFFDetalleDto();
                for(Map.Entry<String, Object>entry: tffde.getT_trabaj().get(i).entrySet()){
                    String key=entry.getKey();
                    String valor=entry.getValue().toString();

                    if(key.equals("PERNR")){
                        detalle.setNroPersona(valor);
                    }else if(key.equals("NOMBR")){
                        detalle.setNombre(valor);
                    }else if(key.equals("STELL")){
                        valor=me.ObtenerDominio("CARGOTRIPU",valor);
                        detalle.setCargo(valor);
                    }
                    HashMap<String, Object>fechas= new HashMap<>();
                    for(int j=0; j<tffde.getT_fechas().size(); j++){

                        String value=tffde.getT_fechas().get(j).get("PERNR").toString();

                        if(value.equals(detalle.getNroPersona())){
                            String fecha="";
                            String centro="";
                            for (Map.Entry<String, Object> entry1:tffde.getT_fechas().get(j).entrySet()){
                                String key1=entry1.getKey();
                                String valor1=entry1.getValue().toString();

                                if(key1.equals("WERKS")){
                                    centro=valor1;
                                }else if(key1.equals("FETRA")){
                                    logger.error("detalle ff_5 fecha: "+valor1);

                                    SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");
                                    SimpleDateFormat formateador = new SimpleDateFormat("dd");
                                    Date date = parseador.parse(valor1);
                                    fecha = formateador.format(date);

                                }
                             }

                            fechas.put(fecha, centro);
                        }
                    }
                    logger.error("detalle ff_6");


                    detalle.setFechas(fechas);
                    detalle.setCentro("");
                    detalle.setDestino("");
                    detalle.setOrigen("");
                }
                ListDetalle.add(detalle);
            }
            dto.setDetalle(ListDetalle);
            dto.setMensaje("Ok");

        }catch (Exception e){
            logger.error("causa error: "+e.getCause());
            logger.error(" error: "+e.getMessage());

            dto.setMensaje(e.getMessage());

        }

        return dto;
    }


    public TrabajoDetalleDtoExports DetalleTrabajoFueraFaenaTransportez(TrabajoFueraFaenaDetalleImports imports)throws Exception{

        TrabajoFueraFaenaExports tffde= new TrabajoFueraFaenaExports();

        TrabajoDetalleDtoExports dto= new TrabajoDetalleDtoExports();

        logger.error("DetalleTrabajoFueraFaenaTransportez_1");
        try{
            TrabajoFueraFaenaImports tfi=new TrabajoFueraFaenaImports();

            String[] fieldfechas= {"WERKS","PERNR","FETRA"};
            String[] fieldtextos= {"TDLINE","TDFORMAT"};
            String[] fieldtrabaj= {"MANDT", "NRTFF", "PERNR", "STELL", "DSCPO", "NOMBR", "ESREG", "INDVI", "CIUD1", "CIUD2", "CIUD3", "ESREV", "UNIOR", "PERSG", "USCRE", "FECRE", "HOCRE", "USMOD", "NMUSM", "FEMOD", "HOMOD", "INDEJ"};
            String[] fieldtrabaff= {"NRTFF","FEFIN","FEINI","TIPTR","SEPES","USCRE","HOCRE","FECRE","USMOD","FEMOD","HOMOD","ESREG","NMUSM"};
            tfi.setIp_nrtff(imports.getNroTrabajo());
            tfi.setIp_tope("");
            tfi.setFieldst_trabff(fieldtrabaff);
            tfi.setFieldst_fechas(fieldfechas);
            tfi.setFieldst_textos(fieldtextos);
            tfi.setFieldst_trabaj(fieldtrabaj);
            List<Options>options= new ArrayList<>();
            List<MaestroOptions> optionz = new ArrayList<>();
            List<MaestroOptionsKey> options2 = new ArrayList<>();
            tfi.setOption(optionz);
            tfi.setOptions(options2);
            tfi.setT_opcion(options);

            tffde=TrabajoFueraFaenaTransporte(tfi);
            logger.error("DetalleTrabajoFueraFaenaTransportez_2");

            if(tffde.getT_textos()!=null){
                String descripcion="";
                String observacion="";
                for (int i = 0; i < tffde.getT_textos().size(); i++) {
                    String format = "";
                    String line = "";
                    for (Map.Entry<String, Object> entry : tffde.getT_textos().get(i).entrySet()) {
                        String key = entry.getKey();
                        String valor = entry.getValue().toString();
                        if (key.equals("TDLINE")) {
                            line = valor;
                        } else if (key.equals("TDFORMAT")) {
                            format = valor;
                        }
                    }
                    dto.setObservacion("");
                    if(format.equals("D")){
                        descripcion=line;
                    }else if(format.equals("O")){
                        observacion=line;
                    }


                }
                dto.setDescripcionTrabajo(descripcion);
                dto.setObservacion(observacion);
            }else {
                dto.setDescripcionTrabajo("");
                dto.setObservacion("");
            }
            logger.error("DetalleTrabajoFueraFaenaTransportez_3");
            String fechaCrea="";
            String horaCrea="";
            String fechaMod="";
            String horaMod="";

            Metodos me=new Metodos();
            if(tffde.getT_trabff()!=null) {

                for (Map.Entry<String, Object> entry : tffde.getT_trabff().get(0).entrySet()) {
                    String key = entry.getKey();
                    String valor = entry.getValue().toString();
                    if (key.equals("NRTFF")) {
                        dto.setNrTrabajo(valor);
                    } else if (key.equals("FEINI")) {
                        dto.setFechaInicio(valor);
                    } else if (key.equals("FEFIN")) {
                        dto.setFechaFin(valor);
                    } else if (key.equals("TIPTR")) {
                        String dom = me.ObtenerDominio("ZDO_TIPOTRABAJO", valor);
                        dto.setTipoTrabajo(dom);
                    } else if (key.equals("SEPES")) {
                        dto.setSemana(valor);
                    } else if (key.equals("USCRE")) {
                        dto.setUsuarioCreacion(valor);
                    } else if (key.equals("FECRE")) {
                        fechaCrea = valor;
                        dto.setFechaCreacion(fechaCrea);
                    } else if (key.equals("HOCRE")) {
                        if(valor==null || valor.isEmpty() || valor.equals(null)) {
                            horaCrea="";
                        }else{
                            //SimpleDateFormat parseador = new SimpleDateFormat("HH:mm:ss");
                            //SimpleDateFormat formateador = new SimpleDateFormat("HH:mm");
                            //Date date = parseador.parse(valor);
                           // horaCrea = formateador.format(date);
                            horaCrea=valor;
                        }
                    } else if (key.equals("USMOD")) {
                        dto.setUsuarioModif(valor);
                    } else if (key.equals("ESREG")) {
                        dto.setEstado(valor);
                    } else if (key.equals("FEMOD")) {
                        fechaMod = valor;
                    } else if (key.equals("NMUSM")) {
                        dto.setNmusm(valor);
                    } else if (key.equals("HOMOD")) {
                        if(valor==null || valor.isEmpty() || valor.equals(null)) {
                            horaMod="";
                        }else {
                            //SimpleDateFormat parseador = new SimpleDateFormat("HH:mm:ss");
                            //SimpleDateFormat formateador = new SimpleDateFormat("HH:mm");
                            //Date date = parseador.parse(valor);
                            //horaMod = formateador.format(date);
                            horaMod=valor;
                        }
                    }
                }
                logger.error("DetalleTrabajoFueraFaenaTransportez_3 A");
                dto.setFechaHoraCreacion(fechaCrea + " " + horaCrea);
                dto.setFechaCreacion(fechaCrea);
                dto.setHoraCreacion(horaCrea);
                if (!dto.getUsuarioModif().equals("")) {
                    dto.setFechaHoraModif(fechaMod + " " + horaMod);
                    dto.setFechaModif(fechaCrea);
                    dto.setHoraModif(horaMod);
                } else {
                    dto.setFechaHoraModif("");
                    dto.setFechaModif(fechaCrea);
                    dto.setHoraModif(horaMod);
                }
                logger.error("DetalleTrabajoFueraFaenaTransportez_3 B");
                logger.error("DetalleTrabajoFueraFaenaTransportez_3 B  fecha inicio: "+dto.getFechaInicio());
                logger.error("DetalleTrabajoFueraFaenaTransportez_3 B  fecha fin: "+ dto.getFechaFin());
                String[] Listfechas=Obtenerfechas(dto.getFechaInicio(), dto.getFechaFin());
                dto.setFechas(Listfechas);
            }else{
                    dto.setNrTrabajo("");
                    dto.setFechaInicio("");
                    dto.setFechaFin("");
                    dto.setTipoTrabajo("");
                    dto.setSemana("");
                    dto.setUsuarioCreacion("");
                    dto.setUsuarioModif("");
                    dto.setEstado("");
                    dto.setNmusm("");
                    dto.setFechaHoraCreacion("");
                    dto.setFechaCreacion("");
                    dto.setHoraCreacion("");
                    dto.setFechaHoraModif("");
                    dto.setFechaModif("");
                    dto.setHoraModif("");

                String[] Listfechas={};
                dto.setFechas(Listfechas);

            }

            logger.error("DetalleTrabajoFueraFaenaTransportez_4");

            List<TrabajoDetalleDto> ListDetalle=new ArrayList<>();

            if(tffde.getT_trabff()!=null) {

                for (int i = 0; i < tffde.getT_trabaj().size(); i++) {
                    TrabajoDetalleDto detalle = new TrabajoDetalleDto();
                    for (Map.Entry<String, Object> entry : tffde.getT_trabaj().get(i).entrySet()) {
                        String key = entry.getKey();
                        String valor = entry.getValue().toString();
                        if (key.equals("NRTFF")) {
                            detalle.setNrtff(valor);
                        } else if (key.equals("PERNR")) {
                            detalle.setPernr(valor);
                        } else if (key.equals("NOMBR")) {
                            detalle.setNombr(valor);
                        } else if (key.equals("STELL")) {
                            //valor=me.ObtenerDominio("CARGOTRIPU",valor);
                            detalle.setStell(valor);
                        } else if (key.equals("DSCPO")) {
                            detalle.setDscpo(valor);
                        } else if (key.equals("ESREG")) {
                            detalle.setEsreg(valor);
                        } else if (key.equals("INDVI")) {
                            detalle.setIndvi(valor);
                        } else if (key.equals("CIUD1")) {
                            detalle.setCiud1(valor);
                        } else if (key.equals("CIUD2")) {
                            detalle.setCiud2(valor);
                        } else if (key.equals("CIUD3")) {
                            detalle.setCiud3(valor);
                        } else if (key.equals("ESREV")) {
                            detalle.setEsrev(valor);
                        } else if (key.equals("UNIOR")) {
                            detalle.setUnior(valor);
                        } else if (key.equals("PERSG")) {
                            detalle.setPersg(valor);
                        } else if (key.equals("USCRE")) {
                            detalle.setUscre(valor);
                        } else if (key.equals("FECRE")) {
                            detalle.setFecre(valor);
                        } else if (key.equals("HOCRE")) {
                            detalle.setHocre(valor);
                        } else if (key.equals("USMOD")) {
                            detalle.setUsmod(valor);
                        } else if (key.equals("NMUSM")) {
                            detalle.setNmusm(valor);
                        } else if (key.equals("FEMOD")) {
                            detalle.setFemod(valor);
                        } else if (key.equals("HOMOD")) {
                            detalle.setHomod(valor);
                        } else if (key.equals("INDEJ")) {
                            detalle.setIndej(valor);
                        } else if (key.equals("DESC_STELL")) {
                            //valor=me.ObtenerDominio("CARGOTRIPU",valor);
                            detalle.setCargo(valor);
                        }
                        HashMap<String, Object> fechas = new HashMap<>();
                        for (int j = 0; j < tffde.getT_fechas().size(); j++) {

                            String value = tffde.getT_fechas().get(j).get("PERNR").toString();

                            if (value.equals(detalle.getPernr())) {
                                String fecha = "";
                                String centro = "";
                                for (Map.Entry<String, Object> entry1 : tffde.getT_fechas().get(j).entrySet()) {
                                    String key1 = entry1.getKey();
                                    String valor1 = entry1.getValue().toString();

                                    if (key1.equals("WERKS")) {
                                        centro = valor1;
                                    } else if (key1.equals("FETRA")) {
                                        logger.error("Detalle fecha: "+valor1);
                                        SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");
                                        SimpleDateFormat formateador = new SimpleDateFormat("dd");
                                        Date date = parseador.parse(valor1);
                                        fecha = formateador.format(date);

                                    }
                                }

                                fechas.put(fecha, centro);
                            }
                        }

                        detalle.setFechas(fechas);
                        detalle.setCentro("");
                        detalle.setDestino("");
                        detalle.setOrigen("");
                    }
                    ListDetalle.add(detalle);
                }
                dto.setDetalle(ListDetalle);

            }else{
                dto.setDetalle(ListDetalle);
            }
            logger.error("DetalleTrabajoFueraFaenaTransportez_5");
            dto.setMensaje(tffde.getMensaje());
        }catch (Exception e){
            logger.error("causa error: "+ e.getCause());
            logger.error("error: "+ e.getMessage());
            dto.setMensaje(e.getMessage());

        }

        return dto;
    }

    @Override
    public SemanaDto ObtenerSemanas() throws Exception {
        SemanaDto dto = new SemanaDto();
        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZHR_LEE_SEMANAS_TRIP);
        JCoParameterList tables = function.getTableParameterList();
        function.execute(destination);
        JCoTable tblSTR_APP = tables.getTable(Tablas.ZHR_STR_SEM_TRIP);
        Metodos metodos= new Metodos();
        List<Semana> lista = new ArrayList<Semana>();
        List<HashMap<String, Object>> listSTR_APP = metodos.ListarObjetosSemana(tblSTR_APP);



        List<HashMap<String, Object>> listAñoActual= new ArrayList<>();
        List<HashMap<String, Object>> listAñoAnterior= new ArrayList<>();

        Date date= new Date();
        SimpleDateFormat getYear = new SimpleDateFormat("yyyy");
        String year = getYear.format(date);
        String yearOld=String.valueOf(Integer.parseInt(year)-1);
        String s=String.valueOf(Year.now());

        logger.error("year = "+year);
        logger.error("year = "+yearOld);
        for(int i=0;i< listSTR_APP.size();i++){



            String mandt="";
            String begda="";
            String vabrj="";
            String permo="";
            String endda="";
            String pabrp="";
            String pabrj="";
            String vabrp="";

            for(Map.Entry<String, Object> Entry: listSTR_APP.get(i).entrySet()){

                String key=Entry.getKey();
                String value=Entry.getValue().toString();

                if(key.equals("MANDT")){
                    mandt=value;
                }
                if(key.equals("BEGDA")){
                    begda=value;

                }
                if(key.equals("VABRJ")){
                    vabrj=value;
                }
                if(key.equals("PERMO")){
                    permo=value;
                }
                if(key.equals("ENDDA")){
                    endda=value;
                }
                if(key.equals("PABRP")){
                    pabrp=value;
                }
                if(key.equals("PABRJ")){
                    pabrj=value;
                }
                if(key.equals("VABRP")){
                    vabrp=value;
                }
            }
            HashMap<String, Object> registro=new HashMap<>();
            logger.error("PERMO : "+permo);
            logger.error("PABRJ : "+pabrj);

            if(permo.equals("08") && pabrj.equals(year)){
                logger.error("AÑO ACTUAL");

                String mes=BuscarMes(begda);
                String diferencia= DiferenciaDias(begda, endda);

                registro.put("DIFER", diferencia);
                registro.put("MES", mes);
                registro.put("MANDT", mandt);
                registro.put("BEGDA", begda);
                registro.put("VABRJ", vabrj);
                registro.put("PERMO", permo);
                registro.put("ENDDA", endda);
                registro.put("PABRP", pabrp);
                registro.put("PABRJ", pabrj);
                registro.put("VABRP", vabrp);

                listAñoActual.add(registro);

            }
            if (permo.equals("08") && pabrj.equals(yearOld)){

                logger.error("AÑO ANTERIOR");

                String mes=BuscarMes(begda);
                String diferencia= DiferenciaDias(begda, endda);

                registro.put("DIFER", diferencia);
                registro.put("MES", mes);
                registro.put("MANDT", mandt);
                registro.put("BEGDA", begda);
                registro.put("VABRJ", vabrj);
                registro.put("PERMO", permo);
                registro.put("ENDDA", endda);
                registro.put("PABRP", pabrp);
                registro.put("PABRJ", pabrj);
                registro.put("VABRP", vabrp);

                listAñoAnterior.add(registro);
            }
        }



        dto.setListAñoActual(listAñoActual);
        dto.setListAñoAnterior(listAñoAnterior);
        return dto;
    }



    public String[] Obtenerfechas(String fechaInicio, String fechaFin)throws Exception{
        logger.error("Obtenerfechas_1");
        logger.error("Obtenerfechas_ fecha inicio: "+ fechaInicio);
        logger.error("Obtenerfechas_ fecha Fin : "+ fechaFin);
        logger.error("Obtenerfechas_1");

        String[]fechas=new String[7];

        SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formateador = new SimpleDateFormat("dd");
        Date date = parseador.parse(fechaInicio);
        String inicio = formateador.format(date);

        logger.error("Obtenerfechas_ inicio: "+inicio);

        logger.error("Obtenerfechas_2");

        date = parseador.parse(fechaFin);
        String fin = formateador.format(date);
        logger.error("Obtenerfechas_ fin: "+fin);

        int con=0;
        for(int i=Integer.parseInt(inicio); i<=Integer.parseInt(fin);i++ ){

           if(i>9){
               fechas[con] = String.valueOf(i);
           }else{
               fechas[con]="0"+String.valueOf(i);
           }


            con++;
        }

        return fechas;
    }

    public String BuscarMes(String fecha)throws Exception{

        SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formateador = new SimpleDateFormat("MM");
        Date date = parseador.parse(fecha);
        int numMes = Integer.parseInt(formateador.format(date));

        String mes="";
        switch(numMes){
            case 1:
                mes="Enero";
                break;
            case 2:
                mes="Febrero";
                break;
            case 3:
                mes="Marzo";
                break;
            case 4:
                mes="Abril";
                break;
            case 5:
                mes="Mayo";
                break;
            case 6:
                mes="Junio";
                break;
            case 7:
                mes="Julio";
                break;
            case 8:
                mes="Agosto";
                break;
            case 9:
                mes="Septiembre";
                break;
            case 10:
                mes="Octubre";
                break;
            case 11:
                mes="Noviembre";
                break;
            case 12:
                mes="Diciembre";
                break;

        }

        return  mes;
    }

    public String DiferenciaDias(String fechaUno, String fechaDos) throws Exception{


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date firstDate = sdf.parse(fechaUno);
        Date secondDate = sdf.parse(fechaDos);

        int dias=(int) ((secondDate.getTime()-firstDate.getTime())/86400000);
        String diferencia=String.valueOf(dias);

        return diferencia;
    }

    public TrabajoFueraFaenaDetalleExports DetalleTrabajoFFTransporte(TrabajoFueraFaenaImports imports)throws Exception{

        logger.error("DetalleTrabajoFFTransporte_1");
        TrabajoFueraFaenaExports tffde= new TrabajoFueraFaenaExports();

        TrabajoFueraFaenaDetalleExports dto= new TrabajoFueraFaenaDetalleExports();

        try{
            TrabajoFueraFaenaImports tfi=new TrabajoFueraFaenaImports();

            String[] fieldfechas= {"WERKS","PERNR","FETRA"};
            String[] fieldtextos= {"TDLINE","TDFORMAT"};
            String[] fieldtrabaj= {"PERNR","NOMBR","STELL"};
            String[] fieldtrabaff= {"NRTFF","FEFIN","FEINI","TIPTR","SEPES","USCRE","HOCRE","FECRE","USMOD","FEMOD","HOMOD","ESREG","DSWKS","WERKS"};

            tfi.setFieldst_trabff(fieldtrabaff);
            tfi.setFieldst_fechas(fieldfechas);
            tfi.setFieldst_textos(fieldtextos);
            tfi.setFieldst_trabaj(fieldtrabaj);

            tfi.setIp_nrtff(imports.getIp_nrtff());
            tfi.setIp_canti(imports.getIp_canti());
            tfi.setIp_cdgfl(imports.getIp_cdgfl());
            tfi.setIp_esreg(imports.getIp_esreg());
            tfi.setIp_fecin(imports.getIp_fecin());
            tfi.setIp_fecfn(imports.getIp_fecfn());
            tfi.setIp_tiptr(imports.getIp_tiptr());
            tfi.setIp_tope(imports.getIp_tope());
            tfi.setIp_werks(imports.getIp_werks());
            tfi.setIp_sepes(imports.getIp_sepes());


            //List<Options>options= new ArrayList<>();
            //List<MaestroOptions> optionz = new ArrayList<>();
            //List<MaestroOptionsKey> options2 = new ArrayList<>();
            tfi.setOption(imports.getOption());
            tfi.setOptions(imports.getOptions());
            tfi.setT_opcion(imports.getT_opcion());

            logger.error("DetalleTrabajoFFTransporte_2");
            tffde=TrabajoFueraFaenaTransporte(imports);

            logger.error("DetalleTrabajoFFTransporte_3");
            if(tffde.getT_textos().size()>0){
                String descripcion="";
                String observacion="";
                for (int i=0; i<tffde.getT_textos().size();i++){
                    String format="";
                    String line="";

                    for(Map.Entry<String, Object> entry:tffde.getT_textos().get(i).entrySet()){
                        String key=entry.getKey();
                        String valor=entry.getValue().toString();
                        if(key.equals("TDLINE")){
                            line=valor;
                        }else if(key.equals("TDFORMAT")){
                            format=valor;
                        }
                    }
                    dto.setObservacion("");
                    if(format.equals("D")){
                        descripcion=line;
                    }else if(format.equals("O")){
                        observacion=line;
                    }


                }
                dto.setDescripcionTrabajo(descripcion);
                dto.setObservacion(observacion);
            }else {
                dto.setDescripcionTrabajo("");
                dto.setObservacion("");
            }
            logger.error("DetalleTrabajoFFTransporte_ textos");

            String fechaCrea="";
            String horaCrea="";
            String fechaMod="";
            String horaMod="";

            Metodos me=new Metodos();

            for(Map.Entry<String, Object> entry:tffde.getT_trabff().get(0).entrySet()){
                String key=entry.getKey();
                String valor=entry.getValue().toString();
                if(key.equals("NRTFF")){
                    dto.setNrTrabajo(valor);
                }else if(key.equals("FEINI")){
                    dto.setFechaInicio(valor);
                }else if(key.equals("FEFIN")){
                    dto.setFechaFin(valor);
                }else if(key.equals("TIPTR")){
                    String dom=me.ObtenerDominio("ZDO_TIPOTRABAJO",valor);
                    dto.setTipoTrabajo(dom);
                }else if(key.equals("SEPES")){
                    dto.setSemana(valor);
                }else if(key.equals("USCRE")){
                    dto.setUsuarioCreacion(valor);
                }else if(key.equals("FECRE")){
                    fechaCrea=valor;
                }else if(key.equals("HOCRE")){
                    /*
                    SimpleDateFormat parseador = new SimpleDateFormat("HH:mm:ss");
                    SimpleDateFormat formateador = new SimpleDateFormat("HH:mm");
                    Date date = parseador.parse(valor);
                    horaCrea=formateador.format(date);*/
                    if(valor==null || valor.isEmpty() || valor.equals(null)) {
                        horaCrea="";
                    }else{
                        horaCrea=valor;
                    }
                }else if(key.equals("USMOD")){
                    dto.setUsuarioModif(valor);
                }else if(key.equals("ESREG")){
                    dto.setEstado(valor);
                }else if(key.equals("FEMOD")){
                    fechaMod=valor;
                }else if(key.equals("HOMOD")){
                    /*
                    SimpleDateFormat parseador = new SimpleDateFormat("HH:mm:ss");
                    SimpleDateFormat formateador = new SimpleDateFormat("HH:mm");
                    Date date = parseador.parse(valor);
                    horaMod=formateador.format(date);*/
                    if(valor==null || valor.isEmpty() || valor.equals(null)) {
                        horaMod="";
                    }else{
                        horaMod=valor;
                    }
                }else if(key.equals("DSWKS")){
                    dto.setEmbarcacion(valor);
                }else if(key.equals("WERKS")){
                    dto.setCodEmbarcacion(valor);
                }
            }
            dto.setFechaHoraCreacion(fechaCrea+" "+horaCrea);
            if(!dto.getUsuarioModif().equals("")) {
                dto.setFechaHoraModif(fechaMod + " " + horaMod);
            }else{
                dto.setFechaHoraModif("");
            }
            logger.error("DetalleTrabajoFFTransporte_ trabaff");

            String[] Listfechas=Obtenerfechas(dto.getFechaInicio(), dto.getFechaFin());
            dto.setFechas(Listfechas);
            List<TrabajoFFDetalleDto> ListDetalle=new ArrayList<>();

            for (int i=0; i<tffde.getT_trabaj().size(); i++){
                TrabajoFFDetalleDto detalle=new TrabajoFFDetalleDto();
                for(Map.Entry<String, Object>entry: tffde.getT_trabaj().get(i).entrySet()){
                    String key=entry.getKey();
                    String valor=entry.getValue().toString();

                    if(key.equals("PERNR")){
                        detalle.setNroPersona(valor);
                    }else if(key.equals("NOMBR")){
                        detalle.setNombre(valor);
                    }else if(key.equals("STELL")){
                        valor=me.ObtenerDominio("CARGOTRIPU",valor);
                        detalle.setCargo(valor);
                    }
                    HashMap<String, Object>fechas= new HashMap<>();
                    for(int j=0; j<tffde.getT_fechas().size(); j++){

                        String value=tffde.getT_fechas().get(j).get("PERNR").toString();

                        if(value.equals(detalle.getNroPersona())){
                            String fecha="";
                            String centro="";
                            for (Map.Entry<String, Object> entry1:tffde.getT_fechas().get(j).entrySet()){
                                String key1=entry1.getKey();
                                String valor1=entry1.getValue().toString();

                                if(key1.equals("WERKS")){
                                    centro=valor1;
                                }else if(key1.equals("FETRA")){
                                    logger.error("fecha: "+valor1);
                                    SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");
                                    SimpleDateFormat formateador = new SimpleDateFormat("dd");
                                    Date date = parseador.parse(valor1);
                                    fecha = formateador.format(date);

                                }
                            }

                            fechas.put(fecha, centro);
                        }
                    }

                    logger.error("DetalleTrabajoFFTransporte_ trabaj");
                    detalle.setFechas(fechas);
                    detalle.setCentro("");
                    detalle.setDestino("");
                    detalle.setOrigen("");
                }
                ListDetalle.add(detalle);
            }

            dto.setT_trabaff(tffde.getT_trabff());
            dto.setT_textos(tffde.getT_textos());
            dto.setT_trabaj(tffde.getT_trabaj());
            dto.setT_mensaje(tffde.getT_mensajes());
            dto.setT_fechas(tffde.getT_fechas());
            dto.setDetalle(ListDetalle);
            dto.setMensaje("Ok");

        }catch (Exception e){

            dto.setMensaje(e.getMessage());

        }

        return dto;
    }

}
