package com.incloud.hcp.rest;


import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.maestro.service.JCOMaestrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "/api/General")
public class GeneralRest {

    @Autowired
    private JCOMaestrosService MaestroService;

    @PostMapping(value = "/Read_Table/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MaestroExport> ConsultarMaestro(@RequestBody MaestroImports imports){

        try {
            return Optional.ofNullable(this.MaestroService.obtenerMaestro(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(value = "/Update_Table/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MensajeDto> EditarMaestro(@RequestBody MaestroEditImports imports){

        try {
            return Optional.ofNullable(this.MaestroService.editarMaestro(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }

/*
    @PostMapping(value = "/ConsultarEmbarcacion/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmbarcacionDto>> ConsultarEmbarcaciones(@RequestBody EmbarcacionImports imports){

        try {
            return Optional.ofNullable(this.MaestroService.obtenerEmbarcaciones(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }
    @PostMapping(value = "/ConsultarPuntosDescarga/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PuntosDescargaDto>> ConsultarPuntosDescarga(@RequestBody String usuario){

        try {
            return Optional.ofNullable(this.MaestroService.obtenerPuntosDescarga(usuario))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(value = "/ConsultarEmpresa/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<EmpresaDto> ObtenerEmpresa(@RequestBody EmpresaImports imports){

        try {
            return Optional.ofNullable(this.MaestroService.obtenerEmpresa(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }*/


}
