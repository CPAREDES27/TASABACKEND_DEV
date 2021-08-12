package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.STR_SETDto;
import com.incloud.hcp.util.Mensaje;
import com.incloud.hcp.util.Tablas;

public interface JCOCapacidadTanquesService {

    Mensaje EditarCaptanques(STR_SETDto ctdto, String tabla)throws Exception;
}
