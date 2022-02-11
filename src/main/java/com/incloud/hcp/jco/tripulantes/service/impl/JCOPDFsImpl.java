package com.incloud.hcp.jco.tripulantes.service.impl;

import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.jco.tripulantes.dto.*;
import com.incloud.hcp.jco.tripulantes.dto.PDFTrimestralConstantes;
import com.incloud.hcp.jco.tripulantes.dto.PDFValeViveresConstantes;
import com.incloud.hcp.util.*;
import com.incloud.hcp.jco.tripulantes.service.JCOPDFsService;
import com.sap.conn.jco.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Service
public class JCOPDFsImpl implements JCOPDFsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JCOImpresFormatosProduceImpl jcoImpresFormatosProduce;
    @Autowired
    private JCOTrabajoFueraFaenaImpl jcoTrabajoFueraFaena;

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

            Metodos me=new Metodos();


            for(int i=0; i<T_ZATRP.getNumRows(); i++){
                T_ZATRP.setRow(i);


                String fechaArribo=ConvertirFecha(T_ZATRP, PDFZarpeConstantes.FEARR);
                String fecha=ConvertirFecha(T_ZATRP, PDFZarpeConstantes.FEZAT);
                String hora=ConvertirHora(T_ZATRP, PDFZarpeConstantes.HRARR);

                String codigoZarpe=T_ZATRP.getString(PDFZarpeConstantes.CDZAT);
                int codigoZ=Integer.parseInt(codigoZarpe);
                String codigo =String.valueOf(codigoZ);
                dto.setCodigoZarpe(codigo);
                dto.setCapitania(T_ZATRP.getString(PDFZarpeConstantes.DSWKP));
                dto.setNombreNave(T_ZATRP.getString(PDFZarpeConstantes.DSWKS));
                dto.setMatricula(T_ZATRP.getString(PDFZarpeConstantes.MREMB));
                dto.setArqueoBruto(T_ZATRP.getString(PDFZarpeConstantes.AQBRT));
                dto.setZonaPesca(T_ZATRP.getString(PDFZarpeConstantes.DSWKP));
                dto.setTiempoOperacio(T_ZATRP.getString(PDFZarpeConstantes.TOPER));
                dto.setEstimadaArribo(fechaArribo+"   "+ hora);
                dto.setRepresentante(T_ZATRP.getString(PDFZarpeConstantes.RACRE ));
                dto.setEmergenciaNombre(T_ZATRP.getString(PDFZarpeConstantes.DSEMP));
                dto.setEmergenciaDireccion(T_ZATRP.getString(PDFZarpeConstantes.DFEMP));
                dto.setEmergenciaTelefono(T_ZATRP.getString(PDFZarpeConstantes.TFEMP));
                dto.setFecha(fecha);

                logger.error("Fecha y hora"+ dto.getEstimadaArribo());


            }
            logger.error("RolTripulacion");
            String[] CamposRolTripulacion= new String[]{PDFZarpeConstantes.NOMBR,
                                                        PDFZarpeConstantes.NRLIB,
                                                        PDFZarpeConstantes.FEFVG,
                                                        PDFZarpeConstantes.STELL};


            List<HashMap<String, Object>> t_daztr=me.ListarObjetos(T_DZATR);
            List<HashMap<String, Object>> t_vgcer=me.ListarObjetos(T_VGCER);

            String[][] RolTripulacion=new String[t_daztr.size()+1][CamposRolTripulacion.length];
            RolTripulacion[0]= PDFZarpeConstantes.fieldRolTripulacion;

            String dni="";
            int indice=1;
            for(int i=0; i<t_daztr.size();i++){

                String[]reg=new String[5];

                for(Map.Entry<String, Object> entry:t_daztr.get(i).entrySet()) {

                    String key = entry.getKey();
                    String valor = entry.getValue().toString();

                    reg[0]=String.valueOf(indice);
                    if(key.equals("NOMBR")){
                        reg[1]=valor;
                    }
                    if(key.equals("NRLIB")){
                            reg[2]=valor;
                    }
                    if(key.equals("FEFVG")){

                        try {
                            reg[3] = valor;
                        }catch (Exception e){
                            reg[3] =valor;
                        }

                    }
                    if(key.equals("DESC_STELL")){
                            reg[4] = valor;
                    }
                    if(key.equals("NRDNI")){
                        dni=valor;
                    }

                }

                if(reg[4].equals("PATRON EP")){
                    dto.setNombrePatron(reg[1]);
                    dto.setDni(dni);
                }
                RolTripulacion[indice]=reg;
                indice++;
            }



            if(dto.getNombrePatron()==null){
                dto.setNombrePatron("");
            }
            if(dto.getDni()==null){
                dto.setDni("");
            }
            logger.error("Certificados");



            List<CertificadoDto> certificadosList=new ArrayList<>();
            for(int i=0; i<t_vgcer.size();i++){
                T_VGCER.setRow(i);

                CertificadoDto c=new CertificadoDto();
                c.setCDCER(T_VGCER.getString("CDCER"));
                c.setNRCER(T_VGCER.getString("NRCER"));
                c.setDSCER(T_VGCER.getString("DSCER"));

                String fecha = T_VGCER.getString("FECCF");

                SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                Date date = parseador.parse(fecha);


                fecha = formateador.format(date);

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date d = new Date();
                String fechaF = dateFormat.format(d);
                Date fActual = parseador.parse(fechaF);

                if (date.after(fActual)) {
                    c.setFECCF(fecha);

                } else {
                    c.setFECCF("");
                }


                certificadosList.add(c);

            }

            String[][] cert=OrdenarCertificados(certificadosList,"");

            for(int i=0; i<cert.length;i++){

                for(int j=0;j<cert[i].length;j++){

                    logger.error("cert["+i+"]["+j+"] : "+cert[i][j]);
                }
            }


            //ordenando los certificados





           PlantillaPDFZarpe(path, dto, RolTripulacion, cert);

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


        document.addPage(page);

        PDFont bold = PDType1Font.HELVETICA_BOLD;
        PDFont font = PDType1Font.HELVETICA;

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.beginText();
        contentStream.setFont(bold, 100);
        contentStream.setNonStrokingColor(Color.lightGray);
        contentStream.moveTextPositionByAmount(395, 198);
        contentStream.showText("_");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.setNonStrokingColor(Color.black);
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
        contentStream.drawString(dto.getArqueoBruto());
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

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(50, 30);
        contentStream.drawString("#"+dto.getCodigoZarpe());
        contentStream.endText();

        logger.error("PlantillaPDF_1");
        drawTableRolTripulacion(page, contentStream, 655.0f, 50.0f, rolTripulacion);
        logger.error("PlantillaPDF_2");
        drawTableCertificados(contentStream,355, 50, certificados);

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
        logger.error("drawTableRolTripulacion_2");


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
                nextx += colWidth+30;
            }else {
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
                nextx += colWidth;
            }

        }


        //now add the text

        logger.error("drawTableRolTripulacion_3");

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
                            textx = 380;
                        }else{
                            textx = 375;
                        }
                        break;
                    case 4:
                        if(i==0){
                            textx = 475;
                        }else {
                            textx = 460;
                        }
                        break;
                }
                logger.error("drawTableRolTripulacion_4");
                logger.error(fields[j]);
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
        //String tasa= Constantes.RUTA_ARCHIVO_IMPORTAR+"logo.png";
        //String guardiaCostera= Constantes.RUTA_ARCHIVO_IMPORTAR+"logocapitania.png";
        //PDImageXObject logoTasa = PDImageXObject.createFromFile(tasa,document);
        //PDImageXObject logoGuardiaCostera = PDImageXObject.createFromFile(guardiaCostera,document);

        document.addPage(page);

// Create a new font object selecting one of the PDF base fonts
        PDFont bold = PDType1Font.HELVETICA_BOLD;
        PDFont font = PDType1Font.HELVETICA;

// Start a new content stream which will "hold" the to be created content
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        //logos superiores
       //contentStream.drawImage(logoTasa, 50, 750);
       // contentStream.drawImage(logoGuardiaCostera, 280, 750);

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
        PDFProtestosDto dto = new PDFProtestosDto();
        Metodos metodo=new Metodos();
        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_REGPRT_ADM_REGPRT);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("IP_TOPE", imports.getIp_tope());
            importx.setValue("IP_CDPRT", imports.getIp_cdprt());
            importx.setValue("IP_CANTI", imports.getIp_canti());
            importx.setValue("IP_PERNR", imports.getIp_pernr());

            List<MaestroOptions> options = imports.getT_opcion();
            List<HashMap<String, Object>> listOptions = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < options.size(); i++) {
                MaestroOptions o = options.get(i);
                HashMap<String, Object> record = new HashMap<String, Object>();

                record.put("DATA", o.getWa());
                listOptions.add(record);
            }

            Metodos me=new Metodos();
            List<HashMap<String, Object>> tmpOptions =me.ValidarOptions(imports.getT_opcion(),imports.getOpcionkeys(),"DATA");

            JCoParameterList tables = stfcConnection.getTableParameterList();

            EjecutarRFC exec= new EjecutarRFC();
            exec.setTable(tables, Tablas.T_OPCION,tmpOptions);

            stfcConnection.execute(destination);

            JCoTable T_BAPRT = tables.getTable(Tablas.T_BAPRT);
            T_BAPRT.setRow(0);

            JCoField fieldF = T_BAPRT.getField(PDFProtestosConstantes.FECRE);
            Date date=fieldF.getDate();
            SimpleDateFormat dia = new SimpleDateFormat("d 'de' MMMM 'de' yyyy", new Locale("es","ES"));
            String fecha = dia.format(date);

            dto.setCodigoProtesto(T_BAPRT.getString(PDFProtestosConstantes.CDPRT));
            dto.setTrato(T_BAPRT.getString(PDFProtestosConstantes.TRATO));
            dto.setGradoSupervisor(T_BAPRT.getString(PDFProtestosConstantes.GRADO));
            dto.setNombreSupervisor(T_BAPRT.getString(PDFProtestosConstantes.NAPSU));
            dto.setDomicilioLegal(T_BAPRT.getString(PDFProtestosConstantes.DRPTA));
            dto.setCargoSupervisor(T_BAPRT.getString(PDFProtestosConstantes.CARSU));
            dto.setNombreBahia(T_BAPRT.getString(PDFProtestosConstantes.NAPBA));
           // dto.setDni(T_BAPRT.getString(PDFProtestosConstantes.NRDNI));
            dto.setPuerto(T_BAPRT.getString(PDFProtestosConstantes.DSWKP));
            dto.setFecha(fecha);
            dto.setNombreEmbarcacion(T_BAPRT.getString(PDFProtestosConstantes.DSWKS));
            dto.setMatricula(T_BAPRT.getString(PDFProtestosConstantes.MREMB));
            dto.setNumeroProtesto(T_BAPRT.getString(PDFProtestosConstantes.CDPRT));
            dto.setCargoBahia(T_BAPRT.getString(PDFProtestosConstantes.CARBA));
            dto.setCarnetProcurador(T_BAPRT.getString(PDFProtestosConstantes.NRCPP));

            JCoTable T_TEXTOS = tables.getTable(Tablas.T_TEXTOS);

            String tdline="";
            for(int i=0; i<T_TEXTOS.getNumRows(); i++){
                T_TEXTOS.setRow(i);
                tdline+= T_TEXTOS.getString(PDFProtestosConstantes.TDLINE);
            }

            dto.setSegundoParrafo(tdline);


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

        document.addPage(page);

        PDFont bold = PDType1Font.HELVETICA_BOLD;
        PDFont font = PDType1Font.HELVETICA;

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.beginText();
        contentStream.setFont(bold, 14);
        contentStream.moveTextPositionByAmount(190, 750);
        contentStream.drawString(PDFProtestosConstantes.titulo);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(365, 700);
        contentStream.drawString(dto.getPuerto()+", "+dto.getFecha());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(40, 670);
        contentStream.drawString(dto.getTrato());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(40, 657);
        contentStream.drawString(dto.getGradoSupervisor());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(40, 644);
        contentStream.drawString(dto.getNombreSupervisor());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(40, 631);
        contentStream.drawString(dto.getCargoSupervisor());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(40, 618);
        contentStream.drawString(PDFProtestosConstantes.capitaniaGuardacostas);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(40, 605);
        contentStream.drawString(dto.getPuerto());
        contentStream.endText();

        String parrafoUno=
                PDFProtestosConstantes.primerParrafo1+dto.getDomicilioLegal()+PDFProtestosConstantes.primerParrafo2+dto.getCargoBahia()+", "+dto.getNombreBahia()+","
                +PDFProtestosConstantes.primerParrafo3+dto.getCarnetProcurador()+","+PDFProtestosConstantes.primerParrafo4+dto.getNombreEmbarcacion()
                +PDFProtestosConstantes.primerParrafo5+dto.getMatricula()+PDFProtestosConstantes.primerParrafo6;

        int finPU= justificarParrafoUno(contentStream,font,12f,  parrafoUno, 570);
        int finUno= justificarParrafo(contentStream,font,12f,  dto.getSegundoParrafo(), finPU-20);
        int finDos= justificarParrafo(contentStream,font,12f,  PDFProtestosConstantes.textoFinal1, finUno-20);


        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(40, finDos-20);
        contentStream.drawString(PDFProtestosConstantes.textoFinal2);
        contentStream.endText();



        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(40, finDos-80);
        contentStream.drawString(PDFProtestosConstantes.atentamente);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 11);
        contentStream.moveTextPositionByAmount(40, 110);
        contentStream.drawString(dto.getNombreBahia());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 9);
        contentStream.moveTextPositionByAmount(40, 97);
        contentStream.drawString("CARNET DE "+dto.getCargoBahia());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 9);
        contentStream.moveTextPositionByAmount(40, 84);
        contentStream.drawString("N° "+dto.getCarnetProcurador());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 9);
        contentStream.moveTextPositionByAmount(40, 64);
        contentStream.drawString(dto.getCodigoProtesto());
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


        List<String> lines = new ArrayList<String>();

        int lastSpace = -1;
        int con=0;
        int c=0;
        while (text.length()> 0)
        {

            int spaceIndex = text.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0)
                spaceIndex = text.length();
            String subString = text.substring(0, spaceIndex);

            float size = fontSize * pdfFont.getStringWidth(subString) / 1000;

            if (size > width)
            {
                if (lastSpace < 0)
                    lastSpace = spaceIndex;
                subString = text.substring(0, lastSpace);
                lines.add(subString);


                text = text.substring(lastSpace).trim();

                lastSpace = -1;
                logger.error("width antes: "+width);
                if(!lines.isEmpty()){

                    width = 510;
                    logger.error("width dsps: "+width);
                }
                //logger.error("contador: "+c);
                //logger.error("lines.lenght: "+lines.size());

                //logger.error("primer if("+con+"): "+lines.get(con));
                con++;
            }
            else if (spaceIndex == text.length())
            {

                lines.add(text);

                text = "";

            }
            else
            {
                lastSpace = spaceIndex;
            }

            c++;

        }


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
                if(i==0){
                    width=477;
                }else{
                    width=510;
                }
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

            contentStream.setCharacterSpacing(charSpacing);
            contentStream.showText(lines.get(i));
            contentStream.endText();
            startY-=fontSize;

        }
        return startY;

    }

    public int justificarParrafo(PDPageContentStream contentStream, PDFont pdfFont, float fontSize,  String text, int startY)
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
            startY-=fontSize+2;

        }
        return startY;
    }

    public PDFExports GenerarPDFZarpeTravesia(PDFZarpeImports imports)throws Exception{
        PDFExports pdf = new PDFExports();
        String path = Constantes.RUTA_ARCHIVO_IMPORTAR + "Archivo.pdf";
        PDFZarpeTravesiaDto dto= new PDFZarpeTravesiaDto();

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


                String fechaArribo=ConvertirFecha(T_ZATRP, PDFZarpeConstantes.FEARR);
                String fechaZarpe=ConvertirFecha(T_ZATRP, PDFZarpeConstantes.FEZAT);
                String horaArribo=ConvertirHora(T_ZATRP, PDFZarpeConstantes.HRARR);
                String horaZarpe=ConvertirHora(T_ZATRP, PDFZarpeConstantes.HRZAR);

                String codigoZarpe=T_ZATRP.getString(PDFZarpeConstantes.CDZAT);
                int codigoZ=Integer.parseInt(codigoZarpe);
                String codigo =String.valueOf(codigoZ);
                dto.setCodigoZarpe(codigo);

                dto.setCapitaniaGuardacostas(T_ZATRP.getString(PDFZarpeConstantes.DSWKP));
                dto.setNombreNave(T_ZATRP.getString(PDFZarpeConstantes.DSWKS));
                dto.setMatricula(T_ZATRP.getString(PDFZarpeConstantes.MREMB));
                dto.setArqueoBruto(T_ZATRP.getString(PDFZarpeConstantes.AQBRT));
                dto.setColorCasco(T_ZATRP.getString(PDFZarpeConstantes.COCAS));
                dto.setSuperEstructura(T_ZATRP.getString(PDFZarpeConstantes.COSUP));
                dto.setPropietario(T_ZATRP.getString(PDFZarpeConstantes.DSEMP));
                dto.setDomicilioFiscal(T_ZATRP.getString(PDFZarpeConstantes.DFEMP));
                dto.setRepresentanteAcreditado(T_ZATRP.getString(PDFZarpeConstantes.RACRE ));
                dto.setTelefono(T_ZATRP.getString(PDFZarpeConstantes.TFEMP));

                if(fechaArribo.equals(null) || fechaArribo==""){
                    dto.setDiaHoraArriboPuerto("");
                }else{
                    dto.setDiaHoraArriboPuerto(fechaArribo+"   "+ horaArribo);
                }

                if(fechaZarpe.equals(null) || fechaZarpe==""){
                    dto.setDiaHoraZarpe("");
                }else {
                    dto.setDiaHoraZarpe(fechaZarpe + "   " + horaZarpe);
                }

            }
            logger.error("RolTripulacion");
            String[] CamposRolTripulacion= new String[]{PDFZarpeConstantes.NOMBR,
                    PDFZarpeConstantes.NRLIB,
                    PDFZarpeConstantes.FEFVG,
                    PDFZarpeConstantes.STELL};

            Metodos me = new Metodos();
            List<HashMap<String, Object>> t_daztr=me.ListarObjetos(T_DZATR);
            List<HashMap<String, Object>> t_vgcer=me.ListarObjetos(T_VGCER);


            String[][] RolTripulacion=new String[t_daztr.size()+1][CamposRolTripulacion.length];

            RolTripulacion[0]= PDFZarpeConstantes.fieldRolTripulacion;

            String dni="";
            int indice=1;
            for(int i=0; i<t_daztr.size();i++){

                String[]reg=new String[5];

                for(Map.Entry<String, Object> entry:t_daztr.get(i).entrySet()) {

                    String key = entry.getKey();
                    String valor = entry.getValue().toString();

                    reg[0]=String.valueOf(indice);
                    if(key.equals("NOMBR")){
                        reg[1]=valor;
                    }
                    if(key.equals("NRLIB")){
                        reg[2]=valor;
                    }
                    if(key.equals("FEFVG")){

                        try {
                            reg[3] = valor;
                        }catch (Exception e){
                            reg[3] =valor;
                        }

                    }
                    if(key.equals("DESC_STELL")){
                        reg[4] = valor;
                    }
                    if(key.equals("NRDNI")){
                        dni=valor;
                    }

                }

                if(reg[4].equals("PATRON EP")){
                    dto.setNombreCapitanPatron(reg[1]);
                    dto.setDni(dni);
                }
                RolTripulacion[indice]=reg;
                indice++;
            }

            if(dto.getNombreCapitanPatron()==null){
                dto.setNombreCapitanPatron("");
            }
            if(dto.getDni()==null){
                dto.setDni("");
            }
            logger.error("Certificados");


            List<CertificadoDto> certificadosList=new ArrayList<>();
            for(int i=0; i<t_vgcer.size();i++){
                T_VGCER.setRow(i);

                CertificadoDto c=new CertificadoDto();
                c.setCDCER(T_VGCER.getString("CDCER"));
                c.setNRCER(T_VGCER.getString("NRCER"));
                c.setDSCER(T_VGCER.getString("DSCER"));

                String fecha = T_VGCER.getString("FECCF");
                logger.error("Certificados fecha: "+fecha);


                try {

                    SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = parseador.parse(fecha);
                    fecha = formateador.format(date);

                    logger.error("Certificados_1");
                    logger.error("fecha :"+fecha);

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date d = new Date();
                    String fechaF = dateFormat.format(d);
                    Date fActual = parseador.parse(fechaF);

                    logger.error("Certificados_2");
                    logger.error("fActual :"+fActual);

                    if (date.after(fActual)) {
                        c.setFECCF(fecha);

                    } else {
                        c.setFECCF("");
                    }
                }catch (Exception e){
                    c.setFECCF("");
                }

                certificadosList.add(c);

            }
            logger.error("PDF Zarpe Travesia_2");
            String[][] cert=OrdenarCertificados(certificadosList,"");
            logger.error("PDF Zarpe Travesia_3");
            PlantillaPDFZarpeTravesia(path, dto, RolTripulacion, cert);
            logger.error("PDF Zarpe Travesia_4");
            Metodos exec = new Metodos();
            pdf.setBase64(exec.ConvertirABase64(path));
            pdf.setMensaje("Ok");


        }catch (Exception e){
            pdf.setMensaje(e.getMessage());

            logger.error("causa"+e.getCause());
            logger.error("error"+e.getMessage());
        }
        return pdf;
    }
    public void PlantillaPDFZarpeTravesia(String path, PDFZarpeTravesiaDto dto, String[][] rolTripulacion, String[][] certificados)throws Exception{

        logger.error("PlantillaPDF");

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);

        document.addPage(page);

        PDFont bold = PDType1Font.HELVETICA_BOLD;
        PDFont font = PDType1Font.HELVETICA;

        PDPageContentStream contentStream = new PDPageContentStream(document, page);



        contentStream.beginText();
        contentStream.setFont(bold, 12);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.moveTextPositionByAmount(180, 770);
        contentStream.drawString(PDFZarpeTravesiaConstantes.titulo);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(60, 755);
        contentStream.drawString(PDFZarpeTravesiaConstantes.capitania );
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(250, 755);
        contentStream.drawString(dto.getCapitaniaGuardacostas());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(250, 754);
        contentStream.drawString("___________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 743);
        contentStream.drawString(PDFZarpeTravesiaConstantes.uno);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(165, 743);
        contentStream.drawString(dto.getNombreNave());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(165, 742);
        contentStream.drawString("_______________________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 731);
        contentStream.drawString(PDFZarpeTravesiaConstantes.dos);
        contentStream.endText();

        //insertando numero de matrícula
        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(155, 731);
        contentStream.drawString(dto.getMatricula());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(155, 730);
        contentStream.drawString("_______________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(300, 731);
        contentStream.drawString(PDFZarpeTravesiaConstantes.tres);
        contentStream.endText();

        //insertando A.B.
        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(334, 731);
        contentStream.drawString(dto.getArqueoBruto());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(334, 730);
        contentStream.drawString("_________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 719);
        contentStream.drawString(PDFZarpeTravesiaConstantes.cuatro);
        contentStream.endText();

        //insertando zona de pesca
        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(155, 719);
        contentStream.drawString(dto.getColorCasco());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(155, 718);
        contentStream.drawString("_______________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(300, 719);
        contentStream.drawString(PDFZarpeTravesiaConstantes.cuatroB);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(400, 719);
        contentStream.drawString(dto.getSuperEstructura());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(400, 718);
        contentStream.drawString("_________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 707);
        contentStream.drawString(PDFZarpeTravesiaConstantes.cinco);
        contentStream.endText();

        //insertar tiempo de operacion
        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(155, 707);
        contentStream.drawString(dto.getPropietario());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(155, 706);
        contentStream.drawString("_________________________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 695);
        contentStream.drawString(PDFZarpeTravesiaConstantes.seis);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6.5f);
        contentStream.moveTextPositionByAmount(155, 695);
        contentStream.drawString(dto.getDomicilioFiscal());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(155, 694);
        contentStream.drawString("_________________________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 683);
        contentStream.drawString(PDFZarpeTravesiaConstantes.siete);
        contentStream.endText();

        //insertando representante acreditado
        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(200, 683);
        contentStream.drawString(dto.getRepresentanteAcreditado());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(200, 682);
        contentStream.drawString("_______________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 671);
        contentStream.drawString(PDFZarpeTravesiaConstantes.ocho);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6.5f);
        contentStream.moveTextPositionByAmount(155, 671);
        contentStream.drawString(dto.getDomicilioFiscal());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(155, 670);
        contentStream.drawString("_________________________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 659);
        contentStream.drawString(PDFZarpeTravesiaConstantes.nueve);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(120, 659);
        contentStream.drawString(dto.getTelefono());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(120, 658);
        contentStream.drawString("__________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(220, 659);
        contentStream.drawString(PDFZarpeTravesiaConstantes.diez);
        contentStream.endText();

        /*
        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(270, 659);
        contentStream.drawString(dto.getTelex());
        contentStream.endText();*/

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(270, 658);
        contentStream.drawString("________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(390, 659);
        contentStream.drawString(PDFZarpeTravesiaConstantes.once);
        contentStream.endText();


        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(430, 658);
        contentStream.drawString("___________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 647);
        contentStream.drawString(PDFZarpeTravesiaConstantes.doce);
        contentStream.endText();


        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 350);
        contentStream.drawString(PDFZarpeTravesiaConstantes.trece);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 12);
        contentStream.moveTextPositionByAmount(220, 200);
        contentStream.drawString(PDFZarpeTravesiaConstantes.planNavegacion);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 180);
        contentStream.drawString(PDFZarpeTravesiaConstantes.catorce);
        contentStream.endText();

        //insertando dni
        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(160, 180);
        contentStream.drawString(dto.getDiaHoraZarpe());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(160, 179);
        contentStream.drawString("____________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(340, 180);
        contentStream.drawString(PDFZarpeTravesiaConstantes.quince);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(440, 179);
        contentStream.drawString("________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 165);
        contentStream.drawString(PDFZarpeTravesiaConstantes.dieciseis);
        contentStream.endText();

        /*
        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(160, 165);
        contentStream.drawString(dto.getRumboInicial());
        contentStream.endText();*/

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(160, 164);
        contentStream.drawString("____________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 150);
        contentStream.drawString(PDFZarpeTravesiaConstantes.diecisiete);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(210, 150);
        contentStream.drawString(dto.getDiaHoraArriboPuerto());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(210, 149);
        contentStream.drawString("_________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 135);
        contentStream.drawString(PDFZarpeTravesiaConstantes.dieciocho);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(210, 134);
        contentStream.drawString("_________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(60, 120);
        contentStream.drawString(dto.getNombreCapitanPatron());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(60, 120);
        contentStream.drawString("_______________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(340, 135);
        contentStream.drawString(PDFZarpeTravesiaConstantes.diecinueve);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(440, 134);
        contentStream.drawString("________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(340, 120);
        contentStream.drawString("______________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 110);
        contentStream.drawString(PDFZarpeTravesiaConstantes.veinte);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(50, 80);
        contentStream.drawString("____________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(340, 110);
        contentStream.drawString(PDFZarpeTravesiaConstantes.veintiuno);
        contentStream.endText();


        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(340, 80);
        contentStream.drawString("______________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 70);
        contentStream.drawString(PDFZarpeTravesiaConstantes.veintidos);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(100, 69);
        contentStream.drawString("_________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(340, 70);
        contentStream.drawString(PDFZarpeTravesiaConstantes.veintitres);
        contentStream.endText();


        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(420, 69);
        contentStream.drawString("____________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(45, 60);
        contentStream.drawString(PDFZarpeTravesiaConstantes.nota);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(45, 50);
        contentStream.drawString(PDFZarpeTravesiaConstantes.notaUno);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(45, 45);
        contentStream.drawString(PDFZarpeTravesiaConstantes.notaDos);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(45, 40);
        contentStream.drawString(PDFZarpeTravesiaConstantes.notaDos1);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(45, 35);
        contentStream.drawString(PDFZarpeTravesiaConstantes.notaTres);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(45, 30);
        contentStream.drawString(PDFZarpeTravesiaConstantes.notaTres1);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(50, 20);
        contentStream.drawString("#"+dto.getCodigoZarpe());
        contentStream.endText();

        logger.error("PlantillaPDF_1");
        drawTableRolTripulacion(page, contentStream, 642.0f, 50.0f, rolTripulacion);
        logger.error("PlantillaPDF_2");
        drawTableCertificados(contentStream,340, 50, certificados);
        logger.error("PlantillaPDF_3");

        contentStream.close();
        document.save(path);
        document.close();



    }


    public PDFExports GenerarPDFRolTripulacion(RolTripulacionImports imports)throws Exception{

        PDFExports pdf = new PDFExports();
        String path = Constantes.RUTA_ARCHIVO_IMPORTAR + "Archivo.pdf";
        PDFRolTripulacionDto dto= new PDFRolTripulacionDto();

         try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_REGROL_ADM_REGROL);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("P_TOPE", imports.getP_tope());
            importx.setValue("P_CDRTR", imports.getP_cdrtr());
            importx.setValue("P_CANTI", imports.getP_canti());

            JCoParameterList tables = stfcConnection.getTableParameterList();
            stfcConnection.execute(destination);

            JCoTable T_ZARTR = tables.getTable(Tablas.T_ZARTR);
            JCoTable T_DZART = tables.getTable(Tablas.T_DZART);


            for(int i=0; i<T_ZARTR.getNumRows(); i++){
                T_ZARTR.setRow(i);

                String fecha=ConvertirFecha(T_ZARTR, PDFRolTripulacionConstantes.FERTR);

                dto.setNombreNave(T_ZARTR.getString(PDFRolTripulacionConstantes.DSWKS));
                dto.setMatricula(T_ZARTR.getString(PDFRolTripulacionConstantes.MREMB));
                dto.setArqueoBruto(T_ZARTR.getString(PDFRolTripulacionConstantes.AQBRT));
                dto.setArmador(T_ZARTR.getString(PDFRolTripulacionConstantes.DSEMP));
                dto.setArqueoNeto(T_ZARTR.getString(PDFRolTripulacionConstantes.AQNET));
                dto.setNumTripulantes(T_ZARTR.getString(PDFRolTripulacionConstantes.NRTRP));
                dto.setFecha(fecha);



            }
             Metodos me= new Metodos();

             logger.error("RolTripulacion");
            String[] CamposRolTripulacion= new String[]{PDFRolTripulacionConstantes.NOMBR,
                    PDFRolTripulacionConstantes.TITRP,
                    PDFRolTripulacionConstantes.STELL,
                    PDFRolTripulacionConstantes.NRLIB,
                    PDFRolTripulacionConstantes.FEFVG};
            String[][] RolTripulacion=new String[T_DZART.getNumRows()+1][CamposRolTripulacion.length];

            RolTripulacion[0]= PDFRolTripulacionConstantes.fieldRolTripulacion;
            int con=1;
            for(int i=0; i<T_DZART.getNumRows(); i++){
                T_DZART.setRow(i);

                String[] registros=new String[CamposRolTripulacion.length+1];
                int campos=0;
                for(int j=0; j<registros.length; j++){
                    if(j==0){
                        registros[j]=String.valueOf(con);

                    }else {

                        if(campos==4){

                            try {
                                String fecha = ConvertirFecha(T_DZART, PDFRolTripulacionConstantes.FEFVG);
                                registros[j] = fecha;
                            }catch (Exception e){
                                registros[j] = T_DZART.getString(CamposRolTripulacion[campos]);
                            }
                        }else if(campos==2){
                            String rol=me.ObtenerDominio("CARGOTRIPU",T_DZART.getString(CamposRolTripulacion[campos]));
                            registros[j] = rol;
                        }else {
                            registros[j] = T_DZART.getString(CamposRolTripulacion[campos]);
                        }

                        if (registros[j].trim().compareTo("PATRON EP") == 0|| registros[j].trim().compareTo("CAPITAN DE NAVEGACION")==0) {
                            dto.setNombrePatron(registros[1]);

                        }

                        campos++;
                    }
                }

                RolTripulacion[con]=registros;
                con++;
            }



             PlantillaPDFRolTripulacion(path, dto, RolTripulacion);

            Metodos exec = new Metodos();
            pdf.setBase64(exec.ConvertirABase64(path));
            pdf.setMensaje("Ok");


        }catch (Exception e){
            pdf.setMensaje(e.getMessage());
        }



        return pdf;
    }

    public  void drawTableRolTripu(PDPage page, PDPageContentStream contentStream,
                                         float y, float margin, String[][] content) throws IOException {

        logger.error("drawTableRolTripulacion");
        final int rows = content.length;
        final int cols = content[0].length;
        final float rowHeight = 15.0f;
        final float tableWidth = page.getMediaBox().getWidth() - 2.0f * margin;
        final float tableHeight = rowHeight * (float) rows;
        final float colWidth = 94.33f;

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
        for (int i = 0; i <= cols+2; i++) {

            if(i==1){
                nextx=margin+20;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
            }else if(i==2){
                nextx=margin+200f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
            }
            else if(i==5 || i==6){
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
                nextx += colWidth-21;
            }else {
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
                nextx += colWidth;
            }

        }


        //now add the text



        float texty=y-12;
        for(int i=0; i<content.length; i++) {

            String[]fields=content[i];
            float textx=margin+5;

            for (int j = 0; j < fields.length; j++) {

                switch (j) {
                    case 1:
                        if(i==0){
                            textx = 100;
                        }else {
                            textx = 58;
                        }
                        break;
                    case 2:
                        if(i==0){
                            textx=265;
                        }else {
                            textx = 250;
                        }
                        break;
                    case 3:
                        if(i==0){
                            textx = 340;
                        }else{
                            textx = 330;
                        }
                        break;
                    case 4:
                        if(i==0){
                            textx = 440;
                        }else {
                            textx = 425;
                        }
                        break;
                    case 5:
                        textx = 510;
                        break;
                }

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 6.5f);
                contentStream.newLineAtOffset(textx, texty);
                contentStream.showText(fields[j]);
                contentStream.endText();


            }
            texty-=15;
        }

    }
    public void PlantillaPDFRolTripulacion(String path, PDFRolTripulacionDto dto, String[][] rolTripulacion)throws Exception{

        logger.error("PlantillaPDF");

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);


        document.addPage(page);

        PDFont bold = PDType1Font.HELVETICA_BOLD;
        PDFont font = PDType1Font.HELVETICA;

        PDPageContentStream contentStream = new PDPageContentStream(document, page);


        contentStream.beginText();
        contentStream.setFont(bold, 155);
        contentStream.setNonStrokingColor(Color.gray);
        contentStream.moveTextPositionByAmount(40, 790);
        contentStream.showText("______");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 12);
        contentStream.setNonStrokingColor(Color.white);
        contentStream.moveTextPositionByAmount(40, 760);
        contentStream.drawString(PDFRolTripulacionConstantes.titulo);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.setNonStrokingColor(Color.black);
        contentStream.moveTextPositionByAmount(35, 740);
        contentStream.drawString(PDFRolTripulacionConstantes.uno);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(150, 740);
        contentStream.drawString(dto.getNombreNave());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(150, 739);
        contentStream.drawString("___________________________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(35, 725);
        contentStream.drawString(PDFRolTripulacionConstantes.dos);
        contentStream.endText();

        //insertando numero de matrícula
        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(150, 725);
        contentStream.drawString(dto.getMatricula());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(150, 724);
        contentStream.drawString("_____________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(310, 725);
        contentStream.drawString(PDFRolTripulacionConstantes.tres);
        contentStream.endText();

        //insertando A.B.
        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(400, 725);
        contentStream.drawString(dto.getArmador());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(400, 724);
        contentStream.drawString("___________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(35, 710);
        contentStream.drawString(PDFRolTripulacionConstantes.cuatro);
        contentStream.endText();

        //insertando zona de pesca
        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(150, 710);
        contentStream.drawString(dto.getArqueoBruto());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(150, 709);
        contentStream.drawString("_____________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(310, 710);
        contentStream.drawString(PDFRolTripulacionConstantes.cinco);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(400, 710);
        contentStream.drawString(dto.getArqueoNeto());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(400, 709);
        contentStream.drawString("___________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(35, 695);
        contentStream.drawString(PDFRolTripulacionConstantes.seis);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(340, 695);
        contentStream.drawString(dto.getNumTripulantes());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(340, 694);
        contentStream.drawString("________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(35, 250);
        contentStream.drawString(PDFRolTripulacionConstantes.siete);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(90, 250);
        contentStream.drawString(dto.getFecha());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(90, 249);
        contentStream.drawString("________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(310, 250);
        contentStream.drawString(PDFRolTripulacionConstantes.ocho);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(370, 249);
        contentStream.drawString("__________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(70, 200);
        contentStream.drawString("___________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(90, 190);
        contentStream.drawString(PDFRolTripulacionConstantes.patronEmbarcacion);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(350, 200);
        contentStream.drawString("__________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(380, 190);
        contentStream.drawString(PDFRolTripulacionConstantes.verificadoPorGrado);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(70, 175);
        contentStream.drawString(PDFRolTripulacionConstantes.nombre);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(125, 175);
        contentStream.drawString("______________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(330, 175);
        contentStream.drawString(PDFRolTripulacionConstantes.nombre);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(380, 175);
        contentStream.drawString("______________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(70, 160);
        contentStream.drawString(PDFRolTripulacionConstantes.dni);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(125, 160);
        contentStream.drawString("______________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(330, 160);
        contentStream.drawString(PDFRolTripulacionConstantes.cip);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(380, 160);
        contentStream.drawString("______________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(210, 100);
        contentStream.drawString("_______________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(250, 90);
        contentStream.drawString(PDFRolTripulacionConstantes.capitanPuerto);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(35, 80);
        contentStream.drawString(PDFRolTripulacionConstantes.nota);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(35, 70);
        contentStream.drawString(PDFRolTripulacionConstantes.notaUno);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(35, 62);
        contentStream.drawString(PDFRolTripulacionConstantes.notaDos);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(35, 54);
        contentStream.drawString(PDFRolTripulacionConstantes.notaTres);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(35, 46);
        contentStream.drawString(PDFRolTripulacionConstantes.notaTres1);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(35, 38);
        contentStream.drawString(PDFRolTripulacionConstantes.notaCuatro);
        contentStream.endText();

        logger.error("PlantillaPDF_1");
        drawTableRolTripu(page, contentStream, 685.0f, 30.0f, rolTripulacion);

        contentStream.close();
        document.save(path);
        document.close();

    }

    public String ConvertirFecha(JCoTable jCoTable, String tabla){

        String fecha="";

        try {
            JCoField fieldF = jCoTable.getField(tabla);
            Date date=fieldF.getDate();
            SimpleDateFormat dia = new SimpleDateFormat("dd/MM/yyyy");
            fecha = dia.format(date);
        }catch (Exception e){
            fecha="";
        }


        return fecha;
    }

    public String ConvertirHora(JCoTable jCoTable, String tabla){

        String hora="";
        try {
            JCoField fieldH = jCoTable.getField(tabla);
            Date time = fieldH.getTime();
            SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
            hora = hour.format(time);
        }catch (Exception e){
            hora="";
        }

        return hora;
    }

    public PDFExports GenerarPDFTrimestral(PDFTrimestralImports imports)throws Exception{

        PDFExports pdf= new PDFExports();
        String path = Constantes.RUTA_ARCHIVO_IMPORTAR + "Archivo.pdf";

        PDFZarpeTravesiaDto dto= new PDFZarpeTravesiaDto();

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

            Metodos me= new Metodos();

            List<HashMap<String, Object>> t_vgcer=me.ListarObjetos(T_VGCER);
            for(int i=0; i<T_ZATRP.getNumRows(); i++){
                T_ZATRP.setRow(i);


                String fechaZarpe=ConvertirFecha(T_ZATRP, PDFZarpeConstantes.FEZAT);


                dto.setCapitaniaGuardacostas(T_ZATRP.getString(PDFZarpeConstantes.DSWKP));
                dto.setNombreNave(T_ZATRP.getString(PDFZarpeConstantes.DSWKS));
                dto.setMatricula(T_ZATRP.getString(PDFZarpeConstantes.MREMB));
                dto.setArqueoBruto(T_ZATRP.getString(PDFZarpeConstantes.AQBRT));
                dto.setColorCasco(T_ZATRP.getString(PDFZarpeConstantes.COCAS));
                dto.setSuperEstructura(T_ZATRP.getString(PDFZarpeConstantes.COSUP));
                dto.setPropietario(T_ZATRP.getString(PDFZarpeConstantes.DSEMP));
                dto.setDomicilioFiscal(T_ZATRP.getString(PDFZarpeConstantes.DFEMP));
                dto.setRepresentanteAcreditado(T_ZATRP.getString(PDFZarpeConstantes.RACRE ));
                dto.setTelefono(T_ZATRP.getString(PDFZarpeConstantes.TFEMP));
                dto.setFecha(fechaZarpe);


            }
            logger.error("TIMESTRAL RolTripulacion");
            String[] CamposRolTripulacion= new String[]{PDFZarpeConstantes.NOMBR,
                    PDFZarpeConstantes.NRLIB,
                    PDFZarpeConstantes.FEFVG,
                    PDFZarpeConstantes.STELL};

            String[][] RolTripulacion=new String[T_DZATR.getNumRows()+1][CamposRolTripulacion.length];

            RolTripulacion[0]= PDFZarpeConstantes.fieldRolTripulacion;


            for(int i=0; i<T_DZATR.getNumRows(); i++){
                T_DZATR.setRow(i);
                String cargo=me.ObtenerDominio("CARGOTRIPU", T_DZATR.getString(PDFZarpeConstantes.STELL));

                if(cargo.trim().compareTo("PATRON EP") == 0){
                    dto.setNombreCapitanPatron(T_DZATR.getString(PDFZarpeConstantes.NOMBR));
                    dto.setDni(T_DZATR.getString(PDFZarpeConstantes.NRDNI));
                    logger.error("patron "+ dto.getNombreCapitanPatron());
                    logger.error("dni "+ dto.getDni());
                }
            }
            if(dto.getNombreCapitanPatron()==null){
                dto.setNombreCapitanPatron("");
            }
            if(dto.getDni()==null){
                dto.setDni("");
            }

            List<CertificadoDto> certificadosList=new ArrayList<>();
            for(int i=0; i<t_vgcer.size();i++){
                T_VGCER.setRow(i);

                CertificadoDto c=new CertificadoDto();
                c.setCDCER(T_VGCER.getString("CDCER"));
                c.setNRCER(T_VGCER.getString("NRCER"));
                c.setDSCER(T_VGCER.getString("DSCER"));

                try {
                    String fecha = T_VGCER.getString("FECCF");

                    SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = parseador.parse(fecha);


                    fecha = formateador.format(date);

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date d = new Date();
                    String fechaF = dateFormat.format(d);
                    Date fActual = parseador.parse(fechaF);

                    if (date.after(fActual)) {
                        c.setFECCF(fecha);

                    } else {
                        c.setFECCF("");
                    }

                }catch (Exception e){
                    c.setFECCF("");

                }

                certificadosList.add(c);

            }

            String[][] cert=OrdenarCertificados(certificadosList,"X");

            if(!imports.getFlag().toUpperCase().equals("X")){
                dto.setNombreCapitanPatron(imports.getPatron());
                dto.setRepresentanteAcreditado(imports.getRepresentante());
                dto.setTelefono(imports.getTelefono());
                dto.setDni("");
            }

            logger.error("TIMESTRAL PDF");
            PlantillaPDFTrimestral(path, dto,  cert);

            Metodos exec = new Metodos();
            pdf.setBase64(exec.ConvertirABase64(path));
            pdf.setMensaje("Ok");


        }catch (Exception e){
            pdf.setMensaje(e.getMessage());
        }
        return pdf;
    }

    public void PlantillaPDFTrimestral(String path, PDFZarpeTravesiaDto dto, String[][] certificados)throws Exception{
        logger.error("PlantillaPDFTrimestral");
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);

        document.addPage(page);

        PDFont bold = PDType1Font.HELVETICA_BOLD;
        PDFont font = PDType1Font.HELVETICA;
        logger.error("PlantillaPDFTrimestral_2");
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.beginText();
        contentStream.setFont(bold, 190);
        contentStream.setNonStrokingColor(Color.gray);
        contentStream.moveTextPositionByAmount(40, 790);
        contentStream.showText("_____");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 10);
        contentStream.setNonStrokingColor(Color.white);
        contentStream.moveTextPositionByAmount(40, 760);
        contentStream.drawString(PDFTrimestralConstantes.titulo);
        contentStream.endText();


        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.moveTextPositionByAmount(50, 735);
        contentStream.drawString(PDFTrimestralConstantes.capitania );
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(250, 735);
        contentStream.drawString(dto.getCapitaniaGuardacostas());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(250, 734);
        contentStream.drawString("____________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(40, 710);
        contentStream.drawString(PDFTrimestralConstantes.uno);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(155, 710);
        contentStream.drawString(dto.getNombreNave());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(155, 709);
        contentStream.drawString("__________________________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(40, 690);
        contentStream.drawString(PDFTrimestralConstantes.dos);
        contentStream.endText();

        //insertando numero de matrícula
        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(145, 690);
        contentStream.drawString(dto.getMatricula());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(145, 689);
        contentStream.drawString("_________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(300, 690);
        contentStream.drawString(PDFTrimestralConstantes.tres);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(334, 690);
        contentStream.drawString(dto.getArqueoBruto());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(334, 689);
        contentStream.drawString("_________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(40, 670);
        contentStream.drawString(PDFTrimestralConstantes.cuatro);
        contentStream.endText();

        logger.error("PlantillaPDFTrimestral_3");
        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(145, 670);
        contentStream.drawString(dto.getColorCasco());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(145, 669);
        contentStream.drawString("_________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(300, 670);
        contentStream.drawString(PDFTrimestralConstantes.cuatroB);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(400, 670);
        contentStream.drawString(dto.getSuperEstructura());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(400, 669);
        contentStream.drawString("__________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(40, 650);
        contentStream.drawString(PDFTrimestralConstantes.cinco);
        contentStream.endText();
        logger.error("PlantillaPDFTrimestral_4");
        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(145, 650);
        contentStream.drawString(dto.getPropietario());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(145, 649);
        contentStream.drawString("____________________________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(40, 630);
        contentStream.drawString(PDFTrimestralConstantes.seis);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(145, 630);
        contentStream.drawString(dto.getDomicilioFiscal());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(145, 629);
        contentStream.drawString("____________________________________________________________________________________________");
        contentStream.endText();
        logger.error("PlantillaPDFTrimestral_5");
        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(40, 610);
        contentStream.drawString(PDFTrimestralConstantes.siete);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(190, 610);
        contentStream.drawString(dto.getRepresentanteAcreditado());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(190, 609);
        contentStream.drawString("__________________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(40, 590);
        contentStream.drawString(PDFTrimestralConstantes.ocho);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(145, 590);
        contentStream.drawString(dto.getDomicilioFiscal());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(145, 589);
        contentStream.drawString("____________________________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(40, 570);
        contentStream.drawString(PDFTrimestralConstantes.nueve);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(120, 570);
        contentStream.drawString(dto.getTelefono());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(120, 569);
        contentStream.drawString("__________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(220, 570);
        contentStream.drawString(PDFTrimestralConstantes.diez);
        contentStream.endText();


        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(270, 570);
        contentStream.drawString("________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(390, 570);
        contentStream.drawString(PDFTrimestralConstantes.once);
        contentStream.endText();


        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(430, 570);
        contentStream.drawString("___________________________");
        contentStream.endText();
        logger.error("PlantillaPDFTrimestral_6");
        contentStream.beginText();
        contentStream.setFont(bold, 13);
        contentStream.moveTextPositionByAmount(250, 545);
        contentStream.drawString(PDFTrimestralConstantes.certificados);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 13);
        contentStream.moveTextPositionByAmount(230, 340);
        contentStream.drawString(PDFTrimestralConstantes.declaracionJurada);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(40, 325);
        contentStream.drawString(PDFTrimestralConstantes.declaracionJuradaTexto);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(40, 315);
        contentStream.drawString(PDFTrimestralConstantes.declaracionJuradaTextoUno);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(40, 295);
        contentStream.drawString(PDFTrimestralConstantes.trece);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(60, 250);
        contentStream.drawString(dto.getNombreCapitanPatron());
        contentStream.endText();
        contentStream.beginText();

        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 249);
        contentStream.drawString("________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(340, 295);
        contentStream.drawString(PDFTrimestralConstantes.catorce);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(350, 285);
        contentStream.drawString(PDFTrimestralConstantes.catorceA);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(410, 285);
        contentStream.drawString(dto.getCapitaniaGuardacostas());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(340, 249);
        contentStream.drawString("____________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(40, 230);
        contentStream.drawString(PDFTrimestralConstantes.quince);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 215);
        contentStream.drawString(PDFTrimestralConstantes.dni);
        contentStream.endText();
        logger.error("PlantillaPDFTrimestral_7");
        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(80, 215);
        contentStream.drawString(dto.getDni());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(340, 145);
        contentStream.drawString("________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(340, 230);
        contentStream.drawString(PDFTrimestralConstantes.dieciseis);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(350, 215);
        contentStream.drawString(PDFTrimestralConstantes.firma);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(50, 145);
        contentStream.drawString("___________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(40, 120);
        contentStream.drawString(PDFTrimestralConstantes.diecisiete);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(100, 120);
        contentStream.drawString(dto.getFecha());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(100, 120);
        contentStream.drawString("_____________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(340, 120);
        contentStream.drawString(PDFTrimestralConstantes.dieciocho);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(420, 120);
        contentStream.drawString("_________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(40, 90);
        contentStream.drawString(PDFTrimestralConstantes.nota);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(40, 80);
        contentStream.drawString(PDFTrimestralConstantes.notaUno);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(40, 73);
        contentStream.drawString(PDFTrimestralConstantes.notaDos);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(40, 66);
        contentStream.drawString(PDFTrimestralConstantes.notaTres);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(40, 59);
        contentStream.drawString(PDFTrimestralConstantes.notaTres1);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 6);
        contentStream.moveTextPositionByAmount(40, 49);
        contentStream.drawString(PDFTrimestralConstantes.nota4);
        contentStream.endText();
        logger.error("PlantillaPDFTrimestral_8");

        drawTableCertificadosTrimestral(page, contentStream,535, 50, certificados);
        logger.error("PlantillaPDF_3");

        contentStream.close();
        document.save(path);
        document.close();

    }

    public  void drawTableCertificadosTrimestral(PDPage page, PDPageContentStream contentStream,
                                                float y, float margin, String[][] content) throws IOException {

        logger.error("drawTableCertificados");
        final int rows = content.length;
        final int cols = content[0].length;
        final float rowHeight = 15.0f;
        final float tableWidth = page.getMediaBox().getWidth() - 2.0f * margin;
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

        logger.error("drawTableCertificados1");

        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols; i++) {

            if(i==1){
                nextx=margin+245;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
            }else if(i==2){
                nextx+=250f;
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
        logger.error("drawTableCertificados2");

        //data -  coordenadas data
        float texty=y-10;
        for(int i=0; i<content.length;i++) {

            String[]fields=content[i];
            float textx=margin+10;

            for (int j = 0; j < fields.length; j++) {

                switch (j) {
                    case 0:
                        if(i==0){
                            textx = 60;
                        }else {
                            textx = 90;
                        }
                        break;
                    case 1:
                        if(i==0){
                            textx = 390;
                        }else {
                            textx = 370;
                        }
                        break;

                }
                int font=7;

                if(i==0){
                    font=8;
                }
                logger.error("drawTableCertificados3");

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, font);
                contentStream.newLineAtOffset(textx, texty);
                try{
                    contentStream.showText(fields[j]);
                }catch (Exception e){
                    contentStream.showText("");
                }
                contentStream.endText();

                logger.error("drawTableCertificados4");

            }
            texty-=15;
        }


    }



    public PDFExports GenerarPDFTrabajoFF(TrabajoFueraFaenaDetalleImports imports)throws Exception{
        PDFExports pdf= new PDFExports();
        try {
            String path = Constantes.RUTA_ARCHIVO_IMPORTAR + "Archivo.pdf";

            TrabajoFueraFaenaImports tfi = new TrabajoFueraFaenaImports();
            TrabajoFueraFaenaDetalleExports dto = jcoTrabajoFueraFaena.DetalleTrabajoFueraFaenaTransporte(imports);



            try {
                if (dto.getObservacion() == null || dto.getObservacion().equals(null)) {
                    dto.setObservacion("");

                }
            }catch (Exception e){
                dto.setObservacion("");

            }


            List<String[]> ListaRegistros = new ArrayList<>();

            String[] cabecera = {"Nro. Personal", "Nombre", "Cargo", dto.getFechas()[0], dto.getFechas()[1], dto.getFechas()[2],
                    dto.getFechas()[3], dto.getFechas()[4], dto.getFechas()[5], dto.getFechas()[6], "Origen", "Centro", "Destino"};

            ListaRegistros.add(cabecera);


            for (int i = 0; i < dto.getDetalle().size(); i++) {

                TrabajoFFDetalleDto detalle = dto.getDetalle().get(i);

                String[] registro = new String[13];
                registro[0] = detalle.getNroPersona();
                registro[1] = detalle.getNombre();
                // registro[2]=detalle.getCargo();
                registro[2] = detalle.getCargo();
                registro[10] = detalle.getOrigen();
                registro[11] = detalle.getCentro();
                registro[12] = detalle.getDestino();

                for (Map.Entry<String, Object> entry : detalle.getFechas().entrySet()) {
                    String key = entry.getKey();
                    String valor = entry.getValue().toString();


                    logger.error("REGISTRO " + key + " : " + valor);

                    if (registro[3] == null) {
                        if (key.trim().equals(dto.getFechas()[0].trim())) {
                            registro[3] = valor;
                            logger.error("registro[3]=valor " + registro[3] + " : " + valor);
                        }
                    }
                    if (registro[4] == null) {
                        if (key.trim().equals(dto.getFechas()[1].trim())) {
                            registro[4] = valor;
                            logger.error("registro[4]=valor " + registro[4] + " : " + valor);
                        }
                    }
                    if (registro[5] == null) {
                        if (key.trim().equals(dto.getFechas()[2].trim())) {
                            registro[5] = valor;
                            logger.error("registro[5]=valor " + registro[5] + " : " + valor);

                        }
                    }
                    if (registro[6] == null) {
                        if (key.trim().equals(dto.getFechas()[3].trim())) {
                            registro[6] = valor;
                            logger.error("registro[6]=valor " + registro[6] + " : " + valor);

                        }
                    }
                    if (registro[7] == null) {
                        if (key.trim().equals(dto.getFechas()[4].trim())) {
                            registro[7] = valor;
                            logger.error("registro[7]=valor " + registro[7] + " : " + valor);

                        }
                    }
                    if (registro[8] == null) {
                        if (key.trim().equals(dto.getFechas()[5].trim())) {
                            registro[8] = valor;
                            logger.error("registro[8]=valor " + registro[8] + " : " + valor);

                        }
                    }
                    if (registro[9] == null) {
                        if (key.trim().equals(dto.getFechas()[6].trim())) {
                            registro[9] = valor;
                            logger.error("registro[9]=valor " + registro[9] + " : " + valor);

                        }
                    }

                }
                if (registro[3] == null) {
                    registro[3] = "";
                }
                if (registro[4] == null) {
                    registro[4] = "";
                }
                if (registro[5] == null) {
                    registro[5] = "";
                }
                if (registro[6] == null) {
                    registro[6] = "";
                }
                if (registro[7] == null) {
                    registro[7] = "";
                }
                if (registro[8] == null) {
                    registro[8] = "";
                }
                if (registro[9] == null) {
                    registro[9] = "";
                }
                ListaRegistros.add(registro);
            }
            logger.error("FECHAS[0]= " + dto.getFechas()[0]);
            logger.error("FECHAS[1]= " + dto.getFechas()[1]);
            logger.error("FECHAS[2]= " + dto.getFechas()[2]);
            logger.error("FECHAS[3]= " + dto.getFechas()[3]);
            logger.error("FECHAS[4]= " + dto.getFechas()[4]);
            logger.error("FECHAS[5]= " + dto.getFechas()[5]);
            logger.error("FECHAS[6]= " + dto.getFechas()[6]);


            PlantillaPDFTrabajoFF(path, dto, ListaRegistros);
            Metodos exec = new Metodos();
            pdf.setBase64(exec.ConvertirABase64(path));
            pdf.setMensaje(dto.getMensaje());

        }catch (Exception e){
            pdf.setMensaje(e.getMessage());
        }
        return pdf;
    }

    public void PlantillaPDFTrabajoFF(String path, TrabajoFueraFaenaDetalleExports dto, List<String[]> content)throws IOException{

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        page.setRotation(90);
        document.addPage(page);

        PDFont font = PDType1Font.HELVETICA;
        PDFont bold = PDType1Font.HELVETICA_BOLD;

        PDRectangle pageSize = page.getMediaBox();
        float pageWidth = pageSize.getWidth();
        PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.OVERWRITE, false);
// add the rotation using the current transformation matrix
// including a translation of pageWidth to use the lower left corner as 0,0 reference
        contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));

        contentStream.beginText();
        contentStream.setFont(bold, 13);
        contentStream.moveTextPositionByAmount(300, 540);
        contentStream.drawString(PDFTrabajoFFConstantes.titulo);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 13);
        contentStream.moveTextPositionByAmount(300, 540);
        contentStream.drawString("___________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 480);
        contentStream.drawString(PDFTrabajoFFConstantes.numeroTrabajo);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(150, 480);
        contentStream.drawString(dto.getNrTrabajo());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 470);
        contentStream.drawString(PDFTrabajoFFConstantes.tipoTrabajo);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(150, 470);
        contentStream.drawString(dto.getTipoTrabajo());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 460);
        contentStream.drawString(PDFTrabajoFFConstantes.semana);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(150, 460);
        contentStream.drawString(dto.getSemana());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 450);
        contentStream.drawString(PDFTrabajoFFConstantes.fechaInicio);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(150, 450);
        contentStream.drawString(dto.getFechaInicio());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(200, 450);
        contentStream.drawString(PDFTrabajoFFConstantes.fechaFin);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(250, 450);
        contentStream.drawString(dto.getFechaFin());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 440);
        contentStream.drawString(PDFTrabajoFFConstantes.descripcionTrabajo);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(150, 440);
        contentStream.drawString(dto.getDescripcionTrabajo());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(50, 430);
        contentStream.drawString(PDFTrabajoFFConstantes.observacion);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(150, 430);
        contentStream.drawString(dto.getObservacion());
        contentStream.endText();

        drawCuadroDetalle(page,contentStream, 45, 390,755,content);
        contentStream.close();

        if(content.size()>20){
            page = new PDPage(PDRectangle.A4);
            page.setRotation(90);
            document.addPage(page);


             pageSize = page.getMediaBox();
             pageWidth = pageSize.getWidth();
             contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.OVERWRITE, false);
// add the rotation using the current transformation matrix
// including a translation of pageWidth to use the lower left corner as 0,0 reference
            contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));

            drawCuadroDetalle2(page,contentStream, 45, 520,755,content);
            contentStream.close();

        }


        document.save(path);
        document.close();
    }

    public  int drawCuadroDetalle(PDPage page, PDPageContentStream contentStream,
                                  int x, int y, int ancho,List<String[]> content) throws IOException {

        logger.error("drawCuadroDetalletrabajoff");
        int cantidadRegistros;
        if(content.size()<=20){
            cantidadRegistros= content.size();
        }else{
            cantidadRegistros=20;
        }
        int rows=cantidadRegistros;
        final int cols = content.get(0).length;
        float rowHeight = 30.0f;
        final float tableHeight =(15 * (float) rows)+15;


        //draw the rows
        float nexty = y ;
        for (int i = 0; i <= rows; i++) {

            if(i!=0){
                rowHeight = 15.0f;
            }
            contentStream.moveTo(x, nexty);
            contentStream.lineTo(x + ancho, nexty);
            contentStream.stroke();
            nexty -= rowHeight;

        }





        //draw the columns
        float nextx = x;
        for (int i = 0; i <= cols+2; i++) {

            if(i==1 ){
                nextx+=70;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
            }else if(i==2){
                nextx+=185f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
            }
            else if(i==3){
                nextx+=90;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
            }
            else if(i==4){
                nextx+=40f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if(i==5){
                nextx+=40f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if(i==6){
                nextx+=40f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if(i==7 ){
                nextx+=40f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if(i==8){
                nextx+=40f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if( i==9){
                nextx+=40f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if( i==10){
                nextx+=40f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if( i==11){
                nextx+=43f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if( i==12){
                nextx+=43f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if( i==13){
                nextx+=43f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if(i==0) {
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else {
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }

        }


        //now add the text
        PDFont font;


        int texty=y-13;
        for(int i=0; i<cantidadRegistros; i++) {

            String[]fields=content.get(i);
            float textx=x+5;

            for (int j = 0; j < fields.length; j++) {

                switch (j) {
                    case 0:
                        textx = 50;
                        break;
                    case 1:
                        textx = 120;
                        break;
                    case 2:
                        textx=305;
                        break;
                    case 3:
                        textx = 395;
                        break;
                    case 4:
                        textx = 435;
                        break;
                    case 5:
                        textx = 475;
                        break;
                    case 6:
                        textx = 520;
                        break;
                    case 7:
                        textx = 555;
                        break;
                    case 8:
                        textx = 600;
                        break;
                    case 9:
                        textx = 635;
                        break;
                    case 10:
                        textx = 675;
                        break;
                    case 11:
                        textx = 715;
                        break;
                    case 12:
                        textx = 760;
                        break;

                }
                if(i==0 ){
                    font=PDType1Font.HELVETICA_BOLD;
                }else{
                    font=PDType1Font.HELVETICA;
                }
                float tam=7.5f;


                if(i==0){
                    contentStream.beginText();
                    contentStream.setFont(font, 9);
                    contentStream.newLineAtOffset(textx, texty-5);
                    contentStream.showText(fields[j]);
                    contentStream.endText();
                }else {
                    contentStream.beginText();
                    contentStream.setFont(font, tam);
                    contentStream.newLineAtOffset(textx, texty);
                    contentStream.showText(fields[j]);
                    contentStream.endText();
                }

            }
            if(i==0){
                texty -= 30;
            }else {

                texty -= 15;
            }

        }



        return texty;
    }
    public  int drawCuadroDetalle2(PDPage page, PDPageContentStream contentStream,
                                  int x, int y, int ancho,List<String[]> content) throws IOException {

        logger.error("drawCuadroDetalletrabajoff");

        int cantidadRegistros= content.size()-20;

        final int rows = content.size()-20;
        final int cols = content.get(0).length;
        float rowHeight = 15.0f;
        final float tableHeight =15 * (float) rows;


        //draw the rows
        float nexty = y ;
        for (int i = 0; i <= rows; i++) {

            //if(i!=0){
            //    rowHeight = 15.0f;
            //}
            contentStream.moveTo(x, nexty);
            contentStream.lineTo(x + ancho, nexty);
            contentStream.stroke();
            nexty -= rowHeight;

        }





        //draw the columns
        float nextx = x;
        for (int i = 0; i <= cols+2; i++) {

            if(i==1 ){
                nextx+=70;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
            }else if(i==2){
                nextx+=185f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
            }
            else if(i==3){
                nextx+=90;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
            }
            else if(i==4){
                nextx+=40f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if(i==5){
                nextx+=40f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if(i==6){
                nextx+=40f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if(i==7 ){
                nextx+=40f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if(i==8){
                nextx+=40f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if( i==9){
                nextx+=40f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if( i==10){
                nextx+=40f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if( i==11){
                nextx+=43f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if( i==12){
                nextx+=43f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if( i==13){
                nextx+=43f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if(i==0) {
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else {
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }

        }


        //now add the text
        PDFont font;


        int texty=y-13;
        for(int i=21; i<content.size(); i++) {

            String[]fields=content.get(i);
            float textx=x+5;

            for (int j = 0; j < fields.length; j++) {

                switch (j) {
                    case 0:
                        textx = 50;
                        break;
                    case 1:
                        textx = 120;
                        break;
                    case 2:
                        textx=305;
                        break;
                    case 3:
                        textx = 395;
                        break;
                    case 4:
                        textx = 435;
                        break;
                    case 5:
                        textx = 475;
                        break;
                    case 6:
                        textx = 520;
                        break;
                    case 7:
                        textx = 555;
                        break;
                    case 8:
                        textx = 600;
                        break;
                    case 9:
                        textx = 635;
                        break;
                    case 10:
                        textx = 675;
                        break;
                    case 11:
                        textx = 715;
                        break;
                    case 12:
                        textx = 760;
                        break;

                }

                font=PDType1Font.HELVETICA;


                contentStream.beginText();
                contentStream.setFont(font, 7.5f);
                contentStream.newLineAtOffset(textx, texty);
                contentStream.showText(fields[j]);
                contentStream.endText();


            }


            texty -= 15;

        }



        return texty;
    }
    public PDFExports GenerarPDFValeViveres(PDFValeViveresImports imports)throws Exception{

        PDFExports pdf= new PDFExports();
        String path = Constantes.RUTA_ARCHIVO_IMPORTAR + "Archivo.pdf";

        try {
            PDFValeViveresDto dto=LectMaesViveres(imports.getNumValeVivere(), imports.getP_user());

            List<PDFValeVivereDetalleDto>detalle=PosiViveres(imports.getNumValeVivere(), imports.getP_user());

            logger.error("detalle.size: "+detalle.size());
            int totalRaciones=0;
            float totalCosto=0;

            for(int i=0; i<detalle.size();i++){

                totalRaciones+= detalle.get(i).getRaciones();
                totalCosto+=detalle.get(i).getTotal();

            }
            DecimalFormat decimal = new DecimalFormat("#.00");
            totalCosto=Float.parseFloat(decimal.format(totalCosto));

            dto.setTotalRaciones(totalRaciones);
             dto.setTotalCosto(totalCosto);

            String codAlm=ObtenerCodAlmacen(dto.getAlmacen(), imports.getP_user());
            dto.setAlmacen(codAlm);


            //actualizando fechas si encuentra otro descripcion de vale diferente a "RACIONES DE VIVERES P/EMBARCACIONES 2"
            String []fechas=new String[detalle.size()];

            logger.error("detalle.size(): "+detalle.size());

           boolean existe=false;
            for(int i=0;i<detalle.size();i++) {
                logger.error("detalle.get(i).getDescripcion(): "+detalle.get(i).getDescripcion());
                logger.error("PDFValeViveresConstantes.descripcionViveres.trim(): "+PDFValeViveresConstantes.descripcionViveres.trim());


                if (!detalle.get(i).getDescripcion().trim().equals(PDFValeViveresConstantes.descripcionViveres.trim())) {
                    String fechaInicial=dto.getFechas()[0];
                    logger.error("fechaInicial: "+fechaInicial);
                    fechas[i]=fechaInicial;
                    logger.error("fechas[i]: "+fechas[i]);
                    existe=true;
                }else{
                    String fecha="";
                    if(existe){
                         fecha=dto.getFechas()[i-1];
                    }else{
                        fecha=dto.getFechas()[i];

                    }

                    fechas[i]=fecha;
                }
            }
            dto.setFechas(fechas);

            PantillaPDFValeViveres(path, dto, detalle, imports.getEstadoImpresion());

            Metodos exec = new Metodos();

            pdf.setBase64(exec.ConvertirABase64(path));
            pdf.setMensaje("Ok");

        }catch (Exception e){
            pdf.setMensaje(e.getMessage());
        }



        return pdf;
    }
    public String ObtenerCodAlmacen(String codAlm, String user)throws  Exception{

        String codAlmacen="";

        HashMap<String, Object> imports = new HashMap<String, Object>();
        logger.error("ARMADOR 4");
        imports.put("QUERY_TABLE", "ZFLALM");
        imports.put("DELIMITER", "|");
        imports.put("NO_DATA", "");
        imports.put("ROWSKIPS", "");
        imports.put("ROWCOUNT", "");
        imports.put("P_USER", user);
        imports.put("P_ORDER", "");
        imports.put("P_PAG", "");

        List<MaestroOptions> option =new ArrayList<>();
        MaestroOptions opt=new MaestroOptions();
        opt.setWa("CDALM = '"+ codAlm+"'");
        option.add(opt);
        List<MaestroOptionsKey > optionsKeys= new ArrayList<>();

        Metodos m= new Metodos();
        List<HashMap<String, Object>> tmpOptions=m.ValidarOptions(option, optionsKeys);

        String[] fields={"CDALE"};

         EjecutarRFC exec=new EjecutarRFC();
        MaestroExport me=exec.Execute_ZFL_RFC_READ_TABLE(imports, tmpOptions, fields);

        for(Map.Entry<String, Object> entry: me.getData().get(0).entrySet()){

                codAlmacen= entry.getValue().toString();

        }

        return codAlmacen;
    }
    public void PantillaPDFValeViveres(String path, PDFValeViveresDto dto, List<PDFValeVivereDetalleDto> detalle, String estadoImpresion)throws IOException{

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);

        document.addPage(page);

        PDFont bold = PDType1Font.HELVETICA_BOLD;
        PDFont font = PDType1Font.HELVETICA;
        PDFont timesBold = PDType1Font.TIMES_BOLD;
        PDFont times = PDType1Font.TIMES_ROMAN;

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        String tasa= Constantes.RUTA_ARCHIVO_IMPORTAR+"tasa.jpg";
        PDImageXObject logoTasa = PDImageXObject.createFromFile(tasa,document);
        contentStream.drawImage(logoTasa, 40, 760);

        if(estadoImpresion.equals("I")) {

            contentStream.beginText();
            contentStream.setFont(bold, 80);
            contentStream.setNonStrokingColor(Color.lightGray);
            contentStream.moveTextPositionByAmount(180, 640);
            contentStream.showText(PDFValeViveresConstantes.copia);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 80);
            contentStream.setNonStrokingColor(Color.lightGray);
            contentStream.moveTextPositionByAmount(180, 380);
            contentStream.showText(PDFValeViveresConstantes.copia);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 80);
            contentStream.setNonStrokingColor(Color.lightGray);
            contentStream.moveTextPositionByAmount(180, 200);
            contentStream.showText(PDFValeViveresConstantes.copia);
            contentStream.endText();

        }

        contentStream.beginText();
        contentStream.setFont(timesBold, 14);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.moveTextPositionByAmount(180, 760);
        contentStream.showText(PDFValeViveresConstantes.titulo);
        contentStream.endText();


        contentStream.beginText();
        contentStream.setFont(times, 8);
        contentStream.moveTextPositionByAmount(40, 760);
        contentStream.showText(PDFValeViveresConstantes.gerenciaPesca);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(times, 8);
        contentStream.moveTextPositionByAmount(430, 803);
        contentStream.showText(PDFValeViveresConstantes.nroPedido);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(times, 8);
        contentStream.moveTextPositionByAmount(430, 790);
        contentStream.showText(PDFValeViveresConstantes.centro);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(times, 8);
        contentStream.moveTextPositionByAmount(490, 790);
        contentStream.showText(dto.getCentro());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(times, 8);
        contentStream.moveTextPositionByAmount(430, 777);
        contentStream.showText(PDFValeViveresConstantes.almacen);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(times, 8);
        contentStream.moveTextPositionByAmount(490, 777);
        contentStream.showText(dto.getAlmacen());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(times, 8);
        contentStream.moveTextPositionByAmount(430, 764);
        contentStream.showText(PDFValeViveresConstantes.nroVale);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(times, 8);
        contentStream.moveTextPositionByAmount(490, 764);
        contentStream.showText(dto.getNroVale());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(times, 8);
        contentStream.moveTextPositionByAmount(430, 751);
        contentStream.showText(PDFValeViveresConstantes.fecha);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(times, 8);
        contentStream.moveTextPositionByAmount(490, 751);
        contentStream.showText(dto.getFecha());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(times, 8);
        contentStream.moveTextPositionByAmount(430, 738);
        contentStream.showText(PDFValeViveresConstantes.temporada);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(times, 8);
        contentStream.moveTextPositionByAmount(490, 738);
        contentStream.showText(dto.getTemporada());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8   );
        contentStream.moveTextPositionByAmount(430, 715);
        contentStream.showText(PDFValeViveresConstantes.ruc);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8   );
        contentStream.moveTextPositionByAmount(490, 715);
        contentStream.showText(dto.getRuc());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(30, 715);
        contentStream.showText(PDFValeViveresConstantes.codigoArmador);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(130, 715);
        contentStream.showText(dto.getCodigoArmador());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(30, 700);
        contentStream.showText(PDFValeViveresConstantes.razonSocial);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(130, 700);
        contentStream.showText(dto.getRazonSocialUno());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(30, 685);
        contentStream.showText(PDFValeViveresConstantes.direccion);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(130, 685);
        contentStream.showText(dto.getDireccion());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(30, 670);
        contentStream.showText(PDFValeViveresConstantes.nombreEmbarcacion);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(130, 670);
        contentStream.showText(dto.getNombreEmbarcacion());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(430, 670);
        contentStream.showText(PDFValeViveresConstantes.matricula);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(490, 670);
        contentStream.showText(dto.getMatricula());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(30, 655);
        contentStream.showText(PDFValeViveresConstantes.codigoProveeduria);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(130, 655);
        contentStream.showText(dto.getCodigoProveeduria());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(430, 655);
        contentStream.showText(PDFValeViveresConstantes.indPropiedad);
        contentStream.endText();

        if(dto.getIndPropiedad().equals("P")){
            dto.setIndPropiedad("PROPIA");
        }
        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(490, 655);
        contentStream.showText(dto.getIndPropiedad());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(30, 640);
        contentStream.showText(PDFValeViveresConstantes.razonSocial);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(130, 640);
        contentStream.showText(dto.getRazonSocialDos());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(30, 625);
        contentStream.showText(PDFValeViveresConstantes.importeViveresConIgv);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(170, 625);
        contentStream.showText(String.valueOf(dto.getTotalCosto()));
        contentStream.endText();

        drawTableCabeceraValeViveres(page, contentStream,590, 20, PDFValeViveresConstantes.cabecerasTabla);



        int y=560;
        for(int i=0; i<detalle.size(); i++){



            PDFValeVivereDetalleDto detalleDto=detalle.get(i);


            contentStream.beginText();
            contentStream.setFont(font, 7.5f);
            contentStream.moveTextPositionByAmount(25, y);
            contentStream.showText(dto.getFechas()[i]);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 7.5f);
            contentStream.moveTextPositionByAmount(95, y);
            contentStream.showText(String.valueOf(detalleDto.getRaciones()));
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 7.5f);
            contentStream.moveTextPositionByAmount(120, y);
            contentStream.showText(String.valueOf(detalleDto.getCostoUnitario()));
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 7.5f);
            contentStream.moveTextPositionByAmount(175, y);
            contentStream.showText(String.valueOf(detalleDto.getDescripcion()));
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 7.5f);
            contentStream.moveTextPositionByAmount(360, y);
            contentStream.showText(String.valueOf(detalleDto.getTotal()));
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 7.5f);
            contentStream.moveTextPositionByAmount(410, y);
            contentStream.showText(String.valueOf(detalleDto.getComentario()));
            contentStream.endText();

            y-=20;
        }



        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(30, y);
        contentStream.showText(PDFValeViveresConstantes.totalRaciones);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(100, y);
        contentStream.showText(String.valueOf(dto.getTotalRaciones()));
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(310, y);
        contentStream.showText(PDFValeViveresConstantes.totalCosto);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(360, y);
        contentStream.showText(String.valueOf(dto.getTotalCosto()));
        contentStream.endText();




        drawCuadroValeViveres(contentStream, 280,30);

        int cant=3;
        int nx=50;

        for(int i=0; i<cant; i++){

            contentStream.beginText();
            contentStream.setFont(font, 8);
            contentStream.moveTextPositionByAmount(nx, 260);
            contentStream.showText(PDFValeViveresConstantes.firma);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 8);
            contentStream.moveTextPositionByAmount(nx+35, 260);
            contentStream.showText(PDFValeViveresConstantes.guion);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 8);
            contentStream.moveTextPositionByAmount(nx, 230);
            contentStream.showText(PDFValeViveresConstantes.nombreMayus);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 8);
            contentStream.newLineAtOffset(nx+40, 230);
            contentStream.showText(PDFValeViveresConstantes.guion);
            contentStream.endText();

            if(i==1){
                nx+=190;
            }else {
                nx += 160;
            }
        }




        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(120, 220);
        contentStream.showText(PDFValeViveresConstantes.radioOperador);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 7);
        contentStream.newLineAtOffset(250, 230);
        contentStream.showText(dto.getCocinero());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(290, 220);
        contentStream.showText(PDFValeViveresConstantes.cocinero);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(470, 220);
        contentStream.showText(PDFValeViveresConstantes.proveeduria);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 11.5f);
        contentStream.moveTextPositionByAmount(30, 170);
        contentStream.showText(PDFValeViveresConstantes.textoUno);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.newLineAtOffset(55, 171);
        contentStream.showText(dto.getCocinero());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 11.5f);
        contentStream.moveTextPositionByAmount(410, 171);
        contentStream.showText(dto.getNombreEmbarcacion());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 11.5f);
        contentStream.moveTextPositionByAmount(30, 160);
        contentStream.showText(PDFValeViveresConstantes.textoDos);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 11.5f);
        contentStream.moveTextPositionByAmount(30, 150);
        contentStream.showText(PDFValeViveresConstantes.textoTres);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 11);
        contentStream.moveTextPositionByAmount(270, 101);
        contentStream.showText("_____________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 11);
        contentStream.moveTextPositionByAmount(270, 90);
        contentStream.showText(PDFValeViveresConstantes.bahiaTasa);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 11);
        contentStream.moveTextPositionByAmount(270, 80);
        contentStream.showText(PDFValeViveresConstantes.nombreMinus);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 11);
        contentStream.moveTextPositionByAmount(270, 70);
        contentStream.showText(PDFValeViveresConstantes.dni);
        contentStream.endText();




        contentStream.close();
        document.save(path);
        document.close();
    }
    public void drawTableCabeceraValeViveres(PDPage page, PDPageContentStream contentStream,
                                             float y, float margin, String[]content)throws IOException{
        logger.error("drawTableCertificados");
        final int rows = 1;
        final int cols = content.length;
        final float rowHeight = 15.0f;
        final float tableWidth = page.getMediaBox().getWidth() - 2.0f * margin;
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
        for (int i = 0; i <= cols; i++) {

            contentStream.moveTo(nextx, y);
            contentStream.lineTo(nextx, y - tableHeight);
            contentStream.stroke();

            if(i==3) {
                nextx += 175;
            }  else if(i==5){
                nextx+=180;
            }else{
                nextx+=50;
            }

        }


        //data -  coordenadas data
        float texty=y-10;
        for(int i=0; i<content.length;i++) {

            //String[]fields=content[i];
            float textx=0;

            switch (i) {
                case 0:
                    textx = 35;
                    break;
                case 1:
                    textx = 75;
                    break;
                case 2:
                    textx = 120;
                    break;
                case 3:
                    textx = 230;
                    break;
                case 4:
                    textx =350;
                    break;
                case 5:
                    textx = 460;
                    break;

            }


            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7);
            contentStream.newLineAtOffset(textx, texty);
            contentStream.showText(content[i]);
            contentStream.endText();




        }


    }

    public void drawCuadroValeViveres( PDPageContentStream contentStream, float y, float margin)throws IOException{

        final int rows = 1;
        final int cols = 1;

        final float tableWidth = 530f;
        final float tableHeight = 80;

        //draw the rows
        float nexty = y ;
        for (int i = 0; i <= rows; i++) {
            contentStream.moveTo(margin, nexty);
            contentStream.lineTo(margin + tableWidth, nexty);
            contentStream.stroke();
            nexty -= tableHeight;

        }


        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols; i++) {


            contentStream.moveTo(nextx, y);
            contentStream.lineTo(nextx, y - tableHeight);
            contentStream.stroke();
            nextx+=tableWidth;
        }


    }

    public PDFValeViveresDto LectMaesViveres(String numVivere, String p_user)throws Exception{

        PDFValeViveresDto dto=new PDFValeViveresDto();

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_LECT_MAES_VIVER);

        JCoParameterList importx = stfcConnection.getImportParameterList();
        importx.setValue("P_USER", p_user);


        JCoParameterList tables = stfcConnection.getTableParameterList();
        JCoTable OPTIONS = tables.getTable(Tablas.OPTIONS);
        OPTIONS.appendRow();
        OPTIONS.setValue("TEXT", "NRVVI LIKE '"+numVivere+"'");

        stfcConnection.execute(destination);

        JCoTable S_DATA = tables.getTable(Tablas.S_DATA);



        S_DATA.setRow(0);
        //dto.setComentario(S_DATA.getString(PDFValeViveresConstantes.OBVVI));
        dto.setCocinero(S_DATA.getString(PDFValeViveresConstantes.NMPER));
        dto.setCentro(S_DATA.getString(PDFValeViveresConstantes.WERKS));
        dto.setAlmacen(S_DATA.getString(PDFValeViveresConstantes.CDALM));
        dto.setNroVale(S_DATA.getString(PDFValeViveresConstantes.NRVVI));
        dto.setFecha(ConvertirFecha(S_DATA, PDFValeViveresConstantes.FCVVI ));
        dto.setRuc(S_DATA.getString(PDFValeViveresConstantes.STCD1));
        dto.setMatricula(S_DATA.getString(PDFValeViveresConstantes.MREMB));
        dto.setTemporada(S_DATA.getString(PDFValeViveresConstantes.DSTPO));
        dto.setIndPropiedad(S_DATA.getString(PDFValeViveresConstantes.INPRP));
        dto.setCodigoArmador(S_DATA.getString(PDFValeViveresConstantes.ARCMC));
        dto.setRazonSocialUno(S_DATA.getString(PDFValeViveresConstantes.NAME1));
        dto.setDireccion(S_DATA.getString(PDFValeViveresConstantes.DIREC));
        dto.setNombreEmbarcacion(S_DATA.getString(PDFValeViveresConstantes.NMEMB));
        dto.setCodigoProveeduria(S_DATA.getString(PDFValeViveresConstantes.CDPVE));
        dto.setRazonSocialDos(S_DATA.getString(PDFValeViveresConstantes.NAME2));
        dto.setFechaInicio(ConvertirFecha(S_DATA, PDFValeViveresConstantes.FITVS));
        dto.setFechaFin(ConvertirFecha(S_DATA, PDFValeViveresConstantes.FFTVS));
        dto.setEstadoImpresion(S_DATA.getString(PDFValeViveresConstantes.ESIMP));

        logger.error("ESTADO IMPRESION: "+ dto.getEstadoImpresion());

        dto.setFechas(ObtenerFechas(dto.getFechaInicio(), dto.getFechaFin()));

        return dto;
    }
    public String[] ObtenerFechas(String fechaInicio, String fechaFin)throws  Exception{

        SimpleDateFormat sformat = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaI= sformat.parse(fechaInicio);
        Date fechaF= sformat.parse(fechaFin);

        logger.error("fechaI: "+fechaI);
        logger.error("fechaF: "+fechaF);


        int diff=(int)((fechaF.getTime() - fechaI.getTime())/86400000);
        int totalDias=diff+1;
        logger.error("diff: "+diff);

        int con=0;

        String[]fechas=new String[totalDias];
        for(int i=0; i<totalDias;i++){

            int x=0;
            if(i>0){
                x=1;
            }
            Calendar c = Calendar.getInstance();
            c.setTime(fechaI);
            c.add(Calendar.DATE, x);

            fechaI=c.getTime();


            SimpleDateFormat parseador = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
            Date date = parseador.parse(fechaI.toString());
            String fecha = formateador.format(date);

            logger.error("fecha: "+fecha );
            fechas[i]=fecha;

           con++;
        }
       /* if(con==diff-1){

            SimpleDateFormat parseador = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
            Date date = parseador.parse(fechaF.toString());
            String fecha = formateador.format(date);
            fechas[diff-1]=fecha;
        }*/

        return fechas;
    }

    public List<PDFValeVivereDetalleDto> PosiViveres(String numVivere, String p_user)throws Exception{

        List<PDFValeVivereDetalleDto> detalle=new ArrayList<>();

        JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
        JCoRepository repo = destination.getRepository();
        JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_LECT_POSI_VIVER);

        JCoParameterList importx = stfcConnection.getImportParameterList();
        importx.setValue("P_USER", p_user);
        importx.setValue("P_CODE", numVivere);


        JCoParameterList tables = stfcConnection.getTableParameterList();


        stfcConnection.execute(destination);

        JCoTable S_POSICION = tables.getTable(Tablas.S_POSICION);

        for(int i=0; i<S_POSICION.getNumRows(); i++){

            S_POSICION.setRow(i);

            PDFValeVivereDetalleDto dto=new PDFValeVivereDetalleDto();

            float costoUnit=Float.parseFloat(S_POSICION.getString(PDFValeViveresConstantes.CUSUM));
            float total=Float.parseFloat(S_POSICION.getString(PDFValeViveresConstantes.QTSUM));
            int raciones=Integer.parseInt(S_POSICION.getString(PDFValeViveresConstantes.CNRAC));

            dto.setDescripcion(S_POSICION.getString(PDFValeViveresConstantes.DSSUM));
            dto.setComentario(S_POSICION.getString(PDFValeViveresConstantes.OBPVA));
            dto.setCostoUnitario(costoUnit);
            dto.setTotal(total);
            dto.setRaciones(raciones);

            detalle.add(dto);
        }

          return detalle;
    }

    public PDFExports GenerarPDFProduce(PDFProduceImports imports)throws Exception{
        PDFExports pdf= new PDFExports();
        String path = Constantes.RUTA_ARCHIVO_IMPORTAR + "Archivo.pdf";

        ImpresFormatosProduceExports ifpe=jcoImpresFormatosProduce.ImpresionFormatosProduce(imports.getImpresFormatosProduceImports());

        PDDocument document = new PDDocument();
        int pag=1;
        for(int i=0; i<ifpe.getT_dtprce().size();i++){

            HashMap<String, Object>data=ifpe.getT_dtprce().get(i);
            PDFProduceDto dto= new PDFProduceDto();

            for (Map.Entry<String,Object>entry:data.entrySet()) {
                String key= entry.getKey();
                Object value=entry.getValue();

                if(key.equals("ARMAD")){
                    dto.setArmadorORespresentante(value.toString());
                }else if(key.equals("DNIAR")){
                    dto.setDocumentoIdentidad(value.toString());
                }else if(key.equals("NMEMB")){
                    dto.setNombreEP(value.toString());
                }else if(key.equals("MREMB")){
                    dto.setMatriculaEP(value.toString());
                }else if(key.equals("RSPMS")){
                    dto.setPermisoPesca(value.toString());
                }else if(key.equals("CVPMS")){
                    dto.setCapacidadBodegaM3(value.toString());
                }else if(key.equals("CPPMS")){
                    dto.setCapacidadBodegaTM(value.toString());
                }else if(key.equals("NRTRI")){
                    dto.setNroTripulantes(value.toString());
                }else if(key.equals("FIDES")){
                    dto.setFecha(value.toString());
                }else if(key.equals("HIDES")){
                    dto.setHora(value.toString());
                    dto.setInicioDescarga(value.toString());
                }else if(key.equals("DSPDG")){
                    dto.setNombre(value.toString());
                }else if(key.equals("HFDES")){
                    dto.setFinDescarga(value.toString());
                }else if(key.equals("CNPDS")){
                    dto.setTMDescargadas(value.toString());
                }

            }
            dto.setComercianteRecibeProd("TECNOLOGICA DE ALIMENTOS S.A.");
            PDPage page = new PDPage(PDRectangle.A4);

            PlantillaPDFProduce(path, dto, imports.getFlag(), page, document, pag);
            pag++;
        }

        document.save(path);
        document.close();

        Metodos exec = new Metodos();
        pdf.setBase64(exec.ConvertirABase64(path));
        pdf.setMensaje("Ok");

        return pdf;
    }

    public void PlantillaPDFProduce (String path, PDFProduceDto dto, String flag, PDPage page, PDDocument document, int pag)throws Exception{



        document.addPage(page);

        PDFont bold = PDType1Font.HELVETICA_BOLD;
        PDFont font = PDType1Font.HELVETICA;

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        if(flag.equals("X")) {
            String cab = Constantes.RUTA_ARCHIVO_IMPORTAR + "cabecera.PNG";
            PDImageXObject cabecera = PDImageXObject.createFromFile(cab, document);
            contentStream.drawImage(cabecera, 70, 770);
        }

        contentStream.beginText();
        contentStream.setFont(bold, 12);
        contentStream.moveTextPositionByAmount(100, 730);
        contentStream.showText(PDFProduceConstantes.titulo);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 12);
        contentStream.moveTextPositionByAmount(280, 715);
        contentStream.showText(PDFProduceConstantes.titulo2);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(70, 680);
        contentStream.showText(PDFProduceConstantes.subtitulo);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(70, 650);
        contentStream.showText(PDFProduceConstantes.armadorORespresentante);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 10);
        contentStream.moveTextPositionByAmount(240, 650);
        contentStream.showText(dto.getArmadorORespresentante());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(210, 649);
        contentStream.showText("__________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(70, 630);
        contentStream.showText(PDFProduceConstantes.documentoIdentidad);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 10);
        contentStream.moveTextPositionByAmount(210, 630);
        contentStream.showText(dto.getDocumentoIdentidad());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(190, 629);
        contentStream.showText("__________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(70, 600);
        contentStream.showText(PDFProduceConstantes.aCotinuacion);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(70, 570);
        contentStream.showText(PDFProduceConstantes.tipoEP);
        contentStream.endText();

        drawCuadroProduceEscala(contentStream,585,155);
        drawCuadroProduceEscalaX(contentStream,585,155);

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(160, 570);
        contentStream.showText(PDFProduceConstantes.mayorEscala);
        contentStream.endText();

        drawCuadroProduceEscala(contentStream,585,245);

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(250, 570);
        contentStream.showText(PDFProduceConstantes.menorEscala);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(70, 540);
        contentStream.showText(PDFProduceConstantes.informacionEmbPesq);
        contentStream.endText();

        drawCuadroProduceEmbPesquera(contentStream, 525, 70);

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(80, 510);
        contentStream.showText(PDFProduceConstantes.nombreEP);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 10);
        contentStream.moveTextPositionByAmount(170, 510);
        contentStream.showText(dto.getNombreEP());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(150, 509);
        contentStream.showText("_____________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(310, 510);
        contentStream.showText(PDFProduceConstantes.matriculaEP);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 10);
        contentStream.moveTextPositionByAmount(390, 510);
        contentStream.showText(dto.getMatriculaEP());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(380, 509);
        contentStream.showText("___________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(80, 490);
        contentStream.drawString(PDFProduceConstantes.permisoPesca);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 10);
        contentStream.moveTextPositionByAmount(210, 490);
        contentStream.drawString(dto.getPermisoPesca());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(185, 489);
        contentStream.drawString("______________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(80, 470);
        contentStream.drawString(PDFProduceConstantes.capacidadBodegaM3);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 10);
        contentStream.moveTextPositionByAmount(215, 470);
        contentStream.drawString(dto.getCapacidadBodegaM3());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(215, 469);
        contentStream.drawString("________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(280, 470);
        contentStream.drawString(PDFProduceConstantes.capacidadBodegaTM);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 10);
        contentStream.moveTextPositionByAmount(310, 470);
        contentStream.drawString(dto.getCapacidadBodegaTM());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(310, 469);
        contentStream.drawString("__________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(370, 470);
        contentStream.drawString(PDFProduceConstantes.nroTripulantes);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 10);
        contentStream.moveTextPositionByAmount(460, 470);
        contentStream.drawString(dto.getNroTripulantes());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(450, 469);
        contentStream.drawString("______");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(70, 430);
        contentStream.drawString(PDFProduceConstantes.informacionDesembarque);
        contentStream.endText();

        drawCuadroProduceDesembarque(contentStream, 420,70);

        drawCuadroProduceMuelleDPA(contentStream,415,90);

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(100, 400);
        contentStream.drawString(PDFProduceConstantes.Muelle);
        contentStream.endText();

        drawCuadroProduceMuelleDPA(contentStream,415,155);

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(170, 400);
        contentStream.drawString(PDFProduceConstantes.DPA);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(230, 400);
        contentStream.drawString(PDFProduceConstantes.fecha);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 10);
        contentStream.moveTextPositionByAmount(270, 400);
        contentStream.drawString(dto.getFecha());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(270, 399);
        contentStream.drawString("____________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(370, 400);
        contentStream.drawString(PDFProduceConstantes.hora);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 10);
        contentStream.moveTextPositionByAmount(410, 400);
        contentStream.drawString(dto.getHora());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(410, 399);
        contentStream.drawString("_____________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(80, 370);
        contentStream.drawString(PDFProduceConstantes.nombre);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 10);
        contentStream.moveTextPositionByAmount(230, 370);
        contentStream.drawString(dto.getNombre());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(130, 369);
        contentStream.drawString("________________________________________________________________");
        contentStream.endText();

        drawCuadroProduceDesembarque2(contentStream,355,70);

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(80, 340);
        contentStream.drawString(PDFProduceConstantes.inicioDescarga);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 10);
        contentStream.moveTextPositionByAmount(200, 340);
        contentStream.drawString(dto.getInicioDescarga());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(180, 339);
        contentStream.drawString("_______________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(330, 340);
        contentStream.drawString(PDFProduceConstantes.finDescarga);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 10);
        contentStream.moveTextPositionByAmount(430, 340);
        contentStream.drawString(dto.getFinDescarga());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(410, 339);
        contentStream.drawString("_____________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(80, 320);
        contentStream.drawString(PDFProduceConstantes.TMDescargadas);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 10);
        contentStream.moveTextPositionByAmount(200, 320);
        contentStream.drawString(dto.getTMDescargadas());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(180, 319);
        contentStream.drawString("_______________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(80, 300);
        contentStream.drawString(PDFProduceConstantes.comercianteRecibeProd);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 10);
        contentStream.moveTextPositionByAmount(290, 300);
        contentStream.drawString(dto.getComercianteRecibeProd());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(290, 299);
        contentStream.drawString("___________________________________");
        contentStream.endText();

        int y=280;

        for(int i=0; i<2; i++){

            contentStream.beginText();
            contentStream.setFont(font, 10);
            contentStream.moveTextPositionByAmount(80, y);
            contentStream.drawString(PDFProduceConstantes.guiaRemision);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(215, y);
            contentStream.drawString("------");
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 10);
            contentStream.moveTextPositionByAmount(180, y-1);
            contentStream.drawString("_____________________");
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 10);
            contentStream.moveTextPositionByAmount(320, y);
            contentStream.drawString(PDFProduceConstantes.vehiculoPlaca);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(430, y);
            contentStream.drawString("------");
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 10);
            contentStream.moveTextPositionByAmount(400, y-1);
            contentStream.drawString("_______________");
            contentStream.endText();

            y-=20;
        }

        drawCuadroProduceDesembarque3(contentStream,245,70);

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(80, 230);
        contentStream.drawString(PDFProduceConstantes.TMDescarte);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 10);
        contentStream.moveTextPositionByAmount(220, 230);
        contentStream.drawString("------");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(170, 229);
        contentStream.drawString("_______________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(80, 210);
        contentStream.drawString(PDFProduceConstantes.comercianteRecibeDescarte);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 10);
        contentStream.moveTextPositionByAmount(390, 210);
        contentStream.drawString("------");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(300, 209);
        contentStream.drawString("_________________________________");
        contentStream.endText();

        y=190;

        for(int i=0; i<2; i++){

            contentStream.beginText();
            contentStream.setFont(font, 10);
            contentStream.moveTextPositionByAmount(80, y);
            contentStream.drawString(PDFProduceConstantes.guiaRemision);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(215, y);
            contentStream.drawString("------");
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 10);
            contentStream.moveTextPositionByAmount(180, y-1);
            contentStream.drawString("_____________________");
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 10);
            contentStream.moveTextPositionByAmount(320, y);
            contentStream.drawString(PDFProduceConstantes.vehiculoPlaca);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(bold, 10);
            contentStream.moveTextPositionByAmount(430, y);
            contentStream.drawString("------");
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 10);
            contentStream.moveTextPositionByAmount(400, y-1);
            contentStream.drawString("_______________");
            contentStream.endText();

            y-=20;
        }


        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(70, 140);
        contentStream.drawString(PDFProduceConstantes.Nota);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(70, 80);
        contentStream.drawString("______________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(75, 70);
        contentStream.drawString(PDFProduceConstantes.firma);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(550, 30);
        contentStream.drawString(String.valueOf(pag));
        contentStream.endText();

        contentStream.close();

    }
    public void drawCuadroProduceEmbPesquera( PDPageContentStream contentStream, float y, float margin)throws IOException{

        final int rows = 1;
        final int cols = 1;

        final float tableWidth = 440f;
        final float tableHeight = 65;

        //draw the rows
        float nexty = y ;
        for (int i = 0; i <= rows; i++) {
            contentStream.moveTo(margin, nexty);
            contentStream.lineTo(margin + tableWidth, nexty);
            contentStream.stroke();
            nexty -= tableHeight;

        }


        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols; i++) {


            contentStream.moveTo(nextx, y);
            contentStream.lineTo(nextx, y - tableHeight);
            contentStream.stroke();
            nextx+=tableWidth;
        }


    }
    public void drawCuadroProduceDesembarque( PDPageContentStream contentStream, float y, float margin)throws IOException{

        final int rows = 1;
        final int cols = 1;

        final float tableWidth = 440f;
        final float tableHeight = 60;

        //draw the rows
        float nexty = y ;
        for (int i = 0; i <= rows; i++) {
            contentStream.moveTo(margin, nexty);
            contentStream.lineTo(margin + tableWidth, nexty);
            contentStream.stroke();
            nexty -= tableHeight;

        }


        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols; i++) {


            contentStream.moveTo(nextx, y);
            contentStream.lineTo(nextx, y - tableHeight);
            contentStream.stroke();
            nextx+=tableWidth;
        }


    }
    public void drawCuadroProduceDesembarque2( PDPageContentStream contentStream, float y, float margin)throws IOException{

        final int rows = 1;
        final int cols = 1;

        final float tableWidth = 440f;
        final float tableHeight =105;

        //draw the rows
        float nexty = y ;
        for (int i = 0; i <= rows; i++) {
            contentStream.moveTo(margin, nexty);
            contentStream.lineTo(margin + tableWidth, nexty);
            contentStream.stroke();
            nexty -= tableHeight;

        }


        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols; i++) {


            contentStream.moveTo(nextx, y);
            contentStream.lineTo(nextx, y - tableHeight);
            contentStream.stroke();
            nextx+=tableWidth;
        }


    }
    public void drawCuadroProduceDesembarque3( PDPageContentStream contentStream, float y, float margin)throws IOException{

        final int rows = 1;
        final int cols = 1;

        final float tableWidth = 440f;
        final float tableHeight = 90;

        //draw the rows
        float nexty = y ;
        for (int i = 0; i <= rows; i++) {
            contentStream.moveTo(margin, nexty);
            contentStream.lineTo(margin + tableWidth, nexty);
            contentStream.stroke();
            nexty -= tableHeight;

        }


        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols; i++) {


            contentStream.moveTo(nextx, y);
            contentStream.lineTo(nextx, y - tableHeight);
            contentStream.stroke();
            nextx+=tableWidth;
        }


    }
    public void drawCuadroProduceMuelleDPA( PDPageContentStream contentStream, float y, float margin)throws IOException{

        final int rows = 1;
        final int cols = 1;

        final float tableWidth = 50f;
        final float tableHeight = 20;

        //draw the rows
        float nexty = y ;
        for (int i = 0; i <= rows; i++) {
            contentStream.moveTo(margin, nexty);
            contentStream.lineTo(margin + tableWidth, nexty);
            contentStream.stroke();
            nexty -= tableHeight;

        }


        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols; i++) {


            contentStream.moveTo(nextx, y);
            contentStream.lineTo(nextx, y - tableHeight);
            contentStream.stroke();
            nextx+=tableWidth;
        }


    }
    public void drawCuadroProduceEscala( PDPageContentStream contentStream, float y, float margin)throws IOException{

        final int rows = 1;
        final int cols = 1;

        final float tableWidth = 75f;
        final float tableHeight = 25;

        //draw the rows
        float nexty = y ;
        for (int i = 0; i <= rows; i++) {
            contentStream.moveTo(margin, nexty);
            contentStream.lineTo(margin + tableWidth, nexty);
            contentStream.stroke();
            nexty -= tableHeight;

        }


        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols; i++) {


            contentStream.moveTo(nextx, y);
            contentStream.lineTo(nextx, y - tableHeight);
            contentStream.stroke();
            nextx+=tableWidth;
        }


    }
    public void drawCuadroProduceEscalaX( PDPageContentStream contentStream, float y, float margin)throws IOException{

        final int rows = 1;
        final int cols = 1;

        final float tableWidth = 75f;
        final float tableHeight = 25;

        //draw the rows

        for (int i = 0; i < rows; i++) {
            contentStream.moveTo(margin, y);
            contentStream.lineTo(margin + tableWidth, y-tableHeight);
            contentStream.stroke();


        }

        for (int i = 0; i < rows; i++) {
            contentStream.moveTo(margin+tableWidth, y);
            contentStream.lineTo(margin, y-tableHeight);
            contentStream.stroke();
            y -= tableHeight;

        }


    }


    public PDFExports GenerarPDFProduceResumen(PDFProduceImports imports)throws Exception{

        PDFExports pdf= new PDFExports();
        String path = Constantes.RUTA_ARCHIVO_IMPORTAR + "Archivo.pdf";
        PDFProduceResumenDto dto= new PDFProduceResumenDto();

        ImpresFormatosProduceExports ifpe=jcoImpresFormatosProduce.ImpresionFormatosProduce(imports.getImpresFormatosProduceImports());

        HashMap<String, Object>rsprce=ifpe.getT_rsprce().get(0);

        for (Map.Entry<String,Object>entry:rsprce.entrySet()) {
            String key= entry.getKey();
            Object value=entry.getValue();

            if(key.equals("NMEMB")){
                dto.setEmbarcacion(value.toString());
            }else if(key.equals("MREMB")){
                dto.setMatricula(value.toString());
            }else if(key.equals("CNPDS")){
                dto.setPescaDescargada(value.toString());
            }else if(key.equals("CNDES")){
                dto.setCantidadDescargada(value.toString());
            }

        }

        HashMap<String, Object>dtprce=ifpe.getT_dtprce().get(0);
        for (Map.Entry<String,Object>entry:dtprce.entrySet()) {
            String key= entry.getKey();
            Object value=entry.getValue();

            if(key.equals("FIDES")){
                dto.setFechaInicio(value.toString());
            }else if(key.equals("FFDES")) {
                dto.setFechaFin(value.toString());
            }
        }



        String [][]resumen={{"1",dto.getEmbarcacion(),dto.getMatricula(),dto.getPescaDescargada(),dto.getCantidadDescargada()},
                {"0", "TOTAL:","",dto.getPescaDescargada(), dto.getCantidadDescargada()}};





        Metodos exec = new Metodos();
        pdf.setBase64(exec.ConvertirABase64(path));
        pdf.setMensaje("Ok");

        return pdf;
    }

    public void PlantillaPDFProduceResumen(String path, PDFProduceResumenDto dto) throws Exception{

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDFont bold = PDType1Font.HELVETICA_BOLD;
        PDFont font = PDType1Font.HELVETICA;

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.beginText();
        contentStream.setFont(bold, 12);
        contentStream.moveTextPositionByAmount(180, 780);
        contentStream.showText("Resumen de Impresión Formatos PRODUCE");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 11);
        contentStream.moveTextPositionByAmount(220, 760);
        contentStream.showText("Del");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 11);
        contentStream.moveTextPositionByAmount(240, 760);
        contentStream.showText(dto.getFechaInicio());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 11);
        contentStream.moveTextPositionByAmount(320, 760);
        contentStream.showText("Al");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 11);
        contentStream.moveTextPositionByAmount(350, 760);
        contentStream.showText(dto.getFechaFin());
        contentStream.endText();

        drawCuadroProduceResumen(contentStream, 740,85, dto);

        contentStream.close();
        document.save(path);
        document.close();

    }

    public void drawCuadroProduceResumen( PDPageContentStream contentStream, float y, float margin, PDFProduceResumenDto dto)throws IOException{

        final int rows = 4;
        final int cols = 6;

        final float tableWidth = 420f;
        final float tableHeight = 45;
        logger.error("drawtable_1");
        //draw the rows
        float nexty = y ;
        for (int i = 0; i < rows; i++) {
            contentStream.moveTo(margin, nexty);
            contentStream.lineTo(margin + tableWidth, nexty);
            contentStream.stroke();
            nexty -= 15;

        }

        logger.error("drawtable_2");
        //draw the columns
        float x = margin;
        for (int i = 0; i < cols; i++) {

            if(i==1){
                x+=30;
            }else if(i==2){
                x+=140;
            }
            else if(i==3){
                x+=90;
            }
            else if(i==4){
                x+=90;
            }
            else if(i==5){
                x+=70;
            }

            contentStream.moveTo(x, y);
            contentStream.lineTo(x, y - tableHeight);
            contentStream.stroke();



        }

        String[] fields={"Embarcación","Matrícula","Pesc. Descargada","Can. Descargada"};
        PDFont font = PDType1Font.HELVETICA;
        logger.error("drawtable_3");
        x=80;
        for(int i=0; i<fields.length; i++){
            logger.error("drawtable_2");
            if(i==0){
                x+=80;

            }
            else if(i==1){
                x+=120;

            }
            else if(i==2){
                x+=80;

            }
            else if(i==3){
                x+=75;

            }
            contentStream.beginText();
            contentStream.setFont(font, 8);
            contentStream.moveTextPositionByAmount(x, y-10);
            contentStream.showText(fields[i]);
            contentStream.endText();
        }

        String[][]resumen={{"1", dto.getEmbarcacion(), dto.getMatricula(), dto.getPescaDescargada(), dto.getCantidadDescargada()},
                {"0", "TOTAL:", "", dto.getPescaDescargada(), dto.getCantidadDescargada()}};

        for(int i=0;i<resumen.length;i++){

            x=90;
            String[]data=resumen[i];
            for (int j=0;j< data.length;j++){

                if(j==0){
                    x=100;

                }
                else if(j==1){
                    x+=30;

                }
                else if(j==2){
                    x+=150;

                }
                else if(j==3){
                    x+=110;

                }
                else if(j==4){
                    x+=90;

                }
                contentStream.beginText();
                contentStream.setFont(font, 8);
                contentStream.moveTextPositionByAmount(x, y-25);
                contentStream.showText(data[j]);
                contentStream.endText();
            }
            y-=15;
        }
    }

    public String[][] OrdenarTripulacion(String[][]rolTripulacion)throws Exception{

        String[][]listaOrdenada=new String[rolTripulacion.length][4];

        listaOrdenada[0]=rolTripulacion[0];

        String [] cargos={"CAPITAN DE NAVEGACION","PATRON EP","SEGUNDO PATRON","INGENIERO DE MAQUINAS","MOTORISTA","SEGUNDO MOTORISTA","PANGUERO","WINCHERO","COCINERO", "TRIPULANTE EP",""};


        int icargo=0;
        int con=1;

        for(int x=0;x<cargos.length;x++){

            for(int i=1;i<rolTripulacion.length;i++){

                if(rolTripulacion[i][3].equals(cargos[x])){
                    rolTripulacion[con]=rolTripulacion[i];
                    con++;
                }

            }

        }

        return rolTripulacion;
    }

    public String[][] OrdenarCertificados( List<CertificadoDto> certificadosList, String pdf)throws Exception{

        logger.error("OrdenarCertificados_1");

        String[][]listaOrdenada=new String[certificadosList.size()+1][3];

        String[]cdcer={"0031","0039","0034","0037","0035","0036","0033","0032"};
        String[]cdcer2={"1","9","4","7","5","6","3","2"};

        String[]cabeceras=PDFZarpeConstantes.fieldCertificados;
        logger.error("OrdenarCertificados_2");

        if(pdf.equals("X")){
            return  listaOrdenada= OrdenarTrimestral(certificadosList, cdcer, cdcer2);

        }
        logger.error("OrdenarCertificados_3");

        listaOrdenada[0]=cabeceras;
        int con=1;




        for(int i=0; i<cdcer.length;i++){

            String[] certificados=new String[3];

                for(CertificadoDto dto: certificadosList){



                    if(i==3  && dto.getCDCER().equals(cdcer[i]) || dto.getCDCER().equals(cdcer2[i])){

                        certificados[0]=String.valueOf(con);
                        certificados[1]=dto.getDSCER();
                        certificados[2]=dto.getNRCER();

                        logger.error("OrdenarCertificados i=3 : "+ dto.getDSCER()+" "+dto.getNRCER());
                        listaOrdenada[con]=certificados;
                        con++;
                    }
                    if(i!=3 && dto.getCDCER().equals(cdcer[i]) || dto.getCDCER().equals(cdcer2[i])){

                        certificados[0]=String.valueOf(con);
                        certificados[1]=dto.getDSCER();
                        certificados[2]=dto.getFECCF();
                        /*
                        logger.error("OrdenarCertificados i!=3 : "+ dto.getDSCER()+" "+dto.getNRCER()+" "+dto.getFECCF());
                        try {
                            DateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");
                            Date d = new Date();
                            String date = parseador.format(d);
                            Date fechaActual = parseador.parse(date);
                            Date fecha = parseador.parse(dto.getFECCF());

                            if (fecha.after(fechaActual)) {

                                certificados[2] = dto.getFECCF();
                            } else {
                                certificados[2] = "";
                            }
                        }catch (Exception e){
                            certificados[2]="";
                        }*/

                        listaOrdenada[con]=certificados;
                        con++;
                     }



                }




        }

        return listaOrdenada;
    }

    public String[][] OrdenarTrimestral(List<CertificadoDto> certificadosList, String[] cdcer, String[] cdcer2)throws Exception{
        String[][]listaOrdenada=new String[certificadosList.size()+1][3];
        String[]cabeceras=PDFTrimestralConstantes.certificadosCabecera;

        listaOrdenada[0]=cabeceras;
        int con=1;
        for(int i=0; i<cdcer.length;i++){

            String[] certificados=new String[2];

            for(CertificadoDto dto: certificadosList){



                if(i==3  && dto.getCDCER().equals(cdcer[i]) || dto.getCDCER().equals(cdcer2[i])){

                    certificados[0]=dto.getDSCER();
                    certificados[1]=dto.getNRCER();

                    logger.error("ARQ. listaOrdenada["+con+"]: "+certificados);
                    listaOrdenada[con]=certificados;
                    con++;
                }
                if(i!=3 && dto.getCDCER().equals(cdcer[i]) || dto.getCDCER().equals(cdcer2[i])){

                    certificados[0]=dto.getDSCER();
                    certificados[1]=dto.getFECCF();
                    /*
                    logger.error("listaOrdenada["+con+"]: "+certificados);


                    DateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");
                    Date d = new Date();
                    String date=parseador.format(d);
                    Date fechaActual = parseador.parse(date);
                    Date fecha=parseador.parse(dto.getFECCF());

                    if(fecha.after(fechaActual)){
                        certificados[1]=fecha.toString();
                    }else{
                        certificados[1]="";
                    }
*/
                    listaOrdenada[con]=certificados;
                    con++;
                }



            }




        }

        return listaOrdenada;
    }
}
