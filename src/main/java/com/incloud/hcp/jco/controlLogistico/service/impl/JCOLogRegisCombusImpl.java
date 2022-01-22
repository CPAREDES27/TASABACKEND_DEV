package com.incloud.hcp.jco.controlLogistico.service.impl;

import com.incloud.hcp.jco.controlLogistico.dto.*;
import com.incloud.hcp.jco.controlLogistico.service.JCOLogRegisCombusService;
import com.incloud.hcp.jco.dominios.dto.DominioExportsData;
import com.incloud.hcp.jco.dominios.dto.DominiosExports;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.jco.reportepesca.dto.DominiosHelper;
import com.incloud.hcp.util.*;
import com.sap.conn.jco.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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


            List<HashMap<String, Object>> str_lgcco = metodo.ListarObjetosLazy(STR_LGCCO);
            List<HashMap<String, Object>> str_csmaj = metodo.ListarObjetosLazy(STR_CSMAJ);
            List<HashMap<String, Object>> str_csmar = metodo.ListarObjetosLazy(STR_CSMAR);
            List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetosLazy(T_MENSAJE);

            //Dominios
            ArrayList<String> listDomNames = new ArrayList<>();
            listDomNames.add(Dominios.ESPRO);

            DominiosHelper helper = new DominiosHelper();
            ArrayList<DominiosExports> listDescipciones = helper.listarDominios(listDomNames);

            DominiosExports esproDom = listDescipciones.stream().filter(d -> d.getDominio().equals(Dominios.ESPRO)).findFirst().orElse(null);

            str_lgcco.stream().map(s -> {
                String espro = s.get("ESPRO").toString();

                DominioExportsData dataEspro = esproDom.getData().stream().filter(d -> d.getId().equals(espro)).findFirst().orElse(null);

                s.put("DESC_ESPRO", dataEspro != null ? dataEspro.getDescripcion() : "");

                return s;
            }).collect(Collectors.toList());

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

    public LogRegCombusExports Nuevo(LogRegCombusImports imports)throws Exception {

        LogRegCombusExports lrce = new LogRegCombusExports();
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
            JCoParameterList tables = stfcConnection.getTableParameterList();
            JCoTable STR_CSMAR = tables.getTable(Tablas.STR_CSMAR);
            JCoTable T_MENSAJE = tables.getTable(Tablas.T_MENSAJE);

                for (int i = 0; i < imports.getStr_csmar().size(); i++) {
                    Csmar dto = imports.getStr_csmar().get(i);
                    STR_CSMAR.appendRow();
                    STR_CSMAR.setValue("NRMAR", dto.getNRMAR());
                    STR_CSMAR.setValue("CDMMA",dto.getCDMMA());
                    STR_CSMAR.setValue("DSMMA",dto.getDSMMA());
                    STR_CSMAR.setValue("FEMAR",dto.getFEMAR());
                    STR_CSMAR.setValue("HAMAR",dto.getHAMAR());
                    STR_CSMAR.setValue("FXMAR",dto.getFXMAR());
                    STR_CSMAR.setValue("HXMAR",dto.getHXMAR());
                    STR_CSMAR.setValue("CDEMB",dto.getCDEMB());
                    STR_CSMAR.setValue("NMEMB",dto.getNMEMB());
                    STR_CSMAR.setValue("PTOZA",dto.getPTOZA());
                    STR_CSMAR.setValue("FECZA",dto.getFECZA());
                    STR_CSMAR.setValue("HIZAR",dto.getHIZAR());
                    STR_CSMAR.setValue("PTOAR",dto.getPTOAR());
                    STR_CSMAR.setValue("FECAR",dto.getFECAR());
                    STR_CSMAR.setValue("HIARR",dto.getHIARR());
                    STR_CSMAR.setValue("STCMB",dto.getSTCMB());
                    STR_CSMAR.setValue("CNSUM",dto.getCNSUM());
                    STR_CSMAR.setValue("CONSU",dto.getCONSU());
                    STR_CSMAR.setValue("STFIN",dto.getSTFIN());
                    STR_CSMAR.setValue("CNPDS",dto.getCNPDS());
                    STR_CSMAR.setValue("FECCONMOV",dto.getFECCONMOV());
                    STR_CSMAR.setValue("CNCAL",dto.getCNCAL());
                    STR_CSMAR.setValue("CNOBS",dto.getCNOBS());
                    STR_CSMAR.setValue("CDIMP",dto.getCDIMP());
                    STR_CSMAR.setValue("ESPRO",dto.getESPRO());
                    STR_CSMAR.setValue("FECCONMO2",dto.getFECCONMO2());
                    STR_CSMAR.setValue("CDIM2",dto.getCDIM2());
                    STR_CSMAR.setValue("ESPR2",dto.getESPR2());
                    STR_CSMAR.setValue("ZCDZAR",dto.getZCDZAR());
                    STR_CSMAR.setValue("DSOBS",dto.getDSOBS());
                    STR_CSMAR.setValue("DSOB2",dto.getDSOB2());
                    STR_CSMAR.setValue("WERKS",dto.getWERKS());
                    STR_CSMAR.setValue("CONS2",dto.getCONS2());
                    STR_CSMAR.setValue("HOZMP",dto.getHOZMP());
                    STR_CSMAR.setValue("HOZA1",dto.getHOZA1());
                    STR_CSMAR.setValue("HOZA2",dto.getHOZA2());
                    STR_CSMAR.setValue("HOZA3",dto.getHOZA3());
                    STR_CSMAR.setValue("HOZA4",dto.getHOZA4());
                    STR_CSMAR.setValue("HOZA5",dto.getHOZA5());
                    STR_CSMAR.setValue("HOZPA",dto.getHOZPA());
                    STR_CSMAR.setValue("HOZFP",dto.getHOZFP());
                    STR_CSMAR.setValue("HOAMP",dto.getHOAMP());
                    STR_CSMAR.setValue("HOAA1",dto.getHOAA1());
                    STR_CSMAR.setValue("HOAA2",dto.getHOAA2());
                    STR_CSMAR.setValue("HOAA3",dto.getHOAA3());
                    STR_CSMAR.setValue("HOAA4",dto.getHOAA4());
                    STR_CSMAR.setValue("HOAA5",dto.getHOAA5());
                    STR_CSMAR.setValue("HOAPA",dto.getHOAPA());
                    STR_CSMAR.setValue("HOAFP",dto.getHOAFP());
                    STR_CSMAR.setValue("HODMP",dto.getHODMP());
                    STR_CSMAR.setValue("HODFP",dto.getHODFP());
                    STR_CSMAR.setValue("HOHMP",dto.getHOHMP());
                    STR_CSMAR.setValue("HOHA1",dto.getHOHA1());
                    STR_CSMAR.setValue("HOHA2",dto.getHOHA2());
                    STR_CSMAR.setValue("HOHA3",dto.getHOHA3());
                    STR_CSMAR.setValue("HOHA4",dto.getHOHA4());
                    STR_CSMAR.setValue("HOHA5",dto.getHOHA5());
                    STR_CSMAR.setValue("HOHPA",dto.getHOHPA());
                    STR_CSMAR.setValue("HOHFP",dto.getHOHFP());
                }
            stfcConnection.execute(destination);
            List<HashMap<String, Object>> t_mensaje = metodo.ListarObjetos(T_MENSAJE);
            lrce.setT_mensaje(t_mensaje);

        }catch(Exception e){

            }
            return lrce;
    }
}

