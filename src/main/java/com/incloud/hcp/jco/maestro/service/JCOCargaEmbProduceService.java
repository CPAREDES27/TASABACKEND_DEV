package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.CargaEmbProduceExports;
import com.incloud.hcp.jco.maestro.dto.CargaEmbProduceImports;

public interface JCOCargaEmbProduceService {

    CargaEmbProduceExports CargaEmbarcaciones(CargaEmbProduceImports importsParams)throws Exception;
}
