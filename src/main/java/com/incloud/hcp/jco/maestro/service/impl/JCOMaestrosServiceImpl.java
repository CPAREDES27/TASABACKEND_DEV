package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.maestro.service.JCOMaestrosService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JCOMaestrosServiceImpl implements JCOMaestrosService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public MaestroExport obtenerMaestro (MaestroImports importsParam) throws Exception {
        //setear mapeo de parametros import
        HashMap<String, Object> imports = new HashMap<String, Object>();
        imports.put("QUERY_TABLE", importsParam.getTabla());
        imports.put("DELIMITER", importsParam.getDelimitador());
        imports.put("NO_DATA", importsParam.getNo_data());
        imports.put("ROWSKIPS", importsParam.getRowskips());
        imports.put("ROWCOUNT", importsParam.getRowcount());
        imports.put("P_USER", importsParam.getP_user());
        imports.put("P_ORDER", importsParam.getOrder());

        //setear mapeo de tabla options
        List<MaestroOptions> options = importsParam.getOptions();
        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
        for(int i = 0; i < options.size(); i++){
            MaestroOptions mo = options.get(i);
            HashMap<String, Object> record = new HashMap<String, Object>();
            record.put("WA", mo.getWa());
            tmpOptions.add(record);
        }

        //ejecutar RFC ZFL_RFC_READ_TABLE
        EjecutarRFC exec = new EjecutarRFC();
        MaestroExport me =  exec.Execute_ZFL_RFC_READ_TABLE(imports, tmpOptions);
        return me;
    }

    public MensajeDto editarMaestro (MaestroEditImports importsParam) throws Exception{
        //setear mapeo de parametros import
        HashMap<String, Object> imports = new HashMap<String, Object>();
        imports.put("I_TABLE", importsParam.getTabla());
        imports.put("P_FLAG", importsParam.getFlag());
        imports.put("P_CASE", importsParam.getP_case());
        imports.put("P_USER", importsParam.getP_user());

        logger.error("EditarMaestro_1");
        //ejecutar RFC ZFL_RFC_READ_TABLE
        EjecutarRFC exec = new EjecutarRFC();
        logger.error("EditarMaestro_2");
        MensajeDto msj =  exec.Execute_ZFL_RFC_UPDATE_TABLE(imports, importsParam.getData());
        logger.error("EditarMaestro_3");
        return msj;

    }


    public List<EmbarcacionDto> obtenerEmbarcaciones(EmbarcacionImports importsParam)throws Exception{

        //setear mapeo de parametros import
        HashMap<String, Object> imports = new HashMap<String, Object>();
        imports.put("P_USER", importsParam.getP_user());
        logger.error("ObtenerEmbarcaciones_1");
        //setear mapeo de tabla options
        List<MaestroOptions> options = importsParam.getOptions();
        List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
        for(int i = 0; i < options.size(); i++){
            MaestroOptions mo = options.get(i);
            HashMap<String, Object> record = new HashMap<String, Object>();
            record.put("WA", mo.getWa());
            tmpOptions.add(record);
        }
        logger.error("ObtenerEmbarcaciones_2");
        List<MaestroOptions> options2 = importsParam.getOptions2();
        List<HashMap<String, Object>> tmpOptions2 = new ArrayList<HashMap<String, Object>>();
        for(int i = 0; i < options2.size(); i++){
            MaestroOptions mo = options2.get(i);
            HashMap<String, Object> record2 = new HashMap<String, Object>();
            record2.put("WA", mo.getWa());
            tmpOptions2.add(record2);
        }
        logger.error("ObtenerEmbarcaciones_3");
        //ejecutar RFC ZFL_RFC_READ_TABLE
        EjecutarRFC exec = new EjecutarRFC();
        logger.error("ObtenerEmbarcaciones_4");
        List<EmbarcacionDto> ListaEmb =  exec.Execute_ZFL_RFC_CONS_EMBARCA(imports, tmpOptions, tmpOptions2);

        logger.error("ObtenerEmbarcaciones_5");
        return ListaEmb;
    }

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
