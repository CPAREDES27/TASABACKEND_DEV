package com.incloud.hcp.jco.tripulantes.service.impl;

import com.incloud.hcp.jco.tripulantes.dto.*;
import com.incloud.hcp.jco.tripulantes.service.JCOReporObservaTripuService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import com.incloud.hcp.util.Tablas;
import com.sap.conn.jco.*;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;
import org.apache.poi.ss.formula.functions.T;
//import org.omg.CORBA.ObjectHelper;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JCOReporObservaTripuImpl implements JCOReporObservaTripuService {

    @Override
    public ReporObservaTripuExports ReporteObservacionesTripulantes(ReporObservaTripuImports imports) throws Exception {

        ReporObservaTripuExports rot=new ReporObservaTripuExports();

        try {

            JCoDestination destination = JCoDestinationManager.getDestination(Constantes.DESTINATION_NAME);
            JCoRepository repo = destination.getRepository();
            JCoFunction stfcConnection = repo.getFunction(Constantes.ZFL_RFC_REG_SALUD_TRIP);

            JCoParameterList importx = stfcConnection.getImportParameterList();
            importx.setValue("IP_TOPE", imports.getIp_tope());
            importx.setValue("IP_PERNR", imports.getIp_pernr());
            importx.setValue("IP_VORNA", imports.getIp_vorna());
            importx.setValue("IP_NACHN", imports.getIp_nachn());
            importx.setValue("IP_NACH2", imports.getIp_nach2());
            importx.setValue("IP_INDAT", imports.getIp_indat());
            importx.setValue("IP_CDEMB", imports.getIp_cdemb());
            importx.setValue("IP_CDGFL", imports.getIp_cdgfl());


            JCoParameterList tables = stfcConnection.getTableParameterList();
            JCoParameterList export = stfcConnection.getExportParameterList();
            stfcConnection.execute(destination);

            JCoTable T_TRIOBS = tables.getTable(Tablas.T_TRIOBS);
            JCoStructure T_MENSAJE = export.getStructure(Tablas.T_MENSAJE);

            Metodos metodo = new Metodos();
            List<HashMap<String, Object>>  t_triobs = metodo.ObtenerListObjetos(T_TRIOBS, imports.getFieldst_triobs());

            List<HashMap<String, Object>> t_mensaje=new ArrayList<HashMap<String, Object>>();
            HashMap<String, Object> record=new HashMap<>();
            for(int i=0; i<T_MENSAJE.getFieldCount();i++){
                record.put(T_MENSAJE.getField(i).toString(), T_MENSAJE.getValue(i).toString());
            }

            rot.setT_triobs(t_triobs);
            rot.setT_mensaje(t_mensaje);
            rot.setMensaje("Ok");

        }catch (Exception e){
            rot.setMensaje(e.getMessage());
        }

        return rot;
    }

    public PDFExports PDFReporteObsTripu(ReporObservaTripuImports imports)throws Exception{

        PDFExports pdf=new PDFExports();
        try {

            String path = Constantes.RUTA_ARCHIVO_IMPORTAR + "Archivo.pdf";

            ReporObservaTripuExports re=  ReporteObservacionesTripulantes(imports);


            List<String[]> content=new ArrayList<>();
            String [] cabeceras={"Código","Nombres","Apellido Paterno","Apellido Materno","E/P","Flota","Fecha Obs."};
            content.add(cabeceras);

            List<PDFReporteObsTripuDto> listRepor= new ArrayList<>();
            for(int i=0; i<re.getT_triobs().size();i++){

                HashMap<String, Object> t_triobs=re.getT_triobs().get(i);

                PDFReporteObsTripuDto dto= new PDFReporteObsTripuDto();

                for(Map.Entry<String, Object> entry: t_triobs.entrySet()){
                    String key= entry.getKey();
                    String valor=entry.getValue().toString();

                    if(key.equals("PERNR")){
                        dto.setCodigo(valor);
                    }else if(key.equals("VORNA")){
                        dto.setNombre(valor);
                    }else if(key.equals("NACHN")){
                        dto.setApellidoPaterno(valor);
                    }else if(key.equals("NACH2")){
                        dto.setApellidoMaterno(valor);
                    }else if(key.equals("NMEMB")){
                        dto.setEp(valor);
                    }else if(key.equals("DSGFL")){
                        dto.setFlota(valor);
                    }else if(key.equals("FEOBS")){
                        dto.setFecha(valor);
                    }

                }
                listRepor.add(dto);

            }

            for (PDFReporteObsTripuDto dto : listRepor) {
                String[]reg=new String[7];
                reg[0]=dto.getCodigo();
                reg[1]=dto.getNombre();
                reg[2]=dto.getApellidoPaterno();
                reg[3]=dto.getApellidoMaterno();
                reg[4]=dto.getEp();
                reg[5]=dto.getFlota();
                reg[6]=dto.getFecha();
                content.add(reg);
            }


            PlantillaPDFReporteObsTripu(path, content);

            Metodos exec = new Metodos();
            pdf.setBase64(exec.ConvertirABase64(path));
            pdf.setMensaje("Ok");

        }catch (Exception e){
            pdf.setMensaje(e.getMessage());
        }

        return  pdf;
    }

    public void PlantillaPDFReporteObsTripu(String path, List<String[]> content)throws Exception{

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);


        document.addPage(page);

        PDFont bold = PDType1Font.HELVETICA_BOLD;

        PDPageContentStream contentStream = new PDPageContentStream(document, page);


        contentStream.beginText();
        contentStream.setFont(bold, 11);
        contentStream.moveTextPositionByAmount(185, 790);
        contentStream.drawString("REPORTE DE OBSERVACIÓN DE TRIPULANTES");
        contentStream.endText();

        drawCuadroDetalle(page,contentStream, 40, 750,530,content);

        contentStream.close();

        if(content.size()>20){
            page = new PDPage(PDRectangle.A4);
            page.setRotation(90);
            document.addPage(page);

            contentStream = new PDPageContentStream(document, page);

            drawCuadroDetalle2(page,contentStream, 40, 770,755,content);

        }

        contentStream.close();
        document.save(path);
        document.close();
    }

    public  int drawCuadroDetalle(PDPage page, PDPageContentStream contentStream,
                                  int x, int y, int ancho,List<String[]> content) throws IOException {

        int cantidadRegistros;
        if(content.size()<=20){
            cantidadRegistros= content.size();
        }else{
            cantidadRegistros=20;
        }
        int rows=cantidadRegistros;
        final int cols = content.get(0).length;
        float rowHeight = 15.0f;
        final float tableHeight =(15 * (float) rows);


        //draw the rows
        float nexty = y ;
        for (int i = 0; i <= rows; i++) {


            contentStream.moveTo(x, nexty);
            contentStream.lineTo(x + ancho, nexty);
            contentStream.stroke();
            nexty -= rowHeight;

        }





        //draw the columns
        float nextx = x;
        for (int i = 0; i <= cols+1; i++) {

            if(i==1 ){
                nextx+=55;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
            }else if(i==2){
                nextx+=97f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
            }
            else if(i==3){
                nextx+=97;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
            }
            else if(i==4){
                nextx+=97f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if(i==5){
                nextx+=62f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if(i==6){
                nextx+=62f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if(i==7 ){
                nextx+=60f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if(i==0) {
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }

        }


        //now add the text
        PDFont font;


        int texty=y-10;
        for(int i=0; i<cantidadRegistros; i++) {

            String[]fields=content.get(i);
            float textx=x+5;

            for (int j = 0; j < fields.length; j++) {

                switch (j) {
                    case 0:
                        textx = 45;
                        break;
                    case 1:
                        textx = 105;
                        break;
                    case 2:
                        textx=205;
                        break;
                    case 3:
                        textx = 300;
                        break;
                    case 4:
                        textx = 395;
                        break;
                    case 5:
                        textx = 455;
                        break;
                    case 6:
                        textx = 515;
                        break;


                }
                float tam;
                if(i==0 ){
                    font=PDType1Font.HELVETICA_BOLD;
                    tam=9f;
                }else{
                    font=PDType1Font.HELVETICA;
                    tam=7.5f;
                }



                    contentStream.beginText();
                    contentStream.setFont(font, tam);
                    contentStream.newLineAtOffset(textx, texty);
                    contentStream.showText(fields[j]);
                    contentStream.endText();


            }


                texty -= 15;

        }



        return texty;
    }
    public  int drawCuadroDetalle2(PDPage page, PDPageContentStream contentStream,
                                   int x, int y, int ancho,List<String[]> content) throws IOException {


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
}
