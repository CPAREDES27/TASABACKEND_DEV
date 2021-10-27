package com.incloud.hcp.jco.tripulantes.service;

import com.incloud.hcp.jco.tripulantes.dto.*;

public interface JCOPDFsService {

    PDFExports GenerarPDFZarpe(PDFZarpeImports imports)throws Exception;
    PDFExports GenerarPDFTravesia(PDFZarpeImports imports)throws Exception;
    PDFExports GenerarPDFProtestos(ProtestosImports imports)throws Exception;
    PDFExports GenerarPDFZarpeTravesia(PDFZarpeImports imports)throws Exception;
    PDFExports GenerarPDFRolTripulacion(RolTripulacionImports imports)throws Exception;
    PDFExports GenerarPDFTrimestral(PDFZarpeImports imports)throws Exception;
    PDFExports GenerarPDFTrabajoFF()throws Exception;
    PDFExports GenerarPDFValeViveres(PDFValeViveresImports imports)throws Exception;
    PDFExports GenerarPDFProduce()throws Exception;
    PDFExports GenerarPDFProduceResumen()throws Exception;

}
