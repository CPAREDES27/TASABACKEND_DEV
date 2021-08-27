package com.incloud.hcp.jco.controlLogistico.service;

import com.incloud.hcp.jco.controlLogistico.dto.ConsultaHorometroExports;
import com.incloud.hcp.jco.controlLogistico.dto.ConsultaHorometroImports;

public interface JCOConsultaHorometroService {

    ConsultaHorometroExports Listar(ConsultaHorometroImports imports)throws Exception;

}
