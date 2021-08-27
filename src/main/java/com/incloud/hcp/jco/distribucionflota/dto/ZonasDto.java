package com.incloud.hcp.jco.distribucionflota.dto;

import java.util.List;

public class ZonasDto {
    private String ZonaName;
    private List<PlantasDto> ListaPlantas;

    public List<PlantasDto> getListaPlantas() {
        return ListaPlantas;
    }

    public void setListaPlantas(List<PlantasDto> listaPlantas) {
        ListaPlantas = listaPlantas;
    }

    public String getZonaName() {
        return ZonaName;
    }

    public void setZonaName(String zonaName) {
        ZonaName = zonaName;
    }

}
