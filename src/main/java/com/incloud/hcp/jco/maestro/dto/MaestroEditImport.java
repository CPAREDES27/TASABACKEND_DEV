package com.incloud.hcp.jco.maestro.dto;

import java.util.List;

public class MaestroEditImport {
    private String tabla;
    private String flag;
    private String p_case;
    private String p_user;
    private String data;
    private String fieldWhere;
    private String keyWhere;
    private List<MaestroUpdate> opcion;

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getP_case() {
        return p_case;
    }

    public void setP_case(String p_case) {
        this.p_case = p_case;
    }

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFieldWhere() {
        return fieldWhere;
    }

    public void setFieldWhere(String fieldWhere) {
        this.fieldWhere = fieldWhere;
    }

    public String getKeyWhere() {
        return keyWhere;
    }

    public void setKeyWhere(String keyWhere) {
        this.keyWhere = keyWhere;
    }

    public List<MaestroUpdate> getOpcion() {
        return opcion;
    }

    public void setOpcion(List<MaestroUpdate> opcion) {
        this.opcion = opcion;
    }
}
