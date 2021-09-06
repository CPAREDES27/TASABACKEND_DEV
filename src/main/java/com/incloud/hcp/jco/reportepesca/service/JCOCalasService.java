package com.incloud.hcp.jco.reportepesca.service;

import com.incloud.hcp.jco.reportepesca.dto.BiomasaExports;
import com.incloud.hcp.jco.reportepesca.dto.BiomasaImports;
import com.incloud.hcp.jco.reportepesca.dto.CalaExports;
import com.incloud.hcp.jco.reportepesca.dto.CalaImports;

public interface JCOCalasService {
    CalaExports ObtenerCalas(CalaImports imports) throws Exception;

    BiomasaExports ConsultarBiomasa(BiomasaImports imports) throws Exception;
}
