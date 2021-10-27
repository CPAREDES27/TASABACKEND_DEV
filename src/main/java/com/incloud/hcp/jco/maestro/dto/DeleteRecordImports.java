package com.incloud.hcp.jco.maestro.dto;

public class DeleteRecordImports {
    private String  i_table;
    private String  p_user;
    private String  t_data;

    public String getT_data() {
        return t_data;
    }

    public void setT_data(String t_data) {
        this.t_data = t_data;
    }

    public String getI_table() {
        return i_table;
    }

    public void setI_table(String i_table) {
        this.i_table = i_table;
    }

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }
}
