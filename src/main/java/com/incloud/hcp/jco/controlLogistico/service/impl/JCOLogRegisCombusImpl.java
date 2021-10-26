package com.incloud.hcp.jco.controlLogistico.service.impl;

import com.incloud.hcp.jco.controlLogistico.dto.LogRegCombusExports;
import com.incloud.hcp.jco.controlLogistico.dto.LogRegCombusImports;
import com.incloud.hcp.jco.controlLogistico.dto.LogRegistroCombusDto;
import com.incloud.hcp.jco.controlLogistico.service.JCOLogRegisCombusService;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JCOLogRegisCombusImpl implements JCOLogRegisCombusService {

    public LogRegCombusExports Listar(LogRegCombusImports imports)throws Exception{

        LogRegCombusExports lrce= new LogRegCombusExports();
        Metodos metodo = new Metodos();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();

            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_REG_COMB_MARE_SAP);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_USER", imports.getP_user());
            importx.setValue("P_TOPE", imports.getP_tope());
            importx.setValue("P_LCCO", imports.getP_lcco());
            importx.setValue("P_CANTI", imports.getP_canti());

            List<MaestroOptions> option = imports.getOption();
            List<MaestroOptionsKey> options2 = imports.getOptions();


            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            tmpOptions=metodo.ValidarOptions(option,options2);



            JCoParameterList tables = stfcConnection.getTableParameterList();
            EjecutarRFC exec=new EjecutarRFC();
            exec.setTable(tables, Tablas.P_OPTIONS, tmpOptions);

            JCoTable STR_LGCCO = tables.getTable(Tablas.STR_LGCCO);

            if(imports.getP_tope().equals("A")){

                for(int i=0; i<imports.getStr_lgcco().size();i++){

                    LogRegistroCombusDto dto=imports.getStr_lgcco().get(i);

                    STR_LGCCO.appendRow();
                    STR_LGCCO.setValue("CDIMP",dto.getCDIMP());
                    STR_LGCCO.setValue("CNCON",dto.getCNCON());
                   // STR_LGCCO.setValue("DESC_ESREG",dto.getDESC_ESREG());
                    STR_LGCCO.setValue("DSIMP",dto.getDSIMP());
                    STR_LGCCO.setValue("DSOBS",dto.getDSOBS());
                    STR_LGCCO.setValue("ESPRO",dto.getESPRO());
                    STR_LGCCO.setValue("ESREG",dto.getESREG());
                    STR_LGCCO.setValue("FECON",dto.getFECON());
                    STR_LGCCO.setValue("MREMB",dto.getMREMB());
                    STR_LGCCO.setValue("NMEMB",dto.getNMEMB());
                    STR_LGCCO.setValue("NRDCO",dto.getNRDCO());
                    STR_LGCCO.setValue("NRLCC",dto.getNRLCC());
                    STR_LGCCO.setValue("NRMAR",dto.getNRMAR());
                    STR_LGCCO.setValue("TPLCC",dto.getTPLCC());
                    STR_LGCCO.setValue("WERKS",dto.getWERKS());
                    //STR_LGCCO.setValue("DESC_ESPRO",dto.getDESC_ESPRO());

                }
            }

            stfcConnection.execute(destination);


           // JCoTable STR_LGCCO = tables.getTable(Tablas.STR_LGCCO);
            JCoTable STR_CSMAJ = tables.getTable(Tablas.STR_CSMAJ);
            JCoTable STR_CSMAR = tables.getTable(Tablas.STR_CSMAR);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);


            List<HashMap<String, Object>> str_lgcco = metodo.ObtenerListObjetos(STR_LGCCO, imports.getFieldsStr_lgcco());
            List<HashMap<String, Object>> str_csmaj = metodo.ObtenerListObjetos(STR_CSMAJ, imports.getFieldsStr_csmaj());
            List<HashMap<String, Object>> str_csmar = metodo.ObtenerListObjetos(STR_CSMAR, imports.getFieldsStr_csmar());
            List<HashMap<String, Object>> t_mensaje = metodo.ObtenerListObjetos(T_MENSAJE, imports.getFieldsT_mensaje());

            List<LogRegistroCombusDto> ListDto= new ArrayList<>();
            for(int i=0; i<str_lgcco.size();i++){

                HashMap<String,Object> data=str_lgcco.get(i);

                LogRegistroCombusDto dto=new LogRegistroCombusDto();
                for (Map.Entry<String, Object> entry :data.entrySet()) {
                    String key= entry.getKey();
                    Object value= entry.getValue();
                    if(key.equals("CDIMP")){
                        dto.setCDIMP(value.toString());
                    }else if(key.equals("CNCON")){
                        dto.setCNCON(value.toString());
                    }else if(key.equals("DESC_ESREG")){
                        dto.setDESC_ESREG(value.toString());
                    }else if(key.equals("DSIMP")){
                        dto.setDSIMP(value.toString());
                    }else if(key.equals("DSOBS")){
                        dto.setDSOBS(value.toString());
                    }else if(key.equals("ESPRO")){
                        dto.setESPRO(value.toString());
                    }else if(key.equals("ESREG")){
                        dto.setESREG(value.toString());
                    }else if(key.equals("FECON")){
                        dto.setFECON(value.toString());
                    }else if(key.equals("MREMB")){
                        dto.setMREMB(value.toString());
                    }else if(key.equals("NMEMB")){
                        dto.setNMEMB(value.toString());
                    }else if(key.equals("NRDCO")){
                        dto.setNRDCO(value.toString());
                    }else if(key.equals("NRLCC")){
                        dto.setNRLCC(value.toString());
                    }else if(key.equals("NRMAR")){
                        dto.setNRMAR(value.toString());
                    }else if(key.equals("TPLCC")){
                        dto.setTPLCC(value.toString());
                    }else if(key.equals("WERKS")) {
                        dto.setWERKS(value.toString());
                    }else if(key.equals("DESC_ESPRO")){
                            dto.setDESC_ESPRO(value.toString());
                }
                }
                ListDto.add(dto);
            }

            for (int i=0; i<ListDto.size();i++){

                if(ListDto.get(i).getESPRO().equals("E")){
                    ListDto.get(i).setEstado("sap-icon://circle-task-2");
                    ListDto.get(i).setColor("Error");
                }
                if(ListDto.get(i).getESPRO().equals("P")){
                    ListDto.get(i).setEstado("sap-icon://circle-task-2");
                    ListDto.get(i).setColor("Success");
                }
                if(ListDto.get(i).getESPRO().equals("A")){
                    ListDto.get(i).setEstado("sap-icon://circle-task-2");
                    ListDto.get(i).setColor("Warning");
                }
            }


            if(!imports.getP_tope().equals("A")){
            lrce.setStr_lgcco(ListDto);
            }
            lrce.setStr_csmaj(str_csmaj);
            lrce.setStr_csmar(str_csmar);
            lrce.setT_mensaje(t_mensaje);
            lrce.setMensaje("Ok");
        }catch (Exception e){
            lrce .setMensaje(e.getMessage());
        }

        return lrce;

    }

}
