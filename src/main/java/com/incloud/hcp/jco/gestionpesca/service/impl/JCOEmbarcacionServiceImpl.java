package com.incloud.hcp.jco.gestionpesca.service.impl;

import com.incloud.hcp.jco.gestionpesca.dto.*;
import com.incloud.hcp.jco.gestionpesca.service.JCOEmbarcacionService;
import com.incloud.hcp.jco.maestro.dto.EmbarcacionImports;
import com.incloud.hcp.jco.maestro.dto.EventosPescaExports;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MensajeDto;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
//@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class JCOEmbarcacionServiceImpl implements JCOEmbarcacionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
        importx.setValue("P_MAREA", marea.getP_marea());
        logger.error("ListarEventosPesca_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        stfcConnection.execute(destination);
        logger.error("ListarEventosPesca_5");

        JCoTable S_MAREA = tables.getTable("S_MAREA");
        logger.error("ListarEventosPesca_6");
        JCoTable S_EVENTO = tables.getTable("S_EVENTO");


        logger.error("ListarEventosPesca_7");

        Metodos metodo = new Metodos();
        List<HashMap<String, Object>> ListarS_MAREA= metodo.ListarObjetos(S_MAREA);
        List<HashMap<String, Object>> ListarS_EVENTO= metodo.ListarObjetos(S_EVENTO);


        MareaDto dto= new MareaDto();
        dto.setS_marea(ListarS_MAREA);
        dto.setS_evento(ListarS_EVENTO);

        dto.setMensaje("Ok");

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
    public MensajeDto crearMareaPropios(MarEventoDtoImport imports) throws Exception{

        MensajeDto msj= new MensajeDto();
        try{
            HashMap<String, Object> importsSap = new HashMap<String, Object>();
            importsSap.put("P_USER", imports.getP_user());
            importsSap.put("P_INDTR",imports.getP_indir());
            importsSap.put("P_NEWPR",imports.getP_newpr());
            importsSap.put("P_NAME1",imports.getP_name1());
            importsSap.put("P_STCD1",imports.getP_stcd1());
            importsSap.put("P_STRAS",imports.getP_stras());
            importsSap.put("P_ORTO2",imports.getP_orto2());
            importsSap.put("P_ORTO1",imports.getP_orto1());
            importsSap.put("P_REGIO",imports.getP_regio());
            importsSap.put("P_DSMMA",imports.getP_dsmma());


            List<HashMap<String, Object>> str_marea = imports.getStr_marea();
            List<HashMap<String, Object>> str_event = imports.getStr_evento();
            List<HashMap<String, Object>> str_horom = imports.getStr_horom();


            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction("ZFL_RFC_MAR_EVENT");
            JCoParameterList jcoTables = function.getTableParameterList();
            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, importsSap);
            exec.setTable(jcoTables, "STR_MAREA",str_marea );
            exec.setTable(jcoTables, "STR_EVENT", str_event);
            exec.setTable(jcoTables, "STR_HOROM", str_horom);

            function.execute(destination);

            JCoTable tableExport = jcoTables.getTable(Tablas.T_MENSAJE);

            for (int i = 0; i < tableExport.getNumRows(); i++) {
                tableExport.setRow(i);

                msj.setMANDT(tableExport.getString("MANDT"));
                msj.setCMIN(tableExport.getString("CMIN"));
                msj.setCDMIN(tableExport.getString("CDMIN"));
                msj.setDSMIN(tableExport.getString("DSMIN"));
                //lista.add(param);
            }

        }catch (Exception e){
            msj.setMANDT("00");
            msj.setCMIN("Error");
            msj.setCDMIN("Exception");
            msj.setDSMIN(e.getMessage());
        }
        return msj;
    }


}
