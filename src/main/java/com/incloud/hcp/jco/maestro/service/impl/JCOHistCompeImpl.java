package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.CapacidadTanquesImports;
import com.incloud.hcp.jco.maestro.service.JCOHistCompeService;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JCOHistCompeImpl implements JCOHistCompeService {


@Autowired
JCOCapacidadTanquesImpl jcoCapacidadTanques;

    @Override
    public Mensaje Editar(CapacidadTanquesImports imports) throws Exception {


        Mensaje msj = jcoCapacidadTanques.Editar(imports);
        return msj;
    }


}