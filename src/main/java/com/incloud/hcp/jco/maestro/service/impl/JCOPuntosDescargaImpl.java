package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.PuntosDescargaDto;
import com.incloud.hcp.jco.maestro.service.JCOPuntosDescargaService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JCOPuntosDescargaImpl implements JCOPuntosDescargaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public List<PuntosDescargaDto> obtenerPuntosDescarga(String usuario)throws Exception{

        List<PuntosDescargaDto> ListPuntosD = new ArrayList<PuntosDescargaDto>();

        logger.error("listaEmbarcacion_1");;
        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        //JCo
        logger.error("listaEmbarcacion_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("listaEmbarcacion_3");;
        JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_MAES_PUNT_DESCA);
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("P_USER", usuario);
        logger.error("listaEmbarcacion_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();

        logger.error("listaEmbarcacion_5");;
        //tableImport.setValue("WA", condicion);
        //Ejecutar Funcion
        stfcConnection.execute(destination);
        logger.error("listaEmbarcacion_6");
        //DestinationAcce

        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable(Tablas.ST_PDG);

        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);
            PuntosDescargaDto dto = new PuntosDescargaDto();

            dto.setCDPDG(tableExport.getString("CDPDG"));
            dto.setCDTPD(tableExport.getString("CDTPD"));
            dto.setDSPDG(tableExport.getString("DSPDG"));
            dto.setESREG(tableExport.getString("ESREG"));
            dto.setCDPTA(tableExport.getString("CDPTA"));
            dto.setCDEMB(tableExport.getString("CDEMB"));
            dto.setNMEMB(tableExport.getString("NMEMB"));
            dto.setDESCR(tableExport.getString("DESCR"));
            ListPuntosD.add(dto);
            //lista.add(param);
        }

        return ListPuntosD;
    }

}
