package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.MensajeDto;
import com.incloud.hcp.jco.maestro.dto.ReglasDatosExterEditImports;
import com.incloud.hcp.jco.maestro.dto.ReglasDatosExterNuevImports;

public interface JCOReglasDatosExterService {

    MensajeDto Nuevo(ReglasDatosExterNuevImports importsParam)throws Exception;
    MensajeDto Editar(ReglasDatosExterEditImports importsParam)throws Exception;


}
