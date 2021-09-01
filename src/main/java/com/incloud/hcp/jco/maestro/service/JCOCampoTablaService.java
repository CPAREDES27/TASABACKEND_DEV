package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.CampoTablaExports;
import com.incloud.hcp.jco.maestro.dto.CampoTablaImports;

public interface JCOCampoTablaService {
    CampoTablaExports Actualizar(CampoTablaImports imports) throws Exception;
}
