package com.incloud.hcp.jco.sistemainformacionflota.service;

import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaCompetenciaRadialExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaCompetenciaRadialImports;


public interface JCOPescaCompetenciaRadialService {

    PescaCompetenciaRadialExports PescaCompetenciaRadial(PescaCompetenciaRadialImports imports)throws Exception;
}
