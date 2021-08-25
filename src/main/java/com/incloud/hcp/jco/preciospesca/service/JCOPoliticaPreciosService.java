package com.incloud.hcp.jco.preciospesca.service;

import com.incloud.hcp.jco.preciospesca.dto.PrecioPescaExports;
import com.incloud.hcp.jco.preciospesca.dto.PrecioPescaImports;
import com.incloud.hcp.jco.preciospesca.dto.PrecioPescaMantExports;
import com.incloud.hcp.jco.preciospesca.dto.PrecioPescaMantImports;

public interface JCOPoliticaPreciosService {
    PrecioPescaExports ObtenerPrecioPesca(PrecioPescaImports imports) throws Exception;

    PrecioPescaMantExports MantPrecioPesca(PrecioPescaMantImports imports) throws Exception;
}
