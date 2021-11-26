package com.incloud.hcp.rest;

import com.incloud.hcp.jco.controlLogistico.dto.InfoEventoImports;
import com.incloud.hcp.jco.controlLogistico.dto.InfoHorometrosAveriadosImport;
import com.incloud.hcp.util.Mail.Dto.CorreoDto;
import com.incloud.hcp.util.Mail.Service.CorreoService;
import com.incloud.hcp.util.Mail.Dto.NotifDescTolvasDto;
import com.incloud.hcp.util.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/correo")
public class CorreoRest {

    @Autowired
    private CorreoService correoService;


/*
    @PostMapping(value = "/EnviarCorreoConAdjunto", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Mensaje> Enviar(@RequestBody CorreoConAdjuntoDto imports) {
        //Parametro dto = new Parametro();

        try {
            return Optional.ofNullable(this.correoService.EnviarCorreoConAdjunto(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }*/

    @PostMapping(value = "/EnvioCorreos", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Mensaje> Enviar(@RequestBody CorreoDto correo) {

        try {
            return Optional.ofNullable(this.correoService.EnviarCorreo(correo))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/EnviarNotifDescTolvas", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Mensaje> EnviarNotifDescTolvas(@RequestBody NotifDescTolvasDto imports) {

        try {
            return Optional.ofNullable(this.correoService.EnviarNotifDescTolvas(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/InformarHorometroAveriado", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Mensaje> InformarHorometroAveriado(@RequestBody NotifDescTolvasDto imports) {

        try {
            return Optional.ofNullable(this.correoService.EnviarNotifDescTolvas(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/EnviarCorreosSiniestro", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Mensaje> EnviarCorreosSiniestro(@RequestBody InfoEventoImports imports) {

        try {
            return Optional.ofNullable(this.correoService.EnviarCorreosSiniestro(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/EnviarInfoHorometroAveriado", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Mensaje> EnviarInfoHorometroAveriado(@RequestBody InfoHorometrosAveriadosImport imports) {

        try {
            return Optional.ofNullable(this.correoService.EnviarInfoHorometroAveriado(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

}
