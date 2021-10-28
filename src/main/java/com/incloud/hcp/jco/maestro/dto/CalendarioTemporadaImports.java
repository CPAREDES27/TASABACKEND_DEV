package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class CalendarioTemporadaImports {

    private String p_user;
    private List<HashMap<String, Object>> t_cal;

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public List<HashMap<String, Object>> getT_cal() {
        return t_cal;
    }

    public void setT_cal(List<HashMap<String, Object>> t_cal) {
        this.t_cal = t_cal;
    }
}
