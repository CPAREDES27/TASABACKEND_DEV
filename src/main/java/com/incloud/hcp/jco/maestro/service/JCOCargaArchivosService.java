package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.CargaArchivoImports;
import com.incloud.hcp.util.Mensaje;

public interface JCOCargaArchivosService {

    Mensaje CargaArchivo(CargaArchivoImports imports)throws Exception;


}
