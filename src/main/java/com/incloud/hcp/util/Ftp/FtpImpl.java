package com.incloud.hcp.util.Ftp;

import com.incloud.hcp.util.Constant;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Mensaje;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.io.*;
import java.util.Base64;

import static org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE;

@Service
public class FtpImpl implements FtpService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Override
    public Mensaje SubirArchivoFtp(FtpImports imports) throws Exception {
        Mensaje msj=new Mensaje();
        FTPClient ftpclient = new FTPClient();
        boolean bool;


        try {

            ftpclient.connect(Constantes.ftp_servidor,Integer.parseInt(Constantes.ftp_puerto));
           int response= ftpclient.getReplyCode();

            if(FTPReply.isPositiveCompletion(response)){
                msj.setMensaje("error: "+response);
            }
            boolean logueo=ftpclient.login(Constantes.ftp_usuario, Constantes.ftp_contraseña);

            if(logueo){
                ftpclient.setFileType(BINARY_FILE_TYPE);
                String path=Constantes.RUTA_ARCHIVO_IMPORTAR+ imports.getNombreArchivo();

                CrearArchivo(imports.getBase64(), path);
                bool=subirFichero(path, imports.getNombreArchivo(), ftpclient);
                ftpclient.listDirectories();
                msj.setMensaje("Conexion exitosa, Archivo subio: "+bool + "- Lista carpetas: "+ftpclient.listDirectories()+" -Directorio actual: "+ ftpclient.printWorkingDirectory());
                ftpclient.disconnect();
            }

        }catch (IOException e){
            msj.setMensaje(e.getMessage());
        }


        return msj;
    }

    public void CrearArchivo(String base64 , String path) throws IOException {

        File file = new File(path);

        try (FileOutputStream fos = new FileOutputStream(file); ) {

            byte[] decoder = Base64.getDecoder().decode(base64);

            fos.write(decoder);
            log.error("Archivo Creado = " + path);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    public  boolean subirFichero(String pathFich, String fich, FTPClient ftpClient){

        InputStream is;
        boolean fichSubido = false;

        try {
            // Capturar el fichero de su ruta.
            is = new BufferedInputStream(new FileInputStream(pathFich));

            // Subir el fichero en sí.
            fichSubido = ftpClient.storeFile(fich, is);
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fichSubido;
    }
}
