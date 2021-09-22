package com.incloud.hcp.jco.sistemainformacionflota.service;

import com.incloud.hcp.jco.sistemainformacionflota.dto.CompraCuotaTercerosExports;
import com.incloud.hcp.jco.sistemainformacionflota.dto.CompraCuotaTercerosImports;

public interface JCOCompraCuotaTercerosService {

    CompraCuotaTercerosExports CompraCuotaTerceros(CompraCuotaTercerosImports imports)throws Exception;
}
