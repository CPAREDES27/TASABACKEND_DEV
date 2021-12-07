package com.incloud.hcp.jco.tripulantes.service;

import com.incloud.hcp.jco.tripulantes.dto.*;

public interface JCOTrabajoFueraFaenaService {

    TrabajoFueraFaenaExports TrabajoFueraFaenaTransporte(TrabajoFueraFaenaImports imports)throws Exception;
    GuardaTrabajoExports GuardaTrabajo(GuardaTrabajoImports imports)throws Exception;
    TrabajoFueraFaenaDetalleExports DetalleTrabajoFueraFaenaTransporte(TrabajoFueraFaenaDetalleImports imports)throws Exception;
    TrabajoDetalleDtoExports DetalleTrabajoFueraFaenaTransportez(TrabajoFueraFaenaDetalleImports imports)throws Exception;
    SemanaDto ObtenerSemanas()throws Exception;

    }
