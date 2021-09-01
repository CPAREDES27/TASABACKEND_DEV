package com.incloud.hcp.rest;

import com.incloud.hcp.jco.preciospesca.dto.*;
import com.incloud.hcp.jco.preciospesca.service.JCOBonosService;
import com.incloud.hcp.jco.preciospesca.service.JCOPrecioMarService;
import com.incloud.hcp.jco.preciospesca.service.JCOPreciosPescaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping(value = "/api/preciospesca")
public class PreciosPescaRest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JCOPreciosPescaService jcoPoliticaPreciosService;

    @Autowired
    private JCOPrecioMarService jcoPrecioMarService;

    @Autowired
    private JCOBonosService jcoBonosService;


    @PostMapping(value = "/Leer", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PrecioPescaExports> LectPreciosPesca(@RequestBody PrecioPescaImports imports) {
        try {
            return Optional.ofNullable(this.jcoPoliticaPreciosService.ObtenerPrecioPesca(imports)).map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }

    @PostMapping(value = "/ConsultarProb", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PrecioProbPescaExports> ConsultarProbPreciosPesca(@RequestBody PrecioProbPescaImports imports) {
        try {
            return Optional.ofNullable(this.jcoPoliticaPreciosService.ObtenerPrecioProbPesca(imports)).map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }

    @PostMapping(value = "/Consultar", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ConsPrecioPescaExports> ConsultarPreciosPesca(@RequestBody ConsPrecioPescaImports imports) {
        try {
            return Optional.ofNullable(this.jcoPoliticaPreciosService.ConsultarPrecioPesca(imports)).map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }

    @PostMapping(value = "/Mant", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PrecioPescaMantExports> MantPreciosPesca(@RequestBody PrecioPescaMantImports imports) {
        try {
            return Optional.ofNullable(this.jcoPoliticaPreciosService.MantPrecioPesca(imports)).map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }

    @PostMapping(value = "/ConsultarPrecioMar", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PrecioMarExports> ConsultarPrecioMar(@RequestBody PrecioMarImports imports) {
        try {
            return Optional.ofNullable(this.jcoPrecioMarService.ObtenerPrecioMar(imports)).map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }

    @PostMapping(value = "/AgregarBono", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BonoExport> AgregarBono(@RequestBody BonoImport imports) {
        try {
            return Optional.ofNullable(this.jcoBonosService.AgregarBono(imports)).map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }
}
