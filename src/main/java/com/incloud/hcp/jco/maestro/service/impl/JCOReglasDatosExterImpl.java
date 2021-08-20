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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JCOReglasDatosExterImpl implements JCOReglasDatosExterService {

    public List<MensajeDto> Crear(ReglasDatosExterNuevImports importsParam)throws Exception{
        List<MensajeDto> ListMensajes = new ArrayList<MensajeDto>();
        MensajeDto msj = new MensajeDto();

        try {
            ReglasDatosExterDto rde = importsParam.getParams();

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

            List<HashMap<String, Object>> s_insert = importsParam.getS_insert();
            List<HashMap<String, Object>> s_eod = importsParam.getS_eod();


            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_FUEN_EXTER);
            JCoParameterList jcoTables = function.getTableParameterList();

            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, imports);
            exec.setTable(jcoTables, Tablas.S_INSERT, s_insert);
            exec.setTable(jcoTables, Tablas.S_EOD, s_eod);
            function.execute(destination);

            JCoTable tableExport = jcoTables.getTable(Tablas.T_MENSAJE);


            for (int i = 0; i < tableExport.getNumRows(); i++) {
                tableExport.setRow(i);

                msj.setMANDT(tableExport.getString("MANDT"));
                msj.setCMIN(tableExport.getString("CMIN"));
                msj.setCDMIN(tableExport.getString("CDMIN"));
                msj.setDSMIN(tableExport.getString("DSMIN"));
                ListMensajes.add(msj);
            }
        }catch (Exception e){

            msj.setMANDT("00");
            msj.setCMIN("Error");
            msj.setCDMIN("Exception");
            msj.setDSMIN(e.getMessage());
        }
        return ListMensajes;
    }

    public List<MensajeDto> Editar(ReglasDatosExterEditImports importsParam)throws Exception{

        List<MensajeDto> ListMensajes= new ArrayList<MensajeDto>();
        MensajeDto msj= new MensajeDto();
        try{
            ReglasDatosExterDto rde= importsParam.getParams();

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

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_FUEN_EXTER);
            JCoParameterList jcoTables = function.getTableParameterList();

            EjecutarRFC exec=new EjecutarRFC();
            exec.setImports(function, imports);
            exec.setTable(jcoTables, Tablas.S_UPDATE,s_update);
            exec.setTable(jcoTables,Tablas.S_EOD,s_eod);

            function.execute(destination);

            JCoTable tableExport = jcoTables.getTable(Tablas.T_MENSAJE);


            for (int i = 0; i < tableExport.getNumRows(); i++) {
                tableExport.setRow(i);

                msj.setMANDT(tableExport.getString("MANDT"));
                msj.setCMIN(tableExport.getString("CMIN"));
                msj.setCDMIN(tableExport.getString("CDMIN"));
                msj.setDSMIN(tableExport.getString("DSMIN"));
                ListMensajes.add(msj);
            }
        }catch (Exception e){

            msj.setMANDT("00");
            msj.setCMIN("Error");
            msj.setCDMIN("Exception");
            msj.setDSMIN(e.getMessage());
            ListMensajes.add(msj);
        }

        return ListMensajes;
    }

}
