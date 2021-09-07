package com.incloud.hcp.jco.distribucionflota.dto;

import java.util.List;

public class PlantasDto {
    private String PlantaName;
    private String Tot_emb;
    private String Tot_bod;
    private String Tot_decl;
    private String Tot_Est;
    private String Tot_PescaReq;

    public String getTot_PescaReq() {
        return Tot_PescaReq;
    }

    public void setTot_PescaReq(String tot_PescaReq) {
        Tot_PescaReq = tot_PescaReq;
    }

    public String getTot_emb() {
        return Tot_emb;
    }

    public void setTot_emb(String tot_emb) {
        Tot_emb = tot_emb;
    }

    public String getTot_bod() {
        return Tot_bod;
    }

    public void setTot_bod(String tot_bod) {
        Tot_bod = tot_bod;
    }

    public String getTot_decl() {
        return Tot_decl;
    }

    public void setTot_decl(String tot_decl) {
        Tot_decl = tot_decl;
    }

    public String getTot_Est() {
        return Tot_Est;
    }

    public void setTot_Est(String tot_Est) {
        Tot_Est = tot_Est;
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
