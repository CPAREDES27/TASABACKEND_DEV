package com.incloud.hcp.jco.tolvas.service.impl;

import com.incloud.hcp.jco.dominios.dto.*;
import com.incloud.hcp.jco.dominios.service.JCODominiosService;
import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.jco.tolvas.dto.*;
import com.incloud.hcp.jco.tolvas.service.JCODeclaracionJuradaTolvasService;
import com.incloud.hcp.jco.tripulantes.dto.PDFExports;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.EjecutarRFC;
import com.incloud.hcp.util.Metodos;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class JCODeclaracionJuradaTolvasImpl implements JCODeclaracionJuradaTolvasService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JCODominiosService jcoDominiosService;

    public DeclaracionJuradaExports DeclaracionJuradaTolvas(DeclaracionJuradaImports imports)throws Exception{
        logger.error("DeclaracionJuradaTolvas_1 ");
        DeclaracionJuradaExports exports= new DeclaracionJuradaExports();

        try {

            CentroDto  centro= ObtenerCentroYRazonSocial(imports.getCentro());
            List<HashMap<String, Object>> detalle= ObtenerDetalle(imports.getCentro(), imports.getFecha());
            String ubicacionPlanta=ObtenerUbicacionPlanta(imports.getCentro());
            List<DominioExportsData> leyendaEspecies=ObtenerLeyendaEspecies();
            List<DominioExportsData> leyendaDestino=ObtenerLeyendaDestino();

            exports.setTitulo(PDFDeclaracionJuradaConstantes.titulo);
            exports.setSubtitulo(PDFDeclaracionJuradaConstantes.subtitlo);
            exports.setCentro(centro.getCentro());
            exports.setRazonSocial(centro.getRazonSocial());
            exports.setDetalle(detalle);
            exports.setUbicacionPlanta(ubicacionPlanta);
            exports.setEspecies(leyendaEspecies);
            exports.setDestino(leyendaDestino);
            exports.setMensaje("Ok");


        }catch (Exception e){
           exports.setMensaje(e.getMessage());
        }

        return exports;
    }


    public CentroDto ObtenerCentroYRazonSocial(String centro)throws Exception{

        CentroDto centroDto=new CentroDto();
        MaestroExport me;

        try {

            //setear mapeo de parametros import
            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("QUERY_TABLE", "ZV_FLPA");
            imports.put("DELIMITER", "|");
            imports.put("NO_DATA", "");
            imports.put("ROWSKIPS", "");
            imports.put("ROWCOUNT", "0");
            imports.put("P_USER", "FGARCIA");
            imports.put("P_ORDER", "");
            //setear mapeo de tabla options

            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();

            String []fields={"WERKS", "DESCR", "CDEMP", "NAME1", "MANDT"};


            List<MaestroOptions> options= new ArrayList<>();

            MaestroOptions mo=new MaestroOptions();
            mo.setWa("WERKS = '"+centro+"'");
            options.add(mo);
            MaestroOptions mo2=new MaestroOptions();
            mo2.setWa("AND INPRP EQ 'P'");
            options.add(mo2);

            List<MaestroOptionsKey>optionsKeys=new ArrayList<>();

            Metodos metodo=new Metodos();

            tmpOptions=metodo.ValidarOptions(options, optionsKeys);

            EjecutarRFC exec = new EjecutarRFC();
            me = exec.Execute_ZFL_RFC_READ_TABLE(imports, tmpOptions, fields);
            logger.error("mensaje: "+me.getMensaje());

            logger.error("DECLARACION JURADA me.getData().size(): "+me.getData().size());


            for(int i=0; i<me.getData().size();i++){

                logger.error("DECLARACION JURADA me.getData().size(): "+me.getData().get(0).size());
                for (Map.Entry<String, Object> entry :me.getData().get(i).entrySet() ) {
                    String key=entry.getKey();
                    String valor=entry.getValue().toString();
                    logger.error("DECLARACION JURADA key: "+key+" valor: "+valor);
                    if(key.equals("DESCR")){
                        centroDto.setCentro(valor);
                    }else if(key.equals("NAME1")){
                        centroDto.setRazonSocial(valor);

                    }

                }
            }


        }catch (Exception e){
             e.getMessage();
        }
        logger.error("DECLARACION JURADA final");


        return centroDto;
    }
    public List<HashMap<String,Object>> ObtenerDetalle(String centro, String fecha)throws Exception{

        MaestroExport me;
        List<HashMap<String, Object>> detalle=new ArrayList<>();
        try {

            //setear mapeo de parametros import
            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("QUERY_TABLE", "ZV_FLDJ");
            imports.put("DELIMITER", "|");
            imports.put("NO_DATA", "");
            imports.put("ROWSKIPS", "");
            imports.put("ROWCOUNT", "0");
            imports.put("P_USER", "FGARCIA");
            imports.put("P_ORDER", "INBAL ASCENDING");
            //setear mapeo de tabla options

            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();

            String []fields={"INBAL", "TICKE", "CDEMB", "NMEMB", "MREMB", "CDSPC", "DSSPC", "CNTOL", "CNPDS", "PESACUMOD", "FIDES", "HIDES", "FFDES", "HFDES"};


            List<MaestroOptions> options= new ArrayList<>();

            MaestroOptions mo=new MaestroOptions();
            mo.setWa("WEPTA = '"+centro+"'");
            options.add(mo);
            MaestroOptions mo2=new MaestroOptions();
            mo2.setWa("AND (FIDES LIKE '"+fecha+"')");
            options.add(mo2);

            List<MaestroOptionsKey>optionsKeys=new ArrayList<>();

            Metodos metodo=new Metodos();

            tmpOptions=metodo.ValidarOptions(options, optionsKeys);

            EjecutarRFC exec = new EjecutarRFC();
            me = exec.Execute_ZFL_RFC_READ_TABLE(imports, tmpOptions, fields);
            detalle=me.getData();


        }catch (Exception e){
            e.getMessage();
        }
        logger.error("DECLARACION JURADA final");


        return detalle;
    }
    public String ObtenerUbicacionPlanta(String centro)throws Exception{

        String ubicacion="";
        MaestroExport me;

        try {

            //setear mapeo de parametros import
            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("QUERY_TABLE", "T001W");
            imports.put("DELIMITER", "|");
            imports.put("NO_DATA", "");
            imports.put("ROWSKIPS", "");
            imports.put("ROWCOUNT", "0");
            imports.put("P_USER", "FGARCIA");
            imports.put("P_ORDER", "");
            //setear mapeo de tabla options

            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();

            String []fields={"STRAS"};

            List<MaestroOptions> options= new ArrayList<>();

            MaestroOptions mo=new MaestroOptions();
            mo.setWa("WERKS = '"+centro+"'");
            options.add(mo);

            List<MaestroOptionsKey>optionsKeys=new ArrayList<>();

            Metodos metodo=new Metodos();

            tmpOptions=metodo.ValidarOptions(options, optionsKeys);

            EjecutarRFC exec = new EjecutarRFC();
            me = exec.Execute_ZFL_RFC_READ_TABLE(imports, tmpOptions, fields);

            for(int i=0; i<me.getData().size();i++){

                for (Map.Entry<String, Object> entry :me.getData().get(i).entrySet() ) {
                    String key=entry.getKey();
                    ubicacion=entry.getValue().toString();


                }
            }


        }catch (Exception e){
            e.getMessage();
        }

        return ubicacion;
    }
    public List<DominioExportsData> ObtenerLeyendaEspecies()throws Exception{

        List<DominioExportsData> especies=new ArrayList<>();

        try {
            DominioParams dominioParams = new DominioParams();
            dominioParams.setDomname("ZDO_ESPECIES");
            dominioParams.setStatus("A");


            List<DominioParams> ListDominioParams = new ArrayList<>();
            ListDominioParams.add(dominioParams);


            DominiosImports dominiosImports = new DominiosImports();
            dominiosImports.setDominios(ListDominioParams);


            DominioDto dominioDto = jcoDominiosService.Listar(dominiosImports);

            List<DominiosExports> ListaDomExports = dominioDto.getData();

            for (int i = 0; i < ListaDomExports.size(); i++) {

                DominiosExports dominiosExports = ListaDomExports.get(i);
                especies = dominiosExports.getData();


            }
        }catch (Exception e){
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
        }


        return especies;
    }
    public List<DominioExportsData> ObtenerLeyendaDestino()throws Exception{

        List<DominioExportsData> especies=new ArrayList<>();

        try {
            DominioParams dominioParams = new DominioParams();
            dominioParams.setDomname("ZDO_DESTINO");
            dominioParams.setStatus("A");


            List<DominioParams> ListDominioParams = new ArrayList<>();
            ListDominioParams.add(dominioParams);


            DominiosImports dominiosImports = new DominiosImports();
            dominiosImports.setDominios(ListDominioParams);


            DominioDto dominioDto = jcoDominiosService.Listar(dominiosImports);

            List<DominiosExports> ListaDomExports = dominioDto.getData();

            for (int i = 0; i < ListaDomExports.size(); i++) {

                DominiosExports dominiosExports = ListaDomExports.get(i);
                especies = dominiosExports.getData();


            }
        }catch (Exception e){
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
        }


        return especies;
    }




    public PDFExports PlantillaPDF(DeclaracionJuradaImports imports)throws Exception{
        PDFExports pdf= new PDFExports();
        String path = Constantes.RUTA_ARCHIVO_IMPORTAR + "Archivo.pdf";

        DeclaracionJuradaExports exports=DeclaracionJuradaTolvas(imports);

        Metodos me=new Metodos();



        PDFDeclaracionJuradaDetallaDto detalle=new PDFDeclaracionJuradaDetallaDto();
        List<String[]>ListDetalle= new ArrayList<>();

        ListDetalle.add(PDFDeclaracionJuradaConstantes.Detalle);
        ListDetalle.add(PDFDeclaracionJuradaConstantes.Detalle2);

        float total=0f;
        for (int i=0;i<exports.getDetalle().size(); i++){

            String[]det= new String[9];
            for (Map.Entry<String, Object> entry :exports.getDetalle().get(i).entrySet()) {
                String key=entry.getKey();
                String valor=entry.getValue().toString();

                if(key.equals("INBAL")){
                    detalle.setTolva(valor);
                    detalle.setBalanza(valor);
                    det[0]=detalle.getBalanza();
                }else if(key.equals("FIDES")){
                    String fecha=ConvertirFecha(valor);
                    detalle.setFecha(fecha);

                }else if(key.equals("TICKE")){
                    detalle.setReporte(valor);
                    det[1]=detalle.getReporte();
                }else if(key.equals("NMEMB")){
                    detalle.setNombreEmbarca(valor);
                    det[2]=detalle.getNombreEmbarca();

                }else if(key.equals("MREMB")){
                    detalle.setMatricula(valor);
                    det[3]=detalle.getMatricula();

                }else if(key.equals("CNTOL")){
                    detalle.setCantidad(valor);
                    det[6]=detalle.getCantidad();

                }else if(key.equals("PESACUMOD")){
                    detalle.setPesoAcumulado(valor);
                    det[7]=detalle.getPesoAcumulado();
                    total+=Float.parseFloat(valor);

                }else if(key.equals("HIDES")){
                    String hora=Convertirhora(valor);
                    detalle.setHoraInicio(hora);
                    det[8]=detalle.getHoraInicio();
                }else if(key.equals("HFDES")){
                    String hora=Convertirhora(valor);
                    detalle.getHoraFin();
                    detalle.setHoraFin(hora);
                }else if(key.equals("DSSPC")){
                    detalle.setEspecie(valor);
                }

            }
            det[4]=ObtenerCodEspecie();
            det[5]=ObtenerCodDestino();
            ListDetalle.add(det);
        }



        //Agregando las cabeceras



        PlantillaPDFDeclaracion(path, exports,detalle, ListDetalle, total);
        Metodos exec = new Metodos();
        pdf.setBase64(exec.ConvertirABase64(path));
        pdf.setMensaje("Ok");

        return  pdf;
    }

    public PDFExports PlantillaPDF2(DeclaracionJurada2Imports imports2)throws Exception{
        PDFExports pdf= new PDFExports();
        String path = Constantes.RUTA_ARCHIVO_IMPORTAR + "Archivo.pdf";
        DeclaracionJuradaImports imports=new DeclaracionJuradaImports();
        imports.setCentro(imports2.getCentro());
        imports.setFecha(imports2.getFecha());

        DeclaracionJuradaExports exports = DeclaracionJuradaTolvas(imports);

        Metodos me=new Metodos();



        PDFDeclaracionJuradaDetallaDto detalle=new PDFDeclaracionJuradaDetallaDto();
        List<String[]>ListDetalle= new ArrayList<>();

        ListDetalle.add(PDFDeclaracionJuradaConstantes.Detalle);
        ListDetalle.add(PDFDeclaracionJuradaConstantes.Detalle2);

        float total=0f;

        // Alistar la lista de descargas
        int indexLast = imports2.getDescargas().size() - 1;
        imports2.getDescargas().remove(indexLast);

        // Indicar los valores de la lista de descargas

        for (int i=0;i<imports2.getDescargas().size(); i++){

            String[]det= new String[9];
            HashMap<String, Object> descarga = imports2.getDescargas().get(i);
            for (Map.Entry<String, Object> entry : descarga.entrySet()) {
                String key=entry.getKey();
                String valor = entry.getValue().toString();

                if(key.equals("INBAL")){
                    detalle.setTolva(valor);
                    detalle.setBalanza(valor);
                    det[0]=detalle.getBalanza();
                }else if(key.equals("FIDES")){
                    String fecha=ConvertirFecha(valor);
                    detalle.setFecha(fecha);
                }else if(key.equals("TICKE")){
                    detalle.setReporte(valor);
                    det[1]=detalle.getReporte();
                }else if(key.equals("NMEMB")){
                    detalle.setNombreEmbarca(valor);
                    det[2]=detalle.getNombreEmbarca();

                }else if(key.equals("MREMB")){
                    detalle.setMatricula(valor);
                    det[3]=detalle.getMatricula();

                }else if(key.equals("CNTOL")){
                    detalle.setCantidad(valor);
                    det[6]=detalle.getCantidad();

                }else if(key.equals("PESACUMOD")){
                    detalle.setPesoAcumulado(valor);
                    det[7]=detalle.getPesoAcumulado();
                    total+=Float.parseFloat(valor);

                }else if(key.equals("HIDES")){
                    String hora=Convertirhora(valor);
                    detalle.setHoraInicio(hora);
                    det[8]=detalle.getHoraInicio();
                }else if(key.equals("HFDES")){
                    String hora=Convertirhora(valor);
                    detalle.getHoraFin();
                    detalle.setHoraFin(hora);
                }else if(key.equals("DSSPC")){
                    detalle.setEspecie(valor);
                }

            }
            det[4]=ObtenerCodEspecie();
            det[5]=ObtenerCodDestino();
            ListDetalle.add(det);
        }

        //Adicionando los campos que no vienen de las tablas
        exports.setUbicacionPlanta(imports2.getUbicacion());
        exports.setObservacion(imports2.getObservacion());
        detalle.setTolva(imports2.getTolva());
        detalle.setBalanza(imports2.getTolva());



        //Agregando las cabeceras


        PlantillaPDFDeclaracion(path, exports,detalle, ListDetalle, total);
        Metodos exec = new Metodos();
        pdf.setBase64(exec.ConvertirABase64(path));
        pdf.setMensaje("Ok");

        return  pdf;
    }

    public void PlantillaPDFDeclaracion(String path, DeclaracionJuradaExports dto, PDFDeclaracionJuradaDetallaDto detalle, List<String[]> content, float total)throws IOException {

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        page.setRotation(90);
        document.addPage(page);

        PDFont font = PDType1Font.HELVETICA;
        PDFont bold = PDType1Font.HELVETICA_BOLD;

        PDRectangle pageSize = page.getMediaBox();
        float pageWidth = pageSize.getWidth();
        PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.OVERWRITE, false);

        contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));

        drawCuadro(contentStream,35,580,15,750);

        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.moveTextPositionByAmount(250, 570);
        contentStream.drawString(PDFDeclaracionJuradaConstantes.titulo);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(370, 555);
        contentStream.drawString(PDFDeclaracionJuradaConstantes.subtitlo);
        contentStream.endText();

        drawCuadro(contentStream,35,550,15,125);

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(40, 540);
        contentStream.drawString(PDFDeclaracionJuradaConstantes.RazonSocial);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(160, 536);
        contentStream.drawString("____________________________________________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(170, 540);
        contentStream.drawString(dto.getRazonSocial());
        contentStream.endText();

        drawCuadro(contentStream,35,530,15,125);

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(40, 520);
        contentStream.drawString(PDFDeclaracionJuradaConstantes.UbicacionPlanta);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(160, 516);
        contentStream.drawString("____________________________________________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(170, 520);
        contentStream.drawString(dto.getUbicacionPlanta());
        contentStream.endText();

        drawCuadro(contentStream,35,510,15,125);

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(40, 500);
        contentStream.drawString(PDFDeclaracionJuradaConstantes.Planta);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(160, 496);
        contentStream.drawString("_________________________________________________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(170, 500);
        contentStream.drawString(dto.getCentro());
        contentStream.endText();


        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(570, 502);
        contentStream.drawString(PDFDeclaracionJuradaConstantes.Tolva);
        contentStream.endText();

        drawCuadro(contentStream,635,514,20,65);

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(640, 502);
        contentStream.drawString(detalle.getTolva());
        contentStream.endText();


        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(570, 482);
        contentStream.drawString(PDFDeclaracionJuradaConstantes.Balanza);
        contentStream.endText();

        drawCuadro(contentStream,635,494,20,65);

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(640, 482);
        contentStream.drawString(detalle.getBalanza());
        contentStream.endText();


        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(715, 482);
        contentStream.drawString(PDFDeclaracionJuradaConstantes.DiaMesAño);
        contentStream.endText();

        drawCuadro(contentStream,700,494,20,80);


        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(640, 462);
        contentStream.drawString(PDFDeclaracionJuradaConstantes.Fecha);
        contentStream.endText();

        drawCuadro(contentStream,700,474,20,80);

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(720, 462);
        contentStream.drawString(detalle.getFecha());
        contentStream.endText();

        int txty=drawCuadroDetalle(page, contentStream, 40,440,745, content, total);

        contentStream.beginText();
        contentStream.setFont(bold, 7);
        contentStream.moveTextPositionByAmount(40, txty-20);
        contentStream.drawString(PDFDeclaracionJuradaConstantes.Observaciones);
        contentStream.endText();

        drawCuadro(contentStream, 160, txty-15, 30, 525);

        contentStream.beginText();
        contentStream.setFont(font, 8);
        contentStream.moveTextPositionByAmount(170, txty-27);
        contentStream.drawString(dto.getObservacion());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 6);
        contentStream.moveTextPositionByAmount(40, txty-60);
        contentStream.drawString(PDFDeclaracionJuradaConstantes.Especies);
        contentStream.endText();

        int y =txty-75;
        for(int i=0; i<dto.getEspecies().size();i++){

            DominioExportsData d=dto.getEspecies().get(i);

            contentStream.beginText();
            contentStream.setFont(bold, 6);
            contentStream.moveTextPositionByAmount(40, y);
            contentStream.drawString(d.getId() + " "+d.getDescripcion());
            contentStream.endText();
            y-=8;
        }
        contentStream.beginText();
        contentStream.setFont(bold, 6);
        contentStream.moveTextPositionByAmount(40, y-10);
        contentStream.drawString(PDFDeclaracionJuradaConstantes.Especificar);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 6);
        contentStream.moveTextPositionByAmount(230, txty-60);
        contentStream.drawString(PDFDeclaracionJuradaConstantes.Destino);
        contentStream.endText();

        y=txty-75;
        for(int i=0; i<dto.getDestino().size();i++){

            DominioExportsData d=dto.getDestino().get(i);

            contentStream.beginText();
            contentStream.setFont(bold, 6);
            contentStream.moveTextPositionByAmount(230, y);
            contentStream.drawString(d.getId() + " "+d.getDescripcion());
            contentStream.endText();
            y-=8;
        }
        // drawTableCertificadosTrimestral(page, contentStream,535, 50, certificados);

        contentStream.close();
        document.save(path);
        document.close();
    }

    public void drawCuadro( PDPageContentStream contentStream, float x, float y, int alto, int ancho)throws IOException{

        final int rows = 1;
        final int cols = 1;


        //draw the rows
        float nexty = y ;
        for (int i = 0; i <= rows; i++) {
            contentStream.moveTo(x, nexty);
            contentStream.lineTo(x + ancho, nexty);
            contentStream.stroke();
            nexty -= alto;

        }


        //draw the columns
        float nextx = x;
        for (int i = 0; i <= cols; i++) {


            contentStream.moveTo(nextx, y);
            contentStream.lineTo(nextx, y - alto);
            contentStream.stroke();
            nextx+=ancho;
        }


    }

    public  int drawCuadroDetalle(PDPage page, PDPageContentStream contentStream,
                                   int x, int y, int ancho,List<String[]> content, float total) throws IOException {

        logger.error("drawTableRolTripulacion");
        final int rows = content.size();
        final int cols = content.get(0).length;
        float rowHeight = 30.0f;
        final float tableHeight =15 * (float) rows;


        //draw the rows
        float nexty = y ;
        for (int i = 0; i < rows; i++) {

            if(i!=0){
                rowHeight = 15.0f;
            }
            contentStream.moveTo(x, nexty);
            contentStream.lineTo(x + ancho, nexty);
            contentStream.stroke();
            nexty -= rowHeight;

        }
        contentStream.moveTo(x, nexty);
        contentStream.lineTo(x + ancho-100, nexty);
        contentStream.stroke();




        //draw the columns
        float nextx = x;
        for (int i = 0; i <= cols+2; i++) {

            if(i==1 ){
                nextx+=50;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
            }else if(i==2){
                nextx+=70f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
            }
            else if(i==3){
                nextx+=205f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();
            }
            else if(i==4){
                nextx+=70f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if(i==5){
                nextx+=50f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if(i==6){
                nextx+=70f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if(i==7 ){
                nextx+=70f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight-15);
                contentStream.stroke();

            }else if(i==8){
                nextx+=60f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight-15);
                contentStream.stroke();

            }else if( i==9){
                nextx+=50f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if( i==10){
                nextx+=50f;
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }else if(i==0) {
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight-15);
                contentStream.stroke();

            }else {
                contentStream.moveTo(nextx, y);
                contentStream.lineTo(nextx, y - tableHeight);
                contentStream.stroke();

            }

        }


        //now add the text
        PDFont font;


        int texty=y-15;
        for(int i=0; i<content.size(); i++) {

            String[]fields=content.get(i);
            float textx=x+5;

            for (int j = 0; j < fields.length; j++) {

                switch (j) {
                    case 0:
                        if(i==0){
                            textx = 55;
                        }else if(i==1){
                            textx = 50;
                        }else {
                            textx = 65;
                        }
                        break;
                    case 1:
                        if(i==0){
                            textx = 110;
                        }else if(i==1){
                        textx = 120;
                         }else {
                            textx = 110;
                        }
                        break;
                    case 2:
                        if(i==0){
                            textx=240;
                        }else if(i==1){
                            textx = 235;
                        }else {
                            textx = 170;
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
                            textx = 445;
                        }else if(i==1){
                            textx = 460;
                        }else {
                            textx = 460;
                        }
                        break;
                    case 5:
                        if(i==0){
                            textx = 500;
                        }else if(i==1){
                            textx = 500;
                        }else {
                            textx = 520;
                        }
                        break;
                    case 6:
                        if(i==0){
                            textx = 570;
                        }else if(i==1){
                            textx = 575;
                        }else {
                            textx = 590;
                        }
                        break;
                    case 7:
                        if(i==0){
                            textx = 645;
                        }else if(i==1){
                            textx = 635;
                        }else {
                            textx = 650;
                        }
                        break;
                    case 8:
                        if(i==0){
                            textx = 700;
                        }else if(i==1){
                            textx = 700;
                        }else {
                            textx = 700;
                        }
                        break;
                    case 9:
                        if(i==0){
                            textx = 750;
                        }else if(i==1){
                            textx = 745;
                        }else {
                            textx = 740;
                        }
                        break;
                    case 10:
                        if(i==0){
                            textx = 770;
                        }else if(i==1){
                            textx = 730;
                        }else {
                            textx = 730;
                        }
                        break;

                }
                if(i==0 || i==1){
                    font=PDType1Font.HELVETICA_BOLD;
                }else{
                    font=PDType1Font.HELVETICA;
                }

                contentStream.beginText();
                contentStream.setFont(font, 6.5f);
                contentStream.newLineAtOffset(textx, texty);
                contentStream.showText(fields[j]);
                contentStream.endText();


            }

            if(i==0){
                texty-=10;
            }else {
                texty -= 15;
            }
        }

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6.5f);
        contentStream.newLineAtOffset(50, texty);
        contentStream.showText("TOTAL:");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6.5f);
        contentStream.newLineAtOffset(650, texty);
        contentStream.showText(String.valueOf(total));
        contentStream.endText();

        return texty;
    }

    public String ObtenerCodEspecie()throws Exception{
        String codEspecie="";

        EjecutarRFC exec =new EjecutarRFC();
        MaestroExport me;
        try{
            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("QUERY_TABLE", "ZFLCNS");
            imports.put("DELIMITER", "|");
            imports.put("NO_DATA", "");
            imports.put("ROWSKIPS", "");
            imports.put("ROWCOUNT", "");
            imports.put("P_USER", "FGARCIA");
            //setear mapeo de tabla options


            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();

            HashMap<String, Object> record = new HashMap<String, Object>();
            record.put("WA", "CDCNS = '81'");
            tmpOptions.add(record);

            String[]fields={"VAL01"};

            //ejecutar RFC ZFL_RFC_READ_TABLE
            me = exec.Execute_ZFL_RFC_READ_TABLE(imports, tmpOptions, fields);

            for(Map.Entry<String, Object> entry:me.getData().get(0).entrySet()){
                codEspecie= entry.getValue().toString();

            }

        }catch (Exception e){

        }

        return codEspecie;
    }

    public String ObtenerCodDestino()throws Exception{
        String codEspecie="";

        EjecutarRFC exec =new EjecutarRFC();
        MaestroExport me;
        try{
            HashMap<String, Object> imports = new HashMap<String, Object>();
            imports.put("QUERY_TABLE", "ZFLCNS");
            imports.put("DELIMITER", "|");
            imports.put("NO_DATA", "");
            imports.put("ROWSKIPS", "");
            imports.put("ROWCOUNT", "");
            imports.put("P_USER", "FGARCIA");
            //setear mapeo de tabla options


            List<HashMap<String, Object>> tmpOptions = new ArrayList<HashMap<String, Object>>();

            HashMap<String, Object> record = new HashMap<String, Object>();
            record.put("WA", "CDCNS = '82'");
            tmpOptions.add(record);

            String[]fields={"VAL01"};

            //ejecutar RFC ZFL_RFC_READ_TABLE
            me = exec.Execute_ZFL_RFC_READ_TABLE(imports, tmpOptions, fields);

            for(Map.Entry<String, Object> entry:me.getData().get(0).entrySet()){
                codEspecie= entry.getValue().toString();

            }

        }catch (Exception e){

        }

        return codEspecie;
    }

    public String ConvertirFecha(String valor)throws Exception{
        SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        Date fecha = parseador.parse(valor);
        valor = formateador.format(fecha);
        return  valor;
    }
    public String Convertirhora(String valor)throws Exception{
        SimpleDateFormat parseador = new SimpleDateFormat("HH:mm");
        SimpleDateFormat formateador = new SimpleDateFormat("HH:mm:ss");
        Date hora = parseador.parse(valor);
        valor = formateador.format(hora);
        return  valor;
    }
}
