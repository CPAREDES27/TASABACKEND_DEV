package com.incloud.hcp.jco.controlLogistico.dto;

public class InfoEventoImports {
    private String envioPrd;
    private EventoForEmailDto evento;

    public String getEnvioPrd() {
        return envioPrd;
    }

    public void setEnvioPrd(String envioPrd) {
        this.envioPrd = envioPrd;
    }

    public EventoForEmailDto getEvento() {
        return evento;
    }

    public void setEvento(EventoForEmailDto evento) {
        this.evento = evento;
    }
}
