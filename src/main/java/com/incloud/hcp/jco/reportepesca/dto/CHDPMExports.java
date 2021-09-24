package com.incloud.hcp.jco.reportepesca.dto;

import java.util.HashMap;
import java.util.List;

public class CHDPMExports {
    private HashMap<String, Object> mchpm;
    private List<HashMap<String, Object>> t_dchpm;
    private List<HashMap<String, Object>> t_bodeg;
    private String mensaje;

    public HashMap<String, Object> getMchpm() {
        return mchpm;
    }

    public void setMchpm(HashMap<String, Object> mchpm) {
        this.mchpm = mchpm;
    }

    public List<HashMap<String, Object>> getT_dchpm() {
        return t_dchpm;
    }

    public void setT_dchpm(List<HashMap<String, Object>> t_dchpm) {
        this.t_dchpm = t_dchpm;
    }

    public List<HashMap<String, Object>> getT_bodeg() {
        return t_bodeg;
    }

    public void setT_bodeg(List<HashMap<String, Object>> t_bodeg) {
        this.t_bodeg = t_bodeg;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
