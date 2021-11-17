package com.incloud.hcp.jco.controlLogistico.dto;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;

import java.util.List;

public class ValeViveresImports {

    private String p_user;
    private String rowcount;
    private String[] fields;

    private List<MaestroOptions> options1;
    private List<MaestroOptionsKey> options2;

    public List<MaestroOptions> getOptions1() {
        return options1;
    }

    public void setOptions1(List<MaestroOptions> options1) {
        this.options1 = options1;
    }

    public List<MaestroOptionsKey> getOptions2() {
        return options2;
    }

    public void setOptions2(List<MaestroOptionsKey> options2) {
        this.options2 = options2;
    }



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
