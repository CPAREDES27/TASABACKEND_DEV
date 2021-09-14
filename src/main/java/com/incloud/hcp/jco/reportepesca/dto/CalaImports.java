package com.incloud.hcp.jco.reportepesca.dto;

import java.util.List;

public class CalaImports {
    private String rowcount;
    private String p_user;
    private List<MaestroOptionsMarea> options;

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

    public List<MaestroOptionsMarea> getOptions() {
        return options;
    }

    public void setOptions(List<MaestroOptionsMarea> options) {
        this.options = options;
    }
}
