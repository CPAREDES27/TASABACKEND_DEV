package com.incloud.hcp.jco.distribucionflota.dto;

public class TercerosDto {
    private String CodPlanta;
    private String DescPlanta;
    private double PescDeclProp;
    private double EmbaPescProp;
    private double CbodProp;

    public double getPescDeclProp() {
        return PescDeclProp;
    }

    public void setPescDeclProp(double pescDeclProp) {
        PescDeclProp = pescDeclProp;
    }

    public double getEmbaPescProp() {
        return EmbaPescProp;
    }

    public void setEmbaPescProp(double embaPescProp) {
        EmbaPescProp = embaPescProp;
    }

    public double getCbodProp() {
        return CbodProp;
    }

    public void setCbodProp(double cbodProp) {
        CbodProp = cbodProp;
    }

    public String getCodPlanta() {
        return CodPlanta;
    }

    public void setCodPlanta(String codPlanta) {
        CodPlanta = codPlanta;
    }

    public String getDescPlanta() {
        return DescPlanta;
    }

    public void setDescPlanta(String descPlanta) {
        DescPlanta = descPlanta;
    }

}
