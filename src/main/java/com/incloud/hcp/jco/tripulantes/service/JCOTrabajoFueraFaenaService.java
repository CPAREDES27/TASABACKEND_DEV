package com.incloud.hcp.jco.tripulantes.service;

import com.incloud.hcp.jco.tripulantes.dto.TrabajoFueraFaenaExports;
import com.incloud.hcp.jco.tripulantes.dto.TrabajoFueraFaenaImports;

public interface JCOTrabajoFueraFaenaService {

    TrabajoFueraFaenaExports TrabajoFueraFaenaTransporte(TrabajoFueraFaenaImports imports)throws Exception;
}
