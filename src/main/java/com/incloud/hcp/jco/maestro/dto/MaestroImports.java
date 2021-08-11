package com.incloud.hcp.jco.maestro.dto;

import java.util.List;

public class MaestroImports {

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getDelimitador() {
        return delimitador;
    }

    public void setDelimitador(String delimitador) {
        this.delimitador = delimitador;
    }

    public String getNo_data() {
        return no_data;
    }

    public void setNo_data(String no_data) {
        this.no_data = no_data;
    }

    public int getRowskips() {
        return rowskips;
    }

    public void setRowskips(int rowskips) {
        this.rowskips = rowskips;
    }

    public int getRowcount() {
        return rowcount;
    }

    public void setRowcount(int rowcount) {
        this.rowcount = rowcount;
    }

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<MaestroOptions> getOptions() {
        return options;
    }

    public void setOptions(List<MaestroOptions> options) {
        this.options = options;
    }

    private String tabla;
    private String delimitador;
    private String no_data;
    private int rowskips;
    private int rowcount;
    private String p_user;
    private String order;
    private List<MaestroOptions> options;

}
