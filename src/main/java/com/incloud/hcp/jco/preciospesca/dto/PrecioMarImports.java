package com.incloud.hcp.jco.preciospesca.dto;

import java.util.List;

public class PrecioMarImports {
    private String p_user;
    private String p_indpr;
    private String p_rows;
    private List<MaestroOptionsPrecioMar> p_options;

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public String getP_indpr() {
        return p_indpr;
    }

    public void setP_indpr(String p_indpr) {
        this.p_indpr = p_indpr;
    }

    public String getP_rows() {
        return p_rows;
    }

    public void setP_rows(String p_rows) {
        this.p_rows = p_rows;
    }

    public List<MaestroOptionsPrecioMar> getP_options() {
        return p_options;
    }

    public void setP_options(List<MaestroOptionsPrecioMar> p_options) {
        this.p_options = p_options;
    }
}
