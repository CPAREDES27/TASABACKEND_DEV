package com.incloud.hcp.jco.requerimientopesca.service;


import com.incloud.hcp.jco.requerimientopesca.dto.ReqPescaDto;
import com.incloud.hcp.jco.requerimientopesca.dto.ReqPescaOptions;

public interface JCORequerimientoPescaService {

    ReqPescaDto ListarRequerimientoPesca(ReqPescaOptions importsParam)throws Exception;
    ReqPescaDto RegistrarRequerimientoPesca(ReqPescaOptions importsParam)throws Exception;
}
