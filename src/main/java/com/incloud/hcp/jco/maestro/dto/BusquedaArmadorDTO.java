package com.incloud.hcp.jco.maestro.dto;

import java.util.List;

public class BusquedaArmadorDTO {
    private String codigo;
    private List<MaestroOptionsKey> options2;
    private List<MaestroOptions> option;

    public List<MaestroOptions> getOption() {
        return option;
    }

    public void setOption(List<MaestroOptions> option) {
        this.option = option;
    }

    public List<MaestroOptionsKey> getOptions2() {
        return options2;
    }

    public void setOptions2(List<MaestroOptionsKey> options2) {
        this.options2 = options2;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
