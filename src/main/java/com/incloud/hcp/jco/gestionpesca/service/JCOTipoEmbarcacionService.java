package com.incloud.hcp.jco.gestionpesca.service;

import com.incloud.hcp.jco.gestionpesca.dto.Options;
import com.incloud.hcp.jco.gestionpesca.dto.PlantasDto;
import com.incloud.hcp.jco.gestionpesca.dto.TipoEmbarcacionDto;


import java.util.List;

public interface JCOTipoEmbarcacionService {
    List<TipoEmbarcacionDto> listaTipoEmbarcacion(List<Options> options) throws Exception;
    List<PlantasDto> listarPlantas() throws  Exception;
}
