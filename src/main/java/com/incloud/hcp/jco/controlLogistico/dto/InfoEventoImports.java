package com.incloud.hcp.jco.controlLogistico.dto;

public class InfoEventoImports {
    private String envioPrd;
    private EventoDto evento;

    public String getEnvioPrd() {
        return envioPrd;
    }

    public void setEnvioPrd(String envioPrd) {
        this.envioPrd = envioPrd;
    }

    public EventoDto getEvento() {
        return evento;
    }

    public void setEvento(EventoDto evento) {
        this.evento = evento;
    }
}
