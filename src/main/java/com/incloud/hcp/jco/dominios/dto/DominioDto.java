package com.incloud.hcp.jco.dominios.dto;

import java.util.List;

public class DominioDto {

    public List<DominiosExports> data;
    public String mensaje;

    public List<DominiosExports> getData() {
        return data;
    }

    public void setData(List<DominiosExports> data) {
        this.data = data;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
