package com.incloud.hcp.jco.tripulantes.service;

import com.incloud.hcp.jco.tripulantes.dto.RolTripulacionExports;
import com.incloud.hcp.jco.tripulantes.dto.RolTripulacionImports;

public interface JCORolTripulacionService {

    RolTripulacionExports RolTripulacion(RolTripulacionImports imports)throws Exception;
}
