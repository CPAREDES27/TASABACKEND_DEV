package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.CapacidadTanquesDto;

import java.util.List;

public interface JCOCapacidadTanquesService {
    List<CapacidadTanquesDto> BuscarCapacidadTanques(String tipoEmbarcacion, String nombreEmbarcacion)throws Exception;
}
