package com.incloud.hcp.jco.tripulantes.service;

import com.incloud.hcp.jco.tripulantes.dto.ReporObservaTripuExports;
import com.incloud.hcp.jco.tripulantes.dto.ReporObservaTripuImports;

public interface JCOReporObservaTripuService {

    ReporObservaTripuExports ReporteObservacionesTripulantes(ReporObservaTripuImports imports)throws Exception;
}
