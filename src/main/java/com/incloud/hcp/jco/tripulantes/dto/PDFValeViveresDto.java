package com.incloud.hcp.jco.tripulantes.dto;

public class PDFValeViveresDto {

    private String centro;
    private String almacen;
    private String nroVale;
    private String fecha;
    private String temporada;
    private String ruc;
    private String matricula;
    private String indPropiedad;
    private String codigoArmador;
    private String razonSocialUno;
    private String direccion;
    private String nombreEmbarcacion;
    private String codigoProveeduria;
    private String razonSocialDos;
    private String fechaInicio;
    private String fechaFin;
    private String comentario;
    private String cocinero;
    private String estadoImpresion;
    private String[] fechas;

    public String getEstadoImpresion() {
        return estadoImpresion;
    }

    public void setEstadoImpresion(String estadoImpresion) {
        this.estadoImpresion = estadoImpresion;
    }

    public String[] getFechas() {
        return fechas;
    }

    public void setFechas(String[] fechas) {
        this.fechas = fechas;
    }

    public String getCocinero() {
        return cocinero;
    }

    public void setCocinero(String cocinero) {
        this.cocinero = cocinero;
    }

    public float getTotalCosto() {
        return totalCosto;
    }

    public void setTotalCosto(float totalCosto) {
        this.totalCosto = totalCosto;
    }

    public int getTotalRaciones() {
        return totalRaciones;
    }

    public void setTotalRaciones(int totalRaciones) {
        this.totalRaciones = totalRaciones;
    }

    private float totalCosto;
    private int totalRaciones;


    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getNroVale() {
        return nroVale;
    }

    public void setNroVale(String nroVale) {
        this.nroVale = nroVale;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getIndPropiedad() {
        return indPropiedad;
    }

    public void setIndPropiedad(String indPropiedad) {
        this.indPropiedad = indPropiedad;
    }

    public String getCodigoArmador() {
        return codigoArmador;
    }

    public void setCodigoArmador(String codigoArmador) {
        this.codigoArmador = codigoArmador;
    }

    public String getRazonSocialUno() {
        return razonSocialUno;
    }

    public void setRazonSocialUno(String razonSocialUno) {
        this.razonSocialUno = razonSocialUno;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombreEmbarcacion() {
        return nombreEmbarcacion;
    }

    public void setNombreEmbarcacion(String nombreEmbarcacion) {
        this.nombreEmbarcacion = nombreEmbarcacion;
    }

    public String getCodigoProveeduria() {
        return codigoProveeduria;
    }

    public void setCodigoProveeduria(String codigoProveeduria) {
        this.codigoProveeduria = codigoProveeduria;
    }

    public String getRazonSocialDos() {
        return razonSocialDos;
    }

    public void setRazonSocialDos(String razonSocialDos) {
        this.razonSocialDos = razonSocialDos;
    }


    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }



    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
