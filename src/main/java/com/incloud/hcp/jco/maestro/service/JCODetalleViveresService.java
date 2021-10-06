package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.DetalleViveresExports;
import com.incloud.hcp.jco.maestro.dto.DetalleViveresImports;

public interface JCODetalleViveresService {

    DetalleViveresExports DetalleImpresionViveres(DetalleViveresImports imports)throws Exception;
}
