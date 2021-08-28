package com.incloud.hcp.jco.distribucionflota.dto;

import java.util.List;

public class PlantasDto {
    private String PlantaName;
    private List<EmbarcacionesDto> ListaEmbarcaciones;

    public String getPlantaName() {
        return PlantaName;
    }

    public void setPlantaName(String plantaName) {
        PlantaName = plantaName;
    }

    public List<EmbarcacionesDto> getListaEmbarcaciones() {
        return ListaEmbarcaciones;
    }

    public void setListaEmbarcaciones(List<EmbarcacionesDto> listaEmbarcaciones) {
        ListaEmbarcaciones = listaEmbarcaciones;
    }
}
