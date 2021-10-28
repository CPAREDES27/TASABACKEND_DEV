package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.DeleteRecordExports;
import com.incloud.hcp.jco.maestro.dto.DeleteRecordImports;

public interface JCODeleteRecordService {
    DeleteRecordExports BorrarRegistro(DeleteRecordImports imports)throws Exception;
}
