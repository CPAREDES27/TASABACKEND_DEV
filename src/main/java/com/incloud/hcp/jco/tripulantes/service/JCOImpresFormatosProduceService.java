package com.incloud.hcp.jco.tripulantes.service;

import com.incloud.hcp.jco.tripulantes.dto.ImpresFormatosProduceExports;
import com.incloud.hcp.jco.tripulantes.dto.ImpresFormatosProduceImports;

public interface JCOImpresFormatosProduceService {

    ImpresFormatosProduceExports ImpresionFormatosProduce(ImpresFormatosProduceImports imports)throws Exception;

}
