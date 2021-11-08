package com.incloud.hcp.jco.maestro.dto;

import java.util.List;

public class UpdateEmbarcaMasivoImports {

    private String p_user;
    private String id;
    private List<UpdateEmbarcaMasivoDto> str_set;

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<UpdateEmbarcaMasivoDto> getStr_set() {
        return str_set;
    }

    public void setStr_set(List<UpdateEmbarcaMasivoDto> str_set) {
        this.str_set = str_set;
    }
}
