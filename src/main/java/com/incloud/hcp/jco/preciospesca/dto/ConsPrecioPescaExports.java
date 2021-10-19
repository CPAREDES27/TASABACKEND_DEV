package com.incloud.hcp.jco.preciospesca.dto;

import java.util.HashMap;
import java.util.List;

public class ConsPrecioPescaExports {
    private List<EstadosPrecioDto> t_prepes;
    private String mensaje;

    public List<EstadosPrecioDto> getT_prepes() {
        return t_prepes;
    }

    public void setT_prepes(List<EstadosPrecioDto> t_prepes) {
        this.t_prepes = t_prepes;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
