package com.incloud.hcp.jco.controlLogistico.service;


import com.incloud.hcp.jco.controlLogistico.dto.RepModifDatCombusExports;
import com.incloud.hcp.jco.controlLogistico.dto.RepModifDatCombusImports;
import com.incloud.hcp.jco.controlLogistico.dto.RepModifDatCombusRegExports;
import com.incloud.hcp.jco.controlLogistico.dto.RepModifDatCombusRegImports;

public interface JCORepModifDatCombusService {

    RepModifDatCombusExports Listar(RepModifDatCombusImports imports)throws Exception;
    RepModifDatCombusRegExports Exportar(RepModifDatCombusImports imports) throws Exception;
}
