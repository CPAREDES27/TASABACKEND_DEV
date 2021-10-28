package com.incloud.hcp.util.Mail.Service;

import com.incloud.hcp.util.Mail.Dto.CorreoConAdjuntoDto;
import com.incloud.hcp.util.Mail.Dto.CorreoDto;
import com.incloud.hcp.util.Mail.Dto.NotifDescTolvasDto;
import com.incloud.hcp.util.Mensaje;
import org.springframework.stereotype.Service;

@Service
public interface CorreoService {

    Mensaje EnviarCorreoConAdjunto(CorreoConAdjuntoDto imports)throws Exception;
    Mensaje EnviarCorreo(CorreoDto correo)throws Exception;
    Mensaje EnviarNotifDescTolvas(NotifDescTolvasDto imports)throws Exception;

}
