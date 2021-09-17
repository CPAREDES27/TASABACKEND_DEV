package com.incloud.hcp.rest;

import com.incloud.hcp.jco.tripulantes.dto.*;
import com.incloud.hcp.jco.tripulantes.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/tripulantes")
public class TripulantesRest {

    @Autowired
    private JCORegistroZarpeService jcoRegistroZarpeService;
    @Autowired
    private JCORolTripulacionService jcoRolTripulacionService;
    @Autowired
    private JCOPDFZarpeService JCOPDFZarpeService;
    @Autowired
    private JCOSeguimientoTripuService jcoSeguimientoTripuService;
    @Autowired
    private JCOProtestosService jcoProtestosService;
    @Autowired
    private JCOTrabajoFueraFaenaService jcoTrabajoFueraFaenaService;

    @PostMapping(value = "/RegistroZarpe/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrosZarpeExports> RegistroZarpe(@RequestBody RegistrosZarpeImports imports){

        try {
            return Optional.ofNullable(this.jcoRegistroZarpeService.RegistroZarpe(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(value = "/RolTripulacion/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RolTripulacionExports> RolTripulacion(@RequestBody RolTripulacionImports imports){

        try {
            return Optional.ofNullable(this.jcoRolTripulacionService.RolTripulacion(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }

    }
    @PostMapping(value = "/PDFZarpe", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PDFExports> GenerarPdfZarpe(@RequestBody PDFZarpeImports imports) {

        try {
            return Optional.ofNullable(this.JCOPDFZarpeService.GenerarPDFZarpe(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/PDFTravesia", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PDFExports> GenerarPdfTravesia(@RequestBody PDFZarpeImports imports) {

        try {
            return Optional.ofNullable(this.JCOPDFZarpeService.GenerarPDFTravesia(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/SeguimientoTripulantes", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SeguimientoTripuExports> SeguimientoTripulantes(@RequestBody SeguimientoTripuImports imports) {

        try {
            return Optional.ofNullable(this.jcoSeguimientoTripuService.SeguimientoTripulantes(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/Protestos", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ProtestosExports> Protestos(@RequestBody ProtestosImports imports) {

        try {
            return Optional.ofNullable(this.jcoProtestosService.Protestos(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/TrabajoFueraFaenaTransporte", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TrabajoFueraFaenaExports> TrabajoFueraFaenaTransporte(@RequestBody TrabajoFueraFaenaImports imports) {

        try {
            return Optional.ofNullable(this.jcoTrabajoFueraFaenaService.TrabajoFueraFaenaTransporte(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }
}
