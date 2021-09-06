package com.incloud.hcp.jco.maestro.dto;

public class MaestroOptionsKey {
    private String key;
    private String valueLow;
    private String valueHigh;
    private String control;
    private String cantidad;

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValueLow() {
        return valueLow;
    }

    public void setValueLow(String valueLow) {
        this.valueLow = valueLow;
    }

    public String getValueHigh() {
        return valueHigh;
    }

    public void setValueHigh(String valueHigh) {
        this.valueHigh = valueHigh;
    }
}
