package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.ConstantesDto;

import java.util.List;

public interface JCOConstantesSevice {
    List<ConstantesDto> ListarConstantes()throws Exception;

}
