package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.CapacidadTanquesImports;
import com.incloud.hcp.util.Mensaje;

public interface JCOCapacidadTanquesService {

    Mensaje Editar(CapacidadTanquesImports importsParam)throws Exception;
}
