package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.CalenTempPescaExports;
import com.incloud.hcp.jco.maestro.dto.CalenTempPescaImports;

public interface JCOCalendTempPescaService {
    CalenTempPescaExports BorrarRegistro(CalenTempPescaImports imports)throws Exception;
}
