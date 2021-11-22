package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.maestro.service.JCOEmbarcacionService;
import com.incloud.hcp.jco.maestro.service.RFCCompartidos.ZFL_RFC_READ_TEABLEImplement;
import com.incloud.hcp.util.*;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JCOEmbarcacionImpl implements JCOEmbarcacionService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ZFL_RFC_READ_TEABLEImplement zfl_rfc_read_teableImplement;
    @Override
    public EmbarcacionExports ListarEmbarcaciones(EmbarcacionImports importsParam) throws Exception {
        EmbarcacionExports dto = new EmbarcacionExports();
        Metodos me = new Metodos();
        try {
            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("P_USER", importsParam.getP_user());
            imports.put("P_PAG", importsParam.getP_pag());
            logger.error("ObtenerEmbarcaciones_1");
            //setear mapeo de tabla options
            List<MaestroOptions> option = importsParam.getOption();
            List<MaestroOptionsKey> options = importsParam.getOptions();


            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            tmpOptions=me.ValidarOptions(option,options);


            logger.error("ObtenerEmbarcaciones_2");
            List<MaestroOptions> option2 = importsParam.getOption2();
            List<MaestroOptionsKey> options2 = importsParam.getOptions2();


            List<HashMap<String, Object>> tmpOptions2 = new ArrayList<HashMap<String, Object>>();
            tmpOptions2=me.ValidarOptions(option2,options2);


            logger.error("ObtenerEmbarcaciones_3");
            //ejecutar RFC ZFL_RFC_READ_TABLE
            EjecutarRFC exec = new EjecutarRFC();
            logger.error("ObtenerEmbarcaciones_4");
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_CONS_EMBARCA_BTP);


            logger.error("Execute_ZFL_RFC_CONS_EMBARCA_1");
            exec.setImports(function, imports);
            logger.error("Execute_ZFL_RFC_CONS_EMBARCA_2");

            JCoParameterList jcoTables = function.getTableParameterList();
            JCoParameterList export = function.getExportParameterList();
            logger.error("Execute_ZFL_RFC_CONS_EMBARCA_3");

            exec.setTable(jcoTables, "P_OPTIONS", tmpOptions);
            exec.setTable(jcoTables, "P_OPTIONS2", tmpOptions2);
            logger.error("Execute_ZFL_RFC_CONS_EMBARCA_4");
            function.execute(destination);
            JCoTable tableExport = jcoTables.getTable(Tablas.STR_EMB);
            dto.setP_totalpag(export.getString("P_TOTALPAG"));

            List<HashMap<String, Object>> data = me.ListarObjetos(tableExport);
            logger.error("CONSULTA EMBARCA");


            dto.setData(data);
            dto.setMensaje("Ok");
        }catch (Exception e){
            dto.setMensaje(e.getMessage());
        }
        return dto;
    }
    public MaestroExport BuscarEmbarcaciones(BusquedaEmbarcacionImports importsParam)throws Exception{
        MaestroExport me = new MaestroExport();
        Metodos metodo=new Metodos();
        try {
            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("P_USER", importsParam.getP_user());
            imports.put("ROWCOUNT", importsParam.getRowcount());

            logger.error("ObtenerEmbarcaciones_1");
            //setear mapeo de tabla options
            List<MaestroOptions> option = importsParam.getOption();
            List<MaestroOptionsKey> options2 = importsParam.getOptions();


            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            tmpOptions=metodo.ValidarOptions(option,options2);



            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_LECT_MAES_EMBAR);
            JCoParameterList jcoTables = function.getTableParameterList();
            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, imports);

            exec.setTable(jcoTables, "OPTIONS", tmpOptions);
            function.execute(destination);
            JCoTable DATA = jcoTables.getTable(Tablas.S_DATA);


            Metodos m = new Metodos();
            List<HashMap<String, Object>> data = m.ListarObjetos(DATA);
            me.setData(data);
            me.setMensaje("Ok");
        }catch (Exception e){
            me.setMensaje(e.getMessage());
        }


        return me;
    }

    public BusqAdicEmbarExports BusquedaAdicionalEmbarca(BusqAdicEmbarImports importsParam) throws Exception{

        BusqAdicEmbarExports dto= new BusqAdicEmbarExports();
        try {
            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("P_USER", importsParam.getP_user());
            imports.put("P_CODE", importsParam.getP_code());

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_ADIC_MAES_EMBAR);
            JCoParameterList jcoTables = function.getTableParameterList();
            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, imports);

            function.execute(destination);
            JCoTable s_pe = jcoTables.getTable(Tablas.S_PE);
            JCoTable s_ps = jcoTables.getTable(Tablas.S_PS);
            JCoTable s_ee = jcoTables.getTable(Tablas.S_EE);
            JCoTable s_be = jcoTables.getTable(Tablas.S_BE);
            JCoTable str_hor = jcoTables.getTable(Tablas.STR_HOR);
            JCoTable t_mensaje = jcoTables.getTable(Tablas.T_MENSAJE);

            Metodos m = new Metodos();
            List<HashMap<String, Object>> S_PE = m.ListarObjetos(s_pe);
            List<HashMap<String, Object>> S_PS = m.ListarObjetos(s_ps);
            List<HashMap<String, Object>> S_EE = m.ListarObjetos(s_ee);
            List<HashMap<String, Object>> S_BE = m.ListarObjetos(s_be);
            List<HashMap<String, Object>> STR_HOR = m.ListarObjetos(str_hor);
            List<HashMap<String, Object>> T_MENSAJE = m.ListarObjetos(t_mensaje);

            dto.setT_mensaje(T_MENSAJE);
            dto.setStr_hor(STR_HOR);
            dto.setS_pe(S_PE);
            dto.setS_ps(S_PS);
            dto.setS_ee(S_EE);
            dto.setS_be(S_BE);
            dto.setMensaje("Ok");
        }catch (Exception e){
            dto.setMensaje(e.getMessage());
        }

        return dto;
    }

    public MensajeEmbarcaExport Nuevo(EmbarcacionNuevImports importsParam)throws Exception{

        MensajeEmbarcaExport msj= new MensajeEmbarcaExport();
        try {
            EmbarcaImports embarcaImports = importsParam.getParams();

            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("P_CASE", embarcaImports.getP_case());
            imports.put("P_CODE", embarcaImports.getP_code());
            imports.put("P_USER", embarcaImports.getP_user());

            List<HashMap<String, Object>> s_emb = importsParam.getS_emb();
            List<HashMap<String, Object>> s_ppe = importsParam.getS_ppe();
            List<HashMap<String, Object>> s_pec = importsParam.getS_pec();
            List<HashMap<String, Object>> s_epe = importsParam.getS_epe();
            List<HashMap<String, Object>> s_bpe = importsParam.getS_bpe();
            List<HashMap<String, Object>> str_hor = importsParam.getStr_hor();


            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_MANT_MAESTRO_EMB);
            JCoParameterList jcoTables = function.getTableParameterList();

            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, imports);
            exec.setTable(jcoTables, Tablas.S_EMB, s_emb);
            exec.setTable(jcoTables, Tablas.S_PPE, s_ppe);
            exec.setTable(jcoTables, Tablas.S_PEC, s_pec);
            exec.setTable(jcoTables, Tablas.S_EPE, s_epe);
            exec.setTable(jcoTables, Tablas.S_BPE, s_bpe);
            exec.setTable(jcoTables, Tablas.STR_HOR, str_hor);
            function.execute(destination);
            Metodos metodo = new Metodos();
            JCoTable tableExport = jcoTables.getTable(Tablas.T_MENSAJE);
            List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetos(tableExport);
            msj.setT_mensaje(t_mensaje);

        }catch (Exception e){
            msj.setMessage(e.toString());
        }

        return msj;
    }

    public Mensaje Editar(EmbarcacionEditImports importsParam)throws Exception{

        Mensaje msj= new Mensaje();
        try {
            EmbarcaImports embarcaImports = importsParam.getParams();

            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("P_CASE", embarcaImports.getP_case());
            imports.put("P_CODE", embarcaImports.getP_code());
            imports.put("P_USER", embarcaImports.getP_user());

            List<HashMap<String, Object>> s_emb = importsParam.getS_emb();
            List<HashMap<String, Object>> s_ppe = importsParam.getS_ppe();
            List<HashMap<String, Object>> s_pec = importsParam.getS_pec();
            List<HashMap<String, Object>> str_hor = importsParam.getStr_hor();


            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_MANT_MAESTRO_EMB);
            JCoParameterList jcoTables = function.getTableParameterList();

            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, imports);
            exec.setTable(jcoTables, Tablas.S_EMB, s_emb);
            exec.setTable(jcoTables, Tablas.S_PPE, s_ppe);
            exec.setTable(jcoTables, Tablas.S_PEC, s_pec);
            exec.setTable(jcoTables, Tablas.STR_HOR, str_hor);
            function.execute(destination);

            msj.setMensaje("Ok");

        }catch (Exception e){
            msj.setMensaje(e.getMessage());
        }

        return msj;
    }


    public MensajeDto MoverEmbarcacion(MoverEmbarcaImports importsParam)throws Exception{

        MensajeDto msj=new MensajeDto();
        try {


            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("P_USER", importsParam.getP_user());
            imports.put("P_CDPTA", importsParam.getP_cdtpa());


            List<HashMap<String, Object>> data = importsParam.getData();


            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_MUEVE_EMBARCA);
            JCoParameterList jcoTables = function.getTableParameterList();

            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, imports);
            exec.setTable(jcoTables, Tablas.STR_DSF, data);
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

            if(msj.getMANDT()==null && msj.getCMIN()==null &&
                    msj.getCDMIN()==null && msj.getDSMIN()==null ){
                msj.setCMIN("200");
                msj.setDSMIN("ok");
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
