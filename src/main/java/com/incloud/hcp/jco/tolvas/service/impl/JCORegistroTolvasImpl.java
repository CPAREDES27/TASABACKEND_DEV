package com.incloud.hcp.jco.tolvas.service.impl;

import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.jco.tolvas.dto.RegistroTolvasImports;
import com.incloud.hcp.jco.tolvas.service.JCORegistroTolvasService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JCORegistroTolvasImpl implements JCORegistroTolvasService {

    public MaestroExport Guardar(RegistroTolvasImports imports)throws Exception{

        MaestroExport m= new MaestroExport();
        Metodos metodo = new Metodos();
        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_SEGUI_DESCA);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            importx.setValue("ROWCOUNT", imports.getRowcount());

            List<MaestroOptions> option = imports.getP_option();
            List<MaestroOptionsKey> options2 = imports.getP_options();


            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            tmpOptions=metodo.ValidarOptions(option,options2);

            JCoParameterList tables = stfcConnection.getTableParameterList();
            EjecutarRFC exec=new EjecutarRFC();
            exec.setTable(tables, Tablas.P_OPTIONS, tmpOptions);
            stfcConnection.execute(destination);
            JCoTable STR_SDE = tables.getTable(Tablas.STR_SDE);


            List<HashMap<String, Object>> str_sde = metodo.ObtenerListObjetos(STR_SDE, imports.getFields());

            m.setData(str_sde);
            m.setMensaje("Ok");

        }catch (Exception e){
            m .setMensaje(e.getMessage());
        }
        return m;
    }

}
