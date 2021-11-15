package com.incloud.hcp.jco.maestro.dto;

import java.util.List;

public class UpdateMasivoImports {

    private List<UpdateMasivoDto> str_set;
    private String p_user;
    private String tabla;

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public List<UpdateMasivoDto> getStr_set() {
        return str_set;
    }

    public void setStr_set(List<UpdateMasivoDto> str_set) {
        this.str_set = str_set;
    }
    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }
}
