package com.incloud.hcp.jco.distribucionflota.service;


import com.incloud.hcp.jco.distribucionflota.dto.DistrFlotaOptExport;
import com.incloud.hcp.jco.distribucionflota.dto.DistrFlotaOptions;
import com.incloud.hcp.jco.distribucionflota.dto.DistribucionFlotaExports;
import com.incloud.hcp.jco.distribucionflota.dto.DistribucionFlotaImports;

public interface JCODistribucionFlotaService {

    DistribucionFlotaExports ListarDistribucionFlota(DistribucionFlotaImports importsParam)throws Exception;
    DistrFlotaOptExport ListarDistFltColumDinamic(DistrFlotaOptions importsParam)throws Exception;
}
