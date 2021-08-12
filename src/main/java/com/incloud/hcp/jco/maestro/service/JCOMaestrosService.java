package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.*;

import java.util.List;

public interface JCOMaestrosService {

    MaestroExport obtenerMaestro (MaestroImports imports) throws Exception;
    MensajeDto editarMaestro (MaestroEditImports imports) throws Exception;
    List<EmbarcacionDto> obtenerEmbarcaciones(EmbarcacionImports importsParam)throws Exception;
    List<PuntosDescargaDto> obtenerPuntosDescarga(String usuario)throws Exception;
    EmpresaDto obtenerEmpresa(EmpresaImports imports)throws Exception;
}
