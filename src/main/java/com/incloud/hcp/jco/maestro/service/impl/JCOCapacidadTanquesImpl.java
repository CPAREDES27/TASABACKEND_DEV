package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.STR_SETDto;
import com.incloud.hcp.jco.maestro.service.JCOCapacidadTanquesService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Mensaje;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JCOCapacidadTanquesImpl implements JCOCapacidadTanquesService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public Mensaje EditarCaptanques(STR_SETDto str_setDto, String tabla)throws Exception{

        EjecutarRFC exec= new EjecutarRFC();
        Mensaje msj= exec.Exec_ZFL_RFC_ACT_CAMP_TAB(str_setDto, tabla);
/*
        logger.error("EditarCaptanques_1");;
        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        //JCo
        logger.error("EditarCaptanques_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("EditarCaptanques_3");;
        JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_ACT_CAMP_TAB);
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("P_USER", str_setDto.getP_USER());
        logger.error("EditarCaptanques_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        JCoTable tableImport = tables.getTable(Tablas.STR_SET);
        tableImport.appendRow();
        logger.error("EditarCaptanques_5");;
        tableImport.setValue("NMTAB", str_setDto.getNMTAB());
        tableImport.setValue("CMSET", str_setDto.getCMSET());
        tableImport.setValue("CMOPT", str_setDto.getCMOPT());
        //Ejecutar Funcion
        logger.error("NMTAB " +  str_setDto.getNMTAB());
        logger.error("CMSET " + str_setDto.getCMOPT());
        logger.error("CMOPT " + str_setDto.getCMSET());
        stfcConnection.execute(destination);
        logger.error("EditarCaptanques_6");

        Mensaje msj= new Mensaje();
        msj.setMensaje("Ok");

        //Recuperar Datos de SAP
        /*
        JCoTable tableExport = tables.getTable(Tablas.T_MENSAJE);
        tableExport.setRow(0);
        mensaje=tableExport.getString("DSMIN");

        if(mensaje==""){
            mensaje="Ok";
        }*/


        return msj;
    }


}
