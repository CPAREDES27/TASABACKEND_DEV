package com.incloud.hcp.util.Mail.Service;

import com.incloud.hcp.util.Mail.Dto.*;
import com.incloud.hcp.util.Mensaje;
import org.springframework.stereotype.Service;

@Service
public interface CorreoService {
   // Mensaje EnviarInfoHorometroAveriado(InfoHorometrosAveriadosImport imports) throws Exception;
    Mensaje EnviarCorreosSiniestro(InfoEventoImports imports) throws Exception;
    Mensaje EnviarCorreoConAdjunto(CorreoConAdjuntoDto imports)throws Exception;
    Mensaje EnviarCorreo(CorreoDto correo)throws Exception;
    //Mensaje EnviarNotifDescTolvas(NotifDescTolvasDto imports)throws Exception;
    CorreoMensaje EnviarNotifDescTolvas_btp(NotifDescTolvasDto imports) throws Exception;
    CorreoMensaje EnviarInfoHorometroAveriado_btp(InfoHorometrosAveriadosImport imports) throws Exception;


}
