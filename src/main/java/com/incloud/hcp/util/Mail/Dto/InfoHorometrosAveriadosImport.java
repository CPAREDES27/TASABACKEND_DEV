package com.incloud.hcp.util.Mail.Dto;

public class InfoHorometrosAveriadosImport {
    private String envioPrd;
    private EmbarcacionForEmailDto embarcacion;

    public String getEnvioPrd() {
        return envioPrd;
    }

    public void setEnvioPrd(String envioPrd) {
        this.envioPrd = envioPrd;
    }

    public EmbarcacionForEmailDto getEmbarcacion() {
        return embarcacion;
    }

    public void setEmbarcacion(EmbarcacionForEmailDto embarcacion) {
        this.embarcacion = embarcacion;
    }
}
