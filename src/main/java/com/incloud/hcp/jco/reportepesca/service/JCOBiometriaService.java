package com.incloud.hcp.jco.reportepesca.service;

import com.incloud.hcp.jco.reportepesca.dto.BiometriaExports;
import com.incloud.hcp.jco.reportepesca.dto.BiometriaImports;

public interface JCOBiometriaService {

    BiometriaExports ReporteBiometria(BiometriaImports imports)throws Exception;

}
