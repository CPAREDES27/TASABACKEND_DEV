package com.incloud.hcp.jco.preciospesca.dto;

import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;

import java.util.List;

public class PrecioPonderadoImport {

    private List<MaestroOptions> option;
    private List<MaestroOptionsKey> options;


    public List<MaestroOptions> getOption() {
        return option;
    }

    public void setOption(List<MaestroOptions> option) {
        this.option = option;
    }

    public List<MaestroOptionsKey> getOptions() {
        return options;
    }

    public void setOptions(List<MaestroOptionsKey> options) {
        this.options = options;
    }
}
