package com.incloud.hcp.jco.gestionpesca.service.impl;

import com.incloud.hcp.jco.gestionpesca.dto.TipoEmbarcacionDto;

import java.util.List;

public interface JCOTipoEmbarcacionService {
    List<TipoEmbarcacionDto> listaTipoEmbarcacion(String condicion) throws Exception;
}
