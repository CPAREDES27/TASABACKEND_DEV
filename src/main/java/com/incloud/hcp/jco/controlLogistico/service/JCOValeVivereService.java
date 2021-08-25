package com.incloud.hcp.jco.controlLogistico.service;

import com.incloud.hcp.jco.controlLogistico.dto.ValeViveresExports;
import com.incloud.hcp.jco.controlLogistico.dto.ControlLogImports;
import com.incloud.hcp.jco.controlLogistico.dto.VvGuardaExports;

public interface JCOValeVivereService {

    ValeViveresExports ListarValeViveres(ControlLogImports imports)throws Exception;
    VvGuardaExports GuardarValeViveres(ControlLogImports imports)throws Exception;
}
