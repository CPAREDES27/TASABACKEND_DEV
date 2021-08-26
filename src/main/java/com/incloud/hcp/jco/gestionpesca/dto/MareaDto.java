package com.incloud.hcp.jco.gestionpesca.dto;

import java.util.HashMap;
import java.util.List;

public class MareaDto {
    private List<HashMap<String, Object>> s_marea;
    private List<HashMap<String, Object>> s_evento;
    private List<HashMap<String, Object>> str_flbsp;
    private List<HashMap<String, Object>> str_pscinc;
    private String mensaje;

    public List<HashMap<String, Object>> getStr_flbsp() {
        return str_flbsp;
    }

    public void setStr_flbsp(List<HashMap<String, Object>> str_flbsp) {
        this.str_flbsp = str_flbsp;
    }

    public List<HashMap<String, Object>> getStr_pscinc() {
        return str_pscinc;
    }

    public void setStr_pscinc(List<HashMap<String, Object>> str_pscinc) {
        this.str_pscinc = str_pscinc;
    }

    public List<HashMap<String, Object>> getS_marea() {
        return s_marea;
    }

    public void setS_marea(List<HashMap<String, Object>> s_marea) {
        this.s_marea = s_marea;
    }

    public List<HashMap<String, Object>> getS_evento() {
        return s_evento;
    }

    public void setS_evento(List<HashMap<String, Object>> s_evento) {
        this.s_evento = s_evento;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
