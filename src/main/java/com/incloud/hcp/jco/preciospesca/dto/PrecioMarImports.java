package com.incloud.hcp.jco.preciospesca.dto;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;

import java.util.List;

public class PrecioMarImports {
    private String p_user;
    private String p_indpr;
    private String p_rows;
    private String p_calidad;
    private String p_flag;
    private List<MaestroOptions> p_option;
    private List<MaestroOptionsKey> p_options;
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

    public String getP_calidad() {
        return p_calidad;
    }

    public void setP_calidad(String p_calidad) {
        this.p_calidad = p_calidad;
    }

    public String getP_flag() {
        return p_flag;
    }

    public void setP_flag(String p_flag) {
        this.p_flag = p_flag;
    }

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
}
