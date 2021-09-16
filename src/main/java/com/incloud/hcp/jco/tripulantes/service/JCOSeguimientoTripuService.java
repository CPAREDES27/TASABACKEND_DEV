package com.incloud.hcp.jco.tripulantes.service;

import com.incloud.hcp.jco.tripulantes.dto.SeguimientoTripuExports;
import com.incloud.hcp.jco.tripulantes.dto.SeguimientoTripuImports;

public interface JCOSeguimientoTripuService {

    SeguimientoTripuExports SeguimientoTripulantes(SeguimientoTripuImports imports)throws Exception;
}
