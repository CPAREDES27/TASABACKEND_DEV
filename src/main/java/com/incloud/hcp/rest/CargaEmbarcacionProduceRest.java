package com.incloud.hcp.rest;

import com.incloud.hcp.jco.maestro.dto.CargaEmbProduceExports;
import com.incloud.hcp.jco.maestro.dto.CargaEmbProduceImports;
import com.incloud.hcp.jco.maestro.dto.EventosPesca2Exports;
import com.incloud.hcp.jco.maestro.dto.EventosPesca2Imports;
import com.incloud.hcp.jco.maestro.service.JCOCargaEmbProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping(value = "/api/cargaembarcacionproduce")
public class CargaEmbarcacionProduceRest {

    @Autowired
    private JCOCargaEmbProduceService jcoCargaEmbProduceService;

    @PostMapping(value = "/Carga/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CargaEmbProduceExports> Carga(@RequestBody CargaEmbProduceImports imports) {

        try {
            return Optional.ofNullable(this.jcoCargaEmbProduceService.CargaEmbarcaciones(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }
}
