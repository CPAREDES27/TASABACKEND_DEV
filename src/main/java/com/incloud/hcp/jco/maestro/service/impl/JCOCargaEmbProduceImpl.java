package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.CargaEmbProduceExports;
import com.incloud.hcp.jco.maestro.dto.CargaEmbProduceImports;
import com.incloud.hcp.jco.maestro.service.JCOCargaEmbProduceService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class JCOCargaEmbProduceImpl implements JCOCargaEmbProduceService {

    public CargaEmbProduceExports CargaEmbarcaciones(CargaEmbProduceImports importsParams)throws Exception{

        CargaEmbProduceExports dto= new CargaEmbProduceExports();

        try {

            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("P_TOPE", importsParams.getP_tope());

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_IMPO_PRODUCE);
            JCoParameterList export = function.getExportParameterList();

            EjecutarRFC exec = new EjecutarRFC();
            exec.setImports(function, imports);
            exec.setTable(export,Tablas.IT_ZFLEMB,importsParams.getIt_zflemb());

            function.execute(destination);

            JCoTable ET_ZFLEMB = export.getTable(Tablas.ET_ZFLEMB);

            Metodos me= new Metodos();
            List<HashMap<String, Object>> et_zflemb=me.ObtenerListObjetos(ET_ZFLEMB, importsParams.getFields_itzflemb());


            dto.setEt_zflemb(et_zflemb);
            dto.setMensaje("Ok");


        }catch (Exception e){
            dto.setMensaje(e.getMessage());
        }


        return dto;
    }
}
