package com.incloud.hcp.jco.tripulantes.service;

import com.incloud.hcp.jco.tripulantes.dto.RegistrosZarpeExports;
import com.incloud.hcp.jco.tripulantes.dto.RegistrosZarpeImports;

public interface JCORegistroZarpeService {

    RegistrosZarpeExports RegistroZarpe(RegistrosZarpeImports imports) throws Exception;
}
