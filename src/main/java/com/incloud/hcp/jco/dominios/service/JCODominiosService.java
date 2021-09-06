package com.incloud.hcp.jco.dominios.service;

import com.incloud.hcp.jco.dominios.dto.DominioDto;
import com.incloud.hcp.jco.dominios.dto.DominiosExports;
import com.incloud.hcp.jco.dominios.dto.DominiosImports;

import java.util.HashMap;
import java.util.List;

public interface JCODominiosService {

    DominioDto  Listar(DominiosImports imports)throws Exception;
}

