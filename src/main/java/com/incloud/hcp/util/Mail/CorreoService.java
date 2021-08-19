package com.incloud.hcp.util.Mail;

import com.incloud.hcp.util.Mensaje;
import org.springframework.stereotype.Service;

@Service
public interface CorreoService {

    Mensaje EnviarCorreo(CorreoDto imports)throws Exception;
    Mensaje EnviarConAdjunto(CorreoDto imports)throws Exception;
}
