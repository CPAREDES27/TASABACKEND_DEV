package com.incloud.hcp.util.Mail.Service.Impl;

import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.maestro.dto.MaestroImports;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.service.impl.JCOMaestrosServiceImpl;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Mail.Dto.CorreoConAdjuntoDto;
import com.incloud.hcp.util.Mail.Dto.CorreoDto;
import com.incloud.hcp.util.Mail.Dto.NotifDescTolvasDto;
import com.incloud.hcp.util.Mail.Service.CorreoService;
import com.incloud.hcp.util.Mensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


import javax.mail.internet.InternetAddress;


@Service
public class CorreoImpl implements CorreoService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private JCOMaestrosServiceImpl jcoMaestrosService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());



    public Mensaje EnviarCorreoConAdjunto(CorreoConAdjuntoDto imports)throws Exception{

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


    public Mensaje EnviarCorreo(CorreoDto correo)throws Exception{

        Mensaje msj= new Mensaje();
        try {
            String uri = "https://7454em4ils3nder-app.azurewebsites.net/api/VisitasaSendEmail?code=LXQwITmvDAAqXTgaDcBAkmbZXBCv5KnS6bY/XszaOjqHus4M3dbDzw==";

            URL url = new URL(uri);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("conten-type", "application/json");
            httpCon.setRequestProperty("apiKey", "r$3#23516ewew5");
            OutputStream os = httpCon.getOutputStream();

            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");

            String sendTo=ConcatListCorreos(correo.getSendTo());

            osw.write("{\n" +
                        "\n" +
                        "    \"sendto\" : \"" + sendTo + "\",\n" +
                        "\n" +
                        "    \"emailsubject\" : \"" + correo.getSubject() + "\",\n" +
                        "\n" +
                        "    \"bodyhtml\" : \"" + correo.getBody() + "\",\n" +
                        "\n" +
                        "    \"from\" : \"notificacionestasa@gmail.com\"\n" +
                        "\n" +
                        "}");

            osw.flush();
            osw.close();
            os.close();  //don't forget to close the OutputStream
            httpCon.connect();


            msj.setMensaje("Ok, " +httpCon.getResponseCode() );
        }catch (Exception e){
            msj.setMensaje(e.getMessage());
        }

        return msj;
    }

    public Mensaje EnviarNotifDescTolvas(NotifDescTolvasDto imports)throws Exception{
        String subject = "CORRECCIÓN DESCARGAS";
        String titulo = "Corrección de Asignación de Embarcaciones a las Descargas";
        String mensaje = "En las siguientes descargas han sido modificadas la asignación de embarcaciones ";
        String[] header = {"Cetro", "NroDesc", "Emba", "Matr", "CBOD", "FechIniDesc", "FechFinDesc", "PescDesc"};
        String[] Fields={"EMAIL"};
        String option="CDMSO = 7 AND CDPTA = '"+imports.getPlanta()+"'";

        Mensaje msj=new Mensaje();
        CorreoDto dto= new CorreoDto();

        try {
            List<MaestroOptions> options = new ArrayList<>();
            MaestroOptions mo = new MaestroOptions();
            mo.setWa(option);
            options.add(mo);

            MaestroImports mi = new MaestroImports();
            mi.setTabla("ZFLMOC");
            mi.setDelimitador("|");
            mi.setFields(Fields);
            mi.setP_user("FGARCIA");
            mi.setOptions(options);


            MaestroExport me = jcoMaestrosService.obtenerMaestro(mi);

            List<String> emails = new ArrayList<>();
            for (int i = 0; i < me.getData().size(); i++) {

                for (Map.Entry<String, Object> entry : me.getData().get(i).entrySet()) {

                    String key = entry.getKey();
                    Object value = entry.getValue();

                    if (!emails.contains(value.toString())) {
                        emails.add(value.toString());
                    }
                }

            }
            String emailSAP = ConcatListCorreos(emails);
            log.error("email SAP: " + emailSAP);

            //emails de prueba
            List<String>emailPrueba= new ArrayList<>();
            emailPrueba.add("amagno.96@outlook.com");
            emailPrueba.add("ifp23@outlook.com");
            emailPrueba.add("grosales@xternal.biz");

            String body = getFormatHtml(titulo, mensaje, header, imports.getData());

            dto.setSubject(subject);
           // dto.setSendTo(emails);
            //se está poniendo emails de prueba
            dto.setSendTo(emailPrueba);
            dto.setBody(body);

            msj=EnviarCorreo(dto);
        }
        catch (Exception e){
           msj.setMensaje(e.getMessage());
        }


        return msj;
    }

    public String getFormatHtml(String titulo, String mensaje, String[]header, String[]data){
        String bodyHtml = null;

        bodyHtml  = "<span style='font-family:'Arial'; font-size:12px;'>";
        bodyHtml += "<strong><span style='font-size:13px;'>" + titulo + "</span></strong>";
        bodyHtml += "<br><br>";
        bodyHtml += "</span>";
        bodyHtml += "<font color =blue>" + mensaje + "</font> <br><hr><br>";

        if (header != null && data != null){
            bodyHtml += "<table border = 1>" +
                    "<tr>" +
                    "<tbody>";

            for (int i = 0; i < header.length; i++ ) {
                bodyHtml += "<td><font color =blue>" + header[i].toString().trim() + "</font></td>" ;
            }

            bodyHtml +="</tbody></tr> ";

            for (int i  = 0; i < data.length; i++ ) {
                String[] vector = data[i].toString().split("%");
                bodyHtml += "<tr>";

                for (int j = 0; j < vector.length ; j++ ) {
                    bodyHtml += "<td>" + vector[j].toString().trim() + "</td>";
                }

                bodyHtml += "</tr>";
            }

            bodyHtml += "</table>";
        }

        bodyHtml += "<br><hr><br>";

        return bodyHtml;
    }

    public String ConcatListCorreos(List<String> listaCorreos){

        String sendTo="";
        if(listaCorreos.size()==1){
            sendTo = listaCorreos.get(0);
        }else {
            for (int i = 0; i < listaCorreos.size(); i++) {

                if(i==listaCorreos.size()-1){
                    sendTo += listaCorreos.get(i);
                }else {
                    sendTo += listaCorreos.get(i) + ", ";
                }

            }
        }

        return sendTo;
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