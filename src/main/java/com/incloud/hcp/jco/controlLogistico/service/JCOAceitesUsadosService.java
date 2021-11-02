package com.incloud.hcp.jco.controlLogistico.service;

import com.incloud.hcp.jco.controlLogistico.dto.AceitesUsadosExports;
import com.incloud.hcp.jco.controlLogistico.dto.AceitesUsadosImports;

public interface JCOAceitesUsadosService {

    AceitesUsadosExports Listar(AceitesUsadosImports imports)throws Exception;
    AceitesUsadosExports Nuevo(AceitesUsadosImports imports)throws Exception;
    AceitesUsadosExports Anular(AceitesUsadosImports imports)throws Exception;

}
