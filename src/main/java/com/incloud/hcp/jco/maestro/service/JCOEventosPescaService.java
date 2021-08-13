package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.EventosPescaDto;
import com.incloud.hcp.jco.maestro.dto.EventosPescaImports;

public interface JCOEventosPescaService {

    EventosPescaDto ListarEventosPesca(EventosPescaImports imports)throws Exception;
}
