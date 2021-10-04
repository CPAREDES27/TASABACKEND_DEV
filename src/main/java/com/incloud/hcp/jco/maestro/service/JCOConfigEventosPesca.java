package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.util.Mensaje;

public interface JCOConfigEventosPesca {
    EventosPesca2Exports ListarEventoPesca(EventosPesca2Imports imports)throws Exception;
    Mensaje EditarEventosPesca(EventosPescaEdit2Imports imports)throws Exception;
}
