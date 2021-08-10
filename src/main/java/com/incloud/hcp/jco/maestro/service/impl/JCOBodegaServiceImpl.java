package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.gestionpesca.dto.Options;
import com.incloud.hcp.jco.maestro.dto.BalanzaDto;
import com.incloud.hcp.jco.maestro.dto.BodegaDto;
import com.incloud.hcp.jco.maestro.service.JCOBodegaService;
import com.incloud.hcp.jco.maestro.service.RFCCompartidos.ZFL_RFC_READ_TEABLEImplement;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JCOBodegaServiceImpl implements JCOBodegaService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ZFL_RFC_READ_TEABLEImplement zfl_rfc_read_teableImplement;
   public List<BodegaDto> BuscarBodegas(List<Options> options)throws Exception{

       String CDBOD, DSBOD,ESREG;
        List<BodegaDto> ListaBodegas=new ArrayList<BodegaDto>();

        JCoTable tableExport = zfl_rfc_read_teableImplement.Buscar(options,"ZFLBOD");
       logger.error("listaBodega_7");

       String response;
       String[] ArrayResponse;

       for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);

            logger.error("listaBodega_8");


            response=tableExport.getString();
            ArrayResponse= response.split("\\|");

            BodegaDto bodega = new BodegaDto();
            bodega.setCDBOD(ArrayResponse[1].trim());
            bodega.setDSBOD(ArrayResponse[2].trim());
            bodega.setESREG(ArrayResponse[3].trim());

            ListaBodegas.add(bodega);
        }


        logger.error("listaBodega_9");

        return ListaBodegas;
    }


}
