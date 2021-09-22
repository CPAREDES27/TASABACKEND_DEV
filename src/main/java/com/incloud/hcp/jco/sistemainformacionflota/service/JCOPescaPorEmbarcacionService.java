package com.incloud.hcp.jco.sistemainformacionflota.service;

import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaPorEmbarcaExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaPorEmbarcaImports;

public interface JCOPescaPorEmbarcacionService {

    PescaPorEmbarcaExports PescaPorEmbarcacion(PescaPorEmbarcaImports imports)throws Exception;
}
