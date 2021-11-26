package com.incloud.hcp.util.Mail.Service.Impl;

import com.incloud.hcp.jco.controlLogistico.dto.*;
import com.incloud.hcp.jco.maestro.dto.*;
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


    @Override
    public Mensaje EnviarInfoHorometroAveriado(InfoHorometrosAveriadosImport imports) throws Exception {
        Mensaje msj = new Mensaje();
        try {
            String remitente = "mareaeventos@tasa.com.pe";
            String asunto = "Horometros averiados";

            //Cuerpo del correo
            String body = "<p>Los siguientes horómetros han sido marcados como averiados en la embarcacion ";
            body += "<strong>" + imports.getEmbarcacion().getDescripcion() + "</strong>:</p>";

            for (EventoForEmailDto evento : imports.getEmbarcacion().getEventos()) {
                ArrayList<HorometroForEmailDto> listHorometrosAveriados = evento.getHorometrosAveriados();
                body += "<li>Para el evento " + evento.getNroEvento() + ": ";
                for (int i = 0; i < listHorometrosAveriados.size(); i++) {
                    HorometroForEmailDto horometroAveriado = listHorometrosAveriados.get(i);
                    body += horometroAveriado.getNombre();
                    if (i < listHorometrosAveriados.size()) {
                        body += ", ";
                    } else {
                        body += ".</li>";
                    }
                }
            }

            //Obtener correos
            String[] fields = {"EMAIL"};

            MaestroImportsKey importsReadTabla = new MaestroImportsKey();
            importsReadTabla.setDelimitador("|");
            importsReadTabla.setFields(fields);
            importsReadTabla.setTabla("ZFLMOC");
            importsReadTabla.setP_user("FGARCIA");
            importsReadTabla.setRowcount(0);
            importsReadTabla.setRowskips(0);
            importsReadTabla.setNo_data("");
            importsReadTabla.setOptions(new ArrayList<>());

            ArrayList<MaestroOptions> listOptionsWA = new ArrayList<>();
            MaestroOptions optionWA = new MaestroOptions();
            optionWA.setWa("CDMSO = '9'");
            listOptionsWA.add(optionWA);
            importsReadTabla.setOption(listOptionsWA);

            MaestroExport responseReadTable = jcoMaestrosService.obtenerMaestro2(importsReadTabla);

            List<HashMap<String, Object>> listDataEmails = responseReadTable.getData();
            List<String> emails = new ArrayList<>();
            for (HashMap<String, Object> dataEmail : listDataEmails) {
                String email = dataEmail.get("EMAIL").toString();
                emails.add(email);
            }
            List<String> emailsToSend = verificarEmails(imports.getEnvioPrd(), emails);

            CorreoDto dto = new CorreoDto();
            dto.setSubject(asunto);
            dto.setSendTo(emailsToSend);
            dto.setBody(body);

            msj = EnviarCorreo(dto);
        } catch (Exception e) {
            msj.setMensaje(e.getMessage());
        }

        return msj;
    }

    @Override
    public Mensaje EnviarCorreosSiniestro(InfoEventoImports imports) throws Exception {
        Mensaje msj = new Mensaje();
        try {
            for (SiniestroForEmailDto siniestro : imports.getEvento().getSiniestros()) {
                String remitente = "mareaeventos@tasa.com.pe";
                String asunto = "Marea y Eventos - Siniestro";
                //Cuerpo del correo
                String codIncidente = siniestro.getCodIncidente();
                String body = "<strong>" + siniestro.getDescCodIncidente() + "</strong>";
                body += "<br><br>" + siniestro.getDescripcion();

                //Obtener correos
                String[] fields = {"EMAIL"};
                List<MaestroOptions> options = new ArrayList<>();
                MaestroOptions mo = new MaestroOptions();
                mo.setWa("CDINC = '" + codIncidente + "'");
                options.add(mo);

                MaestroImports mi = new MaestroImports();
                mi.setTabla("ZFLMSI");
                mi.setDelimitador("|");
                mi.setFields(fields);
                mi.setP_user("FGARCIA");
                mi.setOptions(options);


                MaestroExport responseReadTable = jcoMaestrosService.obtenerMaestro(mi);
                List<HashMap<String, Object>> listDataEmails = responseReadTable.getData();
                List<String> emails = new ArrayList<>();
                for (HashMap<String, Object> dataEmail : listDataEmails) {
                    String email = dataEmail.get("EMAIL").toString();
                    emails.add(email);
                }

                List<String> emailsToSend = verificarEmails(imports.getEnvioPrd(), emails);

                CorreoDto dto = new CorreoDto();
                dto.setSubject(asunto);
                dto.setSendTo(emailsToSend);
                dto.setBody(body);

                msj = EnviarCorreo(dto);
            }
        } catch (Exception e) {
            msj.setMensaje(e.getMessage());
        }

        return msj;
    }

    public Mensaje EnviarCorreoConAdjunto(CorreoConAdjuntoDto imports) throws Exception {

        Mensaje msj = new Mensaje();


        String[] To = imports.getTo();
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();

            // true = multipart message
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            // helper.setFrom(imports.getFrom());
            helper.setFrom(new InternetAddress(imports.getFrom()));

            helper.setTo(To);
            helper.setSubject(imports.getSubject());
            helper.setText(imports.getText(), true);

            if (imports.getBase64() != "" && imports.getNombreArchivo() != "") {

                String RutaArchivo = Constantes.RUTA_ARCHIVO_IMPORTAR + imports.getNombreArchivo();

                CrearArchivo(imports.getBase64(), RutaArchivo);
                FileSystemResource file
                        = new FileSystemResource(new File(RutaArchivo));

                helper.addAttachment(imports.getNombreArchivo(), file);

            }

            if (imports.getRutaArchivo() != "") {

                FileSystemResource file
                        = new FileSystemResource(new File(imports.getRutaArchivo()));

                helper.addAttachment(imports.getNombreArchivo(), file);
            }

            javaMailSender.send(msg);
            msj.setMensaje("Ok");

        } catch (Exception e) {
            msj.setMensaje(e.getMessage());

        }

        return msj;
    }

    /**
     * Evalua las condiciones de emplear los correos de ptoducción, de pruebas o ambos
     *
     * @param enviosPrd condición para indicar que se enviará a los correos de producción
     * @param emailsPrd correos de producción
     * @return List<String>
     * @throws Exception
     */
    public List<String> verificarEmails(String enviosPrd, List<String> emailsPrd) throws Exception {
        List<String> correos = new ArrayList<>();
        try {
            String[] fields = {"CDCNS", "DESCR", "VAL01", "VAL02", "VAL03", "VAL04"};
            MaestroImportsKey importsReadTabla = new MaestroImportsKey();
            importsReadTabla.setDelimitador("|");
            importsReadTabla.setFields(fields);
            importsReadTabla.setTabla("ZFLCNS");
            importsReadTabla.setP_user("FGARCIA");
            importsReadTabla.setRowcount(0);
            importsReadTabla.setRowskips(0);
            importsReadTabla.setNo_data("");
            importsReadTabla.setOptions(new ArrayList<>());

            ArrayList<MaestroOptions> listOptionsWA = new ArrayList<>();
            MaestroOptions optionWA = new MaestroOptions();
            optionWA.setWa("CDCNS = 85 OR CDCNS = 88 OR CDCNS = 89");
            listOptionsWA.add(optionWA);
            importsReadTabla.setOption(listOptionsWA);

            MaestroExport responseReadTable = jcoMaestrosService.obtenerMaestro2(importsReadTabla);
            List<HashMap<String, Object>> constantes = responseReadTable.getData();

            //Evaluar las posibilidades
            HashMap<String, Object> constanteEnviarCorreosPrueba = constantes.stream().filter(constante -> constante.get("CDCNS").toString().equals("88")).findFirst().orElse(null); //BTP_ENVIAR_CORREOSPRUEBA
            HashMap<String, Object> constanteEnviarCorreosPrd = constantes.stream().filter(constante -> constante.get("CDCNS").toString().equals("89")).findFirst().orElse(null); //BTP_ENVIAR_CORREOSPRD

            String enviarCorreosPrueba = constanteEnviarCorreosPrueba != null ? constanteEnviarCorreosPrueba.get("VAL01").toString() : "";
            String enviarCorreoPrd = constanteEnviarCorreosPrd != null ? constanteEnviarCorreosPrd.get("VAL01").toString() : "";

            if (enviosPrd.equalsIgnoreCase("X") && enviarCorreoPrd.equalsIgnoreCase("X")) {
                //Añadir los correos de PRD
                correos = new ArrayList<>(emailsPrd);
            }

            if (enviarCorreosPrueba.equalsIgnoreCase("X")) {
                //Añadir los correos de prueba

                //Obtener correos de prueba
                HashMap<String, Object> constanteCorreos = constantes.stream().filter(constante -> constante.get("CDCNS").toString().equals("85")).findFirst().orElse(null);
                if (constanteCorreos != null) {
                    String correo1 = constanteCorreos.get("VAL01").toString();
                    String correo2 = constanteCorreos.get("VAL02").toString();
                    String correo3 = constanteCorreos.get("VAL03").toString();
                    String correo4 = constanteCorreos.get("VAL04").toString();

                    if (!correo1.equals("")) {
                        correos.add(correo1);
                    }
                    if (!correo2.equals("")) {
                        correos.add(correo2);
                    }
                    if (!correo3.equals("")) {
                        correos.add(correo3);
                    }
                    if (!correo4.equals("")) {
                        correos.add(correo4);
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return correos;
    }


    public Mensaje EnviarCorreo(CorreoDto correo) throws Exception {

        Mensaje msj = new Mensaje();
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

            String sendTo = ConcatListCorreos(correo.getSendTo());

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


            msj.setMensaje("Ok, " + httpCon.getResponseCode());
        } catch (Exception e) {
            msj.setMensaje(e.getMessage());
        }

        return msj;
    }

    public Mensaje EnviarNotifDescTolvas(NotifDescTolvasDto imports) throws Exception {
        String subject = "CORRECCIÓN DESCARGAS";
        String titulo = "Corrección de Asignación de Embarcaciones a las Descargas";
        String mensaje = "En las siguientes descargas han sido modificadas la asignación de embarcaciones ";
        String[] header = {"Cetro", "NroDesc", "Emba", "Matr", "CBOD", "FechIniDesc", "FechFinDesc", "PescDesc"};
        String[] Fields = {"EMAIL"};
        String option = "CDMSO = 7 AND CDPTA = '" + imports.getPlanta() + "'";

        Mensaje msj = new Mensaje();
        CorreoDto dto = new CorreoDto();

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

            /*
            //emails de prueba
            List<String> emailPrueba = new ArrayList<>();
            emailPrueba.add("amagno.96@outlook.com");
            emailPrueba.add("ifp23@outlook.com");
            emailPrueba.add("grosales@xternal.biz");

             */
            List<String> emailsToSend = verificarEmails("", emails);

            String body = getFormatHtml(titulo, mensaje, header, imports.getData());

            dto.setSubject(subject);
            // dto.setSendTo(emails);
            //se está poniendo emails de prueba
            dto.setSendTo(emailsToSend);
            dto.setBody(body);

            msj = EnviarCorreo(dto);
        } catch (Exception e) {
            msj.setMensaje(e.getMessage());
        }


        return msj;
    }


    public String getFormatHtml(String titulo, String mensaje, String[] header, String[] data) {
        String bodyHtml = null;

        bodyHtml = "<span style='font-family:'Arial'; font-size:12px;'>";
        bodyHtml += "<strong><span style='font-size:13px;'>" + titulo + "</span></strong>";
        bodyHtml += "<br><br>";
        bodyHtml += "</span>";
        bodyHtml += "<font color =blue>" + mensaje + "</font> <br><hr><br>";

        if (header != null && data != null) {
            bodyHtml += "<table border = 1>" +
                    "<tr>" +
                    "<tbody>";

            for (int i = 0; i < header.length; i++) {
                bodyHtml += "<td><font color =blue>" + header[i].toString().trim() + "</font></td>";
            }

            bodyHtml += "</tbody></tr> ";

            for (int i = 0; i < data.length; i++) {
                String[] vector = data[i].toString().split("%");
                bodyHtml += "<tr>";

                for (int j = 0; j < vector.length; j++) {
                    bodyHtml += "<td>" + vector[j].toString().trim() + "</td>";
                }

                bodyHtml += "</tr>";
            }

            bodyHtml += "</table>";
        }

        bodyHtml += "<br><hr><br>";

        return bodyHtml;
    }

    public String ConcatListCorreos(List<String> listaCorreos) {

        String sendTo = "";
        if (listaCorreos.size() == 1) {
            sendTo = listaCorreos.get(0);
        } else {
            for (int i = 0; i < listaCorreos.size(); i++) {

                if (i == listaCorreos.size() - 1) {
                    sendTo += listaCorreos.get(i);
                } else {
                    sendTo += listaCorreos.get(i) + ", ";
                }

            }
        }

        return sendTo;
    }

    public void CrearArchivo(String base64, String path) throws IOException {

        File file = new File(path);

        try (FileOutputStream fos = new FileOutputStream(file);) {

            byte[] decoder = Base64.getDecoder().decode(base64);

            fos.write(decoder);
            log.error("Archivo Creado = " + path);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

}