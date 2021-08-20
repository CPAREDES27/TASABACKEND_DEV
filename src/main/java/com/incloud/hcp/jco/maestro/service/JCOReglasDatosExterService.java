package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.MensajeDto;
import com.incloud.hcp.jco.maestro.dto.ReglasDatosExterEditImports;
import com.incloud.hcp.jco.maestro.dto.ReglasDatosExterNuevImports;

import java.util.List;

public interface JCOReglasDatosExterService {

    List<MensajeDto> Crear(ReglasDatosExterNuevImports importsParam)throws Exception;
    List<MensajeDto> Editar(ReglasDatosExterEditImports importsParam)throws Exception;


}
