package com.incloud.hcp.jco.tripulantes.service;

import com.incloud.hcp.jco.tripulantes.dto.PDFExports;
import com.incloud.hcp.jco.tripulantes.dto.PDFZarpeImports;

public interface JCOPDFZarpeService {

    PDFExports GenerarPDFZarpe(PDFZarpeImports imports)throws Exception;
    PDFExports GenerarPDFTravesia(PDFZarpeImports imports)throws Exception;
}
