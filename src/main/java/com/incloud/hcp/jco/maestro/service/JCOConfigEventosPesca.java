package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.EventosPesca2Exports;
import com.incloud.hcp.jco.maestro.dto.EventosPesca2Imports;
import com.incloud.hcp.jco.maestro.dto.EventosPescaEditImports;
import com.incloud.hcp.jco.maestro.dto.EventosPescaImports;
import com.incloud.hcp.util.Mensaje;

public interface JCOConfigEventosPesca {
    EventosPesca2Exports ListarEventoPesca(EventosPesca2Imports imports)throws Exception;
    Mensaje EditarEventosPesca(EventosPescaEditImports imports)throws Exception;
}
