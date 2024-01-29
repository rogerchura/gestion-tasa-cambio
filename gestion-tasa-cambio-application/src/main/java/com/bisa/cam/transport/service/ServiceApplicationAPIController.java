package com.bisa.cam.transport.service;


import com.bisa.cam.business.interactors.TasaApplicationInteractor;
import com.bisa.cam.transport.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

/**
 * Implementacion de la API
 *
 */
@RestController
public class ServiceApplicationAPIController implements ServiceApplicationAPI {
    @Autowired
    private TasaApplicationInteractor applicationInteractor;

    private final Logger logger = LogManager.getLogger(getClass());


    @Override
    public ResponseEntity consultaTasa(ConsultaTasaRequestDto request) {
        ConsultaTasaResponseDto responseDto = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date fecha  = null;
        try {
            fecha = sdf.parse(request.getFecha());
        } catch (ParseException e) {
            responseDto = new ConsultaTasaResponseDto();
            ErrorResponseDto error = new ErrorResponseDto(250l, "Error al convertir la fecha de solicitud.");
            responseDto.setError(error);
            return ResponseEntity.ok(responseDto);
        }
        responseDto = applicationInteractor.consultaTasaCambio(fecha, request.getMoneda());
        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity registrarTasa(CrearTasaCambioRequestDto request) {
        CrearTasaCambioResponseDto responseDto = new CrearTasaCambioResponseDto();
        if(request==null){
            responseDto.getError().setCodError(230L);
            responseDto.getError().setDescError("La solicitud de registro no es valida.");
            ResponseEntity.ok(responseDto);
        }
        responseDto = applicationInteractor.registraCuenta(request);
        return ResponseEntity.ok(responseDto);
    }
}