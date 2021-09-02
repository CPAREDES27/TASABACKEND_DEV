package com.incloud.hcp.jco.gestionpesca.service;

import com.incloud.hcp.jco.gestionpesca.dto.Options;
import com.incloud.hcp.jco.gestionpesca.dto.PlantasDto;
import com.incloud.hcp.jco.gestionpesca.dto.TipoEmbarcacionDto;
import com.incloud.hcp.jco.maestro.dto.MaestroExport;


import java.util.List;

public interface JCOTipoEmbarcacionService {
    List<TipoEmbarcacionDto> listaTipoEmbarcacion(String usuario) throws Exception;
    MaestroExport listarPlantas(String usuario) throws  Exception;
}
