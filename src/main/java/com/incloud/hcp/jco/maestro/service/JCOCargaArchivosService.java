package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.util.Mensaje;

public interface JCOCargaArchivosService {

    Mensaje CargaArchivo(CargaArchivoImports imports)throws Exception;
    CargaDescargaArchivosExports CargaDescargaArchivos(CargaDescargaArchivosImports imports)throws Exception;
    ImpoBtpExports CargaDinamicaArchivos(ImpoBtpImports imports) throws Exception;

}
