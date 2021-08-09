package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.TipoEmbarcacionDto;
import com.incloud.hcp.jco.maestro.service.JCOTipoEmbarcacionService;
import com.incloud.hcp.jco.maestro.service.RFCCompartidos.ZFL_RFC_READ_TEABLEImplement;
import com.sap.conn.jco.JCoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JCOTipoEmbarcacionImplement implements JCOTipoEmbarcacionService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ZFL_RFC_READ_TEABLEImplement zfl_rfc_read_teableImplement;
    public List<TipoEmbarcacionDto> listaTipoEmbarcacion(String condicion) throws Exception {
        //Parametro dto = new Parametro();
        List<TipoEmbarcacionDto> listaEmbarcacion = new ArrayList<TipoEmbarcacionDto>();

        JCoTable tableExport = zfl_rfc_read_teableImplement.Buscar(condicion);
        logger.error("TIPO_7");
        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);
            TipoEmbarcacionDto dto = new TipoEmbarcacionDto();
            String cadena;
            cadena=tableExport.getString();
            dto.setCodigoTipo(tableExport.getString());
            String[] parts = cadena.split("|");
            dto.setDescripcionTipo(parts[0]);
            logger.error("TIPO_8");
            listaEmbarcacion.add(dto);
            //lista.add(param);
        }

        //return listaEmbarcacion;

        return listaEmbarcacion;
    }
}
