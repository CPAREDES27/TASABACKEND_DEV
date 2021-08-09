package com.incloud.hcp.jco.maestro.dto;

public class UnidadMedidaDto {
    private String CDUMD;       //Codigo
    private String DSUMD;  //Descripcion
    private String getCDUMD() {
        return CDUMD;
    }

    public void setCDUMD(String CDUMD) {
        this.CDUMD = CDUMD;
    }

    public String getDSUMD() {
        return DSUMD;
    }

    public void setDSUMD(String DSUMD) {
        this.DSUMD = DSUMD;
    }
}
