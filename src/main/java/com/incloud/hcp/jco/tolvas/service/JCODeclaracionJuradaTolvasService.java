package com.incloud.hcp.jco.tolvas.service;

import com.incloud.hcp.jco.tolvas.dto.DeclaracionJuradaExports;
import com.incloud.hcp.jco.tolvas.dto.DeclaracionJuradaImports;

public interface JCODeclaracionJuradaTolvasService {

    DeclaracionJuradaExports DeclaracionJuradaTolvas(DeclaracionJuradaImports imports)throws Exception;
}
