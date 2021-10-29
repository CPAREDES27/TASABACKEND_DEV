package com.incloud.hcp.jco.distribucionflota.dto;

import java.util.List;

public class PlantasDto {
    private String CodPlanta;
    private String PlantaName;
    private double Tot_emb;
    private double Tot_bod;
    private double Tot_decl;
    private double Tot_Est;
    private double Tot_PescaReq;

    public double getTot_emb() {
        return Tot_emb;
    }

    public void setTot_emb(double tot_emb) {
        Tot_emb = tot_emb;
    }

    public double getTot_bod() {
        return Tot_bod;
    }

    public void setTot_bod(double tot_bod) {
        Tot_bod = tot_bod;
    }

    public double getTot_decl() {
        return Tot_decl;
    }

    public void setTot_decl(double tot_decl) {
        Tot_decl = tot_decl;
    }

    public double getTot_Est() {
        return Tot_Est;
    }

    public void setTot_Est(double tot_Est) {
        Tot_Est = tot_Est;
    }

    public double getTot_PescaReq() {
        return Tot_PescaReq;
    }

    public void setTot_PescaReq(double tot_PescaReq) {
        Tot_PescaReq = tot_PescaReq;
    }

    public String getCodPlanta() {
        return CodPlanta;
    }

    public void setCodPlanta(String codPlanta) {
        CodPlanta = codPlanta;
    }



    private List<EmbarcacionesDto> ListaEmbarcaciones;

    public String getPlantaName() {
        return PlantaName;
    }

    public void setPlantaName(String plantaName) {
        PlantaName = plantaName;
    }

    public List<EmbarcacionesDto> getListaEmbarcaciones() {
        return ListaEmbarcaciones;
    }

    public void setListaEmbarcaciones(List<EmbarcacionesDto> listaEmbarcaciones) {
        ListaEmbarcaciones = listaEmbarcaciones;
    }
}
