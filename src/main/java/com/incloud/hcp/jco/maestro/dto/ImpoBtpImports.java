package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class ImpoBtpImports {
    private String ip_tpcarga;
    private List<HashMap<String, Object>> cuotas_armadores;
    private List<HashMap<String, Object>> embarcaciones;

    public String getIp_tpcarga() {
        return ip_tpcarga;
    }

    public void setIp_tpcarga(String ip_tpcarga) {
        this.ip_tpcarga = ip_tpcarga;
    }

    public List<HashMap<String, Object>> getCuotas_armadores() {
        return cuotas_armadores;
    }

    public void setCuotas_armadores(List<HashMap<String, Object>> cuotas_armadores) {
        this.cuotas_armadores = cuotas_armadores;
    }

    public List<HashMap<String, Object>> getEmbarcaciones() {
        return embarcaciones;
    }

    public void setEmbarcaciones(List<HashMap<String, Object>> embarcaciones) {
        this.embarcaciones = embarcaciones;
    }
}
