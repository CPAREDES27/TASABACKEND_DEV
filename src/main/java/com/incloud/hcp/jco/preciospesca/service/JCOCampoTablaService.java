package com.incloud.hcp.jco.preciospesca.service;

import com.incloud.hcp.jco.preciospesca.dto.CampoTablaExports;
import com.incloud.hcp.jco.preciospesca.dto.CampoTablaImports;

public interface JCOCampoTablaService {
    CampoTablaExports Actualizar(CampoTablaImports imports) throws Exception;

}
