package com.incloud.hcp.rest;

import com.incloud.hcp.jco.maestro.dto.ConfiguracionEventoPescaExports;
import com.incloud.hcp.jco.maestro.dto.EventosPescaEditImports;
import com.incloud.hcp.jco.maestro.dto.EventosPescaExports;
import com.incloud.hcp.jco.maestro.dto.EventosPescaImports;
import com.incloud.hcp.jco.maestro.service.JCOEventosPescaService;
import com.incloud.hcp.util.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/eventospesca")
public class EventosPescaRest {

    @Autowired
    private JCOEventosPescaService jcoEventosPescaService;



    @PostMapping(value = "/Listar/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<EventosPescaExports> ListarEventoPesca(@RequestBody EventosPescaImports imports){

        try {
            return Optional.ofNullable(this.jcoEventosPescaService.ListarEventoPesca(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }
    @PostMapping(value = "/Editar/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Mensaje> EditarEventosPesca(@RequestBody EventosPescaEditImports imports){

        try {
            return Optional.ofNullable(this.jcoEventosPescaService.EditarEventosPesca(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }
    @PostMapping(value = "/ObtenerConfEventosPesca/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ConfiguracionEventoPescaExports> ObtenerConfEventosPesca(){

        try {
            return Optional.ofNullable(this.jcoEventosPescaService.ObtenerConfEventosPesca())
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }
}
