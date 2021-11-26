package com.incloud.hcp.jco.controlLogistico.dto;

import java.util.ArrayList;

public class EmbarcacionDto {
    private String descripcion;
    private ArrayList<EventoDto> eventos;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<EventoDto> getEventos() {
        return eventos;
    }

    public void setEventos(ArrayList<EventoDto> eventos) {
        this.eventos = eventos;
    }
}
