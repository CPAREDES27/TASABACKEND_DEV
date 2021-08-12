package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.EmpresaDto;
import com.incloud.hcp.jco.maestro.dto.EmpresaImports;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.service.JCOEmpresaService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
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

    public EmpresaDto obtenerEmpresa(EmpresaImports imports)throws Exception{

        logger.error("obtenerEmpresa_1");;
        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        //JCo
        logger.error("obtenerEmpresa_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("obtenerEmpresa_3");;
        JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_CONS_EMPRESAS);
        JCoParameterList importx = stfcConnection.getImportParameterList();
        importx.setValue("P_CDUSR", imports.getP_cdusr());
        importx.setValue("P_RUC", imports.getP_ruc());
        logger.error("obtenerEmpresa_4");;

        JCoParameterList tables = stfcConnection.getTableParameterList();

        List<MaestroOptions> options = imports.getOptions();
        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
        for(int i = 0; i < options.size(); i++){
            MaestroOptions mo = options.get(i);
            HashMap<String, Object> record = new HashMap<String, Object>();
            record.put("WA", mo.getWa());
            tmpOptions.add(record);
        }

        EjecutarRFC exe= new EjecutarRFC();
        exe.setTable(tables, "P_OPTIONS", tmpOptions);


        logger.error("obtenerEmpresa_5");;
        //tableImport.setValue("WA", condicion);
        //Ejecutar Funcion
        stfcConnection.execute(destination);
        logger.error("obtenerEmpresa_6");
        //DestinationAcce


        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable("STR_EMP");
        EmpresaDto dto = new EmpresaDto();
        logger.error("obtenerEmpresa_7");
        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);


            dto.setCDGRE(tableExport.getString("CDGRE"));
            dto.setCDEMP(tableExport.getString("CDEMP"));
            dto.setDSEMP(tableExport.getString("DSEMP"));
            dto.setINPRP(tableExport.getString("INPRP"));
            dto.setESREG(tableExport.getString("ESREG"));
            dto.setLIFNR(tableExport.getString("LIFNR"));
            dto.setRUCPRO(tableExport.getString("RUCPRO"));
            dto.setKUNNR(tableExport.getString("KUNNR"));
            dto.setRUCLIE(tableExport.getString("RUCLIE"));

        }
        logger.error("obtenerEmpresa_7");



        return dto;
    }
}
