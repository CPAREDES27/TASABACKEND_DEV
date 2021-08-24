package com.incloud.hcp.rest;


import com.incloud.hcp.jco.maestro.dto.CargaArchivoImports;
import com.incloud.hcp.jco.maestro.service.JCOCargaArchivosService;
import com.incloud.hcp.util.Ftp.FtpExports;
import com.incloud.hcp.util.Ftp.FtpImports;
import com.incloud.hcp.util.Ftp.FtpService;
import com.incloud.hcp.util.Mensaje;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/cargaarchivos")
public class CargaArchivosRest {


    @Autowired
    private JCOCargaArchivosService jcoCargaArchivosService;
    @Autowired
    private FtpService ftpService;

    @PostMapping(value = "/CargaArchivo", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Mensaje> CargarArchivos(@RequestBody CargaArchivoImports imports) {

        try {
            return Optional.ofNullable(this.jcoCargaArchivosService.CargaArchivo(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/CargarArchivoFTP", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Mensaje> CargarArchivoFtp(@RequestBody FtpImports imports) {

        try {
            return Optional.ofNullable(this.ftpService.SubirArchivoFtp(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }
    @PostMapping(value = "/DescargarArchivoFTP", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<FtpExports> DescargarArchivoFtp(@RequestBody FtpImports imports) {

        try {
            return Optional.ofNullable(this.ftpService.DescargarArchivoFtp(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }
}
