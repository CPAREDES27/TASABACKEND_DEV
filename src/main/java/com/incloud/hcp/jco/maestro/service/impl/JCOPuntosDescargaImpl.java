package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.maestro.dto.PuntosDescargaImports;
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



    public MaestroExport ListarPuntosDes(PuntosDescargaImports imports)throws Exception{

        MaestroExport me=new MaestroExport();
        try {
            ;
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            //JCo
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_MAES_PUNT_DESCA);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            JCoParameterList tables = stfcConnection.getTableParameterList();

            stfcConnection.execute(destination);

            JCoTable tableExport = tables.getTable(Tablas.ST_PDG);
            Metodos metodo = new Metodos();
            //List<HashMap<String, Object>> data = metodo.ListarObjetos(tableExport);
            List<HashMap<String, Object>> data = metodo.ObtenerListObjetos(tableExport, imports.getFields());


            me.setData(data);
            me.setMensaje("Ok");
        }catch (Exception e){
            me.setMensaje(e.getMessage());
        }
        return me;
    }


}
