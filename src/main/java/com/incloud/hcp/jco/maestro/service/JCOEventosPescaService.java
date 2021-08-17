package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.EventosPescaEditImports;
import com.incloud.hcp.jco.maestro.dto.EventosPescaExports;
import com.incloud.hcp.jco.maestro.dto.EventosPescaImports;
import com.incloud.hcp.util.Mensaje;

public interface JCOEventosPescaService {


    EventosPescaExports ListarEventoPesca(EventosPescaImports imports)throws Exception;
    Mensaje EditarEventosPesca(EventosPescaEditImports imports)throws Exception;

}
