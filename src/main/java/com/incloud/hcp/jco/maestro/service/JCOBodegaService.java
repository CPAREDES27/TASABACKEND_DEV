package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.gestionpesca.dto.Options;
import com.incloud.hcp.jco.maestro.dto.BodegaDto;

import java.util.List;

public interface JCOBodegaService {

    List<BodegaDto> BuscarBodegas(List<Options> options)throws Exception;
}
