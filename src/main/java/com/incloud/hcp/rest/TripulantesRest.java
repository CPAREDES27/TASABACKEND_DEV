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
    private JCOPDFsService JCOPDFsService;
    @Autowired
    private JCOSeguimientoTripuService jcoSeguimientoTripuService;
    @Autowired
    private JCOProtestosService jcoProtestosService;
    @Autowired
    private JCOTrabajoFueraFaenaService jcoTrabajoFueraFaenaService;
    @Autowired
    private JCOImpresFormatosProduceService jcoImpresFormatosProduceService;
    @Autowired
    private JCOReporObservaTripuService jcoReporObservaTripuService;


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
            return Optional.ofNullable(this.JCOPDFsService.GenerarPDFZarpe(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/PDFTravesia", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PDFExports> GenerarPdfTravesia(@RequestBody PDFZarpeImports imports) {

        try {
            return Optional.ofNullable(this.JCOPDFsService.GenerarPDFTravesia(imports))
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

    @PostMapping(value = "/GuardaTrabajo", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<GuardaTrabajoExports> GuardaTrabajo(@RequestBody GuardaTrabajoImports imports) {

        try {
            return Optional.ofNullable(this.jcoTrabajoFueraFaenaService.GuardaTrabajo(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/ImpresionFormatosProduce", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ImpresFormatosProduceExports> ImpresionFormatosProduce(@RequestBody ImpresFormatosProduceImports imports) {

        try {
            return Optional.ofNullable(this.jcoImpresFormatosProduceService.ImpresionFormatosProduce(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/ReporteObservacionesTripulantes", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReporObservaTripuExports> ReporteObservacionesTripulantes(@RequestBody ReporObservaTripuImports imports) {

        try {
            return Optional.ofNullable(this.jcoReporObservaTripuService.ReporteObservacionesTripulantes(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/PDFProtestos", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PDFExports> PDFProtestos(@RequestBody ProtestosImports imports) {

        try {
            return Optional.ofNullable(this.JCOPDFsService.GenerarPDFProtestos(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/PDFZarpeTravesia", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PDFExports> PDFZarpeTravesia(@RequestBody PDFZarpeImports imports) {

        try {
            return Optional.ofNullable(this.JCOPDFsService.GenerarPDFZarpeTravesia(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/PDFRolTripulacion", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PDFExports> PDFRolTripulacion(@RequestBody RolTripulacionImports imports) {

        try {
            return Optional.ofNullable(this.JCOPDFsService.GenerarPDFRolTripulacion(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/PDFTrimestral", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PDFExports> PDFTrimestral(@RequestBody PDFZarpeImports imports) {

        try {
            return Optional.ofNullable(this.JCOPDFsService.GenerarPDFTrimestral( imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/PDFTrabajoFF", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PDFExports> GenerarPDFTrabajoFF() {

        try {
            return Optional.ofNullable(this.JCOPDFsService.GenerarPDFTrabajoFF())
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }
    @PostMapping(value = "/PDFValeViveres", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PDFExports> GenerarPDFValeViveres(@RequestBody PDFValeViveresImports imports) {

        try {
            return Optional.ofNullable(this.JCOPDFsService.GenerarPDFValeViveres(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }
}
