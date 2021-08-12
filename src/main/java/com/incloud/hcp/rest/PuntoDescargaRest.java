package com.incloud.hcp.rest;

import com.incloud.hcp.jco.maestro.dto.PuntosDescargaDto;
import com.incloud.hcp.jco.maestro.service.JCOMaestrosService;
import com.incloud.hcp.jco.maestro.service.JCOPuntosDescargaService;
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
@RequestMapping(value = "/api/puntosdescarga")
public class PuntoDescargaRest {

    @Autowired
    private JCOPuntosDescargaService jcoPuntosDescargaService;

    @PostMapping(value = "/ConsultarPuntosDescarga/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PuntosDescargaDto>> ConsultarPuntosDescarga(@RequestBody String usuario){

        try {
            return Optional.ofNullable(this.jcoPuntosDescargaService.obtenerPuntosDescarga(usuario))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }
}
