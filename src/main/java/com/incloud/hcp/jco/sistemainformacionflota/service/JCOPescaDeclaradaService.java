package com.incloud.hcp.jco.sistemainformacionflota.service;

import com.incloud.hcp.jco.sistemainformacionflota.dto.*;

public interface JCOPescaDeclaradaService {

    PescaDeclaradaExports PescaDeclarada(PescaDeclaradaImports imports)throws Exception;
    PescaDeclaradaDiariaExports PescaDeclaradaDiaria(PescaDeclaradaDiariaImports imports)throws Exception;
    PescaDeclaradaDifeExports PescaDeclaradaDife(PescaDeclaradaDifeImports imports)throws Exception;
}
