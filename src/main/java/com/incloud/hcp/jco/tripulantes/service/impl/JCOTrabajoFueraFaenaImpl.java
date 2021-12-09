package com.incloud.hcp.jco.tripulantes.service.impl;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.jco.tripulantes.dto.*;
import com.incloud.hcp.jco.tripulantes.service.JCOTrabajoFueraFaenaService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;

@Service
public class JCOTrabajoFueraFaenaImpl implements JCOTrabajoFueraFaenaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public TrabajoFueraFaenaExports TrabajoFueraFaenaTransporte(TrabajoFueraFaenaImports imports) throws Exception {


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



            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC exec= new EjecutarRFC();
            exec.setTable(tables, Tablas.T_OPCION,tmpOptions);

            stfcConnection.execute(destination);

            JCoTable T_TRABFF = tables.getTable(Tablas.T_TRABFF);

            JCoTable T_TRABAJ = tables.getTable(Tablas.T_TRABAJ);
            JCoTable T_FECHAS = tables.getTable(Tablas.T_FECHAS);
            JCoTable T_TEXTOS = tables.getTable(Tablas.T_TEXTOS);
            JCoTable T_MENSAJES = tables.getTable(Tablas.T_MENSAJES);


            List<HashMap<String, Object>> t_trabff = metodo.ObtenerListObjetos2(T_TRABFF, imports.getFieldst_trabff());
            List<HashMap<String, Object>> t_trabaj = metodo.ObtenerListObjetos(T_TRABAJ, imports.getFieldst_trabaj());
            List<HashMap<String, Object>> t_fechas = metodo.ObtenerListObjetos(T_FECHAS, imports.getFieldst_fechas());
            List<HashMap<String, Object>> t_textos = metodo.ObtenerListObjetos(T_TEXTOS, imports.getFieldst_textos());
            List<HashMap<String, Object>>  t_mensajes = metodo.ListarObjetos(T_MENSAJES);



            tff.setT_trabff(t_trabff);
            tff.setT_trabaj(t_trabaj);
            tff.setT_fechas(t_fechas);
            tff.setT_textos(t_textos);
            tff.setT_mensajes(t_mensajes);
            tff.setMensaje("Ok");

        }catch (Exception e){
            tff.setMensaje(e.getMessage());
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

        try{
            TrabajoFueraFaenaImports tfi=new TrabajoFueraFaenaImports();

            String[] fieldfechas= {"WERKS","PERNR","FETRA"};
            String[] fieldtextos= {"TDLINE","TDFORMAT"};
            String[] fieldtrabaj= {"PERNR","NOMBR","STELL"};
            String[] fieldtrabaff= {"NRTFF","FEFIN","FEINI","TIPTR","SEPES","USCRE","HOCRE","FECRE","USMOD","FEMOD","HOMOD","ESREG"};
            tfi.setIp_nrtff(imports.getNroTrabajo());
            tfi.setIp_canti("200");
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
                    SimpleDateFormat parseador = new SimpleDateFormat("HH:mm:ss");
                    SimpleDateFormat formateador = new SimpleDateFormat("HH:mm");
                    Date date = parseador.parse(valor);
                    horaCrea=formateador.format(date);
                }else if(key.equals("USMOD")){
                    dto.setUsuarioModif(valor);
                }else if(key.equals("ESREG")){
                    dto.setEstado(valor);
                }else if(key.equals("FEMOD")){
                    fechaMod=valor;
                }else if(key.equals("HOMOD")){
                    SimpleDateFormat parseador = new SimpleDateFormat("HH:mm:ss");
                    SimpleDateFormat formateador = new SimpleDateFormat("HH:mm");
                    Date date = parseador.parse(valor);
                    horaMod=formateador.format(date);
                }
            }
            dto.setFechaHoraCreacion(fechaCrea+" "+horaCrea);
            if(!dto.getUsuarioModif().equals("")) {
                dto.setFechaHoraModif(fechaMod + " " + horaMod);
            }else{
                dto.setFechaHoraModif("");
            }

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
            dto.setMensaje("Ok");

        }catch (Exception e){

            dto.setMensaje(e.getMessage());

        }

        return dto;
    }


    public TrabajoDetalleDtoExports DetalleTrabajoFueraFaenaTransportez(TrabajoFueraFaenaDetalleImports imports)throws Exception{

        TrabajoFueraFaenaExports tffde= new TrabajoFueraFaenaExports();

        TrabajoDetalleDtoExports dto= new TrabajoDetalleDtoExports();

        try{
            TrabajoFueraFaenaImports tfi=new TrabajoFueraFaenaImports();

            String[] fieldfechas= {"WERKS","PERNR","FETRA"};
            String[] fieldtextos= {"TDLINE","TDFORMAT"};
            String[] fieldtrabaj= {"MANDT", "NRTFF", "PERNR", "STELL", "DSCPO", "NOMBR", "ESREG", "INDVI", "CIUD1", "CIUD2", "CIUD3", "ESREV", "UNIOR", "PERSG", "USCRE", "FECRE", "HOCRE", "USMOD", "NMUSM", "FEMOD", "HOMOD", "INDEJ"};
            String[] fieldtrabaff= {"NRTFF","FEFIN","FEINI","TIPTR","SEPES","USCRE","HOCRE","FECRE","USMOD","FEMOD","HOMOD","ESREG","NMUSM"};
            tfi.setIp_nrtff(imports.getNroTrabajo());
            tfi.setIp_canti("200");
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
                    dto.setDescripcionTrabajo(line);
                }else if(format.equals("O")){
                    dto.setObservacion(line);
                }


            }

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
                    dto.setFechaCreacion(fechaCrea);
                }else if(key.equals("HOCRE")){
                    SimpleDateFormat parseador = new SimpleDateFormat("HH:mm:ss");
                    SimpleDateFormat formateador = new SimpleDateFormat("HH:mm");
                    Date date = parseador.parse(valor);
                    horaCrea=formateador.format(date);
                }else if(key.equals("USMOD")){
                    dto.setUsuarioModif(valor);
                }else if(key.equals("ESREG")){
                    dto.setEstado(valor);
                }else if(key.equals("FEMOD")){
                    fechaMod=valor;
                }else if(key.equals("NMUSM")){
                    dto.setNmusm(valor);
                }
                else if(key.equals("HOMOD")){
                    SimpleDateFormat parseador = new SimpleDateFormat("HH:mm:ss");
                    SimpleDateFormat formateador = new SimpleDateFormat("HH:mm");
                    Date date = parseador.parse(valor);
                    horaMod=formateador.format(date);
                }
            }
            dto.setFechaHoraCreacion(fechaCrea+" "+horaCrea);
            dto.setFechaCreacion(fechaCrea);
            dto.setHoraCreacion(horaCrea);
            if(!dto.getUsuarioModif().equals("")) {
                dto.setFechaHoraModif(fechaMod + " " + horaMod);
                dto.setFechaModif(fechaCrea);
                dto.setHoraModif(horaMod);
            }else{
                dto.setFechaHoraModif("");
                dto.setFechaModif(fechaCrea);
                dto.setHoraModif(horaMod);
            }

            String[] Listfechas=Obtenerfechas(dto.getFechaInicio(), dto.getFechaFin());
            dto.setFechas(Listfechas);
            List<TrabajoDetalleDto> ListDetalle=new ArrayList<>();

            for (int i=0; i<tffde.getT_trabaj().size(); i++){
                TrabajoDetalleDto detalle=new TrabajoDetalleDto();
                for(Map.Entry<String, Object>entry: tffde.getT_trabaj().get(i).entrySet()){
                    String key=entry.getKey();
                    String valor=entry.getValue().toString();
                    if(key.equals("NRTFF")){
                        detalle.setNrtff(valor);
                    }else if(key.equals("PERNR")){
                        detalle.setPernr(valor);
                    }else if(key.equals("NOMBR")){
                        detalle.setNombr(valor);
                    }else if(key.equals("STELL")){
                        //valor=me.ObtenerDominio("CARGOTRIPU",valor);
                        detalle.setStell(valor);
                    }else if(key.equals("DSCPO")){
                        detalle.setDscpo(valor);
                    }else if(key.equals("ESREG")){
                        detalle.setEsreg(valor);
                    }else if(key.equals("INDVI")){
                        detalle.setIndvi(valor);
                    }else if(key.equals("CIUD1")){
                        detalle.setCiud1(valor);
                    }else if(key.equals("CIUD2")){
                        detalle.setCiud2(valor);
                    }else if(key.equals("CIUD3")){
                        detalle.setCiud3(valor);
                    }else if(key.equals("ESREV")){
                        detalle.setEsrev(valor);
                    }else if(key.equals("UNIOR")){
                        detalle.setUnior(valor);
                    }else if(key.equals("PERSG")){
                        detalle.setPersg(valor);
                    }else if(key.equals("USCRE")){
                        detalle.setUscre(valor);
                    }else if(key.equals("FECRE")){
                        detalle.setFecre(valor);
                    }else if(key.equals("HOCRE")){
                        detalle.setHocre(valor);
                    }else if(key.equals("USMOD")){
                        detalle.setUsmod(valor);
                    }else if(key.equals("NMUSM")){
                        detalle.setNmusm(valor);
                    }else if(key.equals("FEMOD")){
                        detalle.setFemod(valor);
                    }else if(key.equals("HOMOD")){
                        detalle.setHomod(valor);
                    }else if(key.equals("INDEJ")){
                        detalle.setIndej(valor);
                    }
                    HashMap<String, Object>fechas= new HashMap<>();
                    for(int j=0; j<tffde.getT_fechas().size(); j++){

                        String value=tffde.getT_fechas().get(j).get("PERNR").toString();

                        if(value.equals(detalle.getPernr())){
                            String fecha="";
                            String centro="";
                            for (Map.Entry<String, Object> entry1:tffde.getT_fechas().get(j).entrySet()){
                                String key1=entry1.getKey();
                                String valor1=entry1.getValue().toString();

                                if(key1.equals("WERKS")){
                                    centro=valor1;
                                }else if(key1.equals("FETRA")){
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
            dto.setMensaje("Ok");

        }catch (Exception e){

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
        List<HashMap<String, Object>> listSTR_APP = metodos.ListarObjetos(tblSTR_APP);


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
        String[]fechas=new String[7];

        SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formateador = new SimpleDateFormat("dd");
        Date date = parseador.parse(fechaInicio);
        String inicio = formateador.format(date);

        date = parseador.parse(fechaFin);
        String fin = formateador.format(date);

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
}
