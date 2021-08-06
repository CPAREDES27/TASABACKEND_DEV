package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.AlmacenDto;
import com.incloud.hcp.jco.maestro.dto.AlmacenExtDto;
import com.incloud.hcp.jco.maestro.dto.PlantaDto;

import java.util.List;

public interface JCOAlmacenService {
    List<PlantaDto> BuscarPlantas(PlantaDto plantaDto)throws Exception;

    List<AlmacenExtDto> BuscarAlmacenExterno(AlmacenExtDto almacenExtDto)throws Exception;

    //List<AlmacenDto> BuscarAlmacen(AlmacenDto almacenDto)throws Exception;
}
