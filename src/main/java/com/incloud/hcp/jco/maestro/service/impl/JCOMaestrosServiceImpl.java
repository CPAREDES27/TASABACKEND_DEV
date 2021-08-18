package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.maestro.service.JCOMaestrosService;
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
public class JCOMaestrosServiceImpl implements JCOMaestrosService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public MaestroExport obtenerMaestro (MaestroImports importsParam) throws Exception {
        //setear mapeo de parametros import
        HashMap<String, Object> imports = new HashMap<String, Object>();
        imports.put("QUERY_TABLE", importsParam.getTabla());
        imports.put("DELIMITER", importsParam.getDelimitador());
        imports.put("NO_DATA", importsParam.getNo_data());
        imports.put("ROWSKIPS", importsParam.getRowskips());
        imports.put("ROWCOUNT", importsParam.getRowcount());
        imports.put("P_USER", importsParam.getP_user());
        imports.put("P_ORDER", importsParam.getOrder());
        logger.error("obtenerMaestro_1");
        //setear mapeo de tabla options
        List<MaestroOptions> options = importsParam.getOptions();
        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
        for(int i = 0; i < options.size(); i++){
            MaestroOptions mo = options.get(i);
            HashMap<String, Object> record = new HashMap<String, Object>();
            record.put("WA", mo.getWa());
            tmpOptions.add(record);
        }
        logger.error("obtenerMaestro_2");

        //ejecutar RFC ZFL_RFC_READ_TABLE
        EjecutarRFC exec = new EjecutarRFC();
        MaestroExport me =  exec.Execute_ZFL_RFC_READ_TABLE(imports, tmpOptions);
        return me;
    }

    public MensajeDto editarMaestro (MaestroEditImports importsParam) throws Exception{
        //setear mapeo de parametros import
        HashMap<String, Object> imports = new HashMap<String, Object>();
        imports.put("I_TABLE", importsParam.getTabla());
        imports.put("P_FLAG", importsParam.getFlag());
        imports.put("P_CASE", importsParam.getP_case());
        imports.put("P_USER", importsParam.getP_user());

        logger.error("EditarMaestro_1");
        //ejecutar RFC ZFL_RFC_READ_TABLE
        EjecutarRFC exec = new EjecutarRFC();
        logger.error("EditarMaestro_2");
        MensajeDto msj =  exec.Execute_ZFL_RFC_UPDATE_TABLE(imports, importsParam.getData());
        logger.error("EditarMaestro_3");
        return msj;

    }





}
