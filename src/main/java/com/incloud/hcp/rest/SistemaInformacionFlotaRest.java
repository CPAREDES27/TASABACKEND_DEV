package com.incloud.hcp.rest;

import com.incloud.hcp.jco.sistemainformacionflota.dto.*;
import com.incloud.hcp.jco.sistemainformacionflota.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping(value = "/api/sistemainformacionflota")
public class SistemaInformacionFlotaRest {

    @Autowired
    private JCOPescaDeclaradaService jcoPescaDeclaradaService;
    @Autowired
    private JCOPescaPorEmbarcacionService jcoPescaPorEmbarcacionService;
    @Autowired
    private JCOPescaDescargadaService jcoPescaDescargadaService;
    @Autowired
    private JCOPescaCompetenciaService jcoPescaCompetenciaService;
    @Autowired
    private JCOCompraCuotaTercerosService jcoCompraCuotaTercerosService;

    @PostMapping(value = "/PescaDeclarada", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PescaDeclaradaExports> PescaDeclarada(@RequestBody PescaDeclaradaImports imports) {

        try {
            return Optional.ofNullable(this.jcoPescaDeclaradaService.PescaDeclarada(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/PescaPorEmbarcacion", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PescaPorEmbarcaExports> PescaPorEmbarcacion(@RequestBody PescaPorEmbarcaImports imports) {

        try {
            return Optional.ofNullable(this.jcoPescaPorEmbarcacionService.PescaPorEmbarcacion(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/PescaDeclaradaDiara", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PescaDeclaradaDiariaExports> PescaDeclaradaDiaria(@RequestBody PescaDeclaradaDiariaImports imports) {

        try {
            return Optional.ofNullable(this.jcoPescaDeclaradaService.PescaDeclaradaDiaria(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/PescaDeclaradaDife", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PescaDeclaradaDifeExports> PescaDeclaradaDife(@RequestBody PescaDeclaradaDifeImports imports) {

        try {
            return Optional.ofNullable(this.jcoPescaDeclaradaService.PescaDeclaradaDife(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/PescaDescargada", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PescaDescargadaExports> PescaDescargada(@RequestBody PescaDescargadaImports imports) {

        try {
            return Optional.ofNullable(this.jcoPescaDescargadaService.PescaDescargada(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/PescaDescargadaDiaResum", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PescaDescargadaDiaResumExports> PescaDescargadaDiaResum(@RequestBody PescaDescargadaDiaResumImports imports) {

        try {
            return Optional.ofNullable(this.jcoPescaDescargadaService.PescaDescargadaDiaResum(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/PescaCompetenciaRadial", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PescaCompetenciaRadialExports> PescaCompetenciaRadial(@RequestBody PescaCompetenciaRadialImports imports) {

        try {
            return Optional.ofNullable(this.jcoPescaCompetenciaService.PescaCompetenciaRadial(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/CompraCuotaTerceros", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CompraCuotaTercerosExports> CompraCuotaTerceros(@RequestBody CompraCuotaTercerosImports imports) {

        try {
            return Optional.ofNullable(this.jcoCompraCuotaTercerosService.CompraCuotaTerceros(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/PescaCompetenciaProduce", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PescaCompetenciaProduceExports> PescaCompetenciaProduce(@RequestBody PescaCompetenciaProduceImports imports) {

        try {
            return Optional.ofNullable(this.jcoPescaCompetenciaService.PescaCompetenciaProduce(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }
}
