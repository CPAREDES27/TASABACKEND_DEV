package com.incloud.hcp.jco.gestionpesca.dto;

import java.util.HashMap;
import java.util.List;

public class ConfigReservas {
    private String BWART;

    public String getBWART() {
        return BWART;
    }

    public void setBWART(String BWART) {
        this.BWART = BWART;
    }

    public String getMATNR() {
        return MATNR;
    }

    public void setMATNR(String MATNR) {
        this.MATNR = MATNR;
    }

    public String getWERKS() {
        return WERKS;
    }

    public void setWERKS(String WERKS) {
        this.WERKS = WERKS;
    }

    public List<HashMap<String, Object>> getAlmacenes() {
        return Almacenes;
    }

    public void setAlmacenes(List<HashMap<String, Object>> almacenes) {
        Almacenes = almacenes;
    }

    private String MATNR;
    private String WERKS;
    private List<HashMap<String, Object>> Almacenes;

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    private String Mensaje;

}
