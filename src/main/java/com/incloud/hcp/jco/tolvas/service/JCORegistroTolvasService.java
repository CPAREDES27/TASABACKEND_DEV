package com.incloud.hcp.jco.tolvas.service;

import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.tolvas.dto.RegistroTolvasImports;

public interface JCORegistroTolvasService {
    MaestroExport Listar(RegistroTolvasImports imports)throws Exception;

}
