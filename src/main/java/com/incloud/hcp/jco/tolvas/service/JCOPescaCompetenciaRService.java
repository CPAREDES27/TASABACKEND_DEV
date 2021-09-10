package com.incloud.hcp.jco.tolvas.service;

import com.incloud.hcp.jco.tolvas.dto.PescaCompetenciaRExports;
import com.incloud.hcp.jco.tolvas.dto.PescaCompetenciaRImports;

public interface JCOPescaCompetenciaRService {

    PescaCompetenciaRExports PescaCompetencia(PescaCompetenciaRImports imports)throws Exception;

}
