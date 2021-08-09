package com.incloud.hcp.rest;

import com.incloud.hcp.jco.maestro.dto.BodegaDto;
import com.incloud.hcp.jco.maestro.service.JCOBodegaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/Bodega")
public class BodegaRest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JCOBodegaService jcoBodegaService;

    @PostMapping(value = "/BuscarBodega", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BodegaDto>> BuscarBodegas(@RequestBody BodegaDto bodegaDto) {
        //Parametro dto = new Parametro();

        try {
            return Optional.ofNullable(this.jcoBodegaService.BuscarBodegas(bodegaDto))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }
}
