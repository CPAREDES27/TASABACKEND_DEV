package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.*;

import java.util.List;

public interface JCOMaestrosService {

    MaestroExport obtenerMaestro (MaestroImports imports) throws Exception;

    MaestroExport obtenerMaestro2 (MaestroImportsKey imports) throws Exception;

    MensajeDto editarMaestro (MaestroEditImports imports) throws Exception;

    MaestroExport editarMaestro2 (MaestroEditImport imports) throws Exception;
    AppMaestrosExports appMaestros(AppMaestrosImports imports)throws Exception;

}
