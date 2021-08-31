package com.incloud.hcp.jco.preciospesca.service;

import com.incloud.hcp.jco.preciospesca.dto.BonoExport;
import com.incloud.hcp.jco.preciospesca.dto.BonoImport;

public interface JCOBonosService {
    BonoExport AgregarBono(BonoImport imports) throws Exception;
}
