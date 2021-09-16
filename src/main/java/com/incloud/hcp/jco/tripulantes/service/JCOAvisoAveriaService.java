package com.incloud.hcp.jco.tripulantes.service;

import com.incloud.hcp.jco.tripulantes.dto.AvisoAveriaExports;
import com.incloud.hcp.jco.tripulantes.dto.AvisoAveriaImports;

public interface JCOAvisoAveriaService {

    AvisoAveriaExports AvisoAveria(AvisoAveriaImports imports)throws Exception;
}
