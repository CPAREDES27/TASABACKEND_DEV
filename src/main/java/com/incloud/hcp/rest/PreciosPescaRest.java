package com.incloud.hcp.rest;

import com.incloud.hcp.jco.preciospesca.dto.PrecioPescaExports;
import com.incloud.hcp.jco.preciospesca.dto.PrecioPescaImports;
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

    @PostMapping(value = "/Buscar", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PrecioPescaExports> ConsultarPreciosPesca(@RequestBody PrecioPescaImports imports) {
        try {
            return Optional.ofNullable(this.jcoPoliticaPreciosService.ObtenerPrecioPesca(imports)).map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }
}
