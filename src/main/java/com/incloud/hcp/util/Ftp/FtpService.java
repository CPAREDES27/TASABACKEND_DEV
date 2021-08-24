package com.incloud.hcp.util.Ftp;

import com.incloud.hcp.util.Mensaje;

public interface FtpService {

    Mensaje SubirArchivoFtp(FtpImports imports)throws Exception;
    FtpExports DescargarArchivoFtp(FtpImports imports)throws Exception;
}
