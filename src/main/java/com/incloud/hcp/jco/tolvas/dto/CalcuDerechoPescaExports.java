package com.incloud.hcp.jco.tolvas.dto;

import java.util.HashMap;
import java.util.List;

public class CalcuDerechoPescaExports {

    private List<HashMap<String, Object>> t_mensaje;
    private List<HashMap<String, Object>> s_derecho;
    private List<HashMap<String, Object>> str_dps;
    private String mensaje;

    public List<HashMap<String, Object>> getStr_dps() {
        return str_dps;
    }

    public void setStr_dps(List<HashMap<String, Object>> str_dps) {
        this.str_dps = str_dps;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<HashMap<String, Object>> getT_mensaje() {
        return t_mensaje;
    }

    public void setT_mensaje(List<HashMap<String, Object>> t_mensaje) {
        this.t_mensaje = t_mensaje;
    }

    public List<HashMap<String, Object>> getS_derecho() {
        return s_derecho;
    }

    public void setS_derecho(List<HashMap<String, Object>> s_derecho) {
        this.s_derecho = s_derecho;
    }


}
