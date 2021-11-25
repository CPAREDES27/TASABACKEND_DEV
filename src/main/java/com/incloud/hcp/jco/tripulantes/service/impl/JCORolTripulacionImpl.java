package com.incloud.hcp.jco.tripulantes.service.impl;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.jco.tripulantes.dto.Options;
import com.incloud.hcp.jco.tripulantes.dto.PersonalDtoImport;
import com.incloud.hcp.jco.tripulantes.dto.RolTripulacionExports;
import com.incloud.hcp.jco.tripulantes.dto.RolTripulacionImports;
import com.incloud.hcp.jco.tripulantes.service.JCORolTripulacionService;
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
public class JCORolTripulacionImpl implements JCORolTripulacionService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public RolTripulacionExports RolTripulacion(RolTripulacionImports imports) throws Exception {

       RolTripulacionExports rt=new RolTripulacionExports();

        Metodos metodo = new Metodos();
        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_REGROL_ADM_REGROL);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_TOPE", imports.getP_tope());
            importx.setValue("P_CDRTR", imports.getP_cdrtr());
            importx.setValue("P_CANTI", imports.getP_canti());

            List<MaestroOptions>  options = imports.getP_option();
            MaestroOptions me = new MaestroOptions();
            me.setWa("CDRTR NE 0");
            options.add(me);
            List<MaestroOptionsKey>  options2 = imports.getP_options();
            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            tmpOptions=metodo.ValidarOptions(options,options2,"DATA");

            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC exec= new EjecutarRFC();
            exec.setTable(tables, Tablas.T_OPCIONES,tmpOptions);

            if(imports.getP_tope().equals("C")){

                exec.setTable(tables, Tablas.T_ZARTR,imports.getT_zartr());
                exec.setTable(tables, Tablas.T_DZART,imports.getT_dzart());
            }

            stfcConnection.execute(destination);
            if(imports.getP_tope().equals("L")) {

                JCoTable T_ZARTR = tables.getTable(Tablas.T_ZARTR);
                JCoTable T_DZART = tables.getTable(Tablas.T_DZART);


                List<HashMap<String, Object>> t_zartr = metodo.ObtenerListObjetos(T_ZARTR, imports.getFieldsT_zartr());
                List<HashMap<String, Object>> t_dzart = metodo.ObtenerListObjetos(T_DZART, imports.getFieldsT_dzart());


                rt.setT_dzart(t_dzart);
                rt.setT_zartr(t_zartr);
            }
            rt.setMensaje("Ok");

        }catch (Exception e){
            rt.setMensaje(e.getMessage());
        }


        return rt;
    }

    @Override
    public RolTripulacionExports PersonalRol(PersonalDtoImport imports) throws Exception {
        JCoDestination destination = JCoDestinationManager.getDestination("TASA_DEST_RFC");
        //JCo

        JCoRepository repo = destination.getRepository();

        JCoFunction stfcConnection = repo.getFunction("ZFL_RFC_READ_TABLE");
        JCoParameterList importx = stfcConnection.getImportParameterList();
        //stfcConnection.getImportParameterList().setValue("P_USER","FGARCIA");
        importx.setValue("QUERY_TABLE", "ZTFL_ZARTR");
        importx.setValue("DELIMITER", "|");
        importx.setValue("P_USER", "FGARCIA");
        importx.setValue("P_ORDER", "FERTR DESCENDING");
        JCoParameterList tables = stfcConnection.getTableParameterList();
        JCoTable tableImport = tables.getTable("OPTIONS");
        tableImport.appendRow();

        tableImport.setValue("WA", "WERKS EQ '"+imports.getEmbarcacion()+"'");


        //Ejecutar Funcion
        stfcConnection.execute(destination);

        //DestinationAcce

        //Recuperar Datos de SAP

        JCoTable tableExport = tables.getTable("DATA");


        return null;
    }
}
