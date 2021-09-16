package com.incloud.hcp.jco.tripulantes.dto;

import java.util.HashMap;
import java.util.List;

public class AvisoAveriaExports {

    private List<HashMap<String, Object>> t_zpmavi;
    private List<HashMap<String, Object>> t_textos;
    private String mensaje;

    public List<HashMap<String, Object>> getT_zpmavi() {
        return t_zpmavi;
    }

    public void setT_zpmavi(List<HashMap<String, Object>> t_zpmavi) {
        this.t_zpmavi = t_zpmavi;
    }

    public List<HashMap<String, Object>> getT_textos() {
        return t_textos;
    }

    public void setT_textos(List<HashMap<String, Object>> t_textos) {
        this.t_textos = t_textos;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
