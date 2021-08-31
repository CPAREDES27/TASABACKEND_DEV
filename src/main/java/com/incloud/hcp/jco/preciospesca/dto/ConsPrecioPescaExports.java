package com.incloud.hcp.jco.preciospesca.dto;

import java.util.HashMap;
import java.util.List;

public class ConsPrecioPescaExports {
    private List<HashMap<String, Object>> t_prepes;
    private String mensaje;

    public List<HashMap<String, Object>> getT_prepes() {
        return t_prepes;
    }

    public void setT_prepes(List<HashMap<String, Object>> t_prepes) {
        this.t_prepes = t_prepes;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
