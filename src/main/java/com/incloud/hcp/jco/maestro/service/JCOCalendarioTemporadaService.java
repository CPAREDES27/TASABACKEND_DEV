package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.CalendarioTemporadaExports;
import com.incloud.hcp.jco.maestro.dto.CalendarioTemporadaImports;

public interface JCOCalendarioTemporadaService {

    CalendarioTemporadaExports Guadar(CalendarioTemporadaImports imports)throws Exception;
}
