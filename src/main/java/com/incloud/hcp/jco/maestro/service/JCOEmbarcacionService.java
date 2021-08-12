package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.BusquedaEmbarcacionDto;
import com.incloud.hcp.jco.maestro.dto.BusquedaEmbarcacionImports;
import com.incloud.hcp.jco.maestro.dto.EmbarcacionDto;
import com.incloud.hcp.jco.maestro.dto.EmbarcacionImports;

import java.util.List;

public interface JCOEmbarcacionService {
    List<EmbarcacionDto> obtenerEmbarcaciones(EmbarcacionImports importsParam)throws Exception;
    List<BusquedaEmbarcacionDto> busquedaEmbarcaciones(BusquedaEmbarcacionImports importsParam)throws Exception;


}
