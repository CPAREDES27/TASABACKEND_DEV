package com.incloud.hcp.rest;

import com.incloud.hcp.jco.maestro.dto.AlmacenDto;
import com.incloud.hcp.jco.maestro.dto.AlmacenExtDto;
import com.incloud.hcp.jco.maestro.dto.PlantaDto;
import com.incloud.hcp.jco.maestro.service.JCOAlmacenService;
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
@RequestMapping(value = "/api/almacen")
public class AlmacenRest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JCOAlmacenService jcoAlmacenService;

    //@GetMapping(value = "/BuscarPlantas", produces = APPLICATION_JSON_VALUE)
    @GetMapping(value = "/BuscarPlantas/{condicion}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlantaDto>> BuscarPlantas(@PathVariable String condicion ) {
        //Parametro dto = new Parametro();

        try {
            return Optional.ofNullable(this.jcoAlmacenService.BuscarPlantas(condicion))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/BuscarAlmacenExt", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AlmacenExtDto>> BuscarAlmacenExt(AlmacenExtDto  almacenExtDto
                                                                ) {
        //Parametro dto = new Parametro();

        try {
            return Optional.ofNullable(this.jcoAlmacenService.BuscarAlmacenExterno(almacenExtDto))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/BuscarAlmacen", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AlmacenDto>> BuscarAlmacen(AlmacenDto  almacenDto
    ) {
        //Parametro dto = new Parametro();

        try {
            return Optional.ofNullable(this.jcoAlmacenService.BuscarAlmacen(almacenDto))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }
}
