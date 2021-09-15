package com.incloud.hcp.jco.maestro.dto;

import java.util.List;

public class EmbarcacionImports {

    private String p_user;
    private List<MaestroOptions> option;
    private List<MaestroOptionsKey> options;
    private List<MaestroOptions> option2;
    private List<MaestroOptionsKey> options2;
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

    public String getP_user() {
        return p_user;
    }

    public void setP_user(String p_user) {
        this.p_user = p_user;
    }

    public List<MaestroOptions> getOption2() {
        return option2;
    }

    public void setOption2(List<MaestroOptions> option2) {
        this.option2 = option2;
    }

    public List<MaestroOptionsKey> getOptions2() {
        return options2;
    }

    public void setOptions2(List<MaestroOptionsKey> options2) {
        this.options2 = options2;
    }
}
