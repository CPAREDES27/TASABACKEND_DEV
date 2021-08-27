package com.incloud.hcp.jco.preciospesca.service;

import com.incloud.hcp.jco.preciospesca.dto.BonoExport;
import com.incloud.hcp.jco.preciospesca.dto.BonoImport;
import com.incloud.hcp.jco.preciospesca.dto.PrecioMarExports;
import com.incloud.hcp.jco.preciospesca.dto.PrecioMarImports;

public interface JCOPreciosAcopioService {
    PrecioMarExports ObtenerPrecioMar(PrecioMarImports imports) throws Exception;

    BonoExport AgregarBonos(BonoImport imports) throws Exception;
}
