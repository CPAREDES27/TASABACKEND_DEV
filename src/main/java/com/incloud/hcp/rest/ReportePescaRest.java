package com.incloud.hcp.rest;

import com.incloud.hcp.jco.reportepesca.dto.*;
import com.incloud.hcp.jco.reportepesca.service.JCOCDHPMService;
import com.incloud.hcp.jco.reportepesca.service.JCOCalasService;
import com.incloud.hcp.jco.reportepesca.service.JCODescargasService;
import com.incloud.hcp.jco.reportepesca.service.JCOMareasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping(value = "/api/reportepesca")
public class ReportePescaRest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JCOMareasService jcoMareasService;

    @Autowired
    private JCODescargasService jcoDescargasService;

    @Autowired
    private JCOCalasService jcoCalasService;

    @Autowired
    private JCOCDHPMService jcocdhpmService;

    @PostMapping(value = "/ConsultarMareas/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MareaExports> ConsultarMareas(@RequestBody MareaImports imports) {

        try {
            return Optional.ofNullable(this.jcoMareasService.ObtenerMareas(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(value = "/ConsultarPescaDescargada", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DescargasExports> ConsultarPescaDescargada(@RequestBody DescargasImports imports) {
        try {
            return Optional.ofNullable(this.jcoDescargasService.ObtenerDescargas(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }

    @PostMapping(value = "/ConsultarCalas", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CalaExports> ConsultarCalas(@RequestBody CalaImports imports) {
        try {
            return Optional.ofNullable(this.jcoCalasService.ObtenerCalas(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }

    @PostMapping(value = "/ConsultarBiomasa", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BiomasaExports> ConsultarBiomasa(@RequestBody BiomasaImports imports) {
        try {
            return Optional.ofNullable(this.jcoCalasService.ConsultarBiomasa(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }

    @PostMapping(value = "/ReporteTDC_CHD", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CHDPMExports> ConsultarCDHPM(@RequestBody CHDPMImports imports) {
        try {
            return Optional.ofNullable(this.jcocdhpmService.ObtenerCHDPM(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }
}
