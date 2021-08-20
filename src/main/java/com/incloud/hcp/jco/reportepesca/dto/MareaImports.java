package com.incloud.hcp.jco.reportepesca.dto;

import java.util.List;

public class MareaImports {
    private String p_user;
    private String rowcount;
    private List<MaestroOptionsMarea> p_options;

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

    public List<MaestroOptionsMarea> getP_options() {
        return p_options;
    }

    public void setP_options(List<MaestroOptionsMarea> p_options) {
        this.p_options = p_options;
    }
}
