package com.incloud.hcp.jco.tripulantes.service.impl;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.jco.tripulantes.dto.Options;
import com.incloud.hcp.jco.tripulantes.dto.RegistrosZarpeExports;
import com.incloud.hcp.jco.tripulantes.dto.RegistrosZarpeImports;
import com.incloud.hcp.jco.tripulantes.service.JCORegistroZarpeService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JCORegistroZarpeImpl implements JCORegistroZarpeService {


     @Override
    public RegistrosZarpeExports RegistroZarpe(RegistrosZarpeImports imports) throws Exception {

         RegistrosZarpeExports rz= new RegistrosZarpeExports();

         try {

             JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
             JCoRepository repo = destination.getRepository();
             JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_REGZAR_ADM_REGZAR);

             JCoParameterList importx = stfcConnection.getImportParameterList();
             importx.setValue("P_TOPE", imports.getP_tope());
             importx.setValue("P_CDZAT", imports.getP_cdzat());
             importx.setValue("P_WERKS", imports.getP_werks());
             importx.setValue("P_WERKP", imports.getP_werkp());
             importx.setValue("P_CANTI", imports.getP_canti());
             importx.setValue("P_CDMMA", imports.getP_cdmma());
             importx.setValue("P_PERNR", imports.getP_pernr());
             Metodos metodo = new Metodos();
             List<MaestroOptions> options = imports.getT_opciones();
             List<MaestroOptionsKey> options2 = imports.getP_options();
             List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
             tmpOptions=metodo.ValidarOptions(options,options2,"DATA");

             JCoParameterList tables = stfcConnection.getTableParameterList();

             EjecutarRFC exec= new EjecutarRFC();
             exec.setTable(tables, Tablas.T_OPCIONES,tmpOptions);

             stfcConnection.execute(destination);

             JCoTable T_ZATRP = tables.getTable(Tablas.T_ZATRP);
             JCoTable T_DZATR = tables.getTable(Tablas.T_DZATR);
             JCoTable T_VGCER = tables.getTable(Tablas.T_VGCER);
             JCoTable T_NZATR = tables.getTable(Tablas.T_NZATR);


             List<HashMap<String, Object>>  t_zatrp = metodo.ObtenerListObjetos(T_ZATRP, imports.getFieldst_zatrp());
             List<HashMap<String, Object>>  t_dzatr = metodo.ObtenerListObjetos(T_DZATR, imports.getFieldst_dzatr());
             List<HashMap<String, Object>>  t_vgcer = metodo.ObtenerListObjetos(T_VGCER, imports.getFieldst_vgcer());
             List<HashMap<String, Object>>  t_nzatr = metodo.ObtenerListObjetos(T_NZATR, imports.getFieldst_nzatr());

             rz.setT_zatrp(t_zatrp);
             rz.setT_dzatr(t_dzatr);
             rz.setT_vgcer(t_vgcer);
             rz.setT_nzatr(t_nzatr);
             rz.setMensaje("Ok");

         }catch (Exception e){
             rz.setMensaje(e.getMessage());
         }

         return rz;
    }
}
