package com.incloud.hcp.util.Mail.Dto;

import java.util.ArrayList;

public class EmbarcacionForEmailDto {
    private String descripcion;
    private ArrayList<EventoForEmailDto> eventos;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<EventoForEmailDto> getEventos() {
        return eventos;
    }

    public void setEventos(ArrayList<EventoForEmailDto> eventos) {
        this.eventos = eventos;
    }
}
