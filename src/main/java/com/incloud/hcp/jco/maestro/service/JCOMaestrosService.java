package com.incloud.hcp.jco.maestro.service;

import com.incloud.hcp.jco.consultaGeneral.dto.RolExport;
import com.incloud.hcp.jco.consultaGeneral.dto.RolImport;
import com.incloud.hcp.jco.maestro.dto.*;

import java.util.ArrayList;
import java.util.List;

public interface JCOMaestrosService {

    MaestroExport obtenerMaestro (MaestroImports imports) throws Exception;
    MaestroExport obtenerMaestro3 (MaestroImportsKey imports) throws Exception;
    MaestroExport obtenerMaestro2 (MaestroImportsKey imports) throws Exception;
    MaestroExport obtenerArmador(BusquedaArmadorDTO codigo) throws Exception;
    UpdateTableExports editarMaestro (MaestroEditImports imports) throws Exception;

    UpdateTableExports editarMaestro2 (MaestroEditImport imports) throws Exception;
    MaestroExport ConsultaRol(RolImport importsParam)throws Exception;
    MensajeDto2 editarMaestro3(MaestroEditImport imports) throws Exception;
    AppMaestrosExports appMaestros(AppMaestrosImports imports)throws Exception;
    AyudaBusquedaExports AyudasBusqueda(AyudaBusquedaImports importsParam)throws Exception;
    CampoTablaExports UpdateEmbarcacionMasivo(UpdateEmbarcaMasivoImports imports)throws Exception;
    CampoTablaExports UpdateTripulantesMasivo(UpdateTripuMasivoImports imports)throws Exception;
    CampoTablaExports UpdateMasivo(UpdateMasivoImports imports)throws Exception;
    UpdateTableExports Update_Table_Maestro(HiscomDTOImport imports) throws Exception;
    ArrayList<EstructurasRfc> obtenerEstructurasRfc(String funcion)throws  Exception;
    MaestroExport obtenerRegistrosMaestro (MaestroImportsKey imports) throws Exception;
}
