package com.incloud.hcp.jco.gestionpesca.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incloud.hcp.jco.gestionpesca.dto.Options;
import com.incloud.hcp.jco.gestionpesca.dto.PlantasDto;
import com.incloud.hcp.jco.gestionpesca.dto.TipoEmbarcacionDto;
import com.incloud.hcp.jco.gestionpesca.service.JCOTipoEmbarcacionService;
import com.incloud.hcp.jco.maestro.service.RFCCompartidos.ZFL_RFC_READ_TEABLEImplement;
import com.sap.conn.jco.*;
import org.checkerframework.checker.nullness.Opt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JCOTipoEmbarcacionImplement implements JCOTipoEmbarcacionService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ZFL_RFC_READ_TEABLEImplement zfl_rfc_read_teableImplement;

    private Options options;

    public List<TipoEmbarcacionDto> listaTipoEmbarcacion(List<Options> options) throws Exception {
        //Parametro dto = new Parametro();
        List<TipoEmbarcacionDto> listaEmbarcacion = new ArrayList<TipoEmbarcacionDto>();



        JCoTable tableExport = zfl_rfc_read_teableImplement.Buscar(options,"ZFLTEM");
        logger.error("TIPO_7");
        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);
            TipoEmbarcacionDto dto = new TipoEmbarcacionDto();
            String cadena;
            cadena=tableExport.getString();
            String[] parts = cadena.split("\\|");
            dto.setCDTEM(parts[1]);
            dto.setDESCR(parts[2].trim());
            logger.error("TIPO_8");
            listaEmbarcacion.add(dto);
            //lista.add(param);
        }

        //return listaEmbarcacion;

        return listaEmbarcacion;
    }


    public List<PlantasDto> listarPlantas() throws Exception {

        List<PlantasDto> listarPlantas = new ArrayList<PlantasDto>();
        logger.error("TIPO_1111");;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        //JCo
        logger.error("TIPO_12");;
        JCoRepository repo = destination.getRepository();
        logger.error("TIPO_3");;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_READ_TABLE");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("P_USER", "FGARCIA");
        importx.setValue("QUERY_TABLE","ZFLCNS");
        importx.setValue("DELIMITER","|");
        logger.error("TIPO_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        JCoTable tableImport = tables.getTable("OPTIONS");
        tableImport.appendRow();
        logger.error("TIPO_5");;
        tableImport.setValue("WA", "CDCNS = 42");
        //Ejecutar Funcion
        stfcConnection.execute(destination);
        logger.error("TIPO_6");
        //DestinationAcce

        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable("DATA");


        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);
            PlantasDto dto = new PlantasDto();
            String cadena;
            cadena=tableExport.getString();
            String[] parts = cadena.split("\\|");
            dto.setData(parts[1]);
            logger.error("TIPO_8");
            listarPlantas.add(dto);
            //lista.add(param);
        }
        return listarPlantas;
    }
}
