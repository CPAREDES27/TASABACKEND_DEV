package com.incloud.hcp.jco.gestionpesca.service;

import com.incloud.hcp.jco.gestionpesca.dto.*;
import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.maestro.dto.MensajeDto;

import java.util.List;

public interface JCOEmbarcacionService {
    List<EmbarcacionDto> listaEmbarcacion(String condicion) throws Exception;

    FlotaDto obtenerDistribucionFlota(String user) throws Exception;

    MareaDto consultaMarea(MareaOptions marea) throws Exception;

    HorometroExport consultarHorometro(HorometroDto horometro) throws Exception;
    MensajeDto crearMareaPropios(MarEventoDtoImport imports) throws Exception;
    String ValidarBodegaCert(BodegaImport imports) throws Exception;
}
