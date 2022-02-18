package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.gestionpesca.dto.BodegaImport;
import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.util.Mensaje;

import java.util.List;

public interface JCOEmbarcacionService {

     EmbarcacionExports ListarEmbarcaciones(EmbarcacionImports importsParam)throws Exception;
     MaestroExport BuscarEmbarcaciones(BusquedaEmbarcacionImports importsParam)throws Exception;
     BusqAdicEmbarExports BusquedaAdicionalEmbarca(BusqAdicEmbarImports importsParam) throws Exception;
     MensajeEmbarcaExport Nuevo(EmbarcacionNuevImports importsParam)throws Exception;
     Mensaje Editar(EmbarcacionEditImports importsParam)throws Exception;
     MensajeDto MoverEmbarcacion(MoverEmbarcaImports importsParam)throws Exception;
     CantidadTripulantesExports ObtenerCantidadTripulantes(CantidadTripulantesImports imports)throws Exception;

}
