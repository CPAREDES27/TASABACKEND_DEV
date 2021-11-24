package com.incloud.hcp.jco.gestionpesca.dto;

public class ConsultaReservaImport {

    private int Marea;
    private String Reserva;

    public int getMarea() {
        return Marea;
    }

    public void setMarea(int marea) {
        Marea = marea;
    }

    public String getReserva() {
        return Reserva;
    }

    public void setReserva(String reserva) {
        Reserva = reserva;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getFlagDetalle() {
        return FlagDetalle;
    }

    public void setFlagDetalle(String flagDetalle) {
        FlagDetalle = flagDetalle;
    }

    private String Usuario;
    private String FlagDetalle;


}
