package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.maestro.dto.PuntosDescargaImports;
import com.incloud.hcp.util.UsuarioDto;

public interface JCOPuntosDescargaService {
    MaestroExport ListarPuntosDes(PuntosDescargaImports imports)throws Exception;
}
