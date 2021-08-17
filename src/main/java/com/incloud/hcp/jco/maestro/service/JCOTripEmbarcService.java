package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.MensajeDto;
import com.incloud.hcp.jco.maestro.dto.TripuEmbarcaImports;
import com.incloud.hcp.util.Mensaje;

public interface JCOTripEmbarcService {

    Mensaje EditarTripuEmbarca(TripuEmbarcaImports imports)throws Exception;

}
