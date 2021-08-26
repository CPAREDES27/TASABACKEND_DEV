package com.incloud.hcp.jco.controlLogistico.service;

import com.incloud.hcp.jco.controlLogistico.dto.ControlLogExports;
import com.incloud.hcp.jco.controlLogistico.dto.ControlLogImports;

public interface JCOAnalisisCombustibleService {

    ControlLogExports Listar(ControlLogImports imports)throws Exception;

}
