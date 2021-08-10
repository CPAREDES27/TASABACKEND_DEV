package com.incloud.hcp.rest;

import com.incloud.hcp.jco.maestro.dto.BombasDto;
import com.incloud.hcp.jco.maestro.dto.CargosDto;
import com.incloud.hcp.jco.maestro.service.JCOCargosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/cargos")
public class CargoRest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JCOCargosService jcoCargosService;

    @GetMapping(value = "/ListarCargos", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CargosDto>> ListarCargos() {
        //Parametro dto = new Parametro();

        try {
            return Optional.ofNullable(this.jcoCargosService.ListarCargos())
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }
}
