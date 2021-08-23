package com.incloud.hcp.jco.reportepesca.service;

import com.incloud.hcp.jco.reportepesca.dto.CHDPMExports;
import com.incloud.hcp.jco.reportepesca.dto.CHDPMImports;

public interface JCOCDHPMService {
    CHDPMExports ObtenerCHDPM(CHDPMImports imports) throws Exception;
}
