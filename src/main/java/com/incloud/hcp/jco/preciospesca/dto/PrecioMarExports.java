package com.incloud.hcp.jco.preciospesca.dto;

import java.util.HashMap;
import java.util.List;

public class PrecioMarExports {
    private List<HashMap<String, Object>> str_pm;
    private String mensaje;

    public List<HashMap<String, Object>> getStr_pm() {
        return str_pm;
    }

    public void setStr_pm(List<HashMap<String, Object>> str_pm) {
        this.str_pm = str_pm;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
