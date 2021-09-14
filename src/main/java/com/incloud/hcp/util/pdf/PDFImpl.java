package com.incloud.hcp.util.pdf;

import com.incloud.hcp.util.Constant;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Mensaje;
import com.incloud.hcp.util.Metodos;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class PDFImpl implements PDFService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public Mensaje GenerarPDF()throws Exception{

        Mensaje m=new Mensaje();
        PDFDto dto= new PDFDto();
        String path = Constantes.RUTA_ARCHIVO_IMPORTAR + "Archivo.pdf";
        try {


            dto.setCapitania("CHICAMA");
            dto.setNombreNave("TASA 34");
            dto.setMatricula("PS-10415-PM");
            dto.setAB("219.24");
            dto.setZonaPesca("CHICAMA");
            dto.setTiempoOperacio("24");
            dto.setEstimadaArribo("25/06/2021");
            dto.setRepresentante("DAVID DELGADO GONZALES");
            dto.setEmergenciaNombre("TECNOLOGICA DE ALIMENTOS S.A.");
            dto.setEmergenciaDireccion("Sub Lote 3A, Zona Industrial del Puerto Malabrigo, Di");
            dto.setEmergenciaTelefono("");
            dto.setNombrePatron("PANTA PAIBA, JULIO");
            dto.setFecha("21/04/2021");
            dto.setDni("03463796");


            PlantillaPDF(path, dto);
            Metodos exec = new Metodos();
            m.setMensaje(exec.ConvertirABase64(path));



        }catch (Exception e){
            m.setMensaje(e.getMessage());
        }
        return m;
    }



    public void PlantillaPDF(String path, PDFDto dto)throws Exception{


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
        contentStream.drawString(PDFConstantes.titulo);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(230, 780);
        contentStream.drawString(PDFConstantes.titulo2);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(65, 765);
        contentStream.drawString(PDFConstantes.capitania );
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
        contentStream.drawString(PDFConstantes.uno);
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
        contentStream.drawString(PDFConstantes.dos);
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
        contentStream.drawString(PDFConstantes.tres);
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
        contentStream.drawString(PDFConstantes.cuatro);
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
        contentStream.drawString(PDFConstantes.cinco);
        contentStream.endText();

        //insertar tiempo de operacion
        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(200, 720);
        contentStream.drawString(dto.getTiempoOperacio());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(200, 719);
        contentStream.drawString("_____________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(50, 710);
        contentStream.drawString(PDFConstantes.seis);
        contentStream.endText();

        //insertar dia y hora estimado de arribo
        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(200, 710);
        contentStream.drawString(dto.getEstimadaArribo());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(200, 709);
        contentStream.drawString("_____________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(50, 700);
        contentStream.drawString(PDFConstantes.siete);
        contentStream.endText();

        //insertando representante acreditado
        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(200, 700);
        contentStream.drawString(dto.getRepresentante());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(200, 699);
        contentStream.drawString("____________________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(50, 690);
        contentStream.drawString(PDFConstantes.ocho);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(160, 690);
        contentStream.drawString(PDFConstantes.ochoA);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(60, 680);
        contentStream.drawString(PDFConstantes.ochoB);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(60, 670);
        contentStream.drawString(PDFConstantes.ochoC);
        contentStream.endText();


        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(50, 660);
        contentStream.drawString(PDFConstantes.nueve);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(50, 370);
        contentStream.drawString(PDFConstantes.diez);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(50, 228);
        contentStream.drawString(PDFConstantes.once);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(60, 216);
        contentStream.drawString(PDFConstantes.onceA);
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
        contentStream.drawString(PDFConstantes.onceB);
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
        contentStream.drawString(PDFConstantes.onceC);
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
        contentStream.drawString(PDFConstantes.doce);
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
        contentStream.drawString(PDFConstantes.trece);
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
        contentStream.drawString(PDFConstantes.catorce);
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
        contentStream.drawString(PDFConstantes.firma);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(175, 115);
        contentStream.drawString(PDFConstantes.firmaPatron);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.moveTextPositionByAmount(350, 125);
        contentStream.drawString(PDFConstantes.firma);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(360, 115);
        contentStream.drawString(PDFConstantes.capitaniaGuardacosta);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(50, 90);
        contentStream.drawString(PDFConstantes.nota);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(50, 80);
        contentStream.drawString(PDFConstantes.notaUno);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(50, 70);
        contentStream.drawString(PDFConstantes.notaDos);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(50, 60);
        contentStream.drawString(PDFConstantes.notaDos1);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(50, 50);
        contentStream.drawString(PDFConstantes.notaTres);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(50, 40);
        contentStream.drawString(PDFConstantes.notaTres1);
        contentStream.endText();



        //tablas

        String[]fields= new String[]{"Item", "Apellidos y Nombres", "Matricula", "Vigencia", "Cargo a bordo"};
        String[][] content = {fields,{"1","a","b", "c", "d"},
                {"c", "d", "2"},
                {"e", "f", "3"},
                {"g", "h", "4"},
                {"g", "h", "4"},
                {"g", "h", "4"},
                {"g", "h", "4"},
                {"g", "h", "4"},
                {"g", "h", "4"},
                {"g", "h", "4"},
                {"g", "h", "4"},
                {"g", "h", "4"},
                {"g", "h", "4"},
                {"g", "h", "4"},
                {"g", "h", "4"},
                {"g", "h", "4"},
                {"g", "h", "4"},
                {"g", "h", "4"},
                {"g", "h", "4"},
                {"g", "h", "4"}};
        drawTableRolTripulacion(page, contentStream, 655.0f, 60.0f, content);

        String[]field= new String[]{"Item", "", "Fecha de Vencimiento"};
        String[][] certificados = {field,{"1", "a", "b"},
                {"2", "a", "b"},
                {"3", "a", "b"},
                {"4", "a", "b"},
                {"5", "a", "b"},
                {"6", "a", "b"},
                {"7", "a", "b"}};
        drawTableCertificados(page, contentStream,365, 60, certificados);

        contentStream.close();
        document.save(path);
        document.close();

    }

    public  void drawTableRolTripulacion(PDPage page, PDPageContentStream contentStream,
                                 float y, float margin, String[][] content) throws IOException {
        final int rows = content.length;
        final int cols = content[0].length;
        final float rowHeight = 13.0f;
        final float tableWidth = page.getMediaBox().getWidth() - 2.0f * margin;
        final float tableHeight = rowHeight * (float) rows;
        //final float colWidth = tableWidth / (float) cols;
        final float colWidth = 85.33f;

        logger.error("page.getMediaBox().getWidth(): "+ page.getMediaBox().getWidth());
        logger.error("tableWidth: "+ tableWidth);
        logger.error("tableHeight: "+ tableHeight);
        logger.error("colWidth: "+ colWidth);
        logger.error("rows: "+ rows);
        logger.error("cols: "+ cols);



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
                nextx=margin+30;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
            }else if(i==2){
                nextx=margin+219f;
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


        //now add the text

        contentStream.setFont(PDType1Font.HELVETICA, 8);



        float texty=y-10;
        for(int i=0; i<content.length; i++) {

            String[]fields=content[i];
            float textx=margin+5;

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
                            textx=300;
                        }else {
                            textx = 290;
                        }
                        break;
                    case 3:
                        textx = 390;
                        break;
                    case 4:
                        textx = 465;
                        break;
                }

                contentStream.beginText();
                contentStream.newLineAtOffset(textx, texty);
                contentStream.showText(fields[j]);
                contentStream.endText();


            }
            texty-=13;
        }
        /*
        final float cellMargin = 2.0f;
        float textx = margin + cellMargin;
        float texty = y - 15.0f;
        for (final String[] aContent : content) {
            for (String text : aContent) {

                contentStream.beginText();
                contentStream.newLineAtOffset(textx, texty);
                contentStream.showText(text);
                contentStream.endText();
                textx += colWidth;
            }
            texty -= rowHeight;
            textx = margin + cellMargin;
        }*/

    }

    public  void drawTableCertificados(PDPage page, PDPageContentStream contentStream,
                                         float y, float margin, String[][] content) throws IOException {
        final int rows = content.length;
        final int cols = content[0].length;
        final float rowHeight = 13.0f;
        final float tableWidth = 400.5f;
        final float tableHeight = rowHeight * (float) rows;
        //final float colWidth = tableWidth / (float) cols;
        final float colWidth = 170f;

        logger.error("page.getMediaBox().getWidth(): "+ page.getMediaBox().getWidth());
        logger.error("tableWidth: "+ tableWidth);
        logger.error("tableHeight: "+ tableHeight);
        logger.error("colWidth: "+ colWidth);
        logger.error("rows: "+ rows);
        logger.error("cols: "+ cols);



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
                nextx=margin+30;
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


        //now add the text

        contentStream.setFont(PDType1Font.HELVETICA, 8);


        float texty=y-10;
        for(int i=0; i<content.length;i++) {

            String[]fields=content[i];
            float textx=margin+5;

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
                        textx = 330;
                        break;

                }

                contentStream.beginText();
                contentStream.newLineAtOffset(textx, texty);
                contentStream.showText(fields[j]);
                contentStream.endText();


            }
            texty-=13;
        }


    }
}
