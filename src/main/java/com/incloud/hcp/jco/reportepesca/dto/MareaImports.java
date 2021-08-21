package com.incloud.hcp.jco.reportepesca.dto;

import java.util.List;

public class MareaImports {
    private String p_user;
    private String rowcount;
    private List<MaestroOptionsMarea> options;

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

    public List<MaestroOptionsMarea> getOptions() {
        return options;
    }

    public void setOptions(List<MaestroOptionsMarea> options) {
        this.options = options;
    }
}
