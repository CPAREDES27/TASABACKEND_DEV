package com.incloud.hcp.util.Ftp;


import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Mensaje;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.FileInputStream;
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
        int response;

        try {

            ftpclient.connect(Constantes.ftp_servidor,Integer.parseInt(Constantes.ftp_puerto));
            response= ftpclient.getReplyCode();

            if(FTPReply.isPositiveCompletion(response)){
                msj.setMensaje("error: "+response);
            }
            boolean logueo=ftpclient.login(Constantes.ftp_usuario, Constantes.ftp_contraseña);

            if(logueo){
                ftpclient.setFileType(BINARY_FILE_TYPE);
                ftpclient.enterLocalPassiveMode();

                if (imports.getRuta()!="") {
                    ftpclient.changeWorkingDirectory(imports.getRuta());
                }
                String path=Constantes.RUTA_ARCHIVO_IMPORTAR+ imports.getNombreArchivo();

                CrearArchivo(imports.getBase64(), path);

                File f=new File(path);
                InputStream fis = new FileInputStream(f);
                bool=ftpclient.storeFile(f.getName(),fis);

                response= ftpclient.getReplyCode();
                if(bool) {
                    msj.setMensaje("Ok, " + response );
                }else{
                    msj.setMensaje("No se cargó el archivo: "+ response);

                }

                ftpclient.disconnect();
            }else   {
                msj.setMensaje("Error de acceso");
            }

        }catch (IOException e){
            msj.setMensaje(e.getMessage());
        }


        return msj;
    }

    public FtpExports DescargarArchivoFtp(FtpImports imports)throws Exception{

        FtpExports ftpExports= new FtpExports();

        FTPClient ftpclient = new FTPClient();
        boolean bool;
        int response;

        try {

            ftpclient.connect(Constantes.ftp_servidor,Integer.parseInt(Constantes.ftp_puerto));
            response= ftpclient.getReplyCode();

            if(FTPReply.isPositiveCompletion(response)){
                ftpExports.setMensaje("error: "+response);
            }
            boolean logueo=ftpclient.login(Constantes.ftp_usuario, Constantes.ftp_contraseña);

           log.error("directorio: "+ftpclient.listDirectories().toString());
            FTPFile[] listfiles=ftpclient.listDirectories();

           for(int i=0; i<listfiles.length; i++){
                log.error("listfiles["+i+"]: "+listfiles[i].toString());
           }
            if(logueo){
                ftpclient.setFileType(BINARY_FILE_TYPE);
                ftpclient.enterLocalPassiveMode();

                String path=Constantes.RUTA_ARCHIVO_IMPORTAR+ imports.getNombreArchivo();

                bool=Descarga(path, ftpclient, imports.getRuta());
                response= ftpclient.getReplyCode();


                if(bool) {

                    byte[] fileContent = FileUtils.readFileToByteArray(new File(path));
                    String encodedString = Base64.getEncoder().encodeToString(fileContent);
                    ftpExports.setBase64(encodedString );

                    ftpExports.setMensaje("Ok, " + response );
                }else{
                    ftpExports.setMensaje("No se descargó el archivo: " + response);

                }

                ftpclient.disconnect();
            }else   {
                ftpExports.setMensaje("Error de acceso");
            }

        }catch (IOException e){
            ftpExports.setMensaje(e.getMessage());
        }

        return ftpExports;
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

    public boolean Descarga(String path, FTPClient ftpclient, String remotePath)throws Exception{

        boolean bool;
        File f=new File(path);
        OutputStream out = new FileOutputStream(f);
        bool=ftpclient.retrieveFile(remotePath, out);

        return bool;
    }



}
