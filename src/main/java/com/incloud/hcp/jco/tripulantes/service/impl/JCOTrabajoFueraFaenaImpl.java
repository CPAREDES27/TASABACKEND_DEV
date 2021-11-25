package com.incloud.hcp.jco.tripulantes.service.impl;

import com.incloud.hcp.jco.tripulantes.dto.*;
import com.incloud.hcp.jco.tripulantes.service.JCOTrabajoFueraFaenaService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JCOTrabajoFueraFaenaImpl implements JCOTrabajoFueraFaenaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public TrabajoFueraFaenaExports TrabajoFueraFaenaTransporte(TrabajoFueraFaenaImports imports) throws Exception {

        logger.error("IP_NRTFF"+ imports.getIp_nrtff());
        logger.error("IP_CDGFL"+ imports.getIp_cdgfl());
        logger.error("IP_WERKS"+ imports.getIp_werks());
        logger.error("IP_TOPE"+imports.getIp_tope());
        logger.error("IP_CANTI"+ imports.getIp_canti());
        logger.error("IP_TIPTR"+ imports.getIp_tiptr());
        logger.error("IP_SEPES"+ imports.getIp_sepes());
        logger.error("IP_ESREG"+ imports.getIp_esreg());
        logger.error("IP_FECIN"+ imports.getIp_fecin());
        logger.error("IP_FECFN"+ imports.getIp_fecfn());
        TrabajoFueraFaenaExports tff=new TrabajoFueraFaenaExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_TFF_CON_TRA_FUE_FAE);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("IP_NRTFF", imports.getIp_nrtff());
            importx.setValue("IP_CDGFL", imports.getIp_cdgfl());
            importx.setValue("IP_WERKS", imports.getIp_werks());
            importx.setValue("IP_TOPE", imports.getIp_tope());
            importx.setValue("IP_CANTI", imports.getIp_canti());
            importx.setValue("IP_TIPTR", imports.getIp_tiptr());
            importx.setValue("IP_SEPES", imports.getIp_sepes());
            importx.setValue("IP_ESREG", imports.getIp_esreg());
            importx.setValue("IP_FECIN", imports.getIp_fecin());
            importx.setValue("IP_FECFN", imports.getIp_fecfn());

            List<Options> options = imports.getT_opcion();
            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < options.size(); i++) {
                Options o = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();

                record.put("DATA", o.getData());
                tmpOptions.add(record);
            }

            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC exec= new EjecutarRFC();
            exec.setTable(tables, Tablas.T_OPCION,tmpOptions);

            stfcConnection.execute(destination);

            JCoTable T_TRABFF = tables.getTable(Tablas.T_TRABFF);

            JCoTable T_TRABAJ = tables.getTable(Tablas.T_TRABAJ);
            JCoTable T_FECHAS = tables.getTable(Tablas.T_FECHAS);
            JCoTable T_TEXTOS = tables.getTable(Tablas.T_TEXTOS);
            JCoTable T_MENSAJES = tables.getTable(Tablas.T_MENSAJES);

            Metodos metodo = new Metodos();
            List<HashMap<String, Object>> t_trabff = metodo.ObtenerListObjetos(T_TRABFF, imports.getFieldst_trabff());
            List<HashMap<String, Object>> t_trabaj = metodo.ObtenerListObjetos(T_TRABAJ, imports.getFieldst_trabaj());
            List<HashMap<String, Object>> t_fechas = metodo.ObtenerListObjetos(T_FECHAS, imports.getFieldst_fechas());
            List<HashMap<String, Object>> t_textos = metodo.ObtenerListObjetos(T_TEXTOS, imports.getFieldst_textos());
            List<HashMap<String, Object>>  t_mensajes = metodo.ListarObjetos(T_MENSAJES);
            logger.error("t_trabff"+ t_trabff.size());
            logger.error("t_trabaj"+ t_trabaj.size());
            logger.error("t_fechas"+ t_fechas.size());
            logger.error("t_textos"+ t_textos.size());


            tff.setT_trabff(t_trabff);
            tff.setT_trabaj(t_trabaj);
            tff.setT_fechas(t_fechas);
            tff.setT_textos(t_textos);
            tff.setT_mensajes(t_mensajes);
            tff.setMensaje("Ok");

        }catch (Exception e){
            tff.setMensaje(e.getMessage());
        }

        return tff;
    }

    @Override
    public GuardaTrabajoExports GuardaTrabajo(GuardaTrabajoImports imports) throws Exception {

        GuardaTrabajoExports gt=new GuardaTrabajoExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_TFF_ACT_TRA_FUE_FAE);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("IP_TOPE", imports.getIp_tope());
            importx.setValue("IP_NRTFF", imports.getIp_nrtff());


            JCoParameterList export = stfcConnection.getExportParameterList();


            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC exe = new EjecutarRFC();
            exe.setTable(tables, Tablas.T_TRABFF, imports.getT_trabff());
            exe.setTable(tables, Tablas.T_TRABAJ, imports.getT_trabaj());
            exe.setTable(tables, Tablas.T_FECHAS, imports.getT_fechas());
            exe.setTable(tables, Tablas.T_ACTCAM, imports.getT_actcam());
            exe.setTable(tables, Tablas.T_TEXTOS, imports.getT_textos());


            JCoTable T_MENSAJ = tables.getTable(Tablas.T_MENSAJ);

            stfcConnection.execute(destination);
            gt.setEp_nrtff(export.getValue(Tablas.EP_NRTFF).toString());

            Metodos metodo = new Metodos();

            List<HashMap<String, Object>>  t_mensaj = metodo.ListarObjetos(T_MENSAJ);


            gt.setT_mensaj(t_mensaj);
            gt.setMensaje("Ok");

        }catch (Exception e){
            gt.setMensaje(e.getMessage());
        }


        return gt;
    }

    public TrabajoFueraFaenaExports DetalleTrabajoFueraFaenaTransporte(TrabajoFueraFaenaDetalleImports imports)throws Exception{

        TrabajoFueraFaenaExports tffde= new TrabajoFueraFaenaExports();

        try{
            TrabajoFueraFaenaImports tfi=new TrabajoFueraFaenaImports();

            tfi.setIp_nrtff(imports.getNroTrabajo());
            tfi.setIp_canti("200");
            tfi.setIp_cdgfl("");
            tfi.setIp_esreg("");
            tfi.setIp_sepes("");
            tfi.setIp_tiptr("");
            tfi.setIp_fecfn("");
            tfi.setIp_fecin("");
            tfi.setIp_werks("");
            tfi.setIp_tope("");
            List<Options>options= new ArrayList<>();
            tfi.setT_opcion(options);

            tffde=TrabajoFueraFaenaTransporte(tfi);


        }catch (Exception e){

            tffde.setMensaje(e.getMessage());

        }

        return tffde;
    }
}
