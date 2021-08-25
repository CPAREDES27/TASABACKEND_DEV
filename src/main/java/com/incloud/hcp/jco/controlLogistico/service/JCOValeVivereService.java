package com.incloud.hcp.jco.controlLogistico.service;

import com.incloud.hcp.jco.controlLogistico.dto.ValeViveresExports;
import com.incloud.hcp.jco.controlLogistico.dto.ValeViveresImports;
import com.incloud.hcp.jco.controlLogistico.dto.VvGuardaExports;

public interface JCOValeVivereService {

    ValeViveresExports ListarValeViveres(ValeViveresImports imports)throws Exception;
    VvGuardaExports GuardarValeViveres(ValeViveresImports imports)throws Exception;
}
