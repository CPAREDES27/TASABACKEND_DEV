package com.incloud.hcp.jco.politicaprecios.dto;

import java.util.List;

public class PrecioPescaImports {
    private String rowcount;
    private String p_user;
    private List<MaestroOptionsPrecioPesca> options;

    public String getRowcount() {
        return rowcount;
    }

    public void setRowcount(String rowcount) {
        this.rowcount = rowcount;
    }

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public List<MaestroOptionsPrecioPesca> getOptions() {
        return options;
    }

    public void setOptions(List<MaestroOptionsPrecioPesca> options) {
        this.options = options;
    }
}
