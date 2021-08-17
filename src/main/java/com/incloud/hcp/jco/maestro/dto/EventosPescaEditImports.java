package com.incloud.hcp.jco.maestro.dto;

import java.util.HashMap;
import java.util.List;

public class EventosPescaEditImports {

    private String i_flag;
    private String p_user;
    private List<HashMap<String, Object>> estcep;
    private List<HashMap<String, Object>> estcce;


    public String getI_flag() {
        return i_flag;
    }

    public void setI_flag(String i_flag) {
        this.i_flag = i_flag;
    }

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public List<HashMap<String, Object>> getEstcep() {
        return estcep;
    }

    public void setEstcep(List<HashMap<String, Object>> estcep) {
        this.estcep = estcep;
    }

    public List<HashMap<String, Object>> getEstcce() {
        return estcce;
    }

    public void setEstcce(List<HashMap<String, Object>> estcce) {
        this.estcce = estcce;
    }



}
