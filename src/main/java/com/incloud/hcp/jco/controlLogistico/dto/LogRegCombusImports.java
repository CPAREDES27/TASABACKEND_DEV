package com.incloud.hcp.jco.controlLogistico.dto;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
//import org.omg.CORBA.OBJ_ADAPTER;

import java.util.HashMap;
import java.util.List;

public class LogRegCombusImports {

    private String p_user;
    private String p_tope;
    private String p_lcco;
    private String p_canti;
    private List<MaestroOptions> option;
    private List<MaestroOptionsKey> options;
    private List<LogRegistroCombusDto> str_lgcco;

    public List<LogRegistroCombusDto> getStr_lgcco() {
        return str_lgcco;
    }

    public void setStr_lgcco(List<LogRegistroCombusDto> str_lgcco) {
        this.str_lgcco = str_lgcco;
    }

    public List<MaestroOptions> getOption() {
        return option;
    }

    public void setOption(List<MaestroOptions> option) {
        this.option = option;
    }

    public List<MaestroOptionsKey> getOptions() {
        return options;
    }

    public void setOptions(List<MaestroOptionsKey> options) {
        this.options = options;
    }

    private String[] fieldsStr_csmar;
    private String[] fieldsStr_csmaj;
    private String[] fieldsStr_lgcco;
    private String[] fieldsT_mensaje;

    public String[] getFieldsStr_csmar() {
        return fieldsStr_csmar;
    }

    public void setFieldsStr_csmar(String[] fieldsStr_csmar) {
        this.fieldsStr_csmar = fieldsStr_csmar;
    }

    public String[] getFieldsStr_csmaj() {
        return fieldsStr_csmaj;
    }

    public void setFieldsStr_csmaj(String[] fieldsStr_csmaj) {
        this.fieldsStr_csmaj = fieldsStr_csmaj;
    }

    public String[] getFieldsStr_lgcco() {
        return fieldsStr_lgcco;
    }

    public void setFieldsStr_lgcco(String[] fieldsStr_lgcco) {
        this.fieldsStr_lgcco = fieldsStr_lgcco;
    }

    public String[] getFieldsT_mensaje() {
        return fieldsT_mensaje;
    }

    public void setFieldsT_mensaje(String[] fieldsT_mensaje) {
        this.fieldsT_mensaje = fieldsT_mensaje;
    }

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public String getP_tope() {
        return p_tope;
    }

    public void setP_tope(String p_tope) {
        this.p_tope = p_tope;
    }

    public String getP_lcco() {
        return p_lcco;
    }

    public void setP_lcco(String p_lcco) {
        this.p_lcco = p_lcco;
    }

    public String getP_canti() {
        return p_canti;
    }

    public void setP_canti(String p_canti) {
        this.p_canti = p_canti;
    }

}
