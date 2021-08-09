package com.incloud.hcp.rest;

import com.incloud.hcp.jco.maestro.dto.BalanzaDto;
import com.incloud.hcp.jco.maestro.dto.UnidadMedidaDto;
import com.incloud.hcp.jco.maestro.service.JCOBalanzasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/Balanza")
public class BalanzaRest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JCOBalanzasService jcoBalanzasService;

    @GetMapping(value = "/ListarUnidadMedida/{condicion}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UnidadMedidaDto>> ListarUnidadMedida(@PathVariable String condicion ) {
        //Parametro dto = new Parametro();

        try {
            return Optional.ofNullable(this.jcoBalanzasService.ListarUnidadMedida(condicion))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }

    @GetMapping(value = "/ListarBalanzas", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BalanzaDto>> ListaBalanzas() {
        //Parametro dto = new Parametro();

        try {
            return Optional.ofNullable(this.jcoBalanzasService.ListarBalanzas())
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }
}
