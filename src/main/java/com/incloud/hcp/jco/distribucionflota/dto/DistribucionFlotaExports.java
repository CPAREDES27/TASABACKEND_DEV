package com.incloud.hcp.jco.distribucionflota.dto;

import java.util.List;

public class DistribucionFlotaExports {

    private List<ZonasDto> ListaZonas;
    private List<DescargasDto> ListaDescargas;
    private List<PropiosDto> ListaPropios;
    private List<TercerosDto> ListaTerceros;
    private List<TotalDto> ListaTotal;

    private String mensaje;
    private double tot_desc_cbod;
    private double tot_desc_dscl;
    private double tot_desc_desc;
    private double tot_terc_cbod;
    private double tot_terc_dscl;
    private double tot_terc_ep;
    private double tot_prop_cbod;
    private double tot_prop_dscl;
    private double tot_prop_ep;
    private double tot_tot_cbod;
    private double tot_tot_dscl;
    private double tot_tot_ep;

    public double getTot_desc_cbod() {
        return tot_desc_cbod;
    }

    public void setTot_desc_cbod(double tot_desc_cbod) {
        this.tot_desc_cbod = tot_desc_cbod;
    }

    public double getTot_desc_dscl() {
        return tot_desc_dscl;
    }

    public void setTot_desc_dscl(double tot_desc_dscl) {
        this.tot_desc_dscl = tot_desc_dscl;
    }

    public double getTot_desc_desc() {
        return tot_desc_desc;
    }

    public void setTot_desc_desc(double tot_desc_desc) {
        this.tot_desc_desc = tot_desc_desc;
    }

    public double getTot_terc_cbod() {
        return tot_terc_cbod;
    }

    public void setTot_terc_cbod(double tot_terc_cbod) {
        this.tot_terc_cbod = tot_terc_cbod;
    }

    public double getTot_terc_dscl() {
        return tot_terc_dscl;
    }

    public void setTot_terc_dscl(double tot_terc_dscl) {
        this.tot_terc_dscl = tot_terc_dscl;
    }

    public double getTot_terc_ep() {
        return tot_terc_ep;
    }

    public void setTot_terc_ep(double tot_terc_ep) {
        this.tot_terc_ep = tot_terc_ep;
    }

    public double getTot_prop_cbod() {
        return tot_prop_cbod;
    }

    public void setTot_prop_cbod(double tot_prop_cbod) {
        this.tot_prop_cbod = tot_prop_cbod;
    }

    public double getTot_prop_dscl() {
        return tot_prop_dscl;
    }

    public void setTot_prop_dscl(double tot_prop_dscl) {
        this.tot_prop_dscl = tot_prop_dscl;
    }

    public double getTot_prop_ep() {
        return tot_prop_ep;
    }

    public void setTot_prop_ep(double tot_prop_ep) {
        this.tot_prop_ep = tot_prop_ep;
    }

    public double getTot_tot_cbod() {
        return tot_tot_cbod;
    }

    public void setTot_tot_cbod(double tot_tot_cbod) {
        this.tot_tot_cbod = tot_tot_cbod;
    }

    public double getTot_tot_dscl() {
        return tot_tot_dscl;
    }

    public void setTot_tot_dscl(double tot_tot_dscl) {
        this.tot_tot_dscl = tot_tot_dscl;
    }

    public double getTot_tot_ep() {
        return tot_tot_ep;
    }

    public void setTot_tot_ep(double tot_tot_ep) {
        this.tot_tot_ep = tot_tot_ep;
    }

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

