package com.incloud.hcp.jco.controlLogistico.dto;

import java.util.ArrayList;

public class EventoDto {
    private String nroEvento;
    private ArrayList<HorometroDto> horometrosAveriados;
    private ArrayList<SiniestroImports> siniestros;

    public String getNroEvento() {
        return nroEvento;
    }

    public void setNroEvento(String nroEvento) {
        this.nroEvento = nroEvento;
    }

    public ArrayList<HorometroDto> getHorometrosAveriados() {
        return horometrosAveriados;
    }

    public void setHorometrosAveriados(ArrayList<HorometroDto> horometrosAveriados) {
        this.horometrosAveriados = horometrosAveriados;
    }

    public ArrayList<SiniestroImports> getSiniestros() {
        return siniestros;
    }

    public void setSiniestros(ArrayList<SiniestroImports> siniestros) {
        this.siniestros = siniestros;
    }
}
