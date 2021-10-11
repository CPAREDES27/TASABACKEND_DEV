package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.util.Mensaje;

public interface JCOEventosPescaService {


    EventosPescaExports ListarEventoPesca(EventosPescaImports imports)throws Exception;
    Mensaje EditarEventosPesca(EventosPescaEditImports imports)throws Exception;
    ConfiguracionEventoPescaExports ObtenerConfEventosPesca()throws Exception;
    HorometrosExport obtenerHorometros(HorometrosImportDto evento) throws Exception;
}
