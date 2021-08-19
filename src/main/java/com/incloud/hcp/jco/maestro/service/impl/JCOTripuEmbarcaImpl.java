package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.MensajeDto;
import com.incloud.hcp.jco.maestro.dto.TripuEmbarcaImports;
import com.incloud.hcp.jco.maestro.service.JCOTripEmbarcService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Mensaje;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class JCOTripuEmbarcaImpl implements JCOTripEmbarcService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Mensaje EditarTripuEmbarca(TripuEmbarcaImports impor)throws Exception{

        HashMap<String, Object> imports = new HashMap<String, Object>();
        imports.put("P_USER", impor.getP_user());

        logger.error("ListarEventosPesca_1");;
        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_TRIPU_EMBAR);
        JCoParameterList jcoTables = function.getTableParameterList();

        logger.error("ListarEventosPesca_4");;

        List<HashMap<String, Object>> str_emb=impor.getStr_emb();

        EjecutarRFC exec= new EjecutarRFC();
        exec.setImports(function, imports);
        exec.setTable(jcoTables,Tablas.STR_EMB,str_emb);

        function.execute(destination);

        Mensaje msje= new Mensaje();
        msje.setMensaje("Ok");

        return msje;
    }
}
