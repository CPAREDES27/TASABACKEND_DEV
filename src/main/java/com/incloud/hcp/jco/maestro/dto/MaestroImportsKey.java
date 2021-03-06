package com.incloud.hcp.jco.maestro.dto;

import java.util.List;

public class MaestroImportsKey {
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


    public List<MaestroOptionsKey> getOptions() {
        return options;
    }

    public void setOptions(List<MaestroOptionsKey> options) {
        this.options = options;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    private String tabla;
    private String delimitador;

    public List<MaestroOptions> getOption() {
        return option;
    }

    public void setOption(List<MaestroOptions> option) {
        this.option = option;
    }

    public String getP_pag() {
        return p_pag;
    }

    public void setP_pag(String p_pag) {
        this.p_pag = p_pag;
    }

    private String p_pag;
    private String no_data;
    private int rowskips;
    private int rowcount;
    private String p_user;
    private String order;
    private List<MaestroOptions> option;
    private List<MaestroOptionsKey> options;
    private String[] fields;
}
