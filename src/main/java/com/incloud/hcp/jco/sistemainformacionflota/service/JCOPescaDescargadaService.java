package com.incloud.hcp.jco.sistemainformacionflota.service;

import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaDescargadaExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.PescaDescargadaImports;

public interface JCOPescaDescargadaService {

    PescaDescargadaExports PescaDescargada(PescaDescargadaImports imports)throws Exception;

}
