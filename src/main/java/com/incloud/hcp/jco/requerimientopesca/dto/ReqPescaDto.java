package com.incloud.hcp.jco.requerimientopesca.dto;

import java.util.HashMap;
import java.util.List;

public class ReqPescaDto {
    private String mensaje;
    private List<RequerimientoPesca> s_reqpesca;
    private List<HashMap<String, Object>> s_mensaje;

    public List<HashMap<String, Object>> getS_mensaje() {
        return s_mensaje;
    }

    public void setS_mensaje(List<HashMap<String, Object>> s_mensaje) {
        this.s_mensaje = s_mensaje;
    }

    public List<RequerimientoPesca> getS_reqpesca() {
        return s_reqpesca;
    }

    public void setS_reqpesca(List<RequerimientoPesca> s_reqpesca) {
        this.s_reqpesca = s_reqpesca;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
