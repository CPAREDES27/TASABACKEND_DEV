package com.incloud.hcp.jco.tripulantes.service.impl;

import com.incloud.hcp.jco.tripulantes.dto.*;
import com.incloud.hcp.util.*;
import com.incloud.hcp.jco.tripulantes.service.JCOPDFZarpeService;
import com.sap.conn.jco.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class JCOPDFZarpeImpl implements JCOPDFZarpeService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());



    public PDFZarpeExports GenerarPDF(PDFZarpeImports imports)throws Exception{

        PDFZarpeExports pdf=new PDFZarpeExports();
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
                dto.setZonaPesca(T_ZATRP.getString(PDFZarpeConstantes.CDZPC));
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

            RolTripulacion[0]=PDFZarpeConstantes.fieldRolTripulacion;
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
            Certificados[0]=PDFZarpeConstantes.fieldCertificados;
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


           PlantillaPDF(path, dto, RolTripulacion, Certificados);

            Metodos exec = new Metodos();
            pdf.setBase64(exec.ConvertirABase64(path));
            pdf.setMensaje("Ok");


        }catch (Exception e){
            pdf.setMensaje(e.getMessage());
        }
        return pdf;
    }

    public void PlantillaPDF(String path, PDFZarpeDto dto, String[][] rolTripulacion, String[][] certificados)throws Exception{

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
        contentStream.moveTextPositionByAmount(150, 216);
        contentStream.drawString(dto.getEmergenciaNombre());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(150, 215);
        contentStream.drawString("_________________________________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(60, 204);
        contentStream.drawString(PDFZarpeConstantes.onceB);
        contentStream.endText();

        //insertando Dirección
        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(150, 204);
        contentStream.drawString(dto.getEmergenciaDireccion());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(150, 203);
        contentStream.drawString("_________________________________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(60, 192);
        contentStream.drawString(PDFZarpeConstantes.onceC);
        contentStream.endText();

        //insertando telefono
        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(150, 192);
        contentStream.drawString(dto.getEmergenciaTelefono());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(150, 191);
        contentStream.drawString("_________________________________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(50, 180);
        contentStream.drawString(PDFZarpeConstantes.doce);
        contentStream.endText();

        //insertando nombre patron
        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(150, 180);
        contentStream.drawString(dto.getNombrePatron());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(150, 179);
        contentStream.drawString("_________________________________________");
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
        contentStream.setFont(font, 7);
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
}
