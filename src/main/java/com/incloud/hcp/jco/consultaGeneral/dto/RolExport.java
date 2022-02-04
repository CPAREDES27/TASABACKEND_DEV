package com.incloud.hcp.jco.consultaGeneral.dto;

import com.fasterxml.jackson.databind.JsonNode;

public class RolExport {
    private JsonNode node ;

    public JsonNode getNode() {
        return node;
    }

    public void setNode(JsonNode node) {
        this.node = node;
    }
}
