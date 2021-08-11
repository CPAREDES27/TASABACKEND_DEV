package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.maestro.dto.MaestroImports;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.service.JCOMaestrosService;
import com.incloud.hcp.util.EjecutarRFC;
import org.springframework.stereotype.Service;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JCOMaestrosServiceImpl implements JCOMaestrosService {

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

        //setear mapeo de tabla options
        List<MaestroOptions> options = importsParam.getOptions();
        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
        for(int i = 0; i < options.size(); i++){
            MaestroOptions mo = options.get(i);
            HashMap<String, Object> record = new HashMap<String, Object>();
            record.put("WA", mo.getWa());
            tmpOptions.add(record);
        }

        //ejecutar RFC ZFL_RFC_READ_TABLE
        EjecutarRFC exec = new EjecutarRFC();
        MaestroExport me =  exec.Execute_ZFL_RFC_READ_TABLE(imports, tmpOptions);
        return me;
    }


}
