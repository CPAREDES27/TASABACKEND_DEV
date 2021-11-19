package com.incloud.hcp.jco.tolvas.service;

import com.incloud.hcp.jco.tolvas.dto.*;

public interface JCOPescaCompetenciaRService {

    PescaCompetenciaRExports PescaCompetencia(PescaCompetenciaRImports imports)throws Exception;
    DescargaExportDto ejecutarPrograma(DescargaImportDto imports)throws Exception;
    PeriodoDtoExport validarPeriodo(PeriodoDtoImport imports)throws Exception;


}
