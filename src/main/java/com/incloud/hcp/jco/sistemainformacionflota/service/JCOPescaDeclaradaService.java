package com.incloud.hcp.jco.sistemainformacionflota.service;

import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaDeclaradaDiariaExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaDeclaradaDiariaImports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaDeclaradaExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaDeclaradaImports;

public interface JCOPescaDeclaradaService {

    PescaDeclaradaExports PescaDeclarada(PescaDeclaradaImports imports)throws Exception;
    PescaDeclaradaDiariaExports PescaDeclaradaDiaria(PescaDeclaradaDiariaImports imports)throws Exception;
}
