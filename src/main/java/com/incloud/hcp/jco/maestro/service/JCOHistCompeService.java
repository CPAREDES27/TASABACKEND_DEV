package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.CapacidadTanquesImports;
import com.incloud.hcp.util.Mensaje;

public interface JCOHistCompeService {

    Mensaje Editar(CapacidadTanquesImports imports)throws Exception;

}
