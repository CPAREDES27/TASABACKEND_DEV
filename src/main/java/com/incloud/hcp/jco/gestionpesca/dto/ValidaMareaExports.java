package com.incloud.hcp.jco.gestionpesca.dto;

import java.util.HashMap;
import java.util.List;

public class ValidaMareaExports {

    private String p_correcto;
    private String p_primeracondicioncn;
    private String p_segundacondicions;
    private List<HashMap<String, Object>> t_mensaje;
    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getP_correcto() {
        return p_correcto;
    }

    public void setP_correcto(String p_correcto) {
        this.p_correcto = p_correcto;
    }

    public String getP_primeracondicioncn() {
        return p_primeracondicioncn;
    }

    public void setP_primeracondicioncn(String p_primeracondicioncn) {
        this.p_primeracondicioncn = p_primeracondicioncn;
    }

    public String getP_segundacondicions() {
        return p_segundacondicions;
    }

    public void setP_segundacondicions(String p_segundacondicions) {
        this.p_segundacondicions = p_segundacondicions;
    }

    public List<HashMap<String, Object>> getT_mensaje() {
        return t_mensaje;
    }

    public void setT_mensaje(List<HashMap<String, Object>> t_mensaje) {
        this.t_mensaje = t_mensaje;
    }
}
