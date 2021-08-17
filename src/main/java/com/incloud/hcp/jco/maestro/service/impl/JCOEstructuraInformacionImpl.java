package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.EstructuraInformacionImports;
import com.incloud.hcp.jco.maestro.dto.MensajeDto;
import com.incloud.hcp.jco.maestro.service.JCOEstructuraInformacionService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class JCOEstructuraInformacionImpl implements JCOEstructuraInformacionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public MensajeDto EditarEstructuraInf(EstructuraInformacionImports estructuraInformacionImports)throws Exception{

        logger.error("EditarEstructuraInf_1");;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        //JCo
        logger.error("EditarEstructuraInf_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("EditarEstructuraInf_3");;
        JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_ESTR_INFOR);

        JCoParameterList importx = stfcConnection.getImportParameterList();
        importx.setValue("P_CDEIN", estructuraInformacionImports.getP_cdein());
        importx.setValue("P_DSEIN", estructuraInformacionImports.getP_dsein());
        importx.setValue("P_CASE", estructuraInformacionImports.getP_case());
        importx.setValue("P_USER", estructuraInformacionImports.getP_user());

        logger.error("EditarEstructuraInf_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();

        stfcConnection.execute(destination);
        logger.error("EditarEstructuraInf_5");
        //DestinationAcce

        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable(Tablas.T_MENSAJE);

        MensajeDto msj= new MensajeDto();
        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);

            msj.setMANDT(tableExport.getString("MANDT"));
            msj.setCMIN(tableExport.getString("CMIN"));
            msj.setCDMIN(tableExport.getString("CDMIN"));
            msj.setDSMIN(tableExport.getString("DSMIN"));
            //lista.add(param);
        }
        logger.error("EditarEstructuraInf_6");

        return msj;
    }


}
