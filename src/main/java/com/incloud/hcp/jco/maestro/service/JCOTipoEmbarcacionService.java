package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.TipoEmbarcacionDto;

import java.util.List;

public interface JCOTipoEmbarcacionService {
    List<TipoEmbarcacionDto> listaTipoEmbarcacion(String condicion) throws Exception;
}
