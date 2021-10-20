package com.incloud.hcp.jco.controlLogistico.dto;

public class HorometroStrDto {
    private String NRMAR;
    private String NREVN;
    private String LCHOR;
    private String CDTHR;

    public String getCDTHR() {
        return CDTHR;
    }

    public void setCDTHR(String CDTHR) {
        this.CDTHR = CDTHR;
    }

    public String getNRMAR() {
        return NRMAR;
    }

    public void setNRMAR(String NRMAR) {
        this.NRMAR = NRMAR;
    }

    public String getNREVN() {
        return NREVN;
    }

    public void setNREVN(String NREVN) {
        this.NREVN = NREVN;
    }

    public String getLCHOR() {
        return LCHOR;
    }

    public void setLCHOR(String LCHOR) {
        this.LCHOR = LCHOR;
    }
}
