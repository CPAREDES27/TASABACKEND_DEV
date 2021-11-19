package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.PescaCompCargaHistoricoExports;
import com.incloud.hcp.jco.maestro.dto.PescaCompCargaHistoricoImports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaDescargadaDiaResumImports;

public interface JCOHistoricoCompetenciaService {
    PescaCompCargaHistoricoExports CargaHistorico(PescaCompCargaHistoricoImports imports) throws Exception;
}
