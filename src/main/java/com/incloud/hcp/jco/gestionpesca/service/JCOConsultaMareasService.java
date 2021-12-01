package com.incloud.hcp.jco.gestionpesca.service;

import com.incloud.hcp.jco.gestionpesca.dto.ReabrirMareaImports;
import com.incloud.hcp.jco.maestro.dto.CampoTablaExports;

public interface JCOConsultaMareasService {
    CampoTablaExports reabrirMarea(ReabrirMareaImports imports) throws Exception;
}
