package com.incloud.hcp.jco.maestro.dto;

import java.util.List;

public class BusquedaEmbarcacionImports {
    private String p_user;
    private String rowcount;
    private List<MaestroOptions> option;
    private List<MaestroOptionsKey> options;

    public List<MaestroOptionsKey> getOptions() {
        return options;
    }

    public void setOptions(List<MaestroOptionsKey> options) {
        this.options = options;
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

    public List<MaestroOptions> getOption() {
        return option;
    }

    public void setOption(List<MaestroOptions> options) {
        this.option = options;
    }

}
