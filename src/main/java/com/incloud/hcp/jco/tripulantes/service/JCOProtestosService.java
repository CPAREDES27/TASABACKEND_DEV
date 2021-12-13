package com.incloud.hcp.jco.tripulantes.service;

import com.incloud.hcp.jco.tripulantes.dto.ProtestoNuevoImport;
import com.incloud.hcp.jco.tripulantes.dto.ProtestosExports;
import com.incloud.hcp.jco.tripulantes.dto.ProtestosImports;

public interface JCOProtestosService {

    ProtestosExports Protestos(ProtestosImports imports)throws Exception;
    ProtestosExports ProtestosNuevo(ProtestoNuevoImport imports)throws Exception;
}
