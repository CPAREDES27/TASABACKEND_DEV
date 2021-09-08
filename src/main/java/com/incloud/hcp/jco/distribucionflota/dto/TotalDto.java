package com.incloud.hcp.jco.distribucionflota.dto;

public class TotalDto {
    private String DescPlanta;
    private String PescDeclProp;
    private String EmbaPescProp;
    private String CbodProp;
    private String dif;

    public String getDescPlanta() {
        return DescPlanta;
    }

    public void setDescPlanta(String descPlanta) {
        DescPlanta = descPlanta;
    }

    public String getPescDeclProp() {
        return PescDeclProp;
    }

    public void setPescDeclProp(String pescDeclProp) {
        PescDeclProp = pescDeclProp;
    }

    public String getEmbaPescProp() {
        return EmbaPescProp;
    }

    public void setEmbaPescProp(String embaPescProp) {
        EmbaPescProp = embaPescProp;
    }

    public String getCbodProp() {
        return CbodProp;
    }

    public void setCbodProp(String cbodProp) {
        CbodProp = cbodProp;
    }

    public String getDif() {
        return dif;
    }

    public void setDif(String dif) {
        this.dif = dif;
    }
}
