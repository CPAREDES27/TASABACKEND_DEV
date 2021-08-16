package com.incloud.hcp.rest;

import com.incloud.hcp.jco.maestro.dto.STR_SETDto;
import com.incloud.hcp.jco.maestro.service.JCOCapacidadTanquesService;
import com.incloud.hcp.util.Constantes;
import com.incloud.hcp.util.Mensaje;
import com.incloud.hcp.util.Tablas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/capacidadtanques")
public class CapacidadTanqueRest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JCOCapacidadTanquesService jcoCapacidadTanquesService;

    @PostMapping(value = "/Editar_Nuevo", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Mensaje> EditarCaptanques(@RequestBody STR_SETDto str_setDto) {
        //Parametro dto = new Parametro();

        try {
            return Optional.ofNullable(this.jcoCapacidadTanquesService.EditarCaptanques(str_setDto, Tablas.ZFLEMB))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }
}
