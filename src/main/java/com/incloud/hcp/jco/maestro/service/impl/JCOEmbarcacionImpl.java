package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.maestro.service.JCOEmbarcacionService;
import com.incloud.hcp.util.EjecutarRFC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JCOEmbarcacionImpl implements JCOEmbarcacionService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<EmbarcacionDto> obtenerEmbarcaciones(EmbarcacionImports importsParam)throws Exception{

        //setear mapeo de parametros import
        HashMap<String, Object> imports = new HashMap<String, Object>();
        imports.put("P_USER", importsParam.getP_user());
        logger.error("ObtenerEmbarcaciones_1");
        //setear mapeo de tabla options
        List<MaestroOptions> options = importsParam.getOptions();
        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
        for(int i = 0; i < options.size(); i++){
            MaestroOptions mo = options.get(i);
            HashMap<String, Object> record = new HashMap<String, Object>();
            record.put("WA", mo.getWa());
            tmpOptions.add(record);
        }
        logger.error("ObtenerEmbarcaciones_2");
        List<MaestroOptions> options2 = importsParam.getOptions2();
        List<HashMap<String, Object>> tmpOptions2 = new ArrayList<HashMap<String, Object>>();
        for(int i = 0; i < options2.size(); i++){
            MaestroOptions mo = options2.get(i);
            HashMap<String, Object> record2 = new HashMap<String, Object>();
            record2.put("WA", mo.getWa());
            tmpOptions2.add(record2);
        }
        logger.error("ObtenerEmbarcaciones_3");
        //ejecutar RFC ZFL_RFC_READ_TABLE
        EjecutarRFC exec = new EjecutarRFC();
        logger.error("ObtenerEmbarcaciones_4");
        List<EmbarcacionDto> ListaEmb =  exec.Execute_ZFL_RFC_CONS_EMBARCA(imports, tmpOptions, tmpOptions2);

        logger.error("ObtenerEmbarcaciones_5");
        return ListaEmb;
    }

    @Override
      public List<BusquedaEmbarcacionDto> busquedaEmbarcaciones(BusquedaEmbarcacionImports importsParam)throws Exception{

        //setear mapeo de parametros import
        HashMap<String, Object> imports = new HashMap<String, Object>();
        imports.put("P_USER", importsParam.getP_user());
        imports.put("ROWCOUNT", importsParam.getRowcount());

        logger.error("ObtenerEmbarcaciones_1");
        //setear mapeo de tabla options
        List<MaestroOptions> options = importsParam.getOptions();
        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
        for(int i = 0; i < options.size(); i++){
            MaestroOptions mo = options.get(i);
            HashMap<String, Object> record = new HashMap<String, Object>();
            record.put("WA", mo.getWa());
            tmpOptions.add(record);
        }


        logger.error("ObtenerEmbarcaciones_3");
        //ejecutar RFC ZFL_RFC_READ_TABLE
        EjecutarRFC exec = new EjecutarRFC();
        logger.error("ObtenerEmbarcaciones_4");
        List<BusquedaEmbarcacionDto> ListaEmb =  exec.Excute_ZFL_RFC_LECT_MAES_EMBAR(imports, tmpOptions);





        logger.error("ObtenerEmbarcaciones_5");
        return ListaEmb;

    }

}
