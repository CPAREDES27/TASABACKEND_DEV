package com.incloud.hcp.jco.preciospesca.service;

import com.incloud.hcp.jco.preciospesca.dto.PrecioMarExports;
import com.incloud.hcp.jco.preciospesca.dto.PrecioMarImports;

public interface JCOPrecioMarService {
    PrecioMarExports ObtenerPrecioMar(PrecioMarImports imports) throws Exception;
}
