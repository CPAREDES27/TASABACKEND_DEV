package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.*;

import java.util.List;

public interface JCOEmbarcacionService {

     MaestroExport ListarEmbarcaciones(EmbarcacionImports importsParam)throws Exception;
     MaestroExport BuscarEmbarcaciones(BusquedaEmbarcacionImports importsParam)throws Exception;
     BusqAdicEmbarExports BusquedaAdicionalEmbarca(BusqAdicEmbarImports importsParam) throws Exception;
     MensajeDto Nuevo(EmbarcacionNuevImports importsParam)throws Exception;
     MensajeDto Editar(EmbarcacionEditImports importsParam)throws Exception;

}
