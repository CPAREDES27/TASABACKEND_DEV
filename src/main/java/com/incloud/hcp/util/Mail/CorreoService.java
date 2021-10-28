package com.incloud.hcp.util.Mail;

import com.incloud.hcp.util.Mensaje;
import org.springframework.stereotype.Service;

@Service
public interface CorreoService {

    Mensaje EnviarCorreo(CorreoConAdjuntoDto imports)throws Exception;
    Mensaje Enviar(CorreoDto correo)throws Exception;

}
