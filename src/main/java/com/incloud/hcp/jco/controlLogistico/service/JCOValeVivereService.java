package com.incloud.hcp.jco.controlLogistico.service;

import com.incloud.hcp.jco.controlLogistico.dto.ControlLogExports;
import com.incloud.hcp.jco.controlLogistico.dto.ControlLogImports;
import com.incloud.hcp.jco.controlLogistico.dto.VvGuardaExports;
import com.incloud.hcp.jco.controlLogistico.dto.VvGuardaImports;

public interface JCOValeVivereService {

    ControlLogExports ListarValeViveres(ControlLogImports imports)throws Exception;
    VvGuardaExports GuardarValeViveres(VvGuardaImports imports)throws Exception;
}
