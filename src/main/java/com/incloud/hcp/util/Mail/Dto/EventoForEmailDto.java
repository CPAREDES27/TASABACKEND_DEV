package com.incloud.hcp.util.Mail.Dto;

import com.incloud.hcp.jco.controlLogistico.dto.HorometroForEmailDto;
import com.incloud.hcp.jco.controlLogistico.dto.SiniestroForEmailDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventoForEmailDto {
    private String nroEvento;
    private List<HashMap<String, Object>> horometrosAveriados;
    private ArrayList<SiniestroForEmailDto> siniestros;


    public List<HashMap<String, Object>> getHorometrosAveriados() {
        return horometrosAveriados;
    }

    public void setHorometrosAveriados(List<HashMap<String, Object>> horometrosAveriados) {
        this.horometrosAveriados = horometrosAveriados;
    }

    public String getNroEvento() {
        return nroEvento;
    }

    public void setNroEvento(String nroEvento) {
        this.nroEvento = nroEvento;
    }



    public ArrayList<SiniestroForEmailDto> getSiniestros() {
        return siniestros;
    }

    public void setSiniestros(ArrayList<SiniestroForEmailDto> siniestros) {
        this.siniestros = siniestros;
    }
}
