package com.incloud.hcp.jco.tolvas.service;

import com.incloud.hcp.jco.tolvas.dto.DeclaracionJuradaExports;
import com.incloud.hcp.jco.tolvas.dto.DeclaracionJuradaImports;
import com.incloud.hcp.jco.tripulantes.dto.PDFExports;

public interface JCODeclaracionJuradaTolvasService {

    DeclaracionJuradaExports DeclaracionJuradaTolvas(DeclaracionJuradaImports imports)throws Exception;
    PDFExports PlantillaPDF(DeclaracionJuradaImports imports)throws Exception;
}
