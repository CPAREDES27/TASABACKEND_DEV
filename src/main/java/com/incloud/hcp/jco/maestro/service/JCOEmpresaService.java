package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.EmpresaImports;
import com.incloud.hcp.jco.maestro.dto.MaestroExport;


public interface JCOEmpresaService {
    MaestroExport ListarEmpresas(EmpresaImports imports)throws Exception;

}
