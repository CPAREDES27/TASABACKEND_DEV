package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class CargaDescargaArchivosExports {

    private String e_trama;
    private List<HashMap<String, Object>> t_mensaje;
    private String mensaje;

    public String getE_trama() {
        return e_trama;
    }

    public void setE_trama(String e_trama) {
        this.e_trama = e_trama;
    }

    public List<HashMap<String, Object>> getT_mensaje() {
        return t_mensaje;
    }

    public void setT_mensaje(List<HashMap<String, Object>> t_mensaje) {
        this.t_mensaje = t_mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
