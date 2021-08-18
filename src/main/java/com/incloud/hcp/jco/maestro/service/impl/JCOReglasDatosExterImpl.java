package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.MensajeDto;
import com.incloud.hcp.jco.maestro.dto.ReglasDatosExterDto;
import com.incloud.hcp.jco.maestro.dto.ReglasDatosExterEditImports;
import com.incloud.hcp.jco.maestro.dto.ReglasDatosExterNuevImports;
import com.incloud.hcp.jco.maestro.service.JCOReglasDatosExterService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JCOReglasDatosExterImpl implements JCOReglasDatosExterService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public  List<MensajeDto> Crear(ReglasDatosExterNuevImports importsParam)throws Exception{

        ReglasDatosExterDto rde= importsParam.getParams();
        logger.error("reglasdatosexternos_1");
        HashMap<String, Object> imports = new HashMap<String, Object>();
        imports.put("P_CLAVE", rde.getP_clave());
        imports.put("P_DSCRP", rde.getP_dscrp());
        imports.put("P_UBAPS", rde.getP_ubaps());
        imports.put("P_PCPDA", rde.getP_pcpda());
        imports.put("P_ESREG", rde.getP_esreg());
        imports.put("P_NMPRG", rde.getP_nmprg());
        imports.put("P_INGMA", rde.getP_ingma());
        imports.put("P_CASE", rde.getP_case());
        imports.put("P_USER", rde.getP_user());

        List<HashMap<String, Object>> s_insert=importsParam.getS_insert();
        List<HashMap<String, Object>> s_eod=importsParam.getS_eod();
        logger.error("reglasdatosexternos_2");


        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_FUEN_EXTER);
        JCoParameterList jcoTables = function.getTableParameterList();
        logger.error("reglasdatosexternos_3");

        EjecutarRFC exec=new EjecutarRFC();
        exec.setImports(function, imports);
        logger.error("reglasdatosexternos_4");

        exec.setTable(jcoTables, Tablas.S_INSERT,s_insert);
        exec.setTable(jcoTables,Tablas.S_EOD,s_eod);
        logger.error("reglasdatosexternos_5");

        function.execute(destination);

        JCoTable tableExport = jcoTables.getTable(Tablas.T_MENSAJE);
        logger.error("reglasdatosexternos_6");
        List<MensajeDto> ListMensajes= new ArrayList<MensajeDto>();
        MensajeDto msj= new MensajeDto();
        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);

            msj.setMANDT(tableExport.getString("MANDT"));
            msj.setCMIN(tableExport.getString("CMIN"));
            msj.setCDMIN(tableExport.getString("CDMIN"));
            msj.setDSMIN(tableExport.getString("DSMIN"));
            ListMensajes.add(msj);
        }
        logger.error("reglasdatosexternos_7");

        return ListMensajes;
    }

    public  List<MensajeDto> Editar(ReglasDatosExterEditImports importsParam)throws Exception{

        ReglasDatosExterDto rde= importsParam.getParams();
        logger.error("reglasdatosexternos_1");

        HashMap<String, Object> imports = new HashMap<String, Object>();
        imports.put("P_CLAVE", rde.getP_clave());
        imports.put("P_DSCRP", rde.getP_dscrp());
        imports.put("P_UBAPS", rde.getP_ubaps());
        imports.put("P_PCPDA", rde.getP_pcpda());
        imports.put("P_ESREG", rde.getP_esreg());
        imports.put("P_NMPRG", rde.getP_nmprg());
        imports.put("P_INGMA", rde.getP_ingma());
        imports.put("P_CASE", rde.getP_case());
        imports.put("P_USER", rde.getP_user());

        List<HashMap<String, Object>> s_update=importsParam.getS_update();
        List<HashMap<String, Object>> s_eod=importsParam.getS_eod();
        logger.error("reglasdatosexternos_2");

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_FUEN_EXTER);
        JCoParameterList jcoTables = function.getTableParameterList();
        logger.error("reglasdatosexternos_3");

        EjecutarRFC exec=new EjecutarRFC();
        exec.setImports(function, imports);
        logger.error("reglasdatosexternos_4");

        exec.setTable(jcoTables, Tablas.S_UPDATE,s_update);
        exec.setTable(jcoTables,Tablas.S_EOD,s_eod);
        logger.error("reglasdatosexternos_5");

        function.execute(destination);
        logger.error("reglasdatosexternos_6");
        JCoTable tableExport = jcoTables.getTable(Tablas.T_MENSAJE);
        logger.error(tableExport.getString());

        List<MensajeDto> ListMensajes= new ArrayList<MensajeDto>();

        MensajeDto msj= new MensajeDto();
        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);

            msj.setMANDT(tableExport.getString("MANDT"));
            msj.setCMIN(tableExport.getString("CMIN"));
            msj.setCDMIN(tableExport.getString("CDMIN"));
            msj.setDSMIN(tableExport.getString("DSMIN"));
            ListMensajes.add(msj);
        }
        logger.error("reglasdatosexternos_7");

        return ListMensajes;
    }

}
