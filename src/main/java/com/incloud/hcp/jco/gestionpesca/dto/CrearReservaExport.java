package com.incloud.hcp.jco.gestionpesca.dto;

import java.util.HashMap;
import java.util.List;

public class CrearReservaExport {

    public String getP_reserva() {
        return p_reserva;
    }

    public void setP_reserva(String p_reserva) {
        this.p_reserva = p_reserva;
    }

    public List<HashMap<String, Object>> getT_mensaje() {
        return t_mensaje;
    }

    public void setT_mensaje(List<HashMap<String, Object>> t_mensaje) {
        this.t_mensaje = t_mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String mensaje;
    public String p_reserva;
    public List<HashMap<String, Object>> t_mensaje;

}
