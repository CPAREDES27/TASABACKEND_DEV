package com.incloud.hcp.jco.reportepesca.service;

import com.incloud.hcp.jco.reportepesca.dto.DescargasExports;
import com.incloud.hcp.jco.reportepesca.dto.DescargasImports;

public interface JCODescargasService {
    DescargasExports ObtenerDescargas(DescargasImports imports) throws Exception;
}
