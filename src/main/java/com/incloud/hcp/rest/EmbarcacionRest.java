package com.incloud.hcp.rest;


import com.incloud.hcp.jco.gestionpesca.dto.*;
import com.incloud.hcp.jco.gestionpesca.service.JCOEmbarcacionService;
import com.incloud.hcp.jco.gestionpesca.service.JCOTipoEmbarcacionService;
import com.incloud.hcp.jco.maestro.dto.*;
import com.incloud.hcp.jco.reportepesca.dto.MareaDto2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(value = "/api/embarcacion")
public class EmbarcacionRest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private JCOEmbarcacionService jcoEmbarcacionService;
    @Autowired
    private JCOTipoEmbarcacionService jcoTipoEmbarcacionService;

    @Autowired
    private com.incloud.hcp.jco.maestro.service.JCOEmbarcacionService EmbarcacionService;



    //@ApiOperation(value = "Lista Embarcacion x", produces = "application/json")
    @GetMapping(value = "/listaEmbarcacion", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmbarcacionDto>> listaEmbarcacion(
    ) {
        //Parametro dto = new Parametro();
        try {
            return Optional.ofNullable(this.jcoEmbarcacionService.listaEmbarcacion("CDEMB = '0000004529'"))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }

    @GetMapping(value = "/listaTipoEmbarcacion", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TipoEmbarcacionDto>> listaTipoEmbarcacion(String usuario) {
        //Parametro dto = new Parametro();
        try {
            return Optional.ofNullable(this.jcoTipoEmbarcacionService.listaTipoEmbarcacion(usuario))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }


    @GetMapping(value = "/listaPlantas", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MaestroExport> listaPlantas(String usuario) {
        //Parametro dto = new Parametro();
        try {
            return Optional.ofNullable(this.jcoTipoEmbarcacionService.listarPlantas(usuario))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }
    }

    @PostMapping(value = "/ConsultarEmbarcacion/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<EmbarcacionExports> ConsultarEmbarcacionesNew(@RequestBody EmbarcacionImports imports){

        try {
            return Optional.ofNullable(this.EmbarcacionService.ListarEmbarcaciones(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }
    @PostMapping(value = "/ValidarBodegaCert/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BodegaExport> ValidarBodegaCert(@RequestBody BodegaImport imports){

        try {
            return Optional.ofNullable(this.jcoEmbarcacionService.ValidarBodegaCert(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }
    @GetMapping(value = "/ObtenerFlota", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<FlotaDto> obtenerDistribucionFlota(String user){

        try {
            return Optional.ofNullable(this.jcoEmbarcacionService.obtenerDistribucionFlota(user))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }
    @PostMapping(value = "/consultaMarea/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MareaDto> consultaMarea(@RequestBody MareaOptions marea){

        try {
            return Optional.ofNullable(this.jcoEmbarcacionService.consultaMarea(marea))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(value = "/consultaMarea2/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MareaDto2> consultaMarea2(@RequestBody MareaOptions marea){

        try {
            return Optional.ofNullable(this.jcoEmbarcacionService.consultaMarea2(marea))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(value = "/consultarHorometro/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<HorometroExport> consultaMarea(@RequestBody HorometroDto horometro){

        try {
            return Optional.ofNullable(this.jcoEmbarcacionService.consultarHorometro(horometro))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }

    @PostMapping(value = "/BusquedasEmbarcacion/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MaestroExport> BuscarEmbarcaciones(@RequestBody BusquedaEmbarcacionImports imports){

        try {
            return Optional.ofNullable(this.EmbarcacionService.BuscarEmbarcaciones(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }
    @PostMapping(value = "/BusqAdicEmbarca/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BusqAdicEmbarExports> BusqAdicEmbarca(@RequestBody BusqAdicEmbarImports imports){

        try {
            return Optional.ofNullable(this.EmbarcacionService.BusquedaAdicionalEmbarca(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }
    @PostMapping(value = "/Editar_Crear/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MensajeDto> Nuevo(@RequestBody EmbarcacionNuevImports imports){

        try {
            return Optional.ofNullable(this.EmbarcacionService.Nuevo(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }
    @PostMapping(value = "/CrearMarea/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MensajeDto> Nuevo(@RequestBody MarEventoDtoImport imports){

        try {
            return Optional.ofNullable(this.jcoEmbarcacionService.crearMareaPropios(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }
    /*@PostMapping(value = "/Editar/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Mensaje> Nuevo(@RequestBody EmbarcacionEditImports imports){

        try {
            return Optional.ofNullable(this.EmbarcacionService.Editar(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }*/

    @PostMapping(value = "/MoverEmbarcacion/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MensajeDto> MoverEmbarcacion(@RequestBody MoverEmbarcaImports imports){

        try {
            return Optional.ofNullable(this.EmbarcacionService.MoverEmbarcacion(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }
    @PostMapping(value = "/ValidarMarea/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ValidaMareaExports> ValidarMarea(@RequestBody ValidaMareaImports imports){

        try {
            return Optional.ofNullable(this.jcoEmbarcacionService.ValidarMarea(imports))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            //String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(e.toString());
        }

    }


}
