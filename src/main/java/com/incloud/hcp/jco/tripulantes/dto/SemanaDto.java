package com.incloud.hcp.jco.tripulantes.dto;

import java.util.HashMap;
import java.util.List;

public class SemanaDto {


    private List<HashMap<String, Object>> listAñoActual;
    private List<HashMap<String, Object>> listAñoAnterior;

    public List<HashMap<String, Object>> getListAñoActual() {
        return listAñoActual;
    }

    public void setListAñoActual(List<HashMap<String, Object>> listAñoActual) {
        this.listAñoActual = listAñoActual;
    }

    public List<HashMap<String, Object>> getListAñoAnterior() {
        return listAñoAnterior;
    }

    public void setListAñoAnterior(List<HashMap<String, Object>> listAñoAnterior) {
        this.listAñoAnterior = listAñoAnterior;
    }
}
