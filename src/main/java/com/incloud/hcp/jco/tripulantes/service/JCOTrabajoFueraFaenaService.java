package com.incloud.hcp.jco.tripulantes.service;

import com.incloud.hcp.jco.tripulantes.dto.*;

public interface JCOTrabajoFueraFaenaService {

    TrabajoFueraFaenaExports TrabajoFueraFaenaTransporte(TrabajoFueraFaenaImports imports)throws Exception;
    GuardaTrabajoExports GuardaTrabajo(GuardaTrabajoImports imports)throws Exception;
    TrabajoFueraFaenaExports DetalleTrabajoFueraFaenaTransporte(TrabajoFueraFaenaDetalleImports imports)throws Exception;

    }
