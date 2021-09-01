package com.incloud.hcp.rest;


import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.maestro.service.JCOCampoTablaService;
import com.incloud.hcp.jco.maestro.service.JCOMaestrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/General")
public class GeneralRest {

    @Autowired
    private JCOMaestrosService MaestroService;

    @Autowired
    private JCOCampoTablaService jcoCampoTablaService;

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

    @PostMapping(value = "/AppMaestros/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AppMaestrosExports> AppMaestros(@RequestBody AppMaestrosImports imports){

        try {
            return Optional.ofNullable(this.MaestroService.appMaestros(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(value = "/Update_Camp_Table/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CampoTablaExports> ActualizarCampoTabla(@RequestBody CampoTablaImports imports){

        try {
            return Optional.ofNullable(this.jcoCampoTablaService.Actualizar(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }
}
