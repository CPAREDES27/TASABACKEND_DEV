package com.incloud.hcp.jco.preciospesca.dto;

import java.util.List;

public class AprobacionPreciosImports {
    private String p_user;
    private List<Set> str_set;

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public List<Set> getStr_set() {
        return str_set;
    }

    public void setStr_set(List<Set> str_set) {
        this.str_set = str_set;
    }
}
