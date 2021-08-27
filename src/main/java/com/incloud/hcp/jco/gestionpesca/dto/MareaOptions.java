package com.incloud.hcp.jco.gestionpesca.dto;

public class MareaOptions {

    private String user;
    private String p_marea;
    private String p_embarcacion;
    private String p_flag;
    private String[] fieldMarea;
    private String[] fieldEvento;
    private String[] fieldFLBSP;
    private String[] fieldPSCINC;

    public String[] getFieldFLBSP() {
        return fieldFLBSP;
    }

    public void setFieldFLBSP(String[] fieldFLBSP) {
        this.fieldFLBSP = fieldFLBSP;
    }

    public String[] getFieldPSCINC() {
        return fieldPSCINC;
    }

    public void setFieldPSCINC(String[] fieldPSCINC) {
        this.fieldPSCINC = fieldPSCINC;
    }

    public String[] getFieldMarea() {
        return fieldMarea;
    }

    public void setFieldMarea(String[] fieldMarea) {
        this.fieldMarea = fieldMarea;
    }

    public String[] getFieldEvento() {
        return fieldEvento;
    }

    public void setFieldEvento(String[] fieldEvento) {
        this.fieldEvento = fieldEvento;
    }

    public String getP_embarcacion() {
        return p_embarcacion;
    }

    public void setP_embarcacion(String p_embarcacion) {
        this.p_embarcacion = p_embarcacion;
    }

    public String getP_flag() {
        return p_flag;
    }

    public void setP_flag(String p_flag) {
        this.p_flag = p_flag;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getP_marea() {
        return p_marea;
    }

    public void setP_marea(String p_marea) {
        this.p_marea = p_marea;
    }
}
