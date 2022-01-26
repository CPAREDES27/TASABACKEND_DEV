package com.incloud.hcp.jco.tripulantes.service.impl;

import com.incloud.hcp.jco.dominios.dto.DominioExportsData;
import com.incloud.hcp.jco.dominios.dto.DominiosExports;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.jco.reportepesca.dto.DominiosHelper;
import com.incloud.hcp.jco.tripulantes.dto.Options;
import com.incloud.hcp.jco.tripulantes.dto.RegistrosZarpeExports;
import com.incloud.hcp.jco.tripulantes.dto.RegistrosZarpeImports;
import com.incloud.hcp.jco.tripulantes.service.JCORegistroZarpeService;
import com.incloud.hcp.util.*;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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



             JCoTable T_ZATRP = tables.getTable(Tablas.T_ZATRP);
             JCoTable T_DZATR = tables.getTable(Tablas.T_DZATR);
             JCoTable T_VGCER = tables.getTable(Tablas.T_VGCER);
             JCoTable T_NZATR = tables.getTable(Tablas.T_NZATR);


             if(imports.getP_tope().equals("A")|| imports.getP_tope().equals("C")){
                 exec.setTable(tables, Tablas.T_ZATRP,imports.getT_zatrp());
                 exec.setTable(tables, Tablas.T_DZATR,imports.getT_dzatr());
                 exec.setTable(tables, Tablas.T_NZATR,imports.getT_nzatr());

             }
             stfcConnection.execute(destination);
             //List<HashMap<String, Object>>  t_zatrp = metodo.ObtenerListObjetos(T_ZATRP, imports.getFieldst_zatrp());
             //List<HashMap<String, Object>>  t_dzatr = metodo.ObtenerListObjetos(T_DZATR, imports.getFieldst_dzatr());
             //List<HashMap<String, Object>>  t_vgcer = metodo.ObtenerListObjetos(T_VGCER, imports.getFieldst_vgcer());
             //List<HashMap<String, Object>>  t_nzatr = metodo.ObtenerListObjetos(T_NZATR, imports.getFieldst_nzatr());

             List<HashMap<String, Object>>  t_zatrp = metodo.ListarObjetosLazy(T_ZATRP);
             List<HashMap<String, Object>>  t_dzatr = metodo.ListarObjetosLazy(T_DZATR);
             List<HashMap<String, Object>>  t_vgcer = metodo.ListarObjetosLazy(T_VGCER);
             List<HashMap<String, Object>>  t_nzatr = metodo.ListarObjetosLazy(T_NZATR);

             JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);
             List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetosLazy(T_MENSAJE);

             ArrayList<String> listDomNames = new ArrayList<>();
             listDomNames.add(Dominios.ZESREG);

             DominiosHelper helper = new DominiosHelper();
             ArrayList<DominiosExports> listDescipciones = helper.listarDominios(listDomNames);

             DominiosExports claseProtesto = listDescipciones.stream().filter(d -> d.getDominio().equals(Dominios.CLASEPROTESTO)).findFirst().orElse(null);

             t_zatrp.stream().map(m -> {
                 String esreg = m.get("ESREG").toString()!=null ? m.get("ESREG").toString() : "";

                 DominioExportsData dataESREG = claseProtesto.getData().stream().filter(d -> d.getId().equals(esreg)).findFirst().orElse(null);
                 m.put("DESC_CLPRT", dataESREG != null ? dataESREG.getDescripcion() : "");

                 return m;
             }).collect(Collectors.toList());


             rz.setT_mensaje(t_mensaje);
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
