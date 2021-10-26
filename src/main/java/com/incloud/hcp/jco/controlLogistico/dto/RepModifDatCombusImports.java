package com.incloud.hcp.jco.controlLogistico.dto;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;

import java.util.List;

public class RepModifDatCombusImports {

    private String p_fase;
    private String p_cant;
    private String[] fieldsT_flocc;
    private List<MaestroOptions> option;
    private List<MaestroOptionsKey> options;
    private String[] fieldsT_mensaje;

    public List<MaestroOptions> getOption() {
        return option;
    }

    public void setOption(List<MaestroOptions> option) {
        this.option = option;
    }

    public String[] getFieldsT_flocc() {
        return fieldsT_flocc;
    }

    public void setFieldsT_flocc(String[] fieldsT_flocc) {
        this.fieldsT_flocc = fieldsT_flocc;
    }

    public List<MaestroOptionsKey> getOptions() {
        return options;
    }

    public void setOptions(List<MaestroOptionsKey> options) {
        this.options = options;
    }

    public String[] getFieldsT_mensaje() {
        return fieldsT_mensaje;
    }

    public void setFieldsT_mensaje(String[] fieldsT_mensaje) {
        this.fieldsT_mensaje = fieldsT_mensaje;
    }

    public String getP_fase() {
        return p_fase;
    }

    public void setP_fase(String p_fase) {
        this.p_fase = p_fase;
    }

    public String getP_cant() {
        return p_cant;
    }

    public void setP_cant(String p_cant) {
        this.p_cant = p_cant;
    }


}
