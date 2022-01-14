package com.incloud.hcp.jco.gestionpesca.dto;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;

import java.util.List;

public class AyudaBusqImport {
    private String p_user;
    private List<MaestroOptions> options;
    private List<MaestroOptionsKey> p_options;
    private List<MaestroOptionsKey> p_options2;

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public List<MaestroOptions> getOptions() {
        return options;
    }

    public void setOptions(List<MaestroOptions> options) {
        this.options = options;
    }

    public List<MaestroOptionsKey> getP_options() {
        return p_options;
    }

    public void setP_options(List<MaestroOptionsKey> p_options) {
        this.p_options = p_options;
    }

    public List<MaestroOptionsKey> getP_options2() {
        return p_options2;
    }

    public void setP_options2(List<MaestroOptionsKey> p_options2) {
        this.p_options2 = p_options2;
    }
}
