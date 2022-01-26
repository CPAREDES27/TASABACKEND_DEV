package com.incloud.hcp.jco.sistemainformacionflota.service;

import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaPorEmbarcaExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaPorEmbarcaImports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaPorEmbarcaRepExports;

public interface JCOPescaPorEmbarcacionService {

    PescaPorEmbarcaExports PescaPorEmbarcacion(PescaPorEmbarcaImports imports)throws Exception;
    PescaPorEmbarcaRepExports ExportPescaPorEmbarcacion(PescaPorEmbarcaImports imports)throws Exception;
}
