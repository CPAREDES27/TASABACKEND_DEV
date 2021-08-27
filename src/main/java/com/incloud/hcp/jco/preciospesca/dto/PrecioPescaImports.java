package com.incloud.hcp.jco.preciospesca.dto;

import java.util.List;

public class PrecioPescaImports {
    private String p_user;
    private List<MaestroOptionsPrecioPesca> p_options;

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public List<MaestroOptionsPrecioPesca> getP_options() {
        return p_options;
    }

    public void setP_options(List<MaestroOptionsPrecioPesca> p_options) {
        this.p_options = p_options;
    }
}
