package com.incloud.hcp.jco.gestionpesca.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incloud.hcp.jco.gestionpesca.dto.Options;
import com.incloud.hcp.jco.gestionpesca.dto.PlantasDto;
import com.incloud.hcp.jco.gestionpesca.dto.TipoEmbarcacionDto;
import com.incloud.hcp.jco.gestionpesca.service.JCOTipoEmbarcacionService;
import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.maestro.dto.MaestroImports;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.service.JCOMaestrosService;
import com.incloud.hcp.jco.maestro.service.RFCCompartidos.ZFL_RFC_READ_TEABLEImplement;
import com.incloud.hcp.jco.maestro.service.impl.JCOMaestrosServiceImpl;
import com.incloud.hcp.util.Constantes;
import com.sap.conn.jco.*;
import org.checkerframework.checker.nullness.Opt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JCOTipoEmbarcacionImplement implements JCOTipoEmbarcacionService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ZFL_RFC_READ_TEABLEImplement zfl_rfc_read_teableImplement;

    @Autowired
    private JCOMaestrosServiceImpl JCOMaestroService;

    private Options options;

    public List<TipoEmbarcacionDto> listaTipoEmbarcacion(String usuario) throws Exception {
        //Parametro dto = new Parametro();
        List<TipoEmbarcacionDto> listaEmbarcacion = new ArrayList<TipoEmbarcacionDto>();

        //amagno
        List<Options> options = new ArrayList<Options>();
        Options opt = new Options();
        opt.setWa("ESREG = 'S'");
        options.add(opt);

        JCoTable tableExport = zfl_rfc_read_teableImplement.Buscar(options,"ZFLTEM", usuario);
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


    public MaestroExport listarPlantas(String usuario) throws Exception {

        //parametros @amagno
        String [] fields = {"VAL01"};
        List<MaestroOptions> listMo = new ArrayList<MaestroOptions>();
        MaestroOptions mo = new MaestroOptions();
        String wa = "CDCNS = " + Constantes.CENTRO_TASA_FLOTA;
        mo.setWa(wa);
        listMo.add(mo);

        //OBTENER CENTRO @amagno
        MaestroImports mi = new MaestroImports();
        mi.setDelimitador("|");
        mi.setFields(fields);
        mi.setNo_data("");
        mi.setOptions(listMo);
        mi.setOrder("");
        mi.setRowcount(0);
        mi.setRowskips(0);
        mi.setTabla("ZFLCNS");
        mi.setP_user(usuario);

        MaestroExport me = JCOMaestroService.obtenerMaestro(mi);
        HashMap<String, Object> record = me.getData().get(0);
        String centro = record.get("VAL01").toString();

        //obtener plantas
        String [] fields1 = {};
        List<MaestroOptions> listMo1 = new ArrayList<MaestroOptions>();
        MaestroOptions mo1 = new MaestroOptions();
        mo1.setWa("ESREG = 'S'");
        MaestroOptions mo2 = new MaestroOptions();
        String optCentro = "AND WERKS <> '" + centro + "'";
        mo2.setWa(optCentro);
        MaestroOptions mo3 = new MaestroOptions();
        mo3.setWa("AND INPRP = 'P'");
        listMo1.add(mo1);
        listMo1.add(mo2);
        listMo1.add(mo3);

        MaestroImports mi2 = new MaestroImports();
        mi2.setDelimitador("|");
        mi2.setFields(fields1);
        mi2.setNo_data("");
        mi2.setOptions(listMo1);
        mi2.setOrder("");
        mi2.setRowcount(0);
        mi2.setRowskips(0);
        mi2.setTabla("ZV_FLPA");
        mi2.setP_user(usuario);

        MaestroExport me1 = JCOMaestroService.obtenerMaestro(mi2);

        /*
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
        }*/
        return me1;
    }
}
