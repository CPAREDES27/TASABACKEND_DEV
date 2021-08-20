package com.incloud.hcp.jco.reportepesca.service;

import com.incloud.hcp.jco.reportepesca.dto.CalaExports;
import com.incloud.hcp.jco.reportepesca.dto.CalaImports;

public interface JCOCalasService {
    CalaExports obtenerCalas(CalaImports imports) throws Exception;
}
