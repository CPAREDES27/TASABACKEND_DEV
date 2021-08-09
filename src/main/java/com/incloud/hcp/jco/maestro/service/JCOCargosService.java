package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.CargosDto;

import java.util.List;

public interface JCOCargosService {

    List<CargosDto> ListarCargos()throws Exception;
}
