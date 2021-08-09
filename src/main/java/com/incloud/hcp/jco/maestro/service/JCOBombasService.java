package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.BombasDto;

import java.util.ArrayList;
import java.util.List;

public interface JCOBombasService {

    List<BombasDto> ListarBombas()throws Exception;

}
