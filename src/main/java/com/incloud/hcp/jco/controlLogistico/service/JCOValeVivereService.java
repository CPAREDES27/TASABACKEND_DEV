package com.incloud.hcp.jco.controlLogistico.service;

import com.incloud.hcp.jco.controlLogistico.dto.ControlLogExports;
import com.incloud.hcp.jco.controlLogistico.dto.ControlLogImports;
import com.incloud.hcp.jco.controlLogistico.dto.VvGuardaExports;

public interface JCOValeVivereService {

    ControlLogExports ListarValeViveres(ControlLogImports imports)throws Exception;
    VvGuardaExports GuardarValeViveres(ControlLogImports imports)throws Exception;
}
