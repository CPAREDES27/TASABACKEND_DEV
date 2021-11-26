package com.incloud.hcp.jco.controlLogistico.dto;

public class InfoHorometrosAveriadosImport {
    private String envioPrd;
    private EmbarcacionDto embarcacion;

    public String getEnvioPrd() {
        return envioPrd;
    }

    public void setEnvioPrd(String envioPrd) {
        this.envioPrd = envioPrd;
    }

    public EmbarcacionDto getEmbarcacion() {
        return embarcacion;
    }

    public void setEmbarcacion(EmbarcacionDto embarcacion) {
        this.embarcacion = embarcacion;
    }
}
