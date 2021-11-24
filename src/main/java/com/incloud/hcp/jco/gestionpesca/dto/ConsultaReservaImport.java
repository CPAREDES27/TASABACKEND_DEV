package com.incloud.hcp.jco.gestionpesca.dto;

public class ConsultaReservaImport {

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

    public int Marea;
    public String Reserva;
    public String Usuario;
    public String FlagDetalle;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String mensaje;


}
