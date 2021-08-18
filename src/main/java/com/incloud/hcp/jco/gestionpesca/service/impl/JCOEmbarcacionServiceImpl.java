package com.incloud.hcp.jco.gestionpesca.service.impl;

import com.incloud.hcp.jco.gestionpesca.dto.EmbarcacionDto;
import com.incloud.hcp.jco.gestionpesca.dto.FlotaDto;
import com.incloud.hcp.jco.gestionpesca.service.JCOEmbarcacionService;
import com.incloud.hcp.jco.maestro.dto.EmbarcacionImports;
import com.incloud.hcp.jco.maestro.dto.EventosPescaExports;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
//@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class JCOEmbarcacionServiceImpl implements JCOEmbarcacionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<EmbarcacionDto> listaEmbarcacion(String condicion) throws Exception {

        List<EmbarcacionDto> listaEmbarcacion = new ArrayList<EmbarcacionDto>();
        logger.error("listaEmbarcacion_1");
        ;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        //JCo
        logger.error("listaEmbarcacion_2");
        ;
        JCoRepository repo = destination.getRepository();
        logger.error("listaEmbarcacion_3");
        ;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_CONS_EMBARCA");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("P_USER", "XTS");
        logger.error("listaEmbarcacion_4");
        ;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        JCoTable tableImport = tables.getTable("P_OPTIONS");
        tableImport.appendRow();
        logger.error("listaEmbarcacion_5");
        ;
        //tableImport.setValue("WA", condicion);
        //Ejecutar Funcion
        stfcConnection.execute(destination);
        logger.error("listaEmbarcacion_6");
        //DestinationAcce

        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable("STR_EMB");

        for (int i = 0; i < tableExport.getNumRows(); i++) {
            tableExport.setRow(i);
            EmbarcacionDto dto = new EmbarcacionDto();

            dto.setCodigoEmbarcacion(tableExport.getString("CDEMB"));
            dto.setNombreEmbarcacion(tableExport.getString("NMEMB"));
            listaEmbarcacion.add(dto);
            //lista.add(param);
        }

        return listaEmbarcacion;
    }

    public FlotaDto obtenerDistribucionFlota(String user) throws Exception{

        logger.error("ListarEventosPesca_1");;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        logger.error("ListarEventosPesca_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("ListarEventosPesca_3");;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_DISTR_FLOTA");
        JCoParameterList importx = stfcConnection.getImportParameterList();

        importx.setValue("P_USER", user);
        logger.error("ListarEventosPesca_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        stfcConnection.execute(destination);
        logger.error("ListarEventosPesca_5");

        JCoTable STR_ZLT = tables.getTable("STR_ZLT");
        logger.error("ListarEventosPesca_6");
        JCoTable STR_DI = tables.getTable("STR_DI");
        JCoTable STR_PTA = tables.getTable("STR_PTA");
        logger.error("ListarEventosPesca_6");
        JCoTable STR_DP = tables.getTable("STR_DP");

        logger.error("ListarEventosPesca_7");

        Metodos metodo = new Metodos();
        List<HashMap<String, Object>> ListarST_CEP= metodo.ListarObjetos(STR_ZLT);
        List<HashMap<String, Object>> ListarST_CCP= metodo.ListarObjetos(STR_DI);
        List<HashMap<String, Object>> ListarSTR_PTA= metodo.ListarObjetos(STR_PTA);
        List<HashMap<String, Object>> ListarSTR_DP= metodo.ListarObjetos(STR_DP);

        FlotaDto dto= new FlotaDto();
        dto.setStr_zlt(ListarST_CEP);
        dto.setStr_di(ListarST_CCP);
        dto.setStr_dp(ListarSTR_DP);
        dto.setStr_pta(ListarSTR_PTA);
        dto.setMensaje("Ok");


        return dto;
    }

}
