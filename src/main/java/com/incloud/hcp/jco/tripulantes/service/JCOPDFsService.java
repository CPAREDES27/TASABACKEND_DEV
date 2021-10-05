package com.incloud.hcp.jco.tripulantes.service;

import com.incloud.hcp.jco.tripulantes.dto.PDFExports;
import com.incloud.hcp.jco.tripulantes.dto.PDFZarpeImports;
import com.incloud.hcp.jco.tripulantes.dto.ProtestosImports;
import com.incloud.hcp.jco.tripulantes.dto.RolTripulacionImports;

public interface JCOPDFsService {

    PDFExports GenerarPDFZarpe(PDFZarpeImports imports)throws Exception;
    PDFExports GenerarPDFTravesia(PDFZarpeImports imports)throws Exception;
    PDFExports GenerarPDFProtestos(ProtestosImports imports)throws Exception;
    PDFExports GenerarPDFZarpeTravesia(PDFZarpeImports imports)throws Exception;
    PDFExports GenerarPDFRolTripulacion(RolTripulacionImports imports)throws Exception;
}
