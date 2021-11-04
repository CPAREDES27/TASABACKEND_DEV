package com.incloud.hcp.jco.controlLogistico.service;

import com.incloud.hcp.jco.controlLogistico.dto.LogRegCombusExports;
import com.incloud.hcp.jco.controlLogistico.dto.LogRegCombusImports;

public interface JCOLogRegisCombusService {

    LogRegCombusExports Listar(LogRegCombusImports imports)throws Exception;
    LogRegCombusExports Nuevo(LogRegCombusImports imports)throws Exception;
}
