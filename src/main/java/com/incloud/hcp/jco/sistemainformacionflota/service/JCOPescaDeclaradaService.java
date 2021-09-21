package com.incloud.hcp.jco.sistemainformacionflota.service;

import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaDeclaradaExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaDeclaradaImports;

public interface JCOPescaDeclaradaService {

    PescaDeclaradaExports PescaDeclarada(PescaDeclaradaImports imports)throws Exception;
}
