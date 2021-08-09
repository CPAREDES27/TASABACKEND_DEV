package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.BodegaDto;

import java.util.List;

public interface JCOBodegaService {

    List<BodegaDto> BuscarBodegas(BodegaDto bodegaDto)throws Exception;
}
