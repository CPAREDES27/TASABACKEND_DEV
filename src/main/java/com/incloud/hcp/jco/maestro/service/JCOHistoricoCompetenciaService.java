package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.STR_SETDto;
import com.incloud.hcp.util.Mensaje;

public interface JCOHistoricoCompetenciaService {

    Mensaje EditarHistoricoCompetencia(STR_SETDto ctdto, String tabla)throws Exception;

}
