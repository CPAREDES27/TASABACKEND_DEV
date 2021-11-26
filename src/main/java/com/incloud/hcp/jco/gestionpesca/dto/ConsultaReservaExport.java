package com.incloud.hcp.jco.gestionpesca.dto;

import java.util.HashMap;
import java.util.List;

public class ConsultaReservaExport {

    private List<HashMap<String, Object>> t_reservas;

    public List<HashMap<String, Object>> getT_reservas() {
        return t_reservas;
    }

    public void setT_reservas(List<HashMap<String, Object>> t_reservas) {
        this.t_reservas = t_reservas;
    }

    public List<HashMap<String, Object>> getT_detalle() {
        return t_detalle;
    }

    public void setT_detalle(List<HashMap<String, Object>> t_detalle) {
        this.t_detalle = t_detalle;
    }

    private List<HashMap<String, Object>> t_detalle;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String mensaje;

}
