package com.incloud.hcp.jco.gestionpesca.service.impl;

import com.incloud.hcp.jco.gestionpesca.dto.DistribucionDto;
import com.incloud.hcp.jco.gestionpesca.dto.EmbarcacionDto;
import com.incloud.hcp.jco.gestionpesca.service.JCOEmbarcacionService;
import com.incloud.hcp.jco.maestro.dto.EmbarcacionImports;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
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
        logger.error("listaEmbarcacion_1");;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        //JCo
        logger.error("listaEmbarcacion_2");;
        JCoRepository repo = destination.getRepository();
        logger.error("listaEmbarcacion_3");;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_CONS_EMBARCA");
        logger.error("listaEmbarcacion_4");;
        JCoParameterList tables = stfcConnection.getTableParameterList();
        JCoTable tableImport = tables.getTable("P_OPTIONS");
        tableImport.appendRow();
        logger.error("listaEmbarcacion_5");;
        //tableImport.setValue("WA", condicion);
        //Ejecutar Funcion
        stfcConnection.execute(destination);
        logger.error("listaEmbarcacion_6");
        //DestinationAcce

        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable("STR_EMB");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("P_USER", "XTS");

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

    public DistribucionDto obtenerDistribucionFlota(String user) throws Exception{

        List<DistribucionDto> listarPlantas = new ArrayList<DistribucionDto>();
        logger.error("TIPO_1111");;
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        //JCo
        logger.error("TIPO_12");;
        JCoRepository repo = destination.getRepository();
        logger.error("TIPO_3");;
        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_DISTR_FLOTA");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("P_USER", user);
        JCoParameterList tables = stfcConnection.getTableParameterList();
        stfcConnection.execute(destination);
        logger.error("TIPO_6");

        JCoTable tableSTR_ZLT = tables.getTable("STR_ZLT");
        JCoTable tableSTR_DI = tables.getTable("STR_DI");
        JCoTable tableSTR_PTA = tables.getTable("STR_PTA");
        JCoTable tableSTR_DP = tables.getTable("STR_DP");
        Metodos metodo = new Metodos();
        List<HashMap<String, Object>> ListarSTR_ZLT= metodo.ListarObjetos(tableSTR_ZLT);
        List<HashMap<String, Object>> ListarSTR_DI= metodo.ListarObjetos(tableSTR_DI);
        List<HashMap<String, Object>> ListarSTR_PTA= metodo.ListarObjetos(tableSTR_PTA);
        List<HashMap<String, Object>> ListarSTR_DP= metodo.ListarObjetos(tableSTR_DP);

        DistribucionDto dto = new DistribucionDto();
        dto.setStr_zlt(ListarSTR_ZLT);
        dto.setStr_di(ListarSTR_DI);
        dto.setStr_pta(ListarSTR_PTA);
        dto.setStr_dp(ListarSTR_DP);
        dto.setMensaje("Ok");
        return dto;
    }
}
