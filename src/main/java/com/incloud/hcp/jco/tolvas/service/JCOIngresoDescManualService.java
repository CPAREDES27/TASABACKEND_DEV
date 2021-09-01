package com.incloud.hcp.jco.tolvas.service;

import com.incloud.hcp.jco.tolvas.dto.IngresoDesManualExports;
import com.incloud.hcp.jco.tolvas.dto.IngresoDescManualImports;

public interface JCOIngresoDescManualService {
    IngresoDesManualExports Guardar(IngresoDescManualImports imports)throws Exception;

}
