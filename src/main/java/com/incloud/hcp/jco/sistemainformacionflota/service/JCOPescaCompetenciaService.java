package com.incloud.hcp.jco.sistemainformacionflota.service;

import com.incloud.hcp.jco.controlLogistico.dto.AnalisisCombusRegExport;
import com.incloud.hcp.jco.sistemainformacionflota.dto.*;


public interface JCOPescaCompetenciaService {

    PescaCompetenciaRadialExports PescaCompetenciaRadial(PescaCompetenciaRadialImports imports)throws Exception;
    PescaCompetenciaProduceExports PescaCompetenciaProduce(PescaCompetenciaProduceImports imports) throws Exception;
    AnalisisCombusRegExport ExportPescaCompetencia(PesCompetenciaProdImports imports)throws Exception;
}
