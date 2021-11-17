package com.incloud.hcp.jco.tolvas.service.impl;

import com.incloud.hcp.jco.tolvas.dto.IngresoDesManualExports;
import com.incloud.hcp.jco.tolvas.dto.IngresoDescManualImports;
import com.incloud.hcp.jco.tolvas.service.JCOIngresoDescManualService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class JCOIngresoDescManualImpl implements JCOIngresoDescManualService {

    public IngresoDesManualExports Guardar(IngresoDescManualImports imports)throws Exception{

        IngresoDesManualExports idm= new IngresoDesManualExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_SAVE_DESC_MANUAL);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());


            JCoParameterList tables = stfcConnection.getTableParameterList();
            EjecutarRFC exec=new EjecutarRFC();

            List<HashMap<String, Object>> data = imports.getStr_des();
            for (int i = 0; i < data.size(); i++){
                HashMap<String, Object> record = data.get(i);
                Iterator iterator = record.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry tmpImport = (Map.Entry) iterator.next();
                    String key = tmpImport.getKey().toString();
                    Object value = tmpImport.getValue();
                    if (key.equalsIgnoreCase("HIDES") || key.equalsIgnoreCase("HFDES")) {
                        DateFormat formatter = new SimpleDateFormat("HH:mm");
                        Time timeValue = new Time(formatter.parse(String.valueOf(value)).getTime());
                        record.put(key, timeValue);
                    }
                }
            }

            exec.setTable(tables, Tablas.STR_DES, imports.getStr_des());
            stfcConnection.execute(destination);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);

            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> t_mensaje = metodo.ObtenerListObjetos(T_MENSAJE, imports.getFieldst_mensaje());

            idm.setT_mensaje(t_mensaje);
            idm.setMensaje("Ok");

        }catch (Exception e){
            idm .setMensaje(e.getMessage());
        }
        return idm;
    }

}