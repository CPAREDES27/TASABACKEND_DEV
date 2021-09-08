package com.incloud.hcp.jco.distribucionflota.dto;

import java.util.List;

public class DistribucionFlotaExports {

    private List<ZonasDto> ListaZonas;
    private List<DescargasDto> ListaDescargas;
    private List<PropiosDto> ListaPropios;
    private List<TercerosDto> ListaTerceros;
    private List<TotalDto> ListaTotal;

    private String mensaje;
    private String tot_desc_cbod;
    private String tot_desc_dscl;
    private String tot_desc_desc;
    private String tot_terc_cbod;
    private String tot_terc_dscl;
    private String tot_terc_ep;
    private String tot_prop_cbod;
    private String tot_prop_dscl;
    private String tot_prop_ep;
    private String tot_tot_cbod;
    private String tot_tot_dscl;
    private String tot_tot_ep;

    public List<TotalDto> getListaTotal() {
        return ListaTotal;
    }

    public void setListaTotal(List<TotalDto> listaTotal) {
        ListaTotal = listaTotal;
    }

    public List<DescargasDto> getListaDescargas() {
        return ListaDescargas;
    }

    public void setListaDescargas(List<DescargasDto> listaDescargas) {
        ListaDescargas = listaDescargas;
    }

    public List<PropiosDto> getListaPropios() {
        return ListaPropios;
    }

    public void setListaPropios(List<PropiosDto> listaPropios) {
        ListaPropios = listaPropios;
    }

    public List<TercerosDto> getListaTerceros() {
        return ListaTerceros;
    }

    public void setListaTerceros(List<TercerosDto> listaTerceros) {
        ListaTerceros = listaTerceros;
    }

    public String getTot_desc_cbod() {
        return tot_desc_cbod;
    }

    public void setTot_desc_cbod(String tot_desc_cbod) {
        this.tot_desc_cbod = tot_desc_cbod;
    }

    public String getTot_desc_dscl() {
        return tot_desc_dscl;
    }

    public void setTot_desc_dscl(String tot_desc_dscl) {
        this.tot_desc_dscl = tot_desc_dscl;
    }

    public String getTot_desc_desc() {
        return tot_desc_desc;
    }

    public void setTot_desc_desc(String tot_desc_desc) {
        this.tot_desc_desc = tot_desc_desc;
    }

    public String getTot_terc_cbod() {
        return tot_terc_cbod;
    }

    public void setTot_terc_cbod(String tot_terc_cbod) {
        this.tot_terc_cbod = tot_terc_cbod;
    }

    public String getTot_terc_dscl() {
        return tot_terc_dscl;
    }

    public void setTot_terc_dscl(String tot_terc_dscl) {
        this.tot_terc_dscl = tot_terc_dscl;
    }

    public String getTot_terc_ep() {
        return tot_terc_ep;
    }

    public void setTot_terc_ep(String tot_terc_ep) {
        this.tot_terc_ep = tot_terc_ep;
    }

    public String getTot_prop_cbod() {
        return tot_prop_cbod;
    }

    public void setTot_prop_cbod(String tot_prop_cbod) {
        this.tot_prop_cbod = tot_prop_cbod;
    }

    public String getTot_prop_dscl() {
        return tot_prop_dscl;
    }

    public void setTot_prop_dscl(String tot_prop_dscl) {
        this.tot_prop_dscl = tot_prop_dscl;
    }

    public String getTot_prop_ep() {
        return tot_prop_ep;
    }

    public void setTot_prop_ep(String tot_prop_ep) {
        this.tot_prop_ep = tot_prop_ep;
    }

    public String getTot_tot_cbod() {
        return tot_tot_cbod;
    }

    public void setTot_tot_cbod(String tot_tot_cbod) {
        this.tot_tot_cbod = tot_tot_cbod;
    }

    public String getTot_tot_dscl() {
        return tot_tot_dscl;
    }

    public void setTot_tot_dscl(String tot_tot_dscl) {
        this.tot_tot_dscl = tot_tot_dscl;
    }

    public String getTot_tot_ep() {
        return tot_tot_ep;
    }

    public void setTot_tot_ep(String tot_tot_ep) {
        this.tot_tot_ep = tot_tot_ep;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<ZonasDto> getListaZonas() {
        return ListaZonas;
    }

    public void setListaZonas(List<ZonasDto> listaZonas) {
        ListaZonas = listaZonas;
    }
}

