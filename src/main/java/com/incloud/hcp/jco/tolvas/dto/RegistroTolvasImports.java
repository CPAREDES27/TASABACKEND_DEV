package com.incloud.hcp.jco.tolvas.dto;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;

import java.util.List;

public class RegistroTolvasImports {

    private String p_user;
    private String rowcount;
    private List<MaestroOptions> options;
    private String[] fields;

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public String getRowcount() {
        return rowcount;
    }

    public void setRowcount(String rowcount) {
        this.rowcount = rowcount;
    }

    public List<MaestroOptions> getOptions() {
        return options;
    }

    public void setOptions(List<MaestroOptions> options) {
        this.options = options;
    }
}
