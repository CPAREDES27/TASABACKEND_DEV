package com.incloud.hcp.jco.consultaGeneral.service;

import com.incloud.hcp.jco.consultaGeneral.dto.ConsultaGeneralExports;
import com.incloud.hcp.jco.consultaGeneral.dto.ConsultaGeneralImports;

public interface JCOConsultaGeneralService {

    ConsultaGeneralExports ConsultaGeneral(ConsultaGeneralImports importsParam)throws Exception;
}
