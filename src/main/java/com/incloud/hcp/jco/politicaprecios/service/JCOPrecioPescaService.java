package com.incloud.hcp.jco.politicaprecios.service;

import com.incloud.hcp.jco.politicaprecios.dto.PrecioPescaExports;
import com.incloud.hcp.jco.politicaprecios.dto.PrecioPescaImports;

public interface JCOPrecioPescaService {
    PrecioPescaExports ObtenerPrecioPesca(PrecioPescaImports imports) throws Exception;
}
