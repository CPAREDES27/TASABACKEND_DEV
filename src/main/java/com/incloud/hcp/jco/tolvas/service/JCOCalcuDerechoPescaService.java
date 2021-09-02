package com.incloud.hcp.jco.tolvas.service;

import com.incloud.hcp.jco.tolvas.dto.CalcuDerechoPescaExports;
import com.incloud.hcp.jco.tolvas.dto.CalcuDerechoPescaImports;

public interface JCOCalcuDerechoPescaService {

    CalcuDerechoPescaExports Listar(CalcuDerechoPescaImports imports)throws Exception;
}
