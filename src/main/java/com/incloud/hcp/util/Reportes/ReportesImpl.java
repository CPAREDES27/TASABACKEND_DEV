package com.incloud.hcp.util.Reportes;
import  java.io.*;
import java.nio.charset.StandardCharsets;

import com.incloud.hcp.util.Constantes;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import  org.apache.poi.hssf.usermodel.HSSFSheet;
import  org.apache.poi.hssf.usermodel.HSSFWorkbook;
import  org.apache.poi.hssf.usermodel.HSSFRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReportesImpl implements ReportesService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    public ReportesExports ExportarReporteExcel()throws Exception{

      ReportesExports re= new ReportesExports();


      try {
          String filename = Constantes.RUTA_ARCHIVO_IMPORTAR+ "Reporte.xlsx" ;
          HSSFWorkbook workbook = new HSSFWorkbook();
          HSSFSheet sheet = workbook.createSheet("Libro1");

          HSSFRow rowhead = sheet.createRow((short)0);
          rowhead.createCell(0).setCellValue("No.");
          rowhead.createCell(1).setCellValue("Name");
          rowhead.createCell(2).setCellValue("Address");
          rowhead.createCell(3).setCellValue("Email");

          HSSFRow row = sheet.createRow((short)1);
          row.createCell(0).setCellValue("1");
          row.createCell(1).setCellValue("Sankumarsingh");
          row.createCell(2).setCellValue("India");
          row.createCell(3).setCellValue("sankumarsingh@gmail.com");
          FileOutputStream fileOut = new FileOutputStream(filename);
          workbook.write(fileOut);
          fileOut.close();
          workbook.close();
          String Base64=CrearBase64(filename);
          re.setBase64(Base64);
          re.setMensaje("ok");

      } catch ( Exception e ) {
          re.setMensaje(e.getMessage());
      }
      return re;
  }

  public String CrearBase64(String fileName) throws IOException {
      File file = new File(fileName);
      byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
      return new String(encoded, StandardCharsets.US_ASCII);
  }

}
