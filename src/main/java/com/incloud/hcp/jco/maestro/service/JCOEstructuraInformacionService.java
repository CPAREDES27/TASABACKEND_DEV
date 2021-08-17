package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.EstructuraInformacionImports;
import com.incloud.hcp.jco.maestro.dto.MensajeDto;

public interface JCOEstructuraInformacionService {

    MensajeDto EditarEstructuraInf(EstructuraInformacionImports estructuraInformacionImports)throws Exception;
}
