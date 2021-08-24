package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.CargaArchivoImports;
import com.incloud.hcp.jco.maestro.service.JCOCargaArchivosService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Mensaje;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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
            JCoParameterList jcoTables = function.getTableParameterList();
            logger.error("getTableParameterList "+function.getTableParameterList());
            logger.error("getMetaData "+ jcoTables.getMetaData());
            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, imports);

            function.execute(destination);

            JCoTable tableExport = jcoTables.getTable(Tablas.W_MENSAJE);

            for (int i = 0; i < tableExport.getNumRows(); i++) {
                tableExport.setRow(i);
                msj.setMensaje(tableExport.getString());
                logger.error("table export carga archvos: "+ tableExport.getString());
                logger.error("getFieldIterator: "+ tableExport.getFieldIterator());

            }

        }catch (Exception e){
            msj.setMensaje(e.getMessage());
        }


        return msj;
    }
}
