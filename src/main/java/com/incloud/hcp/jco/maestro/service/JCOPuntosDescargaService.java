package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.PuntosDescargaDto;

import java.util.List;

public interface JCOPuntosDescargaService {
    List<PuntosDescargaDto> obtenerPuntosDescarga(String usuario)throws Exception;

}
