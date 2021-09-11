package com.incloud.hcp.util.pdf;

import com.incloud.hcp.util.Constant;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Mensaje;
import com.incloud.hcp.util.Metodos;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PDFImpl implements PDFService {

    public Mensaje GenerarPDF()throws Exception{

        Mensaje m=new Mensaje();

        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
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

// Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"
            contentStream.drawImage(logoTasa, 50, 740);
            contentStream.drawImage(logoGuardiaCostera, 280, 740);


            contentStream.beginText();
            contentStream.setFont(bold, 11);
            contentStream.moveTextPositionByAmount(150, 720);
            contentStream.drawString(PDFConstantes.titulo);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 11);
            contentStream.moveTextPositionByAmount(210, 710);
            contentStream.drawString(PDFConstantes.titulo2);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 10);
            contentStream.moveTextPositionByAmount(60, 690);
            contentStream.drawString(PDFConstantes.capitania +"CHICAMA");
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(50, 680);
            contentStream.drawString(PDFConstantes.uno);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(50, 670);
            contentStream.drawString(PDFConstantes.dos);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(300, 670);
            contentStream.drawString(PDFConstantes.tres);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(50, 660);
            contentStream.drawString(PDFConstantes.cuatro);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(50, 650);
            contentStream.drawString(PDFConstantes.cinco);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(50, 640);
            contentStream.drawString(PDFConstantes.seis);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(50, 630);
            contentStream.drawString(PDFConstantes.siete);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(50, 620);
            contentStream.drawString(PDFConstantes.ocho);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(50, 610);
            contentStream.drawString(PDFConstantes.nueve);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(50, 270);
            contentStream.drawString(PDFConstantes.diez);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(50, 260);
            contentStream.drawString(PDFConstantes.once);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(60, 250);
            contentStream.drawString(PDFConstantes.onceA);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(60, 240);
            contentStream.drawString(PDFConstantes.onceB);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(60, 230);
            contentStream.drawString(PDFConstantes.onceC);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(50, 220);
            contentStream.drawString(PDFConstantes.doce);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(300, 220);
            contentStream.drawString(PDFConstantes.trece);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(50, 210);
            contentStream.drawString(PDFConstantes.catorce);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 10);
            contentStream.moveTextPositionByAmount(150, 170);
            contentStream.drawString(PDFConstantes.firma);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(170, 160);
            contentStream.drawString(PDFConstantes.firmaPatron);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 10);
            contentStream.moveTextPositionByAmount(350, 170);
            contentStream.drawString(PDFConstantes.firma);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(370, 160);
            contentStream.drawString(PDFConstantes.capitaniaGuardacosta);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(50, 140);
            contentStream.drawString(PDFConstantes.nota);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 8);
            contentStream.moveTextPositionByAmount(50, 130);
            contentStream.drawString(PDFConstantes.notaUno);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 8);
            contentStream.moveTextPositionByAmount(50, 120);
            contentStream.drawString(PDFConstantes.notaUno1);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 8);
            contentStream.moveTextPositionByAmount(50, 110);
            contentStream.drawString(PDFConstantes.notaDos);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 8);
            contentStream.moveTextPositionByAmount(50, 100);
            contentStream.drawString(PDFConstantes.notaDos1);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 8);
            contentStream.moveTextPositionByAmount(50, 90);
            contentStream.drawString(PDFConstantes.notaTres);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 8);
            contentStream.moveTextPositionByAmount(50, 80);
            contentStream.drawString(PDFConstantes.notaTres1);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 8);
            contentStream.moveTextPositionByAmount(50, 70);
            contentStream.drawString(PDFConstantes.notaTres2);
            contentStream.endText();

            //tablas

                String[]fields= new String[]{"item", "Apellidos y Nombres", "Matricula"};
                    String[][] content = {fields,{"a", "b", "1"},
                            {"c", "d", "2"},
                            {"e", "f", "3"},
                            {"g", "h", "4"},
                            {"i", "j", "5"}};
                    drawTable(page, contentStream, 600.0f, 100.0f, content);



// Make sure that the content stream is closed:
            contentStream.close();

// Save the results and ensure that the document is properly closed:
            String path = Constantes.RUTA_ARCHIVO_IMPORTAR + "Archivo.pdf";
            document.save(path);
            document.close();



            Metodos exec = new Metodos();
            m.setMensaje(exec.ConvertirABase64(path));
        }catch (Exception e){
            m.setMensaje(e.getMessage());
        }
        return m;
    }

    public static void drawTable(PDPage page, PDPageContentStream contentStream,
                                 float y, float margin, String[][] content) throws IOException {
        final int rows = content.length;
        final int cols = content[0].length;
        final float rowHeight = 20.0f;
        final float tableWidth = page.getMediaBox().getWidth() - 2.0f * margin;
        final float tableHeight = rowHeight * (float) rows;
        final float colWidth = tableWidth / (float) cols;

        //draw the rows
        float nexty = y ;
        for (int i = 0; i <= rows; i++) {
            contentStream.moveTo(margin, nexty);
            contentStream.lineTo(margin + tableWidth, nexty);
            contentStream.stroke();
            nexty-= rowHeight;
        }


        //draw the columns
        float nextx = margin;
        for (int i = 1; i <= cols; i++) {
            contentStream.moveTo(nextx, y);
            contentStream.lineTo(nextx, y - tableHeight);
            contentStream.stroke();
            nextx += colWidth;
        }

        //now add the text
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10.0f);

        final float cellMargin = 4.0f;
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
        }
    }
}
