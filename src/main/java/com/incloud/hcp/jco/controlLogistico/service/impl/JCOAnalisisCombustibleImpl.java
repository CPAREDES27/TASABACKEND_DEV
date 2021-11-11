package com.incloud.hcp.jco.controlLogistico.service.impl;

import com.incloud.hcp.jco.controlLogistico.dto.*;
import com.incloud.hcp.jco.controlLogistico.service.JCOAnalisisCombustibleService;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
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
public class JCOAnalisisCombustibleImpl implements JCOAnalisisCombustibleService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AnalisisCombusLisExports Listar(AnalisisCombusLisImports imports)throws Exception{

        AnalisisCombusLisExports ce= new AnalisisCombusLisExports();
        Metodos metodo = new Metodos();
        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_CONT_COMB_MARE);
            logger.error("stfcConnection: "+stfcConnection.toString());

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            importx.setValue("P_ROW", imports.getP_row());
            String cadena="";
            boolean valida=false;
            if(!imports.getEmbarcacionIni().equals("")){
                valida=true;
                cadena+="CDEMB LIKE '"+imports.getEmbarcacionIni()+"'";
            }
            if(!imports.getMotivoIni().equals("")){
               if(valida==true){
                   cadena+=" AND (CDMMA LIKE '"+imports.getMotivoIni()+"')";
               }else{
                   cadena+="(CDMMA LIKE '"+imports.getMotivoIni()+"')";
                   valida=true;
               }
            }
            if(!imports.getFechaIni().equals("") && !imports.getFechaFin().equals("")){
                if(valida==true){
                    cadena+=" AND (FIEVN BETWEEN '"+imports.getFechaIni()+"'"+" AND "+"'"+imports.getFechaFin()+"')";
                }else{
                    cadena+="(FIEVN BETWEEN '"+imports.getFechaIni()+"'"+" AND "+"'"+imports.getFechaFin()+"')";
                    valida=true;
                }
            }
            if(!imports.getFechaIni().equals("") && imports.getFechaFin().equals("")){
                if(valida==true){
                    cadena+=" AND (FIEVN LIKE '"+imports.getFechaIni()+"')";
                }else{
                    cadena+="FIEVN LIKE '"+imports.getFechaIni()+"'";
                    valida=true;
                }
            }
            if(imports.getFechaIni().equals("") && !imports.getFechaIni().equals("")){
                if(valida==true){
                    cadena+=" AND (FIEVN LIKE '"+imports.getFechaFin()+"')";
                }else{
                    cadena+="(FIEVN LIKE '"+imports.getFechaFin()+"')";
                    valida=true;
                }
            }
            if(!imports.getMotivoIni().equals("")){
                if(imports.getMotivoIni().equals("1") || imports.getMotivoIni().equals("2")){
                    if(valida==true){
                        cadena+=" AND (CDTEV EQ '5')";
                    }else{
                        cadena+="(CDTEV EQ '5')";
                        valida=true;
                    }
                }else if(imports.getMotivoIni().equals("7") || imports.getMotivoIni().equals("8")){
                    if(valida==true){
                        cadena+=" AND (CDTEV EQ 'H' OR CDTEV EQ 'T')";
                    }else{
                        cadena+="(CDTEV EQ 'H' OR CDTEV EQ 'T')";
                    }
                }

            }else {
                if(valida==true){
                    cadena+=" AND (CDTEV EQ '5' OR CDTEV EQ 'H' OR CDTEV EQ 'T')";
                }else{
                    cadena+="(CDTEV EQ '5' OR CDTEV EQ 'H' OR CDTEV EQ 'T')";
                }
            }
            logger.error("CADENA FINAL"+ cadena);
            JCoParameterList tables = stfcConnection.getTableParameterList();
            JCoTable tableImport = tables.getTable("P_OPTIONS");
            tableImport.appendRow();
            tableImport.setValue("WA", cadena);



            stfcConnection.execute(destination);
            JCoTable STR_CSMAR = tables.getTable(Tablas.STR_CSMAR);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);


            List<HashMap<String, Object>> str_csmar = metodo.ListarObjetos(STR_CSMAR);
            List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetos(T_MENSAJE);


            ce.setStr_csmar(str_csmar);
            ce.setT_mensaje(t_mensaje);
            ce.setMensaje("Ok");

        }catch (Exception e){
            ce .setMensaje(e.getMessage());
        }

        return ce;
    }

    public ControlLogExports Detalle(AnalisisCombusImports imports)throws Exception{

        ControlLogExports ce=new ControlLogExports();


        try {
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_COMBUS_VEDA);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            importx.setValue("P_NRMAR", imports.getP_nrmar());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            stfcConnection.execute(destination);
            JCoTable tableExport = tables.getTable(Tablas.STR_DEV);


            Metodos metodo = new Metodos();
            //List<HashMap<String, Object>> data = metodo.ListarObjetos(tableExport);
            String[] fields=imports.getFields();
           List<HashMap<String, Object>> data = metodo.ObtenerListObjetos(tableExport, fields);

            ce.setData(data);
            ce.setMensaje("Ok");

        }catch (Exception e){
            ce .setMensaje(e.getMessage());
        }

        return ce;
    }


    public ControlDetalleExport Detalles(AnalisisCombusImports imports)throws Exception{

        ControlDetalleExport ce=new ControlDetalleExport();


        try {
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_CONS_COMB_FASE);
            JCoParameterList importx = stfcConnection.getImportParameterList();

            importx.setValue("P_MAREA", imports.getP_nrmar());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            stfcConnection.execute(destination);
            JCoTable tableExport = tables.getTable(Tablas.STR_FASE);
            JCoTable tableExport2 = tables.getTable(Tablas.STR_DETF);


            Metodos metodo = new Metodos();
            //List<HashMap<String, Object>> data = metodo.ListarObjetos(tableExport);
            String[] fields=imports.getFields();
            List<HashMap<String, Object>> data = metodo.ObtenerListObjetos(tableExport, fields);
            List<HashMap<String, Object>> data2 = metodo.ObtenerListObjetos(tableExport2, fields);

            ce.setStr_detf(data2);
            ce.setStr_fase(data);
            ce.setMensaje("Ok");

        }catch (Exception e){
            ce .setMensaje(e.getMessage());
        }

        return ce;
    }
    public QlikExport QlikView(QlikView imports)throws Exception{

        QlikExport ce=new QlikExport();


        try {
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_QV_COMB_FASE);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_FIEVN", imports.getpFievn());
            importx.setValue("P_FFEVN", imports.getpFfevn());
            importx.setValue("P_CDEMB", imports.getpCdemb());
            importx.setValue("P_CDMMA", imports.getpCdmma());
            importx.setValue("P_ROW", imports.getpRow());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            stfcConnection.execute(destination);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);
            JCoTable STR_CEF = tables.getTable(Tablas.STR_CEF);


            Metodos metodo = new Metodos();
            List<HashMap<String, Object>>  dataT_MENSAJE = metodo.ListarObjetos(T_MENSAJE);

            List<HashMap<String, Object>> dataSTR_CEF = metodo.ListarObjetos(STR_CEF);


            ce.setT_mensaje(dataT_MENSAJE);
            ce.setStr_cef(dataSTR_CEF);

        }catch (Exception e){
            ce.setMensaje(e.getMessage());
        }

        return ce;
    }

    @Override
    public AnalisisDtoExport AnalisisCombu(AnalisisDtoImport imports) throws Exception {
        AnalisisDtoExport dto = new AnalisisDtoExport();
        try {
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_ANALIS_COMB_FASE);
            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_FIEVN", imports.getFechaIni());
            importx.setValue("P_FFEVN", imports.getFechaFin());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            stfcConnection.execute(destination);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);
            JCoTable STR_CEF = tables.getTable(Tablas.STR_CEF);


            Metodos metodo = new Metodos();
            List<HashMap<String, Object>>  dataT_MENSAJE = metodo.ListarObjetos(T_MENSAJE);

            List<HashMap<String, Object>> dataSTR_CEF = metodo.ListarObjetos(STR_CEF);


            dto.setT_mensaje(dataT_MENSAJE);
            dto.setStr_cef(dataSTR_CEF);

        }catch (Exception e){
            dto.setMensaje(e.getMessage());
        }
        return dto;
    }

}
