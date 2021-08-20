package com.incloud.hcp.jco.reportepesca.dto;

import java.util.List;

public class DescargasImports {
    private String p_user;
    private String p_rows;
    private List<MaestroOptionsDescarga> p_options;

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public String getP_rows() {
        return p_rows;
    }

    public void setP_rows(String p_rows) {
        this.p_rows = p_rows;
    }

    public List<MaestroOptionsDescarga> getP_options() {
        return p_options;
    }

    public void setP_options(List<MaestroOptionsDescarga> p_options) {
        this.p_options = p_options;
    }
}
