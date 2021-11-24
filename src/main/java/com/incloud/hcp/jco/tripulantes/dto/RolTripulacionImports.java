package com.incloud.hcp.jco.tripulantes.dto;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;

import java.util.List;

public class RolTripulacionImports {

    private String p_tope;
    private String p_cdrtr;
    private String p_canti;
    private List<MaestroOptions> p_option;
    private List<MaestroOptionsKey> p_options;
    private String[] fieldsT_zartr;
    private String[] fieldsT_dzart;

    public String[] getFieldsT_zartr() {
        return fieldsT_zartr;
    }

    public void setFieldsT_zartr(String[] fieldsT_zartr) {
        this.fieldsT_zartr = fieldsT_zartr;
    }

    public String[] getFieldsT_dzart() {
        return fieldsT_dzart;
    }

    public void setFieldsT_dzart(String[] fieldsT_dzart) {
        this.fieldsT_dzart = fieldsT_dzart;
    }

    public String getP_tope() {
        return p_tope;
    }

    public void setP_tope(String p_tope) {
        this.p_tope = p_tope;
    }

    public String getP_cdrtr() {
        return p_cdrtr;
    }

    public void setP_cdrtr(String p_cdrtr) {
        this.p_cdrtr = p_cdrtr;
    }

    public String getP_canti() {
        return p_canti;
    }

    public void setP_canti(String p_canti) {
        this.p_canti = p_canti;
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
