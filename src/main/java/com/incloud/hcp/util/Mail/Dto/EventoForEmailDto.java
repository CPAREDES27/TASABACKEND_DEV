package com.incloud.hcp.util.Mail.Dto;

import com.incloud.hcp.jco.controlLogistico.dto.HorometroForEmailDto;
import com.incloud.hcp.jco.controlLogistico.dto.SiniestroForEmailDto;

import java.util.ArrayList;

public class EventoForEmailDto {
    private String nroEvento;
    private ArrayList<HorometroForEmailDto> horometrosAveriados;
    private ArrayList<SiniestroForEmailDto> siniestros;

    public String getNroEvento() {
        return nroEvento;
    }

    public void setNroEvento(String nroEvento) {
        this.nroEvento = nroEvento;
    }

    public ArrayList<HorometroForEmailDto> getHorometrosAveriados() {
        return horometrosAveriados;
    }

    public void setHorometrosAveriados(ArrayList<HorometroForEmailDto> horometrosAveriados) {
        this.horometrosAveriados = horometrosAveriados;
    }

    public ArrayList<SiniestroForEmailDto> getSiniestros() {
        return siniestros;
    }

    public void setSiniestros(ArrayList<SiniestroForEmailDto> siniestros) {
        this.siniestros = siniestros;
    }
}
