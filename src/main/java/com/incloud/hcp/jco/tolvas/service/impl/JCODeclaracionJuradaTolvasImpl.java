package com.incloud.hcp.jco.tolvas.service.impl;

import com.incloud.hcp.jco.dominios.dto.*;
import com.incloud.hcp.jco.dominios.service.JCODominiosService;
import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.maestro.dto.MaestroOptions;
import com.incloud.hcp.jco.maestro.dto.MaestroOptionsKey;
import com.incloud.hcp.jco.tolvas.dto.CentroDto;
import com.incloud.hcp.jco.tolvas.dto.DeclaracionJuradaExports;
import com.incloud.hcp.jco.tolvas.dto.DeclaracionJuradaImports;
import com.incloud.hcp.jco.tolvas.service.JCODeclaracionJuradaTolvasService;
import com.incloud.hcp.jco.tripulantes.dto.PDFExports;
import com.incloud.hcp.jco.tripulantes.dto.PDFTrabajoFFConstantes;
import com.incloud.hcp.jco.tripulantes.dto.PDFTrabajoFFDto;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

            exports.setTitulo("DECLARACION JURADA DIARIA DE REPORTE DE PESAJE POR TOLVA O BALANZA");
            exports.setSubtitulo("R.M. Nº 153 - 2001 - PE");
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
            imports.put("P_ORDER", "");
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






    public PDFExports PlantillaPDF()throws Exception{
        PDFExports pdf= new PDFExports();
        String path = Constantes.RUTA_ARCHIVO_IMPORTAR + "Archivo.pdf";
        PDFTrabajoFFDto dto= new PDFTrabajoFFDto();
        PlantillaPDFDeclaracion(path, dto);
        Metodos exec = new Metodos();
        pdf.setBase64(exec.ConvertirABase64(path));
        pdf.setMensaje("Ok");

        return  pdf;
    }

    public void PlantillaPDFDeclaracion(String path, PDFTrabajoFFDto dto)throws IOException {

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
        contentStream.moveTextPositionByAmount(300, 520);
        contentStream.drawString(PDFTrabajoFFConstantes.titulo);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 13);
        contentStream.moveTextPositionByAmount(300, 520);
        contentStream.drawString("___________________________________");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(60, 450);
        contentStream.drawString(PDFTrabajoFFConstantes.numeroTrabajo);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(160, 450);
        contentStream.drawString(dto.getNumeroTrabajo());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(60, 435);
        contentStream.drawString(PDFTrabajoFFConstantes.tipoTrabajo);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(160, 435);
        contentStream.drawString(dto.getTipoTrabajo());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(60, 420);
        contentStream.drawString(PDFTrabajoFFConstantes.semana);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(160, 420);
        contentStream.drawString(dto.getSemana());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(60, 405);
        contentStream.drawString(PDFTrabajoFFConstantes.fechaInicio);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(160, 405);
        contentStream.drawString(dto.getFechaInicio());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(210, 405);
        contentStream.drawString(PDFTrabajoFFConstantes.fechaFin);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(260, 405);
        contentStream.drawString(dto.getFechaFin());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(60, 390);
        contentStream.drawString(PDFTrabajoFFConstantes.descripcionTrabajo);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(160, 390);
        contentStream.drawString(dto.getDescripcionTrabajo());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(60, 375);
        contentStream.drawString(PDFTrabajoFFConstantes.observacion);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(bold, 8);
        contentStream.moveTextPositionByAmount(160, 375);
        contentStream.drawString(dto.getObservacion());
        contentStream.endText();

        // drawTableCertificadosTrimestral(page, contentStream,535, 50, certificados);

        contentStream.close();
        document.save(path);
        document.close();
    }


}