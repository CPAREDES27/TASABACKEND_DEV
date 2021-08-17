package com.incloud.hcp.rest;

import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.maestro.service.JCOPuntosDescargaService;
import com.incloud.hcp.util.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/puntosdescarga")
public class PuntoDescargaRest {

    @Autowired
    private JCOPuntosDescargaService jcoPuntosDescargaService;



    @PostMapping(value = "/ConsultarPuntosDescarga/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MaestroExport> ListarPuntosDescarga(@RequestBody UsuarioDto imports){

        try {
            return Optional.ofNullable(this.jcoPuntosDescargaService.ListarPuntosDes(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }
}
