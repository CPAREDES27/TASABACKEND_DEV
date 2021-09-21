package com.incloud.hcp.jco.tripulantes.service.impl;

import com.incloud.hcp.jco.tripulantes.dto.*;
import com.incloud.hcp.util.*;
import com.incloud.hcp.jco.tripulantes.service.JCOPDFsService;
import com.sap.conn.jco.*;
import io.swagger.models.auth.In;
import org.apache.pdfbox.contentstream.PDContentStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf.PDLayoutAttributeObject;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDGamma;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.pdmodel.interactive.form.PDVariableText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class JCOPDFsImpl implements JCOPDFsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());



    public PDFExports GenerarPDFZarpe(PDFZarpeImports imports)throws Exception{

        PDFExports pdf=new PDFExports();
        PDFZarpeDto dto= new PDFZarpeDto();


        String path = Constantes.RUTA_ARCHIVO_IMPORTAR + "Archivo.pdf";
        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_REGZAR_ADM_REGZAR);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_TOPE", imports.getP_tope());
            importx.setValue("P_CDZAT", imports.getP_cdzat());
            importx.setValue("P_WERKS", imports.getP_werks());
            importx.setValue("P_WERKP", imports.getP_werkp());
            importx.setValue("P_CANTI", imports.getP_canti());
            importx.setValue("P_CDMMA", imports.getP_cdmma());
            importx.setValue("P_PERNR", imports.getP_pernr());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            stfcConnection.execute(destination);

            JCoTable T_ZATRP = tables.getTable(Tablas.T_ZATRP);
            JCoTable T_DZATR = tables.getTable(Tablas.T_DZATR);
            JCoTable T_VGCER = tables.getTable(Tablas.T_VGCER);

            for(int i=0; i<T_ZATRP.getNumRows(); i++){
                T_ZATRP.setRow(i);

                JCoField fieldF = T_ZATRP.getField(PDFZarpeConstantes.FEARR);
                Date date=fieldF.getDate();
                SimpleDateFormat dia = new SimpleDateFormat("dd/MM/yyyy");
                String fecha = dia.format(date);

                JCoField fieldH = T_ZATRP.getField(PDFZarpeConstantes.HRARR );
                Date time =fieldH.getTime();
                SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
                String hora = hour.format(time);

                String codigoZarpe=T_ZATRP.getString(PDFZarpeConstantes.CDZAT);
                int codigoZ=Integer.parseInt(codigoZarpe);
                String codigo =String.valueOf(codigoZ);
                dto.setCodigoZarpe(codigo);
                dto.setCapitania(T_ZATRP.getString(PDFZarpeConstantes.DSWKP));
                dto.setNombreNave(T_ZATRP.getString(PDFZarpeConstantes.DSWKS));
                dto.setMatricula(T_ZATRP.getString(PDFZarpeConstantes.MREMB));
                dto.setAB(T_ZATRP.getString(PDFZarpeConstantes.AQBRT));
                dto.setZonaPesca(T_ZATRP.getString(PDFZarpeConstantes.DSWKP));
                dto.setTiempoOperacio(T_ZATRP.getString(PDFZarpeConstantes.TOPER));
                dto.setEstimadaArribo(fecha+"   "+ hora);
                dto.setRepresentante(T_ZATRP.getString(PDFZarpeConstantes.RACRE ));
                dto.setEmergenciaNombre(T_ZATRP.getString(PDFZarpeConstantes.DSEMP));
                dto.setEmergenciaDireccion(T_ZATRP.getString(PDFZarpeConstantes.DFEMP));
                dto.setEmergenciaTelefono(T_ZATRP.getString(PDFZarpeConstantes.TFEMP));
                dto.setFecha(T_ZATRP.getString(PDFZarpeConstantes.FEZAT));


            }
            logger.error("RolTripulacion");
            String[] CamposRolTripulacion= new String[]{PDFZarpeConstantes.NOMBR,
                                                        PDFZarpeConstantes.NRLIB,
                                                        PDFZarpeConstantes.FEFVG,
                                                        PDFZarpeConstantes.STEXT};
            String[][] RolTripulacion=new String[T_DZATR.getNumRows()+1][CamposRolTripulacion.length];

            RolTripulacion[0]= PDFZarpeConstantes.fieldRolTripulacion;
            int con=1;
            for(int i=0; i<T_DZATR.getNumRows(); i++){
                T_DZATR.setRow(i);

                String[] registros=new String[CamposRolTripulacion.length+1];
                int campos=0;
                for(int j=0; j<registros.length; j++){
                    if(j==0){
                        registros[j]=String.valueOf(con);

                    }else {

                            registros[j] = T_DZATR.getString(CamposRolTripulacion[campos]);
                            String dni = T_DZATR.getString(PDFZarpeConstantes.NRDNI);
                            if (registros[j].trim().compareTo("PATRON E/P") == 0) {
                                dto.setNombrePatron(registros[1]);
                                dto.setDni(dni);
                            }

                        campos++;
                    }
                }

                RolTripulacion[con]=registros;
                con++;
            }
            logger.error("Certificados");

            String[] CamposCertificados= new String[]{PDFZarpeConstantes.DSCER,
                                                         PDFZarpeConstantes.DSETP};
            String[][] Certificados=new String[T_VGCER.getNumRows()+1][CamposCertificados.length];
            Certificados[0]= PDFZarpeConstantes.fieldCertificados;
            logger.error("Certificados_1");
            con=1;
            for(int i=0; i<T_VGCER.getNumRows(); i++){
                T_VGCER.setRow(i);

                String[] registros=new String[CamposCertificados.length+1];
                int campos=0;
                for(int j=0; j<registros.length; j++){

                    if(j==0){
                        registros[j]=String.valueOf(con);
                    }else if(j==1) {

                        registros[j] = T_VGCER.getString(CamposCertificados[campos]);
                        campos++;


                    }else if(j==2){
                        if(registros[1].trim().compareTo("ARQUEO")==0){

                            registros[j]=T_VGCER.getString(PDFZarpeConstantes.NRCER);

                        }else if(registros[1].trim().compareTo("REGISTRO DE RADIOBALIZA")==0
                                || registros[1].trim().compareTo("MATRICULA DE NAVES")==0) {

                            registros[j] = T_VGCER.getString(PDFZarpeConstantes.FECCF);
                        }else{
                            registros[j] = T_VGCER.getString(CamposCertificados[campos]);
                        }
                    campos++;
                    }
                }

                Certificados[con]=registros;
                con++;
            }


           PlantillaPDFZarpe(path, dto, RolTripulacion, Certificados);

            Metodos exec = new Metodos();
            pdf.setBase64(exec.ConvertirABase64(path));
            pdf.setMensaje("Ok");


        }catch (Exception e){
            pdf.setMensaje(e.getMessage());
        }
        return pdf;
    }

    public void PlantillaPDFZarpe(String path, PDFZarpeDto dto, String[][] rolTripulacion, String[][] certificados)throws Exception{

        logger.error("PlantillaPDF");

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        String tasa= Constantes.RUTA_ARCHIVO_IMPORTAR+"logo.png";
        String guardiaCostera= Constantes.RUTA_ARCHIVO_IMPORTAR+"logocapitania.png";
        PDImageXObject logoTasa = PDImageXObject.createFromFile(tasa,document);
        PDImageXObject logoGuardiaCostera = PDImageXObject.createFromFile(guardiaCostera,document);

        document.addPage(page);

// Create a new font object selecting one of the PDF base fonts
        PDFont bold = PDType1Font.HELVETICA_BOLD;
        PDFont font = PDType1Font.HELVETICA;

// Start a new content stream which will "hold" the to be created content
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        //logos superiores
        contentStream.drawImage(logoTasa, 50, 800);
        contentStream.drawImage(logoGuardiaCostera, 280, 800);


        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(170, 790);
        contentStream.drawString(PDFZarpeConstantes.titulo);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(230, 780);
        contentStream.drawString(PDFZarpeConstantes.titulo2);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(65, 765);
        contentStream.drawString(PDFZarpeConstantes.capitania );
        contentStream.endText();

        //insertando capitania guardacostas marítimas
        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(235, 765);
        contentStream.drawString(dto.getCapitania());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(235, 764);
        contentStream.drawString("___________________________________________________________________________");
        contentStream.endText();


        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(50, 750);
        contentStream.drawString(PDFZarpeConstantes.uno);
        contentStream.endText();

        //insertando nombre de nave
        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(150, 750);
        contentStream.drawString(dto.getNombreNave());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(150, 749);
        contentStream.drawString("_________________________________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(50, 740);
        contentStream.drawString(PDFZarpeConstantes.dos);
        contentStream.endText();

        //insertando numero de matrícula
        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(150, 740);
        contentStream.drawString(dto.getMatricula());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(150, 739);
        contentStream.drawString("_______________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(300, 740);
        contentStream.drawString(PDFZarpeConstantes.tres);
        contentStream.endText();

        //insertando A.B.
        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(334, 740);
        contentStream.drawString(dto.getAB());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(334, 739);
        contentStream.drawString("__________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(50, 730);
        contentStream.drawString(PDFZarpeConstantes.cuatro);
        contentStream.endText();

        //insertando zona de pesca
        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(150, 730);
        contentStream.drawString(dto.getZonaPesca());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(150, 729);
        contentStream.drawString("_________________________________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(50, 720);
        contentStream.drawString(PDFZarpeConstantes.cinco);
        contentStream.endText();

        //insertar tiempo de operacion
        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(180, 720);
        contentStream.drawString(dto.getTiempoOperacio());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(180, 719);
        contentStream.drawString("_____________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(50, 710);
        contentStream.drawString(PDFZarpeConstantes.seis);
        contentStream.endText();

        //insertar dia y hora estimado de arribo
        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(180, 710);
        contentStream.drawString(dto.getEstimadaArribo());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(180, 709);
        contentStream.drawString("_____________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(50, 700);
        contentStream.drawString(PDFZarpeConstantes.siete);
        contentStream.endText();

        //insertando representante acreditado
        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(180, 700);
        contentStream.drawString(dto.getRepresentante());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(180, 699);
        contentStream.drawString("_________________________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(50, 690);
        contentStream.drawString(PDFZarpeConstantes.ocho);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(165, 690);
        contentStream.drawString(PDFZarpeConstantes.ochoA);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(60, 680);
        contentStream.drawString(PDFZarpeConstantes.ochoB);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(60, 670);
        contentStream.drawString(PDFZarpeConstantes.ochoC);
        contentStream.endText();


        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(50, 660);
        contentStream.drawString(PDFZarpeConstantes.nueve);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(50, 360);
        contentStream.drawString(PDFZarpeConstantes.diez);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(50, 228);
        contentStream.drawString(PDFZarpeConstantes.once);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(60, 216);
        contentStream.drawString(PDFZarpeConstantes.onceA);
        contentStream.endText();

        //Emergencia
        //insertando nombre
        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(130, 216);
        contentStream.drawString(dto.getEmergenciaNombre());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(130, 215);
        contentStream.drawString("______________________________________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(60, 204);
        contentStream.drawString(PDFZarpeConstantes.onceB);
        contentStream.endText();

        //insertando Dirección
        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(130, 204);
        contentStream.drawString(dto.getEmergenciaDireccion());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(130, 203);
        contentStream.drawString("______________________________________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(60, 192);
        contentStream.drawString(PDFZarpeConstantes.onceC);
        contentStream.endText();

        //insertando telefono
        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(130, 192);
        contentStream.drawString(dto.getEmergenciaTelefono());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(130, 191);
        contentStream.drawString("______________________________________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(50, 180);
        contentStream.drawString(PDFZarpeConstantes.doce);
        contentStream.endText();

        //insertando nombre patron
        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(150, 180);
        contentStream.drawString(dto.getNombrePatron());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(150, 179);
        contentStream.drawString("_____________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(350, 180);
        contentStream.drawString(PDFZarpeConstantes.trece);
        contentStream.endText();

        //insertando fecha
        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(396, 180);
        contentStream.drawString(dto.getFecha());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(396, 179);
        contentStream.drawString("__________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(50, 168);
        contentStream.drawString(PDFZarpeConstantes.catorce);
        contentStream.endText();

        //insertando dni
        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(150, 168);
        contentStream.drawString(dto.getDni());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(150, 167);
        contentStream.drawString("__________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(150, 125);
        contentStream.drawString(PDFZarpeConstantes.firma);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(175, 115);
        contentStream.drawString(PDFZarpeConstantes.firmaPatron);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(350, 125);
        contentStream.drawString(PDFZarpeConstantes.firma);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(360, 115);
        contentStream.drawString(PDFZarpeConstantes.capitaniaGuardacosta);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(50, 90);
        contentStream.drawString(PDFZarpeConstantes.nota);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(50, 80);
        contentStream.drawString(PDFZarpeConstantes.notaUno);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(50, 70);
        contentStream.drawString(PDFZarpeConstantes.notaDos);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(50, 60);
        contentStream.drawString(PDFZarpeConstantes.notaDos1);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(50, 50);
        contentStream.drawString(PDFZarpeConstantes.notaTres);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(50, 40);
        contentStream.drawString(PDFZarpeConstantes.notaTres1);
        contentStream.endText();

        logger.error("PlantillaPDF_1");
        drawTableRolTripulacion(page, contentStream, 655.0f, 60.0f, rolTripulacion);
        logger.error("PlantillaPDF_2");
        drawTableCertificados(contentStream,355, 60, certificados);
        logger.error("PlantillaPDF_3");
        drawCuadroCodigoZarpe(contentStream, 830, 440,dto.getCodigoZarpe());
        contentStream.close();
        document.save(path);
        document.close();

    }

    public  void drawTableRolTripulacion(PDPage page, PDPageContentStream contentStream,
                                 float y, float margin, String[][] content) throws IOException {

        logger.error("drawTableRolTripulacion");
        final int rows = content.length;
        final int cols = content[0].length;
        final float rowHeight = 12.0f;
        final float tableWidth = page.getMediaBox().getWidth() - 2.0f * margin;
        final float tableHeight = rowHeight * (float) rows;
        final float colWidth = 85.33f;

        //draw the rows
        float nexty = y ;
        for (int i = 0; i <= rows; i++) {
                contentStream.moveTo(margin, nexty);
                contentStream.lineTo(margin + tableWidth, nexty);
                contentStream.stroke();
                nexty -= rowHeight;

        }


        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols+1; i++) {

            if(i==1){
                nextx=margin+20;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
            }else if(i==2){
                nextx=margin+209f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
            }else if(i==5){
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
                nextx += colWidth+10;
            }else {
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
                nextx += colWidth;
            }

        }


        //now add the text



        float texty=y-10;
        for(int i=0; i<content.length; i++) {

            String[]fields=content[i];
            float textx=margin+5;

            for (int j = 0; j < fields.length; j++) {

                switch (j) {
                    case 1:
                        if(i==0){
                            textx = 140;
                        }else {
                            textx = 90;
                        }
                        break;
                    case 2:
                        if(i==0){
                            textx=290;
                        }else {
                            textx = 280;
                        }
                        break;
                    case 3:
                        if(i==0){
                            textx = 390;
                        }else{
                            textx = 385;
                        }
                        break;
                    case 4:
                        if(i==0){
                            textx = 465;
                        }else {
                            textx = 450;
                        }
                        break;
                }

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 6);
                contentStream.newLineAtOffset(textx, texty);
                contentStream.showText(fields[j]);
                contentStream.endText();


            }
            texty-=12;
        }

    }

    public  void drawTableCertificados(PDPageContentStream contentStream,
                                         float y, float margin, String[][] content) throws IOException {

        logger.error("drawTableCertificados");
        final int rows = content.length;
        final int cols = content[0].length;
        final float rowHeight = 12.0f;
        final float tableWidth = 400.5f;
        final float tableHeight = rowHeight * (float) rows;
        //final float colWidth = tableWidth / (float) cols;
        final float colWidth = 170f;


        //draw the rows
        float nexty = y ;
        for (int i = 0; i <= rows; i++) {
            contentStream.moveTo(margin, nexty);
            contentStream.lineTo(margin + tableWidth, nexty);
            contentStream.stroke();
            nexty -= rowHeight;

        }


        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols+1; i++) {

            if(i==1){
                nextx=margin+20;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
            }else if(i==2){
                nextx=margin+230.2f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
            }else {
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
                nextx += colWidth;
            }
        }


        float texty=y-10;
        for(int i=0; i<content.length;i++) {

            String[]fields=content[i];
            float textx=margin+10;

            for (int j = 0; j < fields.length; j++) {

                switch (j) {
                    case 1:
                        if(i==0){
                            textx = 150;
                        }else {
                            textx = 100;
                        }
                        break;
                    case 2:
                        if(i==0){
                            textx = 340;
                        }else {
                            textx = 330;
                        }
                        break;

                }

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 6);
                contentStream.newLineAtOffset(textx, texty);
                contentStream.showText(fields[j]);
                contentStream.endText();


            }
            texty-=12;
        }


    }
    public void drawCuadroCodigoZarpe( PDPageContentStream contentStream, float y, float margin
                                        ,String codigoZarpe)throws IOException{

        final int rows = 1;
        final int cols = 1;
        final float rowHeight = 20.0f;
        final float tableWidth = 95f;
        final float tableHeight = 20;
        //final float colWidth = tableWidth / (float) cols;
        final float colWidth = 170f;
        //draw the rows
        float nexty = y ;
        for (int i = 0; i <= rows; i++) {
            contentStream.moveTo(margin, nexty);
            contentStream.lineTo(margin + tableWidth, nexty);
            contentStream.stroke();
            nexty -= rowHeight;

        }


        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols; i++) {


                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
                nextx+=tableWidth;
        }

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 11);
        contentStream.newLineAtOffset(margin+10, y-15);
        contentStream.showText("N°");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 11);
        contentStream.newLineAtOffset(margin+40, y-15);
        contentStream.showText(codigoZarpe);
        contentStream.endText();
    }

    public PDFExports GenerarPDFTravesia(PDFZarpeImports imports)throws Exception{

        String path = Constantes.RUTA_ARCHIVO_IMPORTAR + "Archivo.pdf";
        PDFExports pdf=new PDFExports();
        PDFTravesiaDto dto= new PDFTravesiaDto();

        try{

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_REGZAR_ADM_REGZAR);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_TOPE", imports.getP_tope());
            importx.setValue("P_CDZAT", imports.getP_cdzat());
            importx.setValue("P_WERKS", imports.getP_werks());
            importx.setValue("P_WERKP", imports.getP_werkp());
            importx.setValue("P_CANTI", imports.getP_canti());
            importx.setValue("P_CDMMA", imports.getP_cdmma());
            importx.setValue("P_PERNR", imports.getP_pernr());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            stfcConnection.execute(destination);

            JCoTable T_ZATRP = tables.getTable(Tablas.T_ZATRP);

            for(int i=0; i<T_ZATRP.getNumRows(); i++){
                T_ZATRP.setRow(i);

                JCoField fieldF = T_ZATRP.getField(PDFZarpeConstantes.FEARR);
                Date date=fieldF.getDate();
                SimpleDateFormat dia = new SimpleDateFormat("dd/MM/yyyy");
                String fechaArribo = dia.format(date);

                JCoField fieldH = T_ZATRP.getField(PDFZarpeConstantes.HRARR );
                Date time =fieldH.getTime();
                SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
                String horaArribo = hour.format(time);

                JCoField fieldFEZAT = T_ZATRP.getField(PDFZarpeConstantes.FEZAT);
                Date dateFEZAT=fieldFEZAT.getDate();
                SimpleDateFormat diaFEZAT = new SimpleDateFormat("dd/MM/yyyy");
                String fechaZarpe = diaFEZAT.format(dateFEZAT);

                JCoField fieldHRZAR = T_ZATRP.getField(PDFZarpeConstantes.HRZAR );
                Date timeHRZAR =fieldHRZAR.getTime();
                SimpleDateFormat hourHRZAR = new SimpleDateFormat("HH:mm:ss");
                String horaZarpe = hourHRZAR.format(timeHRZAR);


                dto.setNombreNave(T_ZATRP.getString(PDFZarpeConstantes.DSWKS));
                dto.setMatricula(T_ZATRP.getString(PDFZarpeConstantes.MREMB));
                dto.setZarpePuerto(T_ZATRP.getString(PDFZarpeConstantes.DSWKP));
                dto.setZpFecha(fechaZarpe);
                dto.setZpHora(horaZarpe);
                dto.setArriboPuerto(T_ZATRP.getString(PDFZarpeConstantes.DSWKP));
                dto.setApFecha(fechaArribo);
                dto.setApHora(horaArribo);
                dto.setZonaOperacion("");
                dto.setLatitud("");
                dto.setLongitud("");
                dto.setTiempo("");
                dto.setTiempoNavegacion("");
                dto.setTipoPesca("");
                dto.setCantidad("");
                dto.setDescargar("");
                dto.setComprador("");
                dto.setParteNovedadSalidaMar("");
                dto.setNombrePatron("");
                dto.setLugarFecha("");


                PlantillaPDFTravesia(path, dto);
                Metodos exec = new Metodos();
                pdf.setBase64(exec.ConvertirABase64(path));
                pdf.setMensaje("Ok");

            }

        }catch (Exception e){

            pdf.setMensaje(e.getMessage());
        }

        return pdf;
    }

    public void PlantillaPDFTravesia(String path, PDFTravesiaDto dto)throws Exception{

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        String tasa= Constantes.RUTA_ARCHIVO_IMPORTAR+"logo.png";
        String guardiaCostera= Constantes.RUTA_ARCHIVO_IMPORTAR+"logocapitania.png";
        PDImageXObject logoTasa = PDImageXObject.createFromFile(tasa,document);
        PDImageXObject logoGuardiaCostera = PDImageXObject.createFromFile(guardiaCostera,document);

        document.addPage(page);

// Create a new font object selecting one of the PDF base fonts
        PDFont bold = PDType1Font.HELVETICA_BOLD;
        PDFont font = PDType1Font.HELVETICA;

// Start a new content stream which will "hold" the to be created content
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        //logos superiores
        contentStream.drawImage(logoTasa, 50, 750);
        contentStream.drawImage(logoGuardiaCostera, 280, 750);

        contentStream.beginText();
        contentStream.setFont(bold, 10);
        contentStream.moveTextPositionByAmount(45, 710);
        contentStream.drawString(PDFTravesiaConstantes.titulo);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(40, 670);
        contentStream.drawString(PDFTravesiaConstantes.uno);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(180, 670);
        contentStream.drawString(dto.getNombreNave());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(180, 670);
        contentStream.drawString("__________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(40, 650);
        contentStream.drawString(PDFTravesiaConstantes.dos);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(180, 650);
        contentStream.drawString(dto.getMatricula());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(180, 650);
        contentStream.drawString("__________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(40, 630);
        contentStream.drawString(PDFTravesiaConstantes.tres);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(170, 630);
        contentStream.drawString(dto.getZarpePuerto());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(170, 630);
        contentStream.drawString("____________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(300, 630);
        contentStream.drawString(PDFTravesiaConstantes.fecha);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(340, 630);
        contentStream.drawString(dto.getZpFecha());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(340, 630);
        contentStream.drawString("_________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(450, 630);
        contentStream.drawString(PDFTravesiaConstantes.hora);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(485, 630);
        contentStream.drawString(dto.getZpHora());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(485, 630);
        contentStream.drawString("___________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(40, 610);
        contentStream.drawString(PDFTravesiaConstantes.cuatro);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(170, 610);
        contentStream.drawString(dto.getArriboPuerto());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(170, 610);
        contentStream.drawString("____________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(300, 610);
        contentStream.drawString(PDFTravesiaConstantes.fecha);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(340, 610);
        contentStream.drawString(dto.getApFecha());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(340, 610);
        contentStream.drawString("_________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(450, 610);
        contentStream.drawString(PDFTravesiaConstantes.hora);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(485, 610);
        contentStream.drawString(dto.getApHora());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(485, 610);
        contentStream.drawString("___________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(40, 590);
        contentStream.drawString(PDFTravesiaConstantes.cinco);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(180, 590);
        contentStream.drawString(dto.getZonaOperacion());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(180, 590);
        contentStream.drawString("__________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(100, 570);
        contentStream.drawString(PDFTravesiaConstantes.latitud);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(150, 570);
        contentStream.drawString(dto.getLatitud());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(150, 570);
        contentStream.drawString("___________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(270, 570);
        contentStream.drawString(PDFTravesiaConstantes.longitud);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(330, 570);
        contentStream.drawString(dto.getLongitud());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(330, 570);
        contentStream.drawString("_______________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(420, 570);
        contentStream.drawString(PDFTravesiaConstantes.tiempo);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(465, 570);
        contentStream.drawString("_______________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(465, 570);
        contentStream.drawString(dto.getTiempo());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(40, 550);
        contentStream.drawString(PDFTravesiaConstantes.seis);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(200, 550);
        contentStream.drawString(dto.getTiempoNavegacion());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(200, 550);
        contentStream.drawString("_______________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(40, 530);
        contentStream.drawString(PDFTravesiaConstantes.siete);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(100, 510);
        contentStream.drawString(PDFTravesiaConstantes.sieteA);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(200, 510);
        contentStream.drawString(dto.getTipoPesca());
        contentStream.endText();

        int ySiete=510;
        int camposSiete=3;

        for(int i=0; i<camposSiete; i++){

            contentStream.beginText();
            contentStream.setFont(font, 10);
            contentStream.moveTextPositionByAmount(200, ySiete);
            contentStream.drawString("_______________________");
            contentStream.endText();
            ySiete-=20;
        }


        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(370, 510);
        contentStream.drawString(PDFTravesiaConstantes.sieteB);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(445, 510);
        contentStream.drawString(dto.getCantidad());
        contentStream.endText();

        ySiete=510;

        for(int i=0; i<camposSiete; i++){

            contentStream.beginText();
            contentStream.setFont(font, 10);
            contentStream.moveTextPositionByAmount(445, ySiete);
            contentStream.drawString("__________________");
            contentStream.endText();
            ySiete-=20;
        }


        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(40, 430);
        contentStream.drawString(PDFTravesiaConstantes.ocho);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(150, 430);
        contentStream.drawString(dto.getDescargar());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(150, 430);
        contentStream.drawString("________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(40, 410);
        contentStream.drawString(PDFTravesiaConstantes.nueve);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(150, 410);
        contentStream.drawString(dto.getComprador());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(150, 410);
        contentStream.drawString("________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(40, 390);
        contentStream.drawString(PDFTravesiaConstantes.diez);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(40, 370);
        contentStream.drawString(dto.getParteNovedadSalidaMar());
        contentStream.endText();

        int y=370;
        int campos=7;
        for(int i=0;i<campos; i++) {
            contentStream.beginText();
            contentStream.setFont(font, 10);
            contentStream.moveTextPositionByAmount(40, y);
            contentStream.drawString("____________________________________________________________________________________________");
            contentStream.endText();
            y-=20;
        }

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(40, 230);
        contentStream.drawString(PDFTravesiaConstantes.once);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(250, 230);
        contentStream.drawString(dto.getNombrePatron());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(250, 230);
        contentStream.drawString("______________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(40, 210);
        contentStream.drawString(PDFTravesiaConstantes.doce);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(50, 180);
        contentStream.drawString("__________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(350, 210);
        contentStream.drawString(PDFTravesiaConstantes.trece);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(350, 180);
        contentStream.drawString(dto.getLugarFecha());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(350, 180);
        contentStream.drawString("__________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(40, 140);
        contentStream.drawString(PDFTravesiaConstantes.nota);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(40, 130);
        contentStream.drawString(PDFTravesiaConstantes.notaUno);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(45, 120);
        contentStream.drawString(PDFTravesiaConstantes.notaUnoA);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(40, 110);
        contentStream.drawString(PDFTravesiaConstantes.notaDos);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(40, 100);
        contentStream.drawString(PDFTravesiaConstantes.notatres);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(40, 90);
        contentStream.drawString(PDFTravesiaConstantes.notaCuatro);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(45, 80);
        contentStream.drawString(PDFTravesiaConstantes.notaCuatroA);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(45, 70);
        contentStream.drawString(PDFTravesiaConstantes.notaCuatroB);
        contentStream.endText();

        drawCuadroCodigoZarpe(contentStream, 780, 440,"PRUEBA");

        contentStream.close();
        document.save(path);
        document.close();
    }

    public PDFExports GenerarPDFProtestos(ProtestosImports imports)throws Exception{

        String path = Constantes.RUTA_ARCHIVO_IMPORTAR + "Archivo.pdf";
        PDFExports pdf= new PDFExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_REGPRT_ADM_REGPRT);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("IP_TOPE", imports.getIp_tope());
            importx.setValue("IP_CDPRT", imports.getIp_cdprt());
            importx.setValue("IP_CANTI", imports.getIp_canti());
            importx.setValue("IP_PERNR", imports.getIp_pernr());

            List<Options> options = imports.getT_opcion();
            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < options.size(); i++) {
                Options o = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();

                record.put("DATA", o.getData());
                tmpOptions.add(record);
            }
            JCoParameterList export = stfcConnection.getExportParameterList();
            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC exec= new EjecutarRFC();
            exec.setTable(tables, Tablas.T_OPCION,tmpOptions);

            stfcConnection.execute(destination);

            JCoTable T_BAPRT = tables.getTable(Tablas.T_BAPRT);
            JCoTable T_TEXTOS = tables.getTable(Tablas.T_TEXTOS);
            JCoTable T_MENSAJ = tables.getTable(Tablas.T_MENSAJ);

            Metodos metodo = new Metodos();
            List<HashMap<String, Object>>  t_baprt = metodo.ObtenerListObjetos(T_BAPRT, imports.getFieldst_baprt());
            List<HashMap<String, Object>>  t_textos = metodo.ObtenerListObjetos(T_TEXTOS, imports.getFieldst_textos());
            List<HashMap<String, Object>>  t_mensaj = metodo.ListarObjetos(T_MENSAJ);


            String parrafoDos="Con la finalidad de dar cumplimiento a los diferentes dispositivos legales reglamentarios de " +
                    "Capitanía, pongo de su conocimiento el siguiente protesto informativo, el día 09/12/2020 a las 06:15" +
                    " horas aproximadamente nuestra E/P TASA 45 con matrícula CO- 22029-PM se encontraba acoderando a nuestra " +
                    "chata PABLO VI con matrícula CO-6542-AM para descargar 140 Tons de anchoveta en nuestra planta, producto de" +
                    " la marejada golpeó levemente la pluma de la chata con la proa del lado de estribor del barco, no hubieron" +
                    " daños mayores.";

            PDFProtestosDto dto = new PDFProtestosDto();
            dto.setTrato("SEÑOR");
            dto.setCargoUno("CAPITAN NAVIO");
            dto.setNombreCargoUno("LUDWING ZANABRIA ACOSTA");
            dto.setDomicilioLegal("Av Nestor Gambeta Km 14.1, Ex Fundo Márquez - Callao");
            dto.setCargoDos("");
            dto.setNombreCargoDos("JIMMY ANDERSO JARA SAENZ");
            dto.setDni("47197836");
            dto.setSegundoParrafo(parrafoDos);
            dto.setPuerto("CALLAO");
            dto.setFecha("10 de diciembre del 2020");
            dto.setNombreEmbarcacion("TASA 45");
            dto.setMatricula("CO-22029-PM");


            PlantillaPDFProtestos(path, dto);

            pdf.setBase64(metodo.ConvertirABase64(path));

            pdf.setMensaje("Ok");

        }catch (Exception e){
            pdf.setMensaje(e.getMessage());
        }


        return pdf;
    }

    public void PlantillaPDFProtestos(String Path, PDFProtestosDto dto)throws Exception{

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        String tasa= Constantes.RUTA_ARCHIVO_IMPORTAR+"logo.png";
        PDImageXObject logoTasa = PDImageXObject.createFromFile(tasa,document);
        PDRectangle mediabox=new PDRectangle();
        document.addPage(page);

        PDFont bold = PDType1Font.HELVETICA_BOLD;
        PDFont font = PDType1Font.HELVETICA;
        PDPageContentStream contentStream = new PDPageContentStream(document, page);


        contentStream.drawImage(logoTasa, 50, 770);

        contentStream.beginText();
        contentStream.setFont(bold, 14);
        contentStream.moveTextPositionByAmount(200, 750);
        contentStream.drawString("PROTESTO DE MAR INFORMATIVO");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(360, 710);
        contentStream.drawString(dto.getPuerto()+", "+dto.getFecha());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(40, 680);
        contentStream.drawString(dto.getTrato());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(40, 670);
        contentStream.drawString(dto.getCargoUno());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(40, 660);
        contentStream.drawString(dto.getNombreCargoUno());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(40, 650);
        contentStream.drawString(dto.getCargoDos());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(40, 640);
        contentStream.drawString(PDFProtestosDto.capitaniaGuardacostas);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(40, 630);
        contentStream.drawString(dto.getPuerto());
        contentStream.endText();

        String parrafoUno=PDFProtestosDto.primerParrafo1+dto.getDomicilioLegal()+PDFProtestosDto.primerParrafo2+dto.getNombreCargoDos()
                +PDFProtestosDto.primerParrafo3+dto.getDni()+PDFProtestosDto.primerParrafo4+dto.getNombreEmbarcacion()
                +PDFProtestosDto.primerParrafo5+dto.getMatricula()+PDFProtestosDto.primerParrafo6;

        int finPU= justificarParrafoUno(contentStream,font,12f,  parrafoUno, 600);
        int finPD= justificarParrafoDos(contentStream,font,12f,  dto.getSegundoParrafo(), finPU-20);

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(40, finPD-20);
        contentStream.drawString(PDFProtestosDto.textoFinal1);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(40, finPD-30);
        contentStream.drawString(PDFProtestosDto.textoFinal2);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(40, finPD-60);
        contentStream.drawString(PDFProtestosDto.textoFinal3);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(40, finPD-100);
        contentStream.drawString(PDFProtestosDto.atentamente);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 9);
        contentStream.moveTextPositionByAmount(40, 90);
        contentStream.drawString(PDFProtestosDto.nProtesto+"0000005004");
        contentStream.endText();



        contentStream.close();
        document.save(Path);
        document.close();


    }

    public int justificarParrafoUno(PDPageContentStream contentStream, PDFont pdfFont, float fontSize,  String text, int startY)
            throws IOException{

        logger.error("justificarTexto");
        int width = 470;
        int startX = 40;
       // float startY = 600;


        List<String> lines = new ArrayList<String>();


        int lastSpace = -1;
        while (text.length()> 0)
        {
            if(lines.size()>0){
                width=510;
            }
            int spaceIndex = text.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0)
                spaceIndex = text.length();
            String subString = text.substring(0, spaceIndex);
            logger.error("subString: "+subString);
            float size = fontSize * pdfFont.getStringWidth(subString) / 1000;

            if (size > width)
            {
                if (lastSpace < 0)
                    lastSpace = spaceIndex;
                subString = text.substring(0, lastSpace);
                lines.add(subString);

                text = text.substring(lastSpace).trim();

                lastSpace = -1;
            }
            else if (spaceIndex == text.length())
            {
                logger.error("lines.add(text) "+text);
                lines.add(text);

                text = "";
            }
            else
            {
                lastSpace = spaceIndex;
            }
        }
            logger.error("lines.size: "+lines.size());

        //for (String line: lines)
           for(int i=0; i<lines.size(); i++)
        {


            contentStream.beginText();
            contentStream.setFont(pdfFont, fontSize);
            if(i==0){
                contentStream.newLineAtOffset(startX+30, startY);
            }else {
                contentStream.newLineAtOffset(startX, startY);
            }

            float charSpacing = 0;
            float size=0.0f;
            if (lines.get(i).length() > 1)
            {
                 size = fontSize * pdfFont.getStringWidth(lines.get(i)) / 1000;

                logger.error("size "+size);
                float  free = width - size;
                int tam=lines.size()-1;
                if(i==tam){
                    charSpacing=0;
                }else {

                    if (free > 0) {
                        charSpacing = free / (lines.get(i).length() - 1);
                    }
                }
            }
            logger.error("charSpacing "+charSpacing);
            contentStream.setCharacterSpacing(charSpacing);
            contentStream.showText(lines.get(i));
            //contentStream.newLineAtOffset(startX, -leading);
            contentStream.endText();
            startY-=fontSize;

        }
        return startY;

    }

    public int justificarParrafoDos(PDPageContentStream contentStream, PDFont pdfFont, float fontSize,  String text, int startY)
            throws IOException{


        logger.error("justificarTexto");
        //float width = mediabox.getWidth() - 2*margin;
        //float startX = mediabox.getLowerLeftX() + margin;
        //float startY = mediabox.getUpperRightY() - margin;
        float width = 470;
        float startX = 40f;
        //float startY = 600;



        List<String> lines = new ArrayList<String>();


        int lastSpace = -1;
        while (text.length()> 0)
        {
            if(lines.size()>0){
                width=510;
            }
            int spaceIndex = text.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0)
                spaceIndex = text.length();
            String subString = text.substring(0, spaceIndex);
            logger.error("subString: "+subString);
            float size = fontSize * pdfFont.getStringWidth(subString) / 1000;

            if (size > width)
            {
                if (lastSpace < 0)
                    lastSpace = spaceIndex;
                subString = text.substring(0, lastSpace);
                lines.add(subString);

                text = text.substring(lastSpace).trim();

                lastSpace = -1;
            }
            else if (spaceIndex == text.length())
            {
                logger.error("lines.add(text) "+text);
                lines.add(text);

                text = "";
            }
            else
            {
                lastSpace = spaceIndex;
            }
        }


        for(int i=0; i<lines.size(); i++)
        {
            contentStream.beginText();
            contentStream.setFont(pdfFont, fontSize);
            contentStream.newLineAtOffset(startX, startY);

            float charSpacing = 0;
            float size=0.0f;
            if (lines.get(i).length() > 1)
            {
                size = fontSize * pdfFont.getStringWidth(lines.get(i)) / 1000;

                logger.error("size "+size);
                float  free = width - size;
                int tam=lines.size()-1;
                if(i==tam){
                    charSpacing=0;
                }else {

                    if (free > 0) {
                        charSpacing = free / (lines.get(i).length() - 1);
                    }
                }
            }
            logger.error("charSpacing "+charSpacing);
            contentStream.setCharacterSpacing(charSpacing);
            contentStream.showText(lines.get(i));
            //contentStream.newLineAtOffset(startX, -leading);
            contentStream.endText();
            startY-=fontSize-2;

        }
        return startY;
    }
}
