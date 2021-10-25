package com.incloud.hcp.jco.controlLogistico.dto;

import java.util.List;

public class HorometroListDto {
    private String NRMAR;
    private String NREVN;
    private String FIEVN;
    private String CDEMB;


    public String getCDEMB() {
        return CDEMB;
    }

    public void setCDEMB(String CDEMB) {
        this.CDEMB = CDEMB;
    }



    public String getFIEVN() {
        return FIEVN;
    }

    public void setFIEVN(String FIEVN) {
        this.FIEVN = FIEVN;
    }

    private List<MotorDto> lista;

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

    public List<MotorDto> getLista() {
        return lista;
    }

    public void setLista(List<MotorDto> lista) {
        this.lista = lista;
    }
}
