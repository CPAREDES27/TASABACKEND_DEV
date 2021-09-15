package com.incloud.hcp.jco.tolvas.dto;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;

import java.util.List;

public class RegistroTolvasImports {

    private String p_user;
    private String rowcount;
    private String[] fields;
    private List<MaestroOptions> p_option;
    private List<MaestroOptionsKey> p_options;

    public List<MaestroOptions> getP_option() {
        return p_option;
    }

    public void setP_option(List<MaestroOptions> p_option) {
        this.p_option = p_option;
    }

    public List<MaestroOptionsKey> getP_options() {
        return p_options;
    }

    public void setP_options(List<MaestroOptionsKey> p_options) {
        this.p_options = p_options;
    }

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

}
