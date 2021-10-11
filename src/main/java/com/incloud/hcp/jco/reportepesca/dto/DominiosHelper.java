package com.incloud.hcp.jco.reportepesca.dto;

import com.incloud.hcp.jco.dominios.dto.DominioDto;
import com.incloud.hcp.jco.dominios.dto.DominioParams;
import com.incloud.hcp.jco.dominios.dto.DominiosExports;
import com.incloud.hcp.jco.dominios.dto.DominiosImports;
import com.incloud.hcp.jco.dominios.service.impl.JCODominiosImpl;

import java.util.ArrayList;

public class DominiosHelper {
    private String dominio;

    public ArrayList<DominiosExports> listarDominios(ArrayList<String> domNames) throws Exception {
        JCODominiosImpl dominiosService = new JCODominiosImpl();
        DominiosImports imports = new DominiosImports();
        ArrayList<DominioParams> listDominioParams = new ArrayList<>();
        domNames.forEach(d -> {
            DominioParams dominioParams = new DominioParams();
            dominioParams.setDomname(d);
            dominioParams.setStatus("A");

            listDominioParams.add(dominioParams);
        });

        imports.setDominios(listDominioParams);
        DominioDto dominios = dominiosService.Listar(imports);

        ArrayList<DominiosExports> listDomDescripciones = new ArrayList<>(dominios.data);

        return listDomDescripciones;
    }
}
