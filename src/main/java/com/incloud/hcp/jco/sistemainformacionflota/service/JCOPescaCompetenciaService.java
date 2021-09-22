package com.incloud.hcp.jco.sistemainformacionflota.service;

import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaCompetenciaProduceExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaCompetenciaProduceImports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaCompetenciaRadialExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaCompetenciaRadialImports;


public interface JCOPescaCompetenciaService {

    PescaCompetenciaRadialExports PescaCompetenciaRadial(PescaCompetenciaRadialImports imports)throws Exception;
    PescaCompetenciaProduceExports PescaCompetenciaProduce(PescaCompetenciaProduceImports imports) throws Exception;
}
