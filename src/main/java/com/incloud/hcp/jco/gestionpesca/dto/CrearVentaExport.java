package com.incloud.hcp.jco.gestionpesca.dto;

import java.util.HashMap;
import java.util.List;

public class CrearVentaExport {
    public String mensaje;
    public String p_pedido;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getP_pedido() {
        return p_pedido;
    }

    public void setP_pedido(String p_pedido) {
        this.p_pedido = p_pedido;
    }

    public List<HashMap<String, Object>> getT_mensaje() {
        return t_mensaje;
    }

    public void setT_mensaje(List<HashMap<String, Object>> t_mensaje) {
        this.t_mensaje = t_mensaje;
    }

    public List<HashMap<String, Object>> t_mensaje;
}
