package com.incloud.hcp.jco.distribucionflota.service.impl;

import com.incloud.hcp.EmbarcacionDto;
import com.incloud.hcp.jco.distribucionflota.dto.*;
import com.incloud.hcp.jco.distribucionflota.service.JCODistribucionFlotaService;
import com.incloud.hcp.jco.maestro.dto.EmbarcacionImports;
import com.incloud.hcp.jco.maestro.dto.MaestroExport;
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
public class JCODistribucionFlotaImpl implements JCODistribucionFlotaService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
            logger.error("ListarDistribucionFlota1");

            EjecutarRFC exec = new EjecutarRFC();
            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);

            JCoRepository repo = destination.getRepository();
            JCoFunction function = repo.getFunction(Constantes.ZFL_RFC_DISTR_FLOTA);
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
                                n_embarcacion.setEstSisFrio((s_str_di.getString("ESTSF")));

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
            dto.setListaZonas(zonas);

        }catch (Exception e){
            dto.setMensaje(e.getMessage());
        }
        return dto;

    }

}
