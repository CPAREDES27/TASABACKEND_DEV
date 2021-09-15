package com.incloud.hcp.jco.tripulantes.service;

import com.incloud.hcp.jco.tripulantes.dto.PDFZarpeExports;
import com.incloud.hcp.jco.tripulantes.dto.PDFZarpeImports;
import com.incloud.hcp.util.Mensaje;

public interface JCOPDFZarpeService {

    PDFZarpeExports GenerarPDF(PDFZarpeImports imports)throws Exception;
}
