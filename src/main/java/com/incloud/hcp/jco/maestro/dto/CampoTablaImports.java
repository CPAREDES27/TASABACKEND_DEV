package com.incloud.hcp.jco.maestro.dto;

import java.util.List;

public class CampoTablaImports {
    private String p_user;
    private List<SetDto> str_set;

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public List<SetDto> getStr_set() {
        return str_set;
    }

    public void setStr_set(List<SetDto> str_set) {
        this.str_set = str_set;
    }
}
