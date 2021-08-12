package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.EmpresaDto;
import com.incloud.hcp.jco.maestro.dto.EmpresaImports;
import org.springframework.stereotype.Service;


public interface JCOEmpresaService {
    EmpresaDto obtenerEmpresa(EmpresaImports imports)throws Exception;

}
