package com.incloud.hcp.jco.maestro.service.impl;

import com.incloud.hcp.jco.maestro.dto.STR_SETDto;
import com.incloud.hcp.jco.maestro.service.JCOHistoricoCompetenciaService;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Mensaje;
import org.springframework.stereotype.Service;

@Service
public class JCOHistoricoCompetenciaImpl implements JCOHistoricoCompetenciaService {



    @Override
    public Mensaje EditarHistoricoCompetencia(STR_SETDto str_setDto, String tabla) throws Exception {
        EjecutarRFC exec= new EjecutarRFC();
        Mensaje msj= exec.Exec_ZFL_RFC_ACT_CAMP_TAB(str_setDto, tabla);
        return msj;
    }
}