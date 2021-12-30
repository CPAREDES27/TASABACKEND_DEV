package com.incloud.hcp.jco.gestionpesca.service.impl;

import com.incloud.hcp.jco.dominios.dto.*;
import com.incloud.hcp.jco.dominios.service.impl.JCODominiosImpl;
import com.incloud.hcp.jco.gestionpesca.dto.*;
import com.incloud.hcp.jco.gestionpesca.service.JCOEmbarcacionService;
import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.maestro.service.JCOCampoTablaService;
import com.incloud.hcp.jco.maestro.service.JCOMaestrosService;
import com.incloud.hcp.jco.reportepesca.dto.MareaDto2;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
//@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class JCOEmbarcacionServiceImpl implements JCOEmbarcacionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JCODominiosImpl dominioService;

    @Autowired
    JCOMaestrosService MaestroService;

    @Autowired
    private JCOCampoTablaService jcoCampoTablaService;

    public List<EmbarcacionDto> listaEmbarcacion(String condicion) throws Exception {

        List<EmbarcacionDto> listaEmbarcacion = new ArrayList<EmbarcacionDto>();
        logger.error("listaEmbarcacion_1");
        ;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        //JCo
        logger.error("listaEmbarcacion_2");
        ;
        JCoRepository repo = destination.getRepository();
        logger.error("listaEmbarcacion_3");
        ;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_CONS_EMBARCA");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("P_USER", "XTS");
        logger.error("listaEmbarcacion_4");
        ;
        JCoParameterList tables = stfcConnection.getTableParameterList();
            JCoTable tableImport = tables.getTable("P_OPTIONS");
        tableImport.appendRow();
        logger.error("listaEmbarcacion_5");
        ;
        //tableImport.setValue("WA", condicion);
        //Ejecutar Funcion
        stfcConnection.execute(destination);
        logger.error("listaEmbarcacion_6");
        //DestinationAcce

        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable("STR_EMB");

        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);
            EmbarcacionDto dto = new EmbarcacionDto();

            dto.setCodigoEmbarcacion(tableExport.getString("CDEMB"));
            dto.setNombreEmbarcacion(tableExport.getString("NMEMB"));
            listaEmbarcacion.add(dto);
            //lista.add(param);
        }
        return listaEmbarcacion;
    }

    public FlotaDto obtenerDistribucionFlota(String user) throws Exception{

        logger.error("ListarEventosPesca_1");;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        logger.error("ListarEventosPesca_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("ListarEventosPesca_3");;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_DISTR_FLOTA");
        JCoParameterList importx = stfcConnection.getImportParameterList();

        importx.setValue("P_USER", user);
        logger.error("ListarEventosPesca_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        stfcConnection.execute(destination);
        logger.error("ListarEventosPesca_5");

        JCoTable STR_ZLT = tables.getTable("STR_ZLT");
        logger.error("ListarEventosPesca_6");
        JCoTable STR_DI = tables.getTable("STR_DI");
        JCoTable STR_PTA = tables.getTable("STR_PTA");
        logger.error("ListarEventosPesca_6");
        JCoTable STR_DP = tables.getTable("STR_DP");

        logger.error("ListarEventosPesca_7");

        Metodos metodo = new Metodos();
        List<HashMap<String, Object>> ListarST_CEP= metodo.ListarObjetos(STR_ZLT);
        List<HashMap<String, Object>> ListarST_CCP= metodo.ListarObjetos(STR_DI);
        List<HashMap<String, Object>> ListarSTR_PTA= metodo.ListarObjetos(STR_PTA);
        List<HashMap<String, Object>> ListarSTR_DP= metodo.ListarObjetos(STR_DP);

        FlotaDto dto= new FlotaDto();
        dto.setStr_zlt(ListarST_CEP);
        dto.setStr_di(ListarST_CCP);
        dto.setStr_dp(ListarSTR_DP);
        dto.setStr_pta(ListarSTR_PTA);
        dto.setMensaje("Ok");


        return dto;
    }
    public MareaDto consultaMarea(MareaOptions marea) throws Exception{

        logger.error("ListarEventosPesca_1");;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        logger.error("ListarEventosPesca_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("ListarEventosPesca_3");;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_CONS_MARE_EVENT");
        JCoParameterList importx = stfcConnection.getImportParameterList();

        importx.setValue("P_USER", marea.getUser());
        //importx.setValue("P_MAREA", marea.getP_marea());
        importx.setValue("P_MAREA", Integer.parseInt(marea.getP_marea()));
        //importx.setValue("P_EMBARCACION", marea.getP_embarcacion());
        //importx.setValue("P_FLAG", marea.getP_flag());
        importx.setActive("P_FLAG", true);

        logger.error("ListarEventosPesca_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        stfcConnection.execute(destination);
        logger.error("ListarEventosPesca_5");

        JCoTable S_MAREA = tables.getTable("S_MAREA");
        logger.error("ListarEventosPesca_6");
        JCoTable S_EVENTO = tables.getTable("S_EVENTO");
        JCoTable STR_FLBSP = tables.getTable("STR_FLBSP");
        JCoTable STR_PSCINC = tables.getTable("STR_PSCINC");

        logger.error("ListarEventosPesca_7");

        Metodos metodo = new Metodos();
        List<HashMap<String, Object>> ListarS_MAREA= metodo.ObtenerListObjetos(S_MAREA,marea.getFieldMarea());
        List<HashMap<String, Object>> ListarS_EVENTO= metodo.ObtenerListObjetos(S_EVENTO,marea.getFieldEvento());
        List<HashMap<String, Object>> ListarSTR_FLBSP= metodo.ObtenerListObjetos(STR_FLBSP,marea.getFieldFLBSP());
        List<HashMap<String, Object>> ListarSTR_PSCINC= metodo.ObtenerListObjetos(STR_PSCINC,marea.getFieldPSCINC());

        MareaDto dto= new MareaDto();
        dto.setS_marea(ListarS_MAREA);
        dto.setS_evento(ListarS_EVENTO);
        dto.setStr_flbsp(ListarSTR_FLBSP);
        dto.setStr_pscinc(ListarSTR_PSCINC);


        dto.setMensaje("Ok");

        return dto;
    }

    public MareaDto2 consultaMarea2(MareaOptions marea) throws Exception{
        logger.error("ListarEventosPesca_1");;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        logger.error("ListarEventosPesca_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("ListarEventosPesca_3");;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_CONS_MARE_EVENT");
        JCoParameterList importx = stfcConnection.getImportParameterList();

        importx.setValue("P_USER", marea.getUser());
        //importx.setValue("P_MAREA", marea.getP_marea());
        importx.setValue("P_MAREA", Integer.parseInt(marea.getP_marea()));
        //importx.setValue("P_EMBARCACION", marea.getP_embarcacion());
        //importx.setValue("P_FLAG", marea.getP_flag());
        importx.setActive("P_FLAG", true);

        logger.error("ListarEventosPesca_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        stfcConnection.execute(destination);
        logger.error("ListarEventosPesca_5");

        JCoTable S_MAREA = tables.getTable("S_MAREA");
        logger.error("ListarEventosPesca_6");
        JCoTable S_EVENTO = tables.getTable("S_EVENTO");
        JCoTable STR_FLBSP = tables.getTable("STR_FLBSP");
        JCoTable STR_PSCINC = tables.getTable("STR_PSCINC");

        logger.error("ListarEventosPesca_7");

        Metodos metodo = new Metodos();
        List<HashMap<String, Object>> ListarS_MAREA= metodo.ObtenerListObjetos(S_MAREA,marea.getFieldMarea());
        List<HashMap<String, Object>> ListarS_EVENTO= metodo.ObtenerListObjetos(S_EVENTO,marea.getFieldEvento());
        List<HashMap<String, Object>> ListarSTR_FLBSP= metodo.ObtenerListObjetos(STR_FLBSP,marea.getFieldFLBSP());
        List<HashMap<String, Object>> ListarSTR_PSCINC= metodo.ObtenerListObjetos(STR_PSCINC,marea.getFieldPSCINC());

        MareaDto2 dto= new MareaDto2();
        dto.setS_marea(ListarS_MAREA);
        dto.setS_evento(ListarS_EVENTO);
        dto.setStr_flbsp(ListarSTR_FLBSP);
        dto.setStr_pscinc(ListarSTR_PSCINC);


        dto.setMensaje("Ok");

        // Agrupar los registros por el código de especie y evento

        Map<Object,List<HashMap<String,Object>>> str_flbsp_group=dto.getStr_flbsp().stream().collect(Collectors.groupingBy(s->{
            return new ArrayList<String>(Arrays.asList(s.get("NREVN").toString(),s.get("CDSPC").toString()));
        }));

        int totalCnspc=0;
        int cnspcModa=0;
        double moda=0;

        // Copy Map
        HashMap<String,ArrayList<HashMap<String,Object>>> str_flbsp_group_copy=new HashMap<>();
        for (Map.Entry<Object,List<HashMap<String,Object>>> entry: str_flbsp_group.entrySet()) {
            ArrayList<HashMap<String,Object>> str_flbsp_especie=new ArrayList<>(entry.getValue());
            str_flbsp_group_copy.put(entry.getKey().toString(),str_flbsp_especie);
        }

        //Obtener las especies
        DominiosImports dominiosImports=new DominiosImports();
        ArrayList<DominioParams> listDominioParams=new ArrayList<>();
        DominioParams dominioParams=new DominioParams();
        dominioParams.setStatus("A");
        dominioParams.setDomname("ESPECIE");
        listDominioParams.add(dominioParams);
        dominiosImports.setDominios(listDominioParams);
        DominioDto dominioExport=dominioService.Listar(dominiosImports);
        ArrayList<DominioExportsData> listEspecies=new ArrayList<>(dominioExport.getData().get(0).getData());

        List<HashMap<String,Object>> str_flbsp_matched=new ArrayList<>();
        //Obtener columnas dinamicas
        for (Map.Entry<String,ArrayList<HashMap<String,Object>>> entry: str_flbsp_group_copy.entrySet()) {
            String codEspecie = null;

            //Fila al cual se le añadirán las columnas dinámicas
            HashMap<String,Object> record = (HashMap<String, Object>) entry.getValue().get(0).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
            for (HashMap<String,Object> flbsp: entry.getValue()) {
                codEspecie = flbsp.get("CDSPC").toString();
                String tnmmed="TNMED_"+ flbsp.get("TMMED").toString();
                int cnspc=Integer.parseInt(flbsp.get("CNSPC").toString());
                double tmmed=Double.parseDouble(flbsp.get("TMMED").toString());
                tnmmed=tnmmed.replace('.','_');
                record.put(tnmmed,flbsp.get("CNSPC"));

                //Total de cantidades
                totalCnspc+=cnspc;

                //Moda
                if(cnspcModa<cnspc){
                    moda=tmmed;
                    cnspcModa=cnspc;
                }
            }



            //Buscar el nombre de la especie en la lista y adicionarlo
            String finalCodEspecie = codEspecie;
            DominioExportsData especie=listEspecies.stream().filter(s->s.getId().equals(finalCodEspecie)).findAny().orElse(null);
            String descEspecie=especie!=null?especie.getDescripcion():null;

            record.put("DESC_CDSPC",descEspecie);
            record.put("CDSPC_TOTAL",totalCnspc);
            record.put("MODA", moda);

            str_flbsp_matched.add(record);
        }

        dto.setStr_flbsp_matched(str_flbsp_matched);

        return dto;
    }

    @Override
    public HorometroExport consultarHorometro(HorometroDto horometro) throws Exception {

        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        JCoRepository repo = destination.getRepository();
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_OBT_LEC_ULT_HOR");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        importx.setValue("IP_CDEMB", horometro.getIp_cdemb());
        importx.setValue("IP_NRMAR", horometro.getIp_nrmar());
        JCoParameterList tables = stfcConnection.getTableParameterList();
        stfcConnection.execute(destination);
        JCoTable T_MAREA = tables.getTable("T_MAREA");
        JCoTable T_EVENT = tables.getTable("T_EVENT");
        JCoTable T_LECHOR = tables.getTable("T_LECHOR");
        JCoTable T_MENSAJE = tables.getTable("T_MENSAJE");
        Metodos metodo = new Metodos();
        List<HashMap<String, Object>> ListarT_MAREA= metodo.ListarObjetos(T_MAREA);
        List<HashMap<String, Object>> ListarT_EVENTO= metodo.ListarObjetos(T_EVENT);
        List<HashMap<String, Object>> ListarT_LECHOR= metodo.ListarObjetos(T_LECHOR);
        List<HashMap<String, Object>> ListarT_MENSAJE= metodo.ListarObjetos(T_MENSAJE);
        HorometroExport dto= new HorometroExport();
        dto.setT_marea(ListarT_MAREA);
        dto.setT_event(ListarT_EVENTO);
        dto.setT_lechor(ListarT_LECHOR);
        dto.setT_mensaje(ListarT_MENSAJE);

        return dto;
    }
    public ArrayList<MensajeDto> crearMareaPropios(MarEventoDtoImport imports) throws Exception{

        ArrayList<MensajeDto> mensajes = new ArrayList<MensajeDto>();

        try{
            HashMap<String, Object> importsSap = new HashMap<String, Object>();
            importsSap.put("P_USER", imports.getP_user());
            importsSap.put("P_INDTR",imports.getP_indir());
            importsSap.put("P_NEWPR",imports.getP_newpr());
            importsSap.put("P_NAME1",imports.getP_name1());
            importsSap.put("P_STCD1",imports.getP_stcd1());
            importsSap.put("P_STRAS",imports.getP_stras());
            importsSap.put("P_ORT02",imports.getP_orto2());
            importsSap.put("P_ORT01",imports.getP_orto1());
            importsSap.put("P_REGIO",imports.getP_regio());
            importsSap.put("P_DSMMA",imports.getP_dsmma());

            List<HashMap<String, Object>> str_marea = imports.getStr_marea();
            List<HashMap<String, Object>> str_event = imports.getStr_evento();
            List<HashMap<String, Object>> str_horom = imports.getStr_horom();
            List<HashMap<String, Object>> str_flbsp_c = imports.getStr_flbsp_c();
            List<HashMap<String, Object>> str_flbsp_e = imports.getStr_flbsp_e();
            List<HashMap<String, Object>> str_pscinc = imports.getStr_pscinc();
            List<HashMap<String, Object>> str_equip = imports.getStr_equip();
            List<HashMap<String, Object>> str_psdec = imports.getStr_psdec();
            List<HashMap<String, Object>> str_psbod = imports.getStr_psbod();
            List<HashMap<String, Object>> str_desca = imports.getStr_desca();
            List<HashMap<String, Object>> str_simar = imports.getStr_simar();

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction("ZFL_RFC_MAR_EVENT");
            JCoParameterList jcoTables = function.getTableParameterList();
            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, importsSap);
            exec.setTable(jcoTables, "STR_MAREA",str_marea );
            exec.setTable(jcoTables, "STR_EVENT", str_event);
            exec.setTable(jcoTables, "STR_HOROM", str_horom);
            exec.setTable(jcoTables, "STR_FLBSP_C", str_flbsp_c);
            exec.setTable(jcoTables, "STR_FLBSP_E", str_flbsp_e);
            exec.setTable(jcoTables, "STR_PSCINC", str_pscinc);
            exec.setTable(jcoTables, "STR_EQUIP", str_equip);
            exec.setTable(jcoTables, "STR_PSDEC", str_psdec);
            exec.setTable(jcoTables, "STR_PSBOD", str_psbod);
            exec.setTable(jcoTables, "STR_DESCA", str_desca);
            exec.setTable(jcoTables, "STR_SIMAR", str_simar);

            function.execute(destination);

            JCoTable tableExport = jcoTables.getTable(Tablas.T_MENSAJE);


            for (int i = 0; i < tableExport.getNumRows(); i++) {
                tableExport.setRow(i);
                MensajeDto msj= new MensajeDto();
                msj.setMANDT(tableExport.getString("MANDT"));
                msj.setCMIN(tableExport.getString("CMIN"));
                msj.setCDMIN(tableExport.getString("CDMIN"));
                msj.setDSMIN(tableExport.getString("DSMIN"));
                mensajes.add(msj);
                //lista.add(param);
            }

        }catch (Exception e){
            MensajeDto msj= new MensajeDto();
            msj.setMANDT("00");
            msj.setCMIN("Error");
            msj.setCDMIN("Exception");
            msj.setDSMIN(e.getMessage());
            mensajes.add(msj);
        }
        return mensajes;
    }

    public BodegaExport ValidarBodegaCert(BodegaImport imports) throws Exception{
        Metodos metodo = new Metodos();
        boolean bOk  = true;

        String bodCert = "";
        String werks ="";
        String codEmba = imports.getCodEmba();
        String codPlanta = imports.getCodPlanta();
        String usuario = imports.getUsuario();

        //consulta ZFLPTA
        MaestroImportsKey imports1 = new MaestroImportsKey();
        MaestroOptions mo1 = new MaestroOptions();
        String wa1 = "CDPTA = '" + codPlanta + "'";
        mo1.setWa(wa1);
        List<MaestroOptions> listOptions1 = new ArrayList<MaestroOptions>();
        listOptions1.add(mo1);
        String[] fields = {"WERKS"};
        List<MaestroOptionsKey> listOptKey1 = new ArrayList<MaestroOptionsKey>();
        imports1.setTabla(Tablas.ZFLPTA);
        imports1.setDelimitador("|");
        imports1.setOption(listOptions1);
        imports1.setFields(fields);
        imports1.setOptions(listOptKey1);
        imports1.setOrder("");
        imports1.setRowcount(0);
        imports1.setRowskips(0);
        imports1.setP_user(usuario);
        MaestroExport me1 = MaestroService.obtenerMaestro2(imports1);
        if (me1.getData().size() > 0){
            List<HashMap<String, Object>> data = me1.getData();
            HashMap<String, Object> obj = data.get(0);
            Object objWerk = obj.get("WERKS");
            if(objWerk != null){
                werks = String.valueOf(objWerk);
            }
        }else{
            werks = codPlanta;
            bOk = false;
        }
        imports1.setTabla(Tablas.ZFLEMB);
        String wa2 = "CDEMB = '" +codEmba+ "'";
        logger.error(wa2);
        mo1.setWa(wa2);
        listOptions1.clear();
        listOptions1.add(mo1);
        imports1.setOption(listOptions1);
        String[] fields1 = {"HPACH"};
        imports1.setFields(fields1);
        MaestroExport me2 = MaestroService.obtenerMaestro2(imports1);
        String mssg0 = "M2 SIZE: " + me2.getData().size();
        logger.error(mssg0);
        if(me2.getData().size() > 0){
            List<HashMap<String, Object>> data = me2.getData();
            HashMap<String, Object> obj = data.get(0);
            Object objHpach = obj.get("HPACH");
            if(objHpach != null){
                bodCert = String.valueOf(objHpach).trim();
            }
        }else{
            bodCert = " ";
        }

        String mssg1 = "Bok: " + bOk;
        String mssg2 = "Bod Cert: " + bodCert;
        logger.error(mssg1);
        logger.error(mssg2);
        if (!bodCert.equalsIgnoreCase("S")){

            imports1.setTabla("ZTB_CONSTANTES");
            String[] fields2 = {"LOW"};
            imports1.setFields(fields2);
            String wa3 = "APLICACION = 'FL' AND PROGRAMA = 'ZFL_RFC_DISTR_FLOTA' AND CAMPO = 'WERKS'";
            mo1.setWa(wa3);
            listOptions1.clear();
            listOptions1.add(mo1);
            imports1.setOption(listOptions1);
            MaestroExport me3 = MaestroService.obtenerMaestro2(imports1);
            if(me3.getData().size() > 0){
                List<HashMap<String, Object>> data = me2.getData();
                for (int i = 0; i < data.size(); i++){
                    HashMap<String, Object> obj = data.get(i);
                    Object objLow = obj.get("LOW");
                    if(objLow != null){
                        String low = String.valueOf(objLow);
                        if(low.equalsIgnoreCase(werks)){
                            bOk = false;
                            break;
                        }
                    }
                }
            }
        }else{
            bOk = true;
        }

        String mssg3 = "Val Bodega: " + bOk;
        logger.error(mssg3);

        BodegaExport valBodega = new BodegaExport();
        valBodega.setEstado(bOk);
        return valBodega;

        /*
        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

        JCoRepository repo = destination.getRepository();

        JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_READ_TABLE);
        JCoParameterList importx = stfcConnection.getImportParameterList();

        importx.setValue("DELIMITER","|");
        importx.setValue("QUERY_TABLE",Tablas.ZFLPTA);

        JCoParameterList tables = stfcConnection.getTableParameterList();
        JCoTable tableImport = tables.getTable("OPTIONS");
        tableImport.appendRow();

        tableImport.setValue("WA", "CDPTA = "+"'"+imports.getCodPlanta()+"'");

        stfcConnection.execute(destination);


        JCoTable tableExport = tables.getTable("DATA");
        JCoTable FIELDS = tables.getTable("FIELDS");


        BodegaExport me = new BodegaExport();
        String campo= metodo.ObtenerCampo(tableExport,FIELDS,"WERKS");

        if(campo != null && campo.length()>0){
            werks=campo; //0001
        }else{
            werks=imports.getCodPlanta();
            bOk=false;
        }


        JCoDestination destinations = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        ;
        JCoRepository repos = destinations.getRepository();
        JCoFunction stfcConnections = repos.getFunction(Constantes.ZFL_RFC_READ_TABLE);
        JCoParameterList importz = stfcConnections.getImportParameterList();

        importz.setValue("DELIMITER","|");
        importz.setValue("QUERY_TABLE",Tablas.ZFLEMB);

        JCoParameterList table = stfcConnections.getTableParameterList();
        JCoTable tableImports = table.getTable("OPTIONS");
        tableImports.appendRow();
        tableImports.setValue("WA", "CDEMB = "+"'"+imports.getCodEmba()+"'");

        stfcConnections.execute(destinations);


        JCoTable tableExports = table.getTable("DATA");
        JCoTable FIELD = table.getTable("FIELDS");

        String bodega= metodo.ObtenerCampo(tableExports,FIELD,"HPACH");


        if(!bodega.equalsIgnoreCase("S")){

            JCoDestination destination3 = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

            JCoRepository repos3 = destination3.getRepository();
            JCoFunction stfcConnections3 = repos3.getFunction(Constantes.ZFL_RFC_READ_TABLE);
            JCoParameterList importz3 = stfcConnections3.getImportParameterList();

            importz3.setValue("DELIMITER","|");
            importz3.setValue("QUERY_TABLE","ZTB_CONSTANTES");

            JCoParameterList table3 = stfcConnections.getTableParameterList();
            JCoTable tableImports3 = table3.getTable("OPTIONS");
            tableImports3.appendRow();
            tableImports3.setValue("WA","APLICACION = 'FL'");
            tableImports3.setValue("WA","AND PROGRAMA = 'ZFL_RFC_DISTR_FLOTA'");
            tableImports3.setValue("WA","AND CAMPO = 'WERKS'");
            JCoTable tableFields = table3.getTable("FIELDS");
            tableFields.appendRow();
            tableFields.setValue("FIELDNAME","LOW");

            stfcConnections3.execute(destination3);

            JCoTable tableExports3 = table3.getTable("DATA");

            for(int i=0;i<tableExports3.getNumRows();i++){
                tableExports3.setRow(i);
                String centroData = tableExports3.getString();

                String[] ArrayResponse = tableExports3.getString().split("\\|");

                if(ArrayResponse[0].equalsIgnoreCase(werks)){
                    bOk=false;
                    logger.error("GG",ArrayResponse[0]);
                    break;
                }
            }
        }else{
            bOk=true;
        }
        String log = "ValidarBodegaCert: " + bOk;
        logger.error(log);
        me.setEstado(bOk);
        return  me;*/
    }



    public ValidaMareaExports ValidarMarea(ValidaMareaImports imports)throws Exception{

        ValidaMareaExports vm= new ValidaMareaExports();
        try{
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_VAL_MAREA_PRODUCE);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_CODEMB", imports.getP_codemb());
            importx.setValue("P_CODPTA", imports.getP_codpta());

            JCoParameterList table = stfcConnection.getTableParameterList();
            JCoParameterList export = stfcConnection.getExportParameterList();

            stfcConnection.execute(destination);

            JCoTable T_MENSAJE=table.getTable(Tablas.T_MENSAJE);

            Metodos me=new Metodos();

            List<HashMap<String, Object>> t_mensaje=me.ListarObjetos(T_MENSAJE);



            vm.setP_correcto(export.getValue("P_CORRECTO").toString());
            vm.setP_primeracondicioncn(export.getValue("P_PRIMERACONDICIONCN").toString());
            vm.setP_segundacondicions(export.getValue("P_SEGUNDACONDICIONS").toString());
            vm.setT_mensaje(t_mensaje);
            vm.setMensaje("Ok");

        }catch (Exception e){
            vm.setMensaje(e.getMessage());
        }

        return vm;
    }

    public ConsultaReservaExport consultarReserva(ConsultaReservaImport cri){
        ConsultaReservaExport cre = new ConsultaReservaExport();
        try {
            int marea = cri.getMarea();
            String usuario = cri.getUsuario();
            String reserva = cri.getReserva();
            String flag = cri.getFlagDetalle();

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_GCOM_CONS_COMBU);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            JCoParameterList tables = stfcConnection.getTableParameterList();

            importx.setValue("P_USER", usuario);

            if(flag.equalsIgnoreCase("X")){
                importx.setValue("P_RESERVA", reserva);
                importx.setValue("P_NRMAR", marea);
            }else{
                JCoTable tableImport = tables.getTable("OPTIONS");
                tableImport.appendRow();
                String consulta = "NRMAR = " + marea + " AND ESRSV = 'S'";
                tableImport.setValue("DATA", consulta);
            }


            stfcConnection.execute(destination);
            JCoTable t_reservas = tables.getTable(Tablas.T_RESERVAS);
            JCoTable t_detalle = tables.getTable(Tablas.T_DETALLE);

            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> listaReservas = metodo.ListarObjetos(t_reservas);
            List<HashMap<String, Object>> listaDetalle = metodo.ListarObjetos(t_detalle);
            cre.setT_detalle(listaDetalle);
            cre.setT_reservas(listaReservas);

        }catch(Exception e){
            cre.setMensaje(e.getMessage());
        }
        return cre;
    }

    public ConfigReservas obtenerConfigReservas(String usuario){
        ConfigReservas cr = new ConfigReservas();
        try{
            String tabla = Tablas.ZFLCCC;
            String[] fields = {"BWART", "MATNR", "WERKS"};
            String wa = "CLSIS = '01'";
            List<MaestroOptionsKey> listOptKey1 = new ArrayList<MaestroOptionsKey>();
            MaestroOptions mo1 = new MaestroOptions();
            mo1.setWa(wa);
            List<MaestroOptions> listOptions1 = new ArrayList<MaestroOptions>();
            listOptions1.add(mo1);
            MaestroImportsKey imports1 = new MaestroImportsKey();
            imports1.setTabla(tabla);
            imports1.setDelimitador("|");
            imports1.setOption(listOptions1);
            imports1.setFields(fields);
            imports1.setOptions(listOptKey1);
            imports1.setOrder("");
            imports1.setRowcount(1);
            imports1.setRowskips(0);
            imports1.setP_user(usuario);
            MaestroExport me1 = MaestroService.obtenerMaestro2(imports1);
            if(me1.getData().size() > 0){
                List<HashMap<String, Object>> data = me1.getData();
                HashMap<String, Object> obj = data.get(0);
                Object objWerk = obj.get("WERKS");
                String strWerk = String.valueOf(objWerk);
                Object objBwart = obj.get("BWART");
                String strBwart = String.valueOf(objBwart);
                Object objMatnr = obj.get("MATNR");
                String strMatnr = String.valueOf(objMatnr);
                cr.setBWART(strBwart);
                cr.setMATNR(strMatnr);
                cr.setWERKS(strWerk);
                tabla = Tablas.ZV_FLAL;
                String[] fields1 = {"CDALE", "DSALM", "CDALM"};
                String wa1 = "ESREG = 'S'";
                String wa2 = "AND WERKS = '" + strWerk + "'";
                MaestroOptions mo2 = new MaestroOptions();
                mo2.setWa(wa1);
                MaestroOptions mo3 = new MaestroOptions();
                mo3.setWa(wa2);
                List<MaestroOptions> listOptions2 = new ArrayList<MaestroOptions>();
                listOptions2.add(mo2);
                listOptions2.add(mo3);
                imports1.setTabla(tabla);
                imports1.setOption(listOptions2);
                imports1.setFields(fields1);
                imports1.setRowcount(0);
                MaestroExport me2 = MaestroService.obtenerMaestro2(imports1);
                cr.setAlmacenes(me2.getData());
            }

        }catch(Exception e){
            cr.setMensaje(e.getMessage());
        }
        return cr;
    }

    public MaestroExport obtenerSuministros(SuministroImport si){
        MaestroExport me = new MaestroExport();
        try {
            String Usuario = si.getUsuario();
            String Material = si.getMaterial();
            String[] fields = {"CDSUM", "MAKTX", "CDUMD", "DSUMD"};
            String wa1 = "ESREG = 'S'";
            String wa2 = "AND MATNR = '" + Material + "'";
            MaestroOptions mo1 = new MaestroOptions();
            mo1.setWa(wa1);
            MaestroOptions mo2 = new MaestroOptions();
            mo2.setWa(wa2);
            List<MaestroOptions> listOptions = new ArrayList<MaestroOptions>();
            listOptions.add(mo1);
            listOptions.add(mo2);
            List<MaestroOptionsKey> listOptKey = new ArrayList<MaestroOptionsKey>();
            MaestroImportsKey imports = new MaestroImportsKey();
            imports.setTabla(Tablas.ZV_FLSM);
            imports.setDelimitador("|");
            imports.setOption(listOptions);
            String mssg = "FIELDS: " + fields.length;
            logger.error(mssg);
            imports.setFields(fields);
            imports.setOptions(listOptKey);
            imports.setOrder("");
            imports.setRowcount(1);
            imports.setRowskips(0);
            imports.setP_user(Usuario);
            MaestroExport me2 = MaestroService.obtenerMaestro2(imports);
            me.setFields(me2.getFields());
            me.setData(me2.getData());
            me.setMensaje(me2.getMensaje());
        }catch (Exception e){
            me.setMensaje(e.getMessage());
        }
        return  me;
    }

    @Override
    public CampoTablaExports reabrirMarea(ReabrirMareaImports imports) throws Exception {
        try {
            CampoTablaExports dto = new CampoTablaExports();

            CampoTablaImports params = new CampoTablaImports();
            params.setP_user(imports.getUser());


            SimpleDateFormat dtf = new SimpleDateFormat("yyyyMMdd HHmmss");
            dtf.setTimeZone(TimeZone.getTimeZone("America/Lima"));

            Date now = new Date();
            String dateTimeNow = dtf.format(now);
            String date = dateTimeNow.substring(0, 8);
            String time = dateTimeNow.substring(9);

            String setStr = "ATMOD = '" + imports.getUser() + "' ";
            setStr += "FCMOD = '" + date + "' ";
            setStr += "HRMOD = '" + time + "' ";
            setStr += "ESMAR = 'A'";

            String opt = "NRMAR = " + imports.getNrmar();

            List<SetDto> listSets = new ArrayList<>();
            SetDto set = new SetDto();
            set.setCmopt(opt);
            set.setCmset(setStr);
            set.setNmtab("ZFLMAR");

            listSets.add(set);
            params.setStr_set(listSets);

            dto = this.jcoCampoTablaService.Actualizar(params);

            return dto;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public AnularMareaExports anularMarea(AnularMareaImports imports) throws Exception {
        try {
            AnularMareaExports dto = new AnularMareaExports();

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_ANULA_MAREA);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_MAREA", imports.getP_marea());


            JCoParameterList tables = stfcConnection.getTableParameterList();
            stfcConnection.execute(destination);

            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);
            Metodos me = new Metodos();
            List<HashMap<String, Object>> t_mensaje = me.ListarObjetos(T_MENSAJE);

            dto.setT_mensaje(t_mensaje);
            dto.setMensaje("Ok");

            return dto;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public MaestroExport obtenerEmbaComb(EmbaCombImport imports) throws Exception{
        MaestroExport me = new MaestroExport();
        try{
            String cdemb = imports.getEmbarcacion();
            String usuario = imports.getUsuario();
            String[] fields = {"CVADM", "CPSDM", "CVPMS", "CPPMS", "CDTAN", "CDEMB", "MANDT"};
            String wa = "CDEMB EQ '" + cdemb + "'";
            MaestroOptions mo1 = new MaestroOptions();
            mo1.setWa(wa);
            List<MaestroOptions> listOptions = new ArrayList<MaestroOptions>();
            listOptions.add(mo1);
            List<MaestroOptionsKey> listOptKey = new ArrayList<MaestroOptionsKey>();
            MaestroImportsKey imports1 = new MaestroImportsKey();
            imports1.setTabla(Tablas.ZFLEMB);
            imports1.setDelimitador("|");
            imports1.setOption(listOptions);
            imports1.setFields(fields);
            imports1.setOptions(listOptKey);
            imports1.setOrder("");
            imports1.setRowcount(1);
            imports1.setRowskips(0);
            imports1.setP_user(usuario);
            me = MaestroService.obtenerMaestro2(imports1);
            if(me.getData().size() > 0){
                List<HashMap<String, Object>> data1 = me.getData();
                HashMap<String, Object> obj1 = data1.get(0);
                String[] fields1 = {"STCMB"};
                String wa1 = "CDEMB EQ '" + cdemb + "'";
                String wa2 = "AND (CDTEV EQ '1' OR CDTEV EQ '5' OR CDTEV EQ '6' OR CDTEV EQ 'H') ";
                String wa3 = "AND (STCMB IS NOT NULL OR STCMB > 0)";
                MaestroOptions mo2 = new MaestroOptions();
                mo2.setWa(wa1);
                MaestroOptions mo3 = new MaestroOptions();
                mo3.setWa(wa2);
                MaestroOptions mo4 = new MaestroOptions();
                mo4.setWa(wa3);
                List<MaestroOptions> listOptions1 = new ArrayList<MaestroOptions>();
                listOptions1.add(mo2);
                listOptions1.add(mo3);
                listOptions1.add(mo4);
                MaestroImportsKey imports2 = new MaestroImportsKey();
                imports2.setTabla(Tablas.ZV_FLCO);
                imports2.setDelimitador("|");
                imports2.setOption(listOptions1);
                imports2.setFields(fields1);
                imports2.setOptions(listOptKey);
                imports2.setOrder("NRMAR DESCENDING NREVN DESCENDING");
                imports2.setRowcount(1);
                imports2.setRowskips(0);
                imports2.setP_user(usuario);
                MaestroExport me1 = MaestroService.obtenerMaestro2(imports2);
                if(me1.getData().size() > 0){
                    List<HashMap<String, Object>> data = me1.getData();
                    HashMap<String, Object> obj = data.get(0);
                    Object objStcmb = obj.get("STCMB");
                    String strStcmb = String.valueOf(objStcmb);
                    obj1.put("STCMB", strStcmb);
                }
            }
        }catch (Exception e){
            me.setMensaje(e.getMessage());
        }
        return  me;
    }

    @Override
    public CrearReservaExport crearReserva(CrearReservaImport imports) throws Exception{
        CrearReservaExport cre = new CrearReservaExport();
        try{
            //Parametros
            String p_user = imports.getP_user();
            String p_cdemb = imports.getP_cdemb();
            String p_lgort = imports.getP_lgort();
            int p_nrevn = imports.getP_nrevn();
            String p_fhrsv = imports.getP_fhrsv();//'24092021'
            int year = Integer.parseInt(p_fhrsv.substring(4,8));
            int mes = Integer.parseInt(p_fhrsv.substring(2,4));
            int dia = Integer.parseInt(p_fhrsv.substring(0,2));
            /*String aniotest = "ANIO: " + year;
            String mestest = "MES: " + mes;
            String diatest = "DIA: " + dia;
            String dateTest = aniotest + " " + mestest + " " + diatest;
            logger.error(dateTest);*/
            /*Calendar c1 = Calendar.getInstance();
            c1.set(Calendar.MONTH, (mes - 1));
            c1.set(Calendar.YEAR, year);
            c1.set(Calendar.DATE, dia);*/

            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            Date dateFhrsv = sdf.parse(p_fhrsv);

            List<HashMap<String, Object>> str_rcb = imports.getStr_rcb();

            //RFC
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_CREAR_RESERV_COMBU);
            JCoParameterList tables = function.getTableParameterList();

            //imports
            HashMap<String, Object> importsSap = new HashMap<String, Object>();
            importsSap.put("P_USER", p_user);
            importsSap.put("P_CDEMB", p_cdemb);
            importsSap.put("P_LGORT", p_lgort);
            importsSap.put("P_NREVN", p_nrevn);
            importsSap.put("P_FHRSV", dateFhrsv);

            //send params
            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, importsSap);
            exec.setTable(tables, "STR_RCB", str_rcb);

            //exec rfc
            function.execute(destination);

            //get resultados
            JCoTable t_mensaje = tables.getTable(Tablas.T_MENSAJE);
            Metodos me = new Metodos();
            List<HashMap<String, Object>> mensajes = me.ListarObjetos(t_mensaje);
            JCoParameterList export = function.getExportParameterList();
            Object p_reserva = export.getValue("P_RESERVA");
            String strReserva = String.valueOf(p_reserva);
            cre.setT_mensaje(mensajes);
            cre.setP_reserva(strReserva);

        }catch (Exception e){
            cre.setMensaje(e.getMessage());
        }
        return  cre;
    }

    @Override
    public AnularRerservaExport anularReserva(AnularReservaImport imports) throws Exception{
        AnularRerservaExport are = new AnularRerservaExport();
        try{
            //parametros
            String p_user = imports.getP_user();
            List<HashMap<String, Object>> str_rsc = imports.getStr_rsc();

            //rfc
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_ANUL_RESERV_COMBUS);
            JCoParameterList tables = function.getTableParameterList();

            //imports
            HashMap<String, Object> importsSap = new HashMap<String, Object>();
            importsSap.put("P_USER", p_user);

            //send params
            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, importsSap);
            exec.setTable(tables, "STR_RSC", str_rsc);

            //exec rfc
            function.execute(destination);

            //get resultados
            JCoTable t_mensaje = tables.getTable(Tablas.T_MENSAJE);
            JCoTable tbl_str_rsc = tables.getTable(Tablas.STR_RSC);
            Metodos me = new Metodos();
            List<HashMap<String, Object>> mensajes = me.ListarObjetos(t_mensaje);
            List<HashMap<String, Object>> e_str_rsc = me.ListarObjetos(tbl_str_rsc);

            are.setT_mensaje(mensajes);
            are.setStr_rsc(e_str_rsc);

        }catch (Exception e){
            are.setMensaje(e.getMessage());
        }
        return are;
    }

    @Override
    public CrearVentaExport crearVenta(CrearVentaImport imports) throws Exception{
        CrearVentaExport cve = new CrearVentaExport();
        try{
            //DATOS
            String p_user = imports.getP_user();
            int p_nrmar = imports.getP_nrmar();
            String p_cdemb = imports.getP_cdemb();
            String p_werks = imports.getP_werks();
            String p_lgort = imports.getP_lgort();
            int p_nrevn = imports.getP_nrevn();
            String p_fhrsv = imports.getP_fhrsv();
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            Date dateFhrsv = sdf.parse(p_fhrsv);
            List<HashMap<String, Object>> str_rcb = imports.getStr_rcb();

            //rfc
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_CREAR_VENTA_COMBU);
            JCoParameterList tables = function.getTableParameterList();

            //imports
            HashMap<String, Object> importsSap = new HashMap<String, Object>();
            importsSap.put("P_USER", p_user);
            importsSap.put("P_NRMAR", p_nrmar);
            importsSap.put("P_CDEMB", p_cdemb);
            importsSap.put("P_WERKS", p_werks);
            importsSap.put("P_LGORT", p_lgort);
            importsSap.put("P_NREVN", p_nrevn);
            importsSap.put("P_FHRSV", dateFhrsv);

            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, importsSap);
            exec.setTable(tables, "STR_RCB", str_rcb);

            //exec rfc
            function.execute(destination);

            //get resultados
            JCoTable t_mensaje = tables.getTable(Tablas.T_MENSAJE);
            Metodos me = new Metodos();
            List<HashMap<String, Object>> mensajes = me.ListarObjetos(t_mensaje);
            JCoParameterList export = function.getExportParameterList();
            Object p_pedido = export.getValue("P_PEDIDO");
            String strPedido = String.valueOf(p_pedido);
            cve.setT_mensaje(mensajes);
            cve.setP_pedido(strPedido);

        }catch (Exception e){
            cve.setMensaje(e.getMessage());
        }
        return cve;
    }

    @Override
    public AnularVentaExport anularVenta(AnularVentaImport imports) throws Exception{
        AnularVentaExport ave = new AnularVentaExport();
        try{
            //rfc
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_ANULA_VENTA_COMBU);
            JCoParameterList tables = function.getTableParameterList();
            List<HashMap<String, Object>> ventas = imports.getP_ventas();
            List<HashMap<String, Object>> mensajesExport = new ArrayList<HashMap<String, Object>>();

            for (int i = 0; i < ventas.size(); i++){
                HashMap<String, Object> obj = ventas.get(i);
                String p_usuario = (String) obj.get("p_user");
                int p_nrmar = (int) obj.get("p_nrmar");
                String p_vbeln = (String) obj.get("p_vbeln");

                //imports
                HashMap<String, Object> importsSap = new HashMap<String, Object>();
                importsSap.put("P_USER", p_usuario);
                importsSap.put("P_NRMAR", p_nrmar);
                importsSap.put("P_VBELN", p_vbeln);

                EjecutarRFC exec = new EjecutarRFC();
                exec.setImports(function, importsSap);

                //exec rfc
                function.execute(destination);

                JCoTable t_mensaje = tables.getTable(Tablas.T_MENSAJE);
                Metodos me = new Metodos();
                List<HashMap<String, Object>> mensajes = me.ListarObjetos(t_mensaje);
                mensajesExport.addAll(mensajes);
            }

            if(mensajesExport.size() > 0){
                ave.setT_mensaje(mensajesExport);
            }
        }catch (Exception e){
            ave.setMensaje(e.getMessage());
        }
        return  ave;
    }



}
