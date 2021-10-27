package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.DeleteRecordExports;
import com.incloud.hcp.jco.maestro.dto.DeleteRecordImports;
import com.incloud.hcp.jco.maestro.service.JCODeleteRecordService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class JCODeleteRecordImpl implements JCODeleteRecordService {

    public DeleteRecordExports BorrarRegistro(DeleteRecordImports imports)throws Exception{

        DeleteRecordExports dto= new DeleteRecordExports();

        try{
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_DELETE_RECORDS);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("I_TABLE", imports.getI_table());
            importx.setValue("P_USER", imports.getP_user());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            JCoTable T_DATA = tables.getTable(Tablas.T_DATA);
            T_DATA.appendRow();
            T_DATA.setValue("DATA",imports.getT_data());

            stfcConnection.execute(destination);

            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);

            Metodos me=new Metodos();
            List<HashMap<String, Object>> t_mensaje=me.ListarObjetos(T_MENSAJE);

            dto.setT_mensaje(t_mensaje);
            dto.setMensaje("Ok");

        }catch (Exception e){

            dto.setMensaje(e.getMessage());
        }

        return dto;
    }

}
