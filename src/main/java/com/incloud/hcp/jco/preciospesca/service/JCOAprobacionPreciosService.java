package com.incloud.hcp.jco.preciospesca.service;

import com.incloud.hcp.jco.preciospesca.dto.AprobacionPrecioExports;
import com.incloud.hcp.jco.preciospesca.dto.AprobacionPreciosImports;

public interface JCOAprobacionPreciosService {
    AprobacionPrecioExports AprobarPrecios(AprobacionPreciosImports imports) throws Exception;
}
