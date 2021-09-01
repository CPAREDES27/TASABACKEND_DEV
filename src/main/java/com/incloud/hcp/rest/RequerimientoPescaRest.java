package com.incloud.hcp.rest;

import com.incloud.hcp.jco.requerimientopesca.dto.ReqPescaDto;
import com.incloud.hcp.jco.requerimientopesca.dto.ReqPescaOptions;
import com.incloud.hcp.jco.requerimientopesca.service.JCORequerimientoPescaService;
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
@RequestMapping(value = "/api/requerimientopesca")
public class RequerimientoPescaRest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JCORequerimientoPescaService jcoRequerimientoPescaService;

    @PostMapping(value = "/listar", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReqPescaDto> Listar(@RequestBody ReqPescaOptions imports) {
        //Parametro dto = new Parametro();

        try {
            return Optional.ofNullable(this.jcoRequerimientoPescaService.ListarRequerimientoPesca(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/registrar", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReqPescaDto> Registrar(@RequestBody ReqPescaOptions imports) {
        //Parametro dto = new Parametro();

        try {
            return Optional.ofNullable(this.jcoRequerimientoPescaService.RegistrarRequerimientoPesca(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }
}
