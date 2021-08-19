package com.incloud.hcp.util.Mail;

import com.incloud.hcp.util.Mensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.FileDataSource;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Base64;

/*
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

 */

@Service
public class CorreoImpl implements CorreoService {

    @Autowired
    private JavaMailSender javaMailSender;

    private final Logger log = LoggerFactory.getLogger(this.getClass());



    public Mensaje EnviarCorreo(CorreoDto imports) throws Exception{
        Mensaje msj= new Mensaje();

        String [] To=imports.getTo();
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(imports.getFrom());
            msg.setSubject(imports.getSubject());
            msg.setText(imports.getText());
            msg.setTo(To);

            javaMailSender.send(msg);
            msj.setMensaje("Ok");
        }catch (Exception e) {
            log.error(e.getMessage());
            msj.setMensaje(e.toString());
        }


        return msj;

    }
    public Mensaje EnviarConAdjunto(CorreoDto imports)throws Exception{

        Mensaje msj= new Mensaje();

        String [] To=imports.getTo();
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();

            // true = multipart message
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);

            helper.setTo(To);

            helper.setSubject(imports.getSubject());

            // default = text/plain
            // helper.setText("Check attachment for image!");
            // true = text/html

            helper.setText(imports.getText(), true);

            // hard coded a file path
            //FileSystemResource file = new FileSystemResource(new File("path/android.png"));
            String fds=imports.getNombreArchivo();
            FileDataSource f=new FileDataSource(fds);
            //helper.addAttachment(imports.getNombreArchivo(), new ClassPathResource(imports.getRutaArchivo()));
            helper.addAttachment(imports.getNombreArchivo(), f);

            javaMailSender.send(msg);
            msj.setMensaje("Ok");

        }catch (Exception e){
            msj.setMensaje(e.getMessage());

        }

        return msj;
    }

    public String Decode(String base64){
        Base64.Decoder decoder= Base64.getDecoder();
            byte[]decoded= decoder.decode(base64);

           
        return new  String(decoded);
    }
}