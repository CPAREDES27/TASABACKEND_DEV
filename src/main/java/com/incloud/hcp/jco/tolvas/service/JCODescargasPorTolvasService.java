package com.incloud.hcp.jco.tolvas.service;

import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.maestro.dto.MaestroImportsKey;

public interface JCODescargasPorTolvasService {
    MaestroExport buscarDescargasPorTolvas(MaestroImportsKey imports) throws Exception;
}
