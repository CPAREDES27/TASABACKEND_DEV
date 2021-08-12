package com.incloud.hcp.rest;

import com.incloud.hcp.jco.maestro.dto.STR_SETDto;
import com.incloud.hcp.jco.maestro.service.JCOHistoricoCompetenciaService;
import com.incloud.hcp.util.Mensaje;
import com.incloud.hcp.util.Tablas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/historicocompetencia")
public class HistoricoCompetenciaRest {

    @Autowired
    private JCOHistoricoCompetenciaService jcoHistoricoCompetenciaService;

    @PostMapping(value = "/Editar_nuevo", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Mensaje> EditarCaptanques(@RequestBody STR_SETDto str_setDto) {
        //Parametro dto = new Parametro();

        try {
            return Optional.ofNullable(this.jcoHistoricoCompetenciaService.EditarHistoricoCompetencia(str_setDto, Tablas.ZTFL_HISCOM))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }
}
