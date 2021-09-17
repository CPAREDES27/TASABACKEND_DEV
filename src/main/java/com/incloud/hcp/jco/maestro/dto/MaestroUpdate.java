package com.incloud.hcp.jco.maestro.dto;

public class MaestroUpdate implements Comparable<MaestroUpdate> {
    private String field;
    private String valor;
    private String orden;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    @Override
    public int compareTo(MaestroUpdate o) {
        return orden.compareTo(o.getOrden());
    }
}
