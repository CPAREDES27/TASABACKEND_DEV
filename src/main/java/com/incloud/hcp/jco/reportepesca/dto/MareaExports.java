package com.incloud.hcp.jco.reportepesca.dto;

import java.util.HashMap;
import java.util.List;

public class MareaExports {
    private List<HashMap<String, Object>> options;
    private List<HashMap<String, Object>> s_marea;
    private String mensaje;

    public List<HashMap<String, Object>> getOptions() {
        return options;
    }

    public void setOptions(List<HashMap<String, Object>> options) {
        this.options = options;
    }

    public List<HashMap<String, Object>> getS_marea() {
        return s_marea;
    }

    public void setS_marea(List<HashMap<String, Object>> s_marea) {
        this.s_marea = s_marea;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
