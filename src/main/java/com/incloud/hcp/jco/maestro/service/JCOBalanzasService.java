package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.BalanzaDto;
import com.incloud.hcp.jco.maestro.dto.UnidadMedidaDto;

import java.util.List;

public interface JCOBalanzasService {

    List<UnidadMedidaDto> ListarUnidadMedida(String condicion)throws Exception;
    List<BalanzaDto> ListarBalanzas()throws Exception;

}
