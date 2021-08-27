package com.incloud.hcp.jco.controlLogistico.service;

import com.incloud.hcp.jco.controlLogistico.dto.AnalisisCombusLisExports;
import com.incloud.hcp.jco.controlLogistico.dto.AnalisisCombusLisImports;
import com.incloud.hcp.jco.controlLogistico.dto.AnalisisCombusImports;
import com.incloud.hcp.jco.controlLogistico.dto.ControlLogExports;

public interface JCOAnalisisCombustibleService {

    AnalisisCombusLisExports Listar(AnalisisCombusLisImports imports)throws Exception;
    ControlLogExports Detalle(AnalisisCombusImports imports)throws Exception;
}
