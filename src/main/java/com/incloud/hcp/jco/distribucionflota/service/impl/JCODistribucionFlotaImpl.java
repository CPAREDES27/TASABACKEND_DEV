package com.incloud.hcp.jco.distribucionflota.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incloud.hcp.jco.distribucionflota.dto.*;
import com.incloud.hcp.jco.distribucionflota.service.JCODistribucionFlotaService;
import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.maestro.dto.MaestroImportsKey;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.jco.maestro.service.JCOMaestrosService;
import com.incloud.hcp.jco.maestro.service.impl.JCOMaestrosServiceImpl;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class JCODistribucionFlotaImpl implements JCODistribucionFlotaService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JCOMaestrosService MaestroService;

    @Override
    public DistribucionFlotaExports ListarDistribucionFlota(DistribucionFlotaImports importsParam) throws Exception {

        DistribucionFlotaExports dto = new DistribucionFlotaExports();

        try {

            /******************************** LLAMADA AL RFC DE DISTRIBUCION DE FLOTA ***********************/

            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("P_USER", importsParam.getP_user());
            imports.put("P_INPRP", importsParam.getP_inprp());
            imports.put("P_INUBC", importsParam.getP_inubc());
            imports.put("P_CDTEM", importsParam.getP_cdtem());
            imports.put("P_ZONAAREA", importsParam.getP_zonaarea());
            logger.error("ListarDistribucionFlota1");

            EjecutarRFC exec = new EjecutarRFC();
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_DISTR_FLOTA_BTP);
            exec.setImports(function, imports);

            JCoParameterList jcoTables = function.getTableParameterList();
            function.execute(destination);

            JCoTable s_str_zlt = jcoTables.getTable(Tablas.STR_ZLT);
            JCoTable s_str_di = jcoTables.getTable(Tablas.STR_DI);
            JCoTable s_str_pta = jcoTables.getTable(Tablas.STR_PTA);
            JCoTable s_str_dp = jcoTables.getTable(Tablas.STR_DP);

            /* ----------------------------------- Fin de consumo de RFC ----------------------------------*/

            /************************************* lOGICA DE ARMADO DE REQUEST *****************************/

            List<ZonasDto> zonas = new ArrayList<ZonasDto>();
            List<DescargasDto> Descargas = new ArrayList<DescargasDto>();
            double vd_totdesc_cbod = 0;
            double vd_totdesc_decl = 0;
            double vd_totdesc_desc = 0;
            for (int i = 0; i < s_str_zlt.getNumRows(); i++) {
                s_str_zlt.setRow(i);
                ZonasDto n_zona = new ZonasDto();
                n_zona.setZonaName(s_str_zlt.getString("DSZLT"));
                List<PlantasDto> plantas = new ArrayList<PlantasDto>();
                for (int j = 0; j < s_str_pta.getNumRows(); j++) {
                    s_str_pta.setRow(j);
                    PlantasDto n_planta = new PlantasDto();
                    String cz1 = s_str_pta.getString("CDZLT");
                    String cz2 = s_str_zlt.getString("CDZLT");
                    logger.error("cz1 : " + cz1 + " - cz2 : " + cz2);
                    if(s_str_pta.getString("CDZLT").equalsIgnoreCase(s_str_zlt.getString("CDZLT"))){
                        logger.error("cz12 : " + cz1 + " - cz22 : " + cz2);
                        n_planta.setCodPlanta(s_str_pta.getString("CDPTA"));
                        n_planta.setPlantaName(s_str_pta.getString("DESCR"));
                        n_planta.setTot_PescaReq(Double.parseDouble(s_str_pta.getString("CNPRQ")));
                        List<EmbarcacionesDto> embarcaciones = new ArrayList<EmbarcacionesDto>();
                        //VARIABLES TOTALES
                        int vi_contadorEmb = 0;
                        double vi_contadorBod = 0;
                        double vi_contadorDecl = 0;
                        int vi_totdesc_est = 0;
                        for (int k = 0; k < s_str_di.getNumRows(); k++) {
                            s_str_di.setRow(k);
                            EmbarcacionesDto n_embarcacion =  new EmbarcacionesDto();
                            if(s_str_di.getString("CDPTA").equalsIgnoreCase(s_str_pta.getString("CDPTA"))){
                                //CARGA DEL TAP DESC
                                boolean bOk = true;
                                String estMarea = s_str_di.getString("ESMAR");
                                String estMareaCie = s_str_di.getString("ESCMA");
                                String embaNomin = s_str_di.getString("EMPTO");
                                String indProp = s_str_di.getString("INPRP");

                                if (embaNomin.equalsIgnoreCase("N") && indProp.equalsIgnoreCase("P")) {
                                    bOk = false;

                                }

                                if((estMarea.equalsIgnoreCase("C") && estMareaCie.equalsIgnoreCase("T")) && bOk) {
                                    DescargasDto o_descarga = new DescargasDto();

                                    o_descarga.setCbodEmba(s_str_di.getString("CPPMS"));
                                    o_descarga.setCodEmba(s_str_di.getString("CDEMB"));
                                    o_descarga.setCodPlanta(s_str_di.getString("CDPTA"));
                                    o_descarga.setDescEmba(s_str_di.getString("NMEMB"));
                                    o_descarga.setDescPlanta(s_str_di.getString("DESCR"));
                                    o_descarga.setPescDecl(s_str_di.getString("CNPCM"));
                                    o_descarga.setPescDesc(s_str_di.getString("CNPDS"));

                                    Descargas.add(o_descarga);

                                    vd_totdesc_decl = vd_totdesc_decl + Double.parseDouble(o_descarga.getPescDecl());
                                    vd_totdesc_desc = vd_totdesc_desc + Double.parseDouble(o_descarga.getPescDesc());
                                    vd_totdesc_cbod = vd_totdesc_cbod + Double.parseDouble(o_descarga.getCbodEmba());

                                }

                                //SUMATORIA DE VARIABLES TOTALES
                                vi_contadorEmb++;
                                vi_contadorBod = Double.parseDouble(s_str_di.getString("CPPMS")) + vi_contadorBod;
                                vi_contadorDecl = Double.parseDouble(s_str_di.getString("CNPCM")) + vi_contadorDecl;
                                if(s_str_di.getString("DSEEC").equalsIgnoreCase("ESDE")){
                                    vi_totdesc_est++;
                                }

                                n_embarcacion.setCodPlanta(s_str_di.getString("CDPTA"));
                                n_embarcacion.setNomPlanta(s_str_di.getString("DESCR"));
                                n_embarcacion.setCodEmba(s_str_di.getString("CDEMB"));
                                n_embarcacion.setDescEmba(s_str_di.getString("NMEMB"));
                                n_embarcacion.setCbodEmba(s_str_di.getString("CPPMS"));
                                n_embarcacion.setPescDecl(s_str_di.getString("CNPCM"));
                                n_embarcacion.setEstado(s_str_di.getString("DSEEC"));
                                n_embarcacion.setHoraArribo(s_str_di.getString("HEARR"));
                                if(n_embarcacion.getHoraArribo() != null && n_embarcacion.getHoraArribo() != "")
                                {String[] parts = n_embarcacion.getHoraArribo().split(":");
                                    n_embarcacion.setHoraArribo(parts[0] + ":" + parts[1]);}
                                n_embarcacion.setDiaAnt(s_str_di.getString("DA"));
                                n_embarcacion.setTdc(s_str_di.getString("TDC"));
                                n_embarcacion.setDescZonaCala(s_str_di.getString("ZONA"));
                                n_embarcacion.setEstSisFrio(s_str_di.getString("ESTSF"));
                                n_embarcacion.setColor(s_str_di.getString("CLGFL"));
                                n_embarcacion.setNumMarea(s_str_di.getString("NRMAR"));
                                n_embarcacion.setIndicador(s_str_di.getString("INPRP"));
                                n_embarcacion.setSemaforo("");
                                n_embarcacion.setSemaforoColor("");

                                String CodMotMarea = s_str_di.getString("CDMMA");
                                String TDC = s_str_di.getString("TDC");
                                BigDecimal n_TDC;
                                if(TDC != null && !TDC.isEmpty()){
                                    n_TDC = new BigDecimal(TDC);
                                }else{
                                    n_TDC = new BigDecimal("0");
                                }

                                if (CodMotMarea.equalsIgnoreCase("2")){ //CHI
                                    if (n_TDC.compareTo(new BigDecimal(12)) == -1){
                                        n_embarcacion.setSemaforo("sap-icon://color-fill");
                                        n_embarcacion.setSemaforoColor("#A1F03F");
                                    } else if ((n_TDC.compareTo(new BigDecimal(13)) == -1) || (n_TDC.compareTo(new BigDecimal(13)) == 0)){
                                        n_embarcacion.setSemaforo("sap-icon://color-fill");
                                        n_embarcacion.setSemaforoColor("#FFC556");
                                    } else if ((n_TDC.compareTo(new BigDecimal(13)) == 1)){
                                        n_embarcacion.setSemaforo("sap-icon://color-fill");
                                        n_embarcacion.setSemaforoColor("#FF322D");
                                    }
                                } else if (CodMotMarea.equalsIgnoreCase("1")){ //CHD
                                    if (n_TDC.compareTo(new BigDecimal(16)) == -1){
                                        n_embarcacion.setSemaforo("sap-icon://color-fill");
                                        n_embarcacion.setSemaforoColor("#A1F03F");
                                    } else if ((n_TDC.compareTo(new BigDecimal(17)) == -1) || (n_TDC.compareTo(new BigDecimal(17)) == 0)){
                                        n_embarcacion.setSemaforo("sap-icon://color-fill");
                                        n_embarcacion.setSemaforoColor("#FFC556");
                                    } else if ((n_TDC.compareTo(new BigDecimal(17)) == 1)){
                                        n_embarcacion.setSemaforo("sap-icon://color-fill");
                                        n_embarcacion.setSemaforoColor("#FF322D");
                                    }
                                }

                                embarcaciones.add(n_embarcacion);
                            }

                        }
                        //--------------------Validacion de campo de columnas ---------------------------
                        logger.error("Validacion de campo de columnas : "+  importsParam.getP_codPlanta() + " - " + s_str_pta.getString("CDPTA"));
                        if(importsParam.getP_codPlanta().equalsIgnoreCase(s_str_pta.getString("CDPTA"))){
                            embarcaciones = this.EmbarcacionesFiltradas(embarcaciones, importsParam.getP_numFilas());
                        }
                        //-------------------------------------------------------------------------------
                        n_planta.setTot_emb(vi_contadorEmb);
                        n_planta.setTot_bod(vi_contadorBod);
                        n_planta.setTot_decl(vi_contadorDecl);
                        n_planta.setTot_Est(vi_totdesc_est);
                        n_planta.setListaEmbarcaciones(embarcaciones);
                        plantas.add(n_planta);
                    }
                }
                logger.error("cantidad de plantas : " + plantas.size());
                n_zona.setListaPlantas(plantas);
                zonas.add(n_zona);
            }
            /* ----------------------------------- Fin de armado de JSON----------------------------------*/
            //String prueba = "";
            //prueba.compareTo();
            dto.setListaZonas(zonas);
            /************************************* lOGICA DE SETEO DE RESUMEN *****************************/
            dto.setListaDescargas(Descargas);
            dto.setTot_desc_cbod(vd_totdesc_cbod);
            dto.setTot_desc_desc(vd_totdesc_desc);
            dto.setTot_desc_dscl(vd_totdesc_decl);

            List<PropiosDto> lst_propios = new ArrayList<PropiosDto>();
            List<TercerosDto> lst_terceros = new ArrayList<TercerosDto>();
            List<TotalDto> lst_totales = new ArrayList<TotalDto>();

            double vd_totterc_cbod= 0;
            double vd_totterc_dscl = 0;
            double vd_totterc_ep = 0;
            double vd_totprop_cbod= 0;
            double vd_totprop_dscl = 0;
            double vd_totprop_ep = 0;
            double vd_tottot_cbod= 0;
            double vd_tottot_dscl = 0;
            double vd_tottot_ep = 0;

            for (int i = 0; i < s_str_dp.getNumRows(); i++) {
                s_str_dp.setRow(i);
                BigDecimal b_cnpcm = new BigDecimal(s_str_dp.getString("CNPCM"));
                BigDecimal b_cnpdt = new BigDecimal(s_str_dp.getString("CNPDT"));


               // MaestroExport maestroPl =  this.MaestroService.obtenerMaestro2();

                if (b_cnpcm.compareTo(new BigDecimal(0))> 0) {

                    MaestroImportsKey maestro = new MaestroImportsKey();
                    String[] fields = {"CXPXD"};
                    List<MaestroOptions> options = new ArrayList<MaestroOptions>();
                    List<MaestroOptionsKey> options2 = new ArrayList<MaestroOptionsKey>();

                    MaestroOptions item_option = new MaestroOptions();
                    String v_planta = s_str_dp.getString("DESCR");
                    item_option.setWa("DESCR ='"+ v_planta + "'");
                    options.add(item_option);

                    maestro.setDelimitador("|");
                    maestro.setFields(fields);
                    maestro.setNo_data("");
                    maestro.setOption(options);
                    maestro.setOptions(options2);
                    maestro.setOrder("");
                    maestro.setP_user(importsParam.getP_user());
                    maestro.setRowcount(0);
                    maestro.setRowskips(0);
                    maestro.setTabla("ZFLPTA");
                    String v_PescDecl = s_str_dp.getString("CNPCM");
                    String v_dif = this.prueba(maestro,importsParam.getP_user(),v_PescDecl);

                    TotalDto resumenTotal = new TotalDto();
                    resumenTotal.setDif(v_dif);

                    /*-----------------------------------------------------------*/
                    PropiosDto resumenProp = new PropiosDto();

                    resumenProp.setCodPlanta(s_str_dp.getString("CDPTA"));
                    resumenProp.setDescPlanta(s_str_dp.getString("DESCR"));
                    resumenProp.setPescDeclProp(Double.parseDouble(s_str_dp.getString("CNPCM")));
                    resumenProp.setEmbaPescProp(Double.parseDouble( s_str_dp.getString("CNEMB")));
                    resumenProp.setCbodProp(Double.parseDouble(s_str_dp.getString("CPPMP")));

                    resumenTotal.setCodPlanta(s_str_dp.getString("CDPTA"));
                    resumenTotal.setDescPlanta(s_str_dp.getString("DESCR"));
                    resumenTotal.setPescDeclProp(Double.parseDouble(s_str_dp.getString("CNPCM")));
                    resumenTotal.setEmbaPescProp(Double.parseDouble(s_str_dp.getString("CNEMB")));
                    resumenTotal.setCbodProp(Double.parseDouble(s_str_dp.getString("CPPMP")));

                    vd_totprop_cbod = vd_totprop_cbod + resumenProp.getCbodProp();
                    vd_totprop_dscl = vd_totprop_dscl + resumenProp.getPescDeclProp();
                    vd_totprop_ep = vd_totprop_ep + resumenProp.getEmbaPescProp();

                    vd_tottot_cbod = vd_tottot_cbod + resumenProp.getCbodProp();
                    vd_tottot_dscl = vd_tottot_dscl + resumenProp.getPescDeclProp();
                    vd_tottot_ep = vd_tottot_ep + resumenProp.getEmbaPescProp();

                    logger.error("CESARIN: "+resumenTotal.getCbodProp());
                    lst_propios.add(resumenProp);
                    lst_totales.add(resumenTotal);

                }

                if (b_cnpdt.compareTo(new BigDecimal(0))> 0) {

                    MaestroImportsKey maestro = new MaestroImportsKey();
                    String[] fields = {"CXPXD"};
                    List<MaestroOptions> options = new ArrayList<MaestroOptions>();
                    List<MaestroOptionsKey> options2 = new ArrayList<MaestroOptionsKey>();

                    MaestroOptions item_option = new MaestroOptions();
                    String v_planta = s_str_dp.getString("DESCR");
                    item_option.setWa("DESCR ='"+ v_planta + "'");
                    options.add(item_option);

                    maestro.setDelimitador("|");
                    maestro.setFields(fields);
                    maestro.setNo_data("");
                    maestro.setOption(options);
                    maestro.setOptions(options2);
                    maestro.setOrder("");
                    maestro.setP_user(importsParam.getP_user());
                    maestro.setRowcount(0);
                    maestro.setRowskips(0);
                    maestro.setTabla("ZFLPTA");
                    String v_PescDecl = s_str_dp.getString("CNPDT");
                    String v_dif = this.prueba(maestro,importsParam.getP_user(),v_PescDecl);

                    TotalDto resumenTotal = new TotalDto();
                    resumenTotal.setDif(v_dif);

                    /*-----------------------------------------------------------*/

                    TercerosDto resumenTerc = new TercerosDto();

                    resumenTerc.setCodPlanta(s_str_dp.getString("CDPTA"));
                    resumenTerc.setDescPlanta(s_str_dp.getString("DESCR"));
                    resumenTerc.setPescDeclProp(Double.parseDouble(s_str_dp.getString("CNPDT")));
                    resumenTerc.setEmbaPescProp(Double.parseDouble(s_str_dp.getString("CNEMT")));
                    resumenTerc.setCbodProp(Double.parseDouble(s_str_dp.getString("CPPMT")));

                    resumenTotal.setCodPlanta(s_str_dp.getString("CDPTA"));
                    resumenTotal.setDescPlanta(s_str_dp.getString("DESCR"));
                    resumenTotal.setPescDeclProp(Double.parseDouble(s_str_dp.getString("CNPDT")));
                    resumenTotal.setEmbaPescProp(Double.parseDouble(s_str_dp.getString("CNEMT")));
                    resumenTotal.setCbodProp(Double.parseDouble(s_str_dp.getString("CPPMT")));

                    vd_totterc_cbod = vd_totterc_cbod + resumenTerc.getCbodProp();
                    vd_totterc_dscl = vd_totterc_dscl + resumenTerc.getPescDeclProp();
                    vd_totterc_ep = vd_totterc_ep + resumenTerc.getEmbaPescProp();

                    vd_tottot_cbod = vd_tottot_cbod + resumenTerc.getCbodProp();
                    vd_tottot_dscl = vd_tottot_dscl + resumenTerc.getPescDeclProp();
                    vd_tottot_ep = vd_tottot_ep + resumenTerc.getEmbaPescProp();

                    logger.error("CESARIN: "+resumenTotal.getCbodProp());
                    lst_terceros.add(resumenTerc);
                    lst_totales.add(resumenTotal);

                }

            }
            List<TotalDto> lst_totales2 = new ArrayList<TotalDto>();
            String planta="";
            double sumCBOD=0;
            double sumDCL=0;
            double sumEP=0;
            for(int i=0; i<lst_totales.size();i++){


                if(!lst_totales.get(i).getDescPlanta().equals(planta)){

                    if(i==0){
                        planta=lst_totales.get(i).getDescPlanta();
                        sumCBOD=lst_totales.get(i).getCbodProp();
                        sumDCL=lst_totales.get(i).getPescDeclProp();
                        sumEP=lst_totales.get(i).getEmbaPescProp();
                    }else if(i==(lst_totales.size()-1)){

                        TotalDto resumenTotal2 = new TotalDto();

                        resumenTotal2.setCodPlanta(lst_totales.get(i-1).getCodPlanta());
                        resumenTotal2.setDescPlanta(lst_totales.get(i-1).getDescPlanta());
                        resumenTotal2.setPescDeclProp(sumDCL);
                        resumenTotal2.setEmbaPescProp(sumEP);
                        resumenTotal2.setCbodProp(sumCBOD);

                        lst_totales2.add(resumenTotal2);

                        TotalDto resumenTotal3 = new TotalDto();

                        resumenTotal3.setCodPlanta(lst_totales.get(i).getCodPlanta());
                        resumenTotal3.setDescPlanta(lst_totales.get(i).getDescPlanta());
                        resumenTotal3.setPescDeclProp(lst_totales.get(i).getPescDeclProp());
                        resumenTotal3.setEmbaPescProp(lst_totales.get(i).getEmbaPescProp());
                        resumenTotal3.setCbodProp(lst_totales.get(i).getCbodProp());

                        lst_totales2.add(resumenTotal3);
                    }else{
                        TotalDto resumenTotal2 = new TotalDto();

                        resumenTotal2.setCodPlanta(lst_totales.get(i-1).getCodPlanta());
                        resumenTotal2.setDescPlanta(lst_totales.get(i-1).getDescPlanta());
                        resumenTotal2.setPescDeclProp(sumDCL);
                        resumenTotal2.setEmbaPescProp(sumEP);
                        resumenTotal2.setCbodProp(sumCBOD);

                        lst_totales2.add(resumenTotal2);

                    }
                    planta=lst_totales.get(i).getDescPlanta();
                    sumCBOD=lst_totales.get(i).getCbodProp();
                    sumDCL=lst_totales.get(i).getPescDeclProp();
                    sumEP=lst_totales.get(i).getEmbaPescProp();
                }else{
                    //planta=lst_totales.get(i).getDescPlanta();
                    sumCBOD+=lst_totales.get(i).getCbodProp();
                    sumDCL+=lst_totales.get(i).getPescDeclProp();
                    sumEP+=lst_totales.get(i).getEmbaPescProp();

                    if (i==(lst_totales.size()-1)){

                        TotalDto resumenTotal2 = new TotalDto();

                        resumenTotal2.setCodPlanta(lst_totales.get(i-1).getCodPlanta());
                        resumenTotal2.setDescPlanta(lst_totales.get(i-1).getDescPlanta());
                        resumenTotal2.setPescDeclProp(sumDCL);
                        resumenTotal2.setEmbaPescProp(sumEP);
                        resumenTotal2.setCbodProp(sumCBOD);

                        lst_totales2.add(resumenTotal2);
                    }
                }

            }

            dto.setListaPropios(lst_propios);
            dto.setTot_prop_cbod(vd_totprop_cbod);
            dto.setTot_prop_dscl(vd_totprop_dscl);
            dto.setTot_prop_ep(vd_totprop_ep);
            dto.setListaTerceros(lst_terceros);
            dto.setTot_terc_cbod(vd_totterc_cbod);
            dto.setTot_terc_dscl(vd_totterc_dscl);
            dto.setTot_terc_ep(vd_totterc_ep);
            dto.setListaTotal(lst_totales2);
            dto.setTot_tot_cbod(vd_tottot_cbod);
            dto.setTot_tot_dscl(vd_tottot_dscl);
            dto.setTot_tot_ep(vd_tottot_ep);
            /*----------------------------------- Fin de SETEO de resumen----------------------------------*/

        }catch (Exception e){
            dto.setMensaje(e.getMessage());
        }
        return dto;

    }


    public DistrFlotaOptExport ListarDistFltColumDinamic(DistrFlotaOptions importsParam) throws Exception {

        DistrFlotaOptExport dto = new DistrFlotaOptExport();

        try {

            /******************************** LLAMADA AL RFC DE DISTRIBUCION DE FLOTA ***********************/

            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("P_USER", importsParam.getP_user());
            imports.put("P_INPRP", importsParam.getP_inprp());
            imports.put("P_INUBC", importsParam.getP_inubc());
            imports.put("P_CDTEM", importsParam.getP_cdtem());
            logger.error("ListarDistribucionFlota1");

            EjecutarRFC exec = new EjecutarRFC();
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_DISTR_FLOTA_BTP);
            exec.setImports(function, imports);

            JCoParameterList jcoTables = function.getTableParameterList();
            function.execute(destination);

            JCoTable s_str_zlt = jcoTables.getTable(Tablas.STR_ZLT);
            JCoTable s_str_di = jcoTables.getTable(Tablas.STR_DI);
            JCoTable s_str_pta = jcoTables.getTable(Tablas.STR_PTA);
            JCoTable s_str_dp = jcoTables.getTable(Tablas.STR_DP);

            /* ----------------------------------- Fin de consumo de RFC ----------------------------------*/

            /*Metodos metodo = new Metodos();
            List<HashMap<String, Object>> Listar_s_str_zlt= metodo.ObtenerListObjetos(s_str_zlt,marea.getFieldMarea());
            List<HashMap<String, Object>> Listar_s_str_di= metodo.ObtenerListObjetos(S_EVENTO,marea.getFieldEvento());
            List<HashMap<String, Object>> Listar_s_str_pta= metodo.ObtenerListObjetos(STR_FLBSP,marea.getFieldFLBSP());
            List<HashMap<String, Object>> Listar_s_str_dp= metodo.ObtenerListObjetos(STR_PSCINC,marea.getFieldPSCINC());*/

            /************************************* lOGICA DE ARMADO DE REQUEST *****************************/

            List<ZonasDto> zonas = new ArrayList<ZonasDto>();
            for (int i = 0; i < s_str_zlt.getNumRows(); i++) {
                s_str_zlt.setRow(i);
                ZonasDto n_zona = new ZonasDto();
                n_zona.setZonaName(s_str_zlt.getString("DSZLT"));
                List<PlantasDto> plantas = new ArrayList<PlantasDto>();
                for (int j = 0; j < s_str_pta.getNumRows(); j++) {
                    s_str_pta.setRow(j);
                    PlantasDto n_planta = new PlantasDto();
                    String cz1 = s_str_pta.getString("CDZLT");
                    String cz2 = s_str_zlt.getString("CDZLT");
                    logger.error("cz1 : " + cz1 + " - cz2 : " + cz2);
                    if(s_str_pta.getString("CDZLT").equalsIgnoreCase(s_str_zlt.getString("CDZLT"))){
                        logger.error("cz12 : " + cz1 + " - cz22 : " + cz2);
                        n_planta.setPlantaName(s_str_pta.getString("DESCR"));
                        List<EmbarcacionesDto> embarcaciones = new ArrayList<EmbarcacionesDto>();
                        for (int k = 0; k < s_str_di.getNumRows(); k++) {
                            s_str_di.setRow(k);
                            EmbarcacionesDto n_embarcacion =  new EmbarcacionesDto();
                            if(s_str_di.getString("CDPTA").equalsIgnoreCase(s_str_pta.getString("CDPTA"))){
                                //n_embarcacion.setFlagEmba(s_str_di.);
                                n_embarcacion.setDescEmba(s_str_di.getString("NMEMB"));
                                n_embarcacion.setCbodEmba(s_str_di.getString("CDEMB"));
                                n_embarcacion.setPescDecl(s_str_di.getString("CNPCM"));
                                n_embarcacion.setEstado(s_str_di.getString("DSEEC"));
                                n_embarcacion.setHoraArribo(s_str_di.getString("HEARR"));
                                n_embarcacion.setDiaAnt(s_str_di.getString("DA"));
                                n_embarcacion.setTdc(s_str_di.getString("TDC"));
                                n_embarcacion.setDescZonaCala(s_str_di.getString("ZONA"));
                                n_embarcacion.setEstSisFrio(s_str_di.getString("ESTSF"));

                                String CodMotMarea = s_str_di.getString("CDMMA");
                                String TDC = s_str_di.getString("TDC");
                                BigDecimal n_TDC;
                                if(TDC != null || !TDC.isEmpty()){
                                    n_TDC = new BigDecimal(TDC);
                                }else{
                                    n_TDC = new BigDecimal("0");
                                }
                                if (CodMotMarea.equalsIgnoreCase("2")){ //CHI
                                    if (n_TDC.compareTo(new BigDecimal(12)) == -1){
                                        n_embarcacion.setSemaforo("images/green_line.gif");
                                    } else if ((n_TDC.compareTo(new BigDecimal(13)) == -1) || (n_TDC.compareTo(new BigDecimal(13)) == 0)){
                                        n_embarcacion.setSemaforo("images/yellow_line.gif");
                                    } else if ((n_TDC.compareTo(new BigDecimal(13)) == 1)){
                                        n_embarcacion.setSemaforo("images/red_line.gif");
                                    }
                                } else if (CodMotMarea.equalsIgnoreCase("1")){ //CHD
                                    if (n_TDC.compareTo(new BigDecimal(16)) == -1){
                                        n_embarcacion.setSemaforo("images/green_line.gif");
                                    } else if ((n_TDC.compareTo(new BigDecimal(17)) == -1) || (n_TDC.compareTo(new BigDecimal(17)) == 0)){
                                        n_embarcacion.setSemaforo("images/yellow_line.gif");
                                    } else if ((n_TDC.compareTo(new BigDecimal(17)) == 1)){
                                        n_embarcacion.setSemaforo("images/red_line.gif");
                                    }
                                }

                                embarcaciones.add(n_embarcacion);
                            }

                        }
                        n_planta.setListaEmbarcaciones(embarcaciones);
                        plantas.add(n_planta);
                    }
                }
                logger.error("cantidad de plantas : " + plantas.size());
                n_zona.setListaPlantas(plantas);
                zonas.add(n_zona);
            }
            /* ----------------------------------- Fin de armado de JSON----------------------------------*/
            //String prueba = "";
            //prueba.compareTo();
            //dto.setListaZonas(zonas);

        }catch (Exception e){
            dto.setMensaje(e.getMessage());
        }
        return dto;

    }


    public String prueba(MaestroImportsKey p_maestro,String p_user, String p_pescDecl){
    try {
        String r_diferencia = "";
        String v_cxpxd = "";
        String v_porcMaxProc = "";
        String v_horasProc = "";
        ObjectMapper objectMapper = new ObjectMapper();
        String usuarioJson = objectMapper.writeValueAsString(p_maestro);
        logger.error("JSON : " + usuarioJson);

        JCOMaestrosServiceImpl impMaestro = new JCOMaestrosServiceImpl();
        MaestroExport maestroPl =  impMaestro.obtenerMaestro2(p_maestro);
        logger.error("Prueba01");
        List<HashMap<String, Object>> list = maestroPl.getData();
        logger.error("cantidad de REG MET : " + list.size());
        for (HashMap<String, Object> map : list) {
            for (HashMap.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if(key.equalsIgnoreCase("CXPXD")){
                    v_cxpxd = String.valueOf(value);
                }
            }

        }
        /*---------------------------------------------------------------------------------------------------------------*/
        MaestroImportsKey maestro_cons1 = new MaestroImportsKey();
        String[] fields_cons1 = {"VAL01"};
        List<MaestroOptions> options_cons1 = new ArrayList<MaestroOptions>();
        List<MaestroOptionsKey> options2_cons1 = new ArrayList<MaestroOptionsKey>();

        MaestroOptions item_option_cons1 = new MaestroOptions();
        item_option_cons1.setWa("CDCNS ='64'");
        options_cons1.add(item_option_cons1);

        maestro_cons1.setDelimitador("|");
        maestro_cons1.setFields(fields_cons1);
        maestro_cons1.setNo_data("");
        maestro_cons1.setOption(options_cons1);
        maestro_cons1.setOptions(options2_cons1);
        maestro_cons1.setOrder("");
        maestro_cons1.setP_user(p_user);
        maestro_cons1.setRowcount(0);
        maestro_cons1.setRowskips(0);
        maestro_cons1.setTabla("ZFLCNS");

        MaestroExport maestro_const1 =  impMaestro.obtenerMaestro2(maestro_cons1);
        List<HashMap<String, Object>> list_cons1 = maestro_const1.getData();
        for (HashMap<String, Object> map : list_cons1) {
            for (HashMap.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if(key.equalsIgnoreCase("VAL01")){
                    v_porcMaxProc = String.valueOf(value);
                }
            }

        }
        /*---------------------------------------------------------------------------------------------------------------*/
        MaestroImportsKey maestro_cons2 = new MaestroImportsKey();
        String[] fields_cons2 = {"VAL01"};
        List<MaestroOptions> options_cons2 = new ArrayList<MaestroOptions>();
        List<MaestroOptionsKey> options2_cons2 = new ArrayList<MaestroOptionsKey>();

        MaestroOptions item_option_cons2 = new MaestroOptions();
        item_option_cons2.setWa("CDCNS ='65'");
        options_cons2.add(item_option_cons2);

        maestro_cons2.setDelimitador("|");
        maestro_cons2.setFields(fields_cons2);
        maestro_cons2.setNo_data("");
        maestro_cons2.setOption(options_cons2);
        maestro_cons2.setOptions(options2_cons2);
        maestro_cons2.setOrder("");
        maestro_cons2.setP_user(p_user);
        maestro_cons2.setRowcount(0);
        maestro_cons2.setRowskips(0);
        maestro_cons2.setTabla("ZFLCNS");

        MaestroExport maestro_const2 =  impMaestro.obtenerMaestro2(maestro_cons2);
        List<HashMap<String, Object>> list_cons2 = maestro_const2.getData();
        for (HashMap<String, Object> map : list_cons2) {
            for (HashMap.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if(key.equalsIgnoreCase("VAL01")){
                    v_horasProc = String.valueOf(value);
                }
            }

        }
        /*------------------------------------------------------------------------------------------------------*/
        BigDecimal capProc = new BigDecimal(v_cxpxd);
        BigDecimal porcMaxProcBD = new BigDecimal(v_porcMaxProc);
        BigDecimal horasProcBD = new BigDecimal(v_horasProc);
        BigDecimal calculado = capProc.multiply(porcMaxProcBD).multiply(horasProcBD) ;
        BigDecimal declarado = new BigDecimal(p_pescDecl);
        BigDecimal diferencia = declarado.subtract(calculado) ;

        if (diferencia.compareTo(new BigDecimal(0)) == -1){
            diferencia = new BigDecimal(0);
            r_diferencia = String.valueOf(diferencia);
        } else {
            r_diferencia = String.valueOf(diferencia);
        }

        return r_diferencia;

    }catch(Exception e){
        String mensaje = e.getMessage();
        return mensaje;
    }

    }

    public List<EmbarcacionesDto> EmbarcacionesFiltradas(List<EmbarcacionesDto> listaEmbarcaciones, String nroFilas){
        List<EmbarcacionesDto> embarcacion_Filtrada = new ArrayList<EmbarcacionesDto>();
        int ultimaFilaRec = listaEmbarcaciones.size() - Integer.parseInt(nroFilas);
        logger.error("ENTRO EmbarcacionesFiltradas : " + ultimaFilaRec + " - " +  listaEmbarcaciones.size());

        if(ultimaFilaRec < 0){
            embarcacion_Filtrada = listaEmbarcaciones;
        }else{
            for(int indice = listaEmbarcaciones.size() - 1;indice>=ultimaFilaRec;indice--)
            {
                logger.error("ENTRO EmbarcacionesFiltradas");
                EmbarcacionesDto embarcacion_item =  new EmbarcacionesDto();
                embarcacion_item = listaEmbarcaciones.get(indice);
                embarcacion_Filtrada.add(embarcacion_item);
            }
        }

        //logger.error("Embarcacion cantidad : " + embarcacion_Filtrada.size());

        return embarcacion_Filtrada;
    }


}
