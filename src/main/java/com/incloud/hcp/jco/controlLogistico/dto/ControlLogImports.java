package com.incloud.hcp.jco.controlLogistico.dto;

public class ControlLogImports {

    private String p_user;
    private String rowcount;
    private String[] fields;


    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }


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
}
