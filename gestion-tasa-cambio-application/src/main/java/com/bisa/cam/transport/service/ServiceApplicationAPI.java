package com.bisa.cam.transport.service;

import com.bisa.cam.transport.*;
import com.bisa.cam.utils.spring.rest.RestControllerStub;
import com.bisa.cam.utils.spring.rest.validators.request.PayloadCheck;
import com.bisa.cam.utils.spring.rest.validators.request.RequestValidation;
import com.bisa.cam.utils.spring.rest.validators.request.json.JsonSchema;
import com.bisa.cam.utils.spring.rest.validators.request.params.PathVariableCheck;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>API Specification</p>
 *  Gestion Tasas de Cambio
 *
 */
 /* @RequestMapping("/transfer-applications") */
@RequestMapping("/gestion-tasa-cambio")
@OpenAPIDefinition(info = @Info(title = "API de Gestion de Tasas de Cambio",
        description = "La API permite el registro de tasas de cambio para una moneda, consulta de tasas de cambio para una fecha.", version = "1.0.0",
        contact = @Contact(name = "Cajero Automatico Multi-Moneda", url = "http://www.luna.com"))
)

public interface ServiceApplicationAPI extends RestControllerStub {

    @PostMapping("/tasa/consultar-tasa")
    @Operation(tags = {"Gestion Tasa Cambio"}, responses =
            {
                    @ApiResponse(responseCode = "200", description = "Solicitud aceptada",
                            content = @Content(schema = @Schema(implementation = ConsultaTasaResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Argumento(s) de la solicitud invalido(s)",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Error interno en el servicio",
                            content = @Content(schema = @Schema(implementation = Error.class)))
            })
    @RequestValidation(payload = @PayloadCheck( jsonSchema = @JsonSchema(""))    )
    ResponseEntity consultaTasa(@RequestBody ConsultaTasaRequestDto request);


    @PostMapping("/registrar-tasa")
    @Operation(tags = {"Gestion Tasa Cambio"}, responses =
            {
                    @ApiResponse(responseCode = "200", description = "Solicitud aceptada",
                            content = @Content(schema = @Schema(implementation = CrearTasaCambioResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "El cuerpo de la solicitud es invalido",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "422", description = "No se pudo procesar la solicitud",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Error interno en el servicio",
                            content = @Content(schema = @Schema(implementation = Error.class)))
            })
    @RequestValidation(payload = @PayloadCheck( jsonSchema = @JsonSchema(""))    )
    /*@RequestValidation(payload = @PayloadCheck(
            jsonSchema = @JsonSchema("classpath:/schemas/json/crear-cuenta-request.json")))*/
    ResponseEntity registrarTasa(@RequestBody CrearTasaCambioRequestDto request);

}