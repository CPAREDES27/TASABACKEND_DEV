package com.incloud.hcp.rest;

import com.incloud.hcp.jco.maestro.dto.MaestroExport;
import com.incloud.hcp.jco.tolvas.dto.*;
import com.incloud.hcp.jco.tolvas.service.JCOCalcuDerechoPescaService;
import com.incloud.hcp.jco.tolvas.service.JCOIngresoDescManualService;
import com.incloud.hcp.jco.tolvas.service.JCOPescaCompetenciaRService;
import com.incloud.hcp.jco.tolvas.service.JCORegistroTolvasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/tolvas")
public class TolvasRest {

    @Autowired
    private JCORegistroTolvasService jcoRegistroTolvasService;
    @Autowired
    private JCOIngresoDescManualService jcoIngresoDescManualService;
    @Autowired
    private JCOCalcuDerechoPescaService jcoCalcuDerechoPescaService;
    @Autowired
    private JCOPescaCompetenciaRService jcoPescaCompetenciaRService;

    @PostMapping(value = "/registrotolvas_listar", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MaestroExport> Listar(@RequestBody RegistroTolvasImports imports) {

        try {
            return Optional.ofNullable(this.jcoRegistroTolvasService.Guardar(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/ingresodescargamanual_guardar", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<IngresoDesManualExports> Guardar(@RequestBody IngresoDescManualImports imports) {

        try {
            return Optional.ofNullable(this.jcoIngresoDescManualService.Guardar(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }
    @PostMapping(value = "/calculoderechopesca", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CalcuDerechoPescaExports> Guardar(@RequestBody CalcuDerechoPescaImports imports) {

        try {
            return Optional.ofNullable(this.jcoCalcuDerechoPescaService.Listar(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/pescacompetenciaradial", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PescaCompetenciaRExports> pescaCompetenciaR(@RequestBody PescaCompetenciaRImports imports) {

        try {
            return Optional.ofNullable(this.jcoPescaCompetenciaRService.PescaCompetencia(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }
}
