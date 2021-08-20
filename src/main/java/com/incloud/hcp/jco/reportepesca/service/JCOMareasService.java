package com.incloud.hcp.jco.reportepesca.service;

import com.incloud.hcp.jco.reportepesca.dto.MareaExports;
import com.incloud.hcp.jco.reportepesca.dto.MareaImports;

public interface JCOMareasService {
    MareaExports ObtenerMareas(MareaImports imports) throws Exception;
}
