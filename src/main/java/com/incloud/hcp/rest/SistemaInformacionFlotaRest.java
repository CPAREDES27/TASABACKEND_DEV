package com.incloud.hcp.rest;

import com.incloud.hcp.jco.requerimientopesca.dto.ReqPescaDto;
import com.incloud.hcp.jco.requerimientopesca.dto.ReqPescaOptions;
import com.incloud.hcp.jco.sistemainformacionflota.dto.*;
import com.incloud.hcp.jco.sistemainformacionflota.service.JCOPescaDeclaradaService;
import com.incloud.hcp.jco.sistemainformacionflota.service.JCOPescaPorEmbarcacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/sistemainformacionflota")
public class SistemaInformacionFlotaRest {

    @Autowired
    JCOPescaDeclaradaService jcoPescaDeclaradaService;
    @Autowired
    JCOPescaPorEmbarcacionService jcoPescaPorEmbarcacionService;

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
}
