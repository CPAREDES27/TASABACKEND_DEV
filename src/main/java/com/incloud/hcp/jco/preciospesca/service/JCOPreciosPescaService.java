package com.incloud.hcp.jco.preciospesca.service;

import com.incloud.hcp.jco.preciospesca.dto.*;

public interface JCOPreciosPescaService {
    PrecioProbPescaExports ObtenerPrecioProbPesca(PrecioProbPescaImports imports) throws Exception;

    PrecioPescaExports ObtenerPrecioPesca(PrecioPescaImports imports) throws Exception;

    PrecioPescaMantExports MantPrecioPesca(PrecioPescaMantImports imports) throws Exception;

    ConsPrecioPescaExports ConsultarPrecioPesca(ConsPrecioPescaImports imports) throws Exception;
}
