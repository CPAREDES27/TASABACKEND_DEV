package com.incloud.hcp.jco.reportepesca.service;

import com.incloud.hcp.jco.reportepesca.dto.DescargasExports;
import com.incloud.hcp.jco.reportepesca.dto.DescargasImports;
import com.incloud.hcp.jco.reportepesca.dto.InterlocutorExports;
import com.incloud.hcp.jco.reportepesca.dto.InterlocutorImports;

public interface JCODescargasService {
    DescargasExports ObtenerDescargas(DescargasImports imports) throws Exception;

    InterlocutorExports AgregarInterlocutor(InterlocutorImports imports) throws Exception;
}
