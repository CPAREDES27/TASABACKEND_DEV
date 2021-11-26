package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.CargaArchivoImports;
import com.incloud.hcp.jco.maestro.dto.CargaDescargaArchivosExports;
import com.incloud.hcp.jco.maestro.dto.CargaDescargaArchivosImports;
import com.incloud.hcp.jco.maestro.service.JCOCargaArchivosService;
import com.incloud.hcp.util.*;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class JCOCargaArchivosImpl implements JCOCargaArchivosService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Mensaje CargaArchivo(CargaArchivoImports importsParam) throws Exception {

        Mensaje msj= new Mensaje();

        try {

            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("P_USER", importsParam.getP_user());
            imports.put("P_CODE", importsParam.getP_code());
            imports.put("P_CHANGE", importsParam.getP_change());
            imports.put("P_VALIDA", importsParam.getP_valida());


            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_JOB_FUENT_EXTERN);
            JCoParameterList jcoTablesExport = function.getExportParameterList();

            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, imports);

            function.execute(destination);
           String value= jcoTablesExport.getValue(0).toString();
            msj.setMensaje(value);


        }catch (Exception e){
            msj.setMensaje(e.getMessage());
        }


        return msj;
    }

    @Override
    public CargaDescargaArchivosExports CargaDescargaArchivos(CargaDescargaArchivosImports importsParam) throws Exception {

        CargaDescargaArchivosExports cda=new CargaDescargaArchivosExports();


        try {
            logger.error("base64INI");


            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("I_TRAMA", importsParam.getI_trama());
            imports.put("I_DIRECTORIO", importsParam.getI_directorio());
            imports.put("I_FILENAME", importsParam.getI_filename());
            imports.put("I_ACCION", importsParam.getI_accion());
            imports.put("I_PROCESOBTP",importsParam.getI_procesobtp());
            imports.put("I_USER",importsParam.getI_user());

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_CRG_DESCRG_ARCH);
            JCoParameterList export = function.getExportParameterList();

            JCoParameterList tablas = function.getTableParameterList();

            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, imports);

            function.execute(destination);
            JCoTable T_MENSAJE= tablas.getTable(Tablas.T_MENSAJE);

            Metodos me=new Metodos();
            List<HashMap<String, Object>> t_mensaje= me.ListarObjetos(T_MENSAJE);

            cda.setT_mensaje(t_mensaje);
            cda.setE_trama(export.getValue(Tablas.E_TRAMA).toString());
            cda.setMensaje("Ok");

            logger.error("base64fin");
        }catch (Exception e){
            cda.setMensaje(e.getMessage());
        }


        return cda;
    }
}
