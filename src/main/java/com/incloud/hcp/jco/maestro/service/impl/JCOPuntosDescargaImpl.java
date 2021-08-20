package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.maestro.service.JCOPuntosDescargaService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.incloud.hcp.util.UsuarioDto;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JCOPuntosDescargaImpl implements JCOPuntosDescargaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());



    public MaestroExport ListarPuntosDes(UsuarioDto imports)throws Exception{

        MaestroExport me=new MaestroExport();
        try {
            logger.error("obtenerPuntosDescarga_1");
            ;
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            //JCo
            logger.error("obtenerPuntosDescarga_2");
            ;
            JCoRepository repo = destination.getRepository();
            logger.error("obtenerPuntosDescarga_3");
            ;
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_MAES_PUNT_DESCA);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
            importx.setValue("P_USER", imports.getP_user());
            logger.error("obtenerPuntosDescarga_4");
            ;
            JCoParameterList tables = stfcConnection.getTableParameterList();

            logger.error("obtenerPuntosDescarga_5");

            stfcConnection.execute(destination);
            logger.error("obtenerPuntosDescarga_6");

            JCoTable tableExport = tables.getTable(Tablas.ST_PDG);
            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> data = metodo.ListarObjetos(tableExport);


            me.setData(data);
            me.setMensaje("Ok");
        }catch (Exception e){
            me.setMensaje(e.getMessage());
        }
        return me;
    }


}
