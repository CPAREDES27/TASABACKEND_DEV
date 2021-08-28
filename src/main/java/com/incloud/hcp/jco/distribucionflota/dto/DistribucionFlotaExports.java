package com.incloud.hcp.jco.distribucionflota.dto;

import java.util.List;

public class DistribucionFlotaExports {

    private List<ZonasDto> ListaZonas;
    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<ZonasDto> getListaZonas() {
        return ListaZonas;
    }

    public void setListaZonas(List<ZonasDto> listaZonas) {
        ListaZonas = listaZonas;
    }
}

