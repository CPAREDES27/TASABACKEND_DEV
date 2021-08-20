package com.incloud.hcp.util.Mail;

import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Mensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;



@Service
public class CorreoImpl implements CorreoService {

    @Autowired
    private JavaMailSender javaMailSender;

    private final Logger log = LoggerFactory.getLogger(this.getClass());



    public Mensaje EnviarCorreo(CorreoDto imports)throws Exception{

        Mensaje msj= new Mensaje();


        String [] To=imports.getTo();
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();

            // true = multipart message
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
           // helper.setFrom(imports.getFrom());
            helper.setFrom(new InternetAddress(imports.getFrom()));

            helper.setTo(To);
            helper.setSubject(imports.getSubject());
            helper.setText(imports.getText(), true);

            if(imports.getBase64()!="" && imports.getNombreArchivo()!=""){

                String RutaArchivo=Constantes.RUTA_ARCHIVO_IMPORTAR +imports.getNombreArchivo();

                CrearArchivo(imports.getBase64(), RutaArchivo);
                FileSystemResource file
                        = new FileSystemResource(new File(RutaArchivo));

                helper.addAttachment(imports.getNombreArchivo(), file);

            }

            if(imports.getRutaArchivo()!="") {

                FileSystemResource file
                        = new FileSystemResource(new File(imports.getRutaArchivo()));

                helper.addAttachment(imports.getNombreArchivo(), file);
            }


            javaMailSender.send(msg);
            msj.setMensaje("Ok");

        }catch (Exception e){
            msj.setMensaje(e.getMessage());

        }

        return msj;
    }


    public void CrearArchivo(String base64 , String path) throws IOException {

        File file = new File(path);

        try ( FileOutputStream fos = new FileOutputStream(file); ) {

            byte[] decoder = Base64.getDecoder().decode(base64);

            fos.write(decoder);
            log.error("Archivo Creado = " + path);
        } catch (Exception e) {
           log.error(e.getMessage());
        }

    }
}