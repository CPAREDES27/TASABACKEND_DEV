package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.maestro.service.JCOEmpresaService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JCOEmpresaImpl implements JCOEmpresaService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());



    public MaestroExport ListarEmpresas(EmpresaImports imports)throws Exception{

        MaestroExport me=new MaestroExport();
        try {
            logger.error("obtenerEmpresa_1");
            ;
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            //JCo
            logger.error("obtenerEmpresa_2");
            ;
            JCoRepository repo = destination.getRepository();
            logger.error("obtenerEmpresa_3");
            ;
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_CONS_EMPRESAS);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_CDUSR", imports.getP_cdusr());
            importx.setValue("P_RUC", imports.getP_ruc());
            logger.error("obtenerEmpresa_4");
            ;

            JCoParameterList tables = stfcConnection.getTableParameterList();

            List<MaestroOptions> options = imports.getOptions();
            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < options.size(); i++) {
                MaestroOptions mo = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();
                record.put("WA", mo.getWa());
                tmpOptions.add(record);
            }

            EjecutarRFC exe = new EjecutarRFC();
            exe.setTable(tables, "P_OPTIONS", tmpOptions);

            logger.error("obtenerEmpresa_5");
            ;

            stfcConnection.execute(destination);
            logger.error("obtenerEmpresa_6");


            JCoTable tableExport = tables.getTable("STR_EMP");

            logger.error("obtenerEmpresa_7");

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
