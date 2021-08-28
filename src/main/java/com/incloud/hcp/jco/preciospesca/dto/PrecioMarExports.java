package com.incloud.hcp.jco.preciospesca.dto;

import java.util.HashMap;
import java.util.List;

public class PrecioMarExports {
    private List<HashMap<String, Object>> str_pm;
    private List<HashMap<String, Object>> t_mensaje;
    private String mensaje;

    public List<HashMap<String, Object>> getStr_pm() {
        return str_pm;
    }

    public void setStr_pm(List<HashMap<String, Object>> str_pm) {
        this.str_pm = str_pm;
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
