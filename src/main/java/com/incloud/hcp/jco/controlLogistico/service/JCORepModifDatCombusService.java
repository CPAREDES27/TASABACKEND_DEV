package com.incloud.hcp.jco.controlLogistico.service;


import com.incloud.hcp.jco.controlLogistico.dto.RepModifDatCombusExports;
import com.incloud.hcp.jco.controlLogistico.dto.RepModifDatCombusImports;

public interface JCORepModifDatCombusService {

    RepModifDatCombusExports Listar(RepModifDatCombusImports imports)throws Exception;
}
