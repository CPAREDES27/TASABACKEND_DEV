package com.incloud.hcp.jco.gestionpesca.dto;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;

import java.util.List;

public class BodegaImport {
    private List<MaestroOptions> options;

    public List<MaestroOptions> getOptions() {
        return options;
    }

    public void setOptions(List<MaestroOptions> options) {
        this.options = options;
    }
}
