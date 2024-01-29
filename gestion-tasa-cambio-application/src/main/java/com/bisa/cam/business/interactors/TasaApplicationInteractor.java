package com.bisa.cam.business.interactors;


import com.bisa.cam.business.repositories.TasaCambioRepository;
import com.bisa.cam.domain.Cuenta;
import com.bisa.cam.domain.TasaCambio;
import com.bisa.cam.transport.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class TasaApplicationInteractor {

    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private TasaCambioRepository repository;

    public ConsultaTasaResponseDto consultaTasaCambio(Date fecha, String moneda) {

        TasaCambio tasaCambio = null;
        ConsultaTasaResponseDto responseDto = new ConsultaTasaResponseDto();

        java.sql.Date sqlDate = new java.sql.Date(fecha.getTime());

        try {
            //StringUtils.isNumeric(fechaId);
            tasaCambio = repository.consultarTasaCambio(sqlDate, moneda);
        } catch (Exception e) {
           logger.error(e.getMessage(), e);
           tasaCambio = null;
        }
        if(tasaCambio!=null && tasaCambio.getId()!=null){
            responseDto.setError(new ErrorResponseDto());
            TasaResponseDto tasaDto = new TasaResponseDto();
            tasaDto.setFecha(tasaCambio.getFecha());
            tasaDto.setCanal(tasaCambio.getCanal());
            tasaDto.setMoneda(tasaCambio.getMoneda());
            tasaDto.setCambioOficial(tasaCambio.getCambioOficial());
            tasaDto.setCambioCompra(tasaCambio.getCambioCompra());
            tasaDto.setCambioVenta(tasaCambio.getCambioVenta());
            tasaDto.setEstado(tasaCambio.getEstado());
            responseDto.setTasa(tasaDto);
        }else{
            responseDto.setError(new ErrorResponseDto(240l, "No se encontro la tasa de cambio para la fecha."));
            responseDto.setTasa(new TasaResponseDto());
        }
        return responseDto;
    }

    public CrearTasaCambioResponseDto registraCuenta(CrearTasaCambioRequestDto request) {

        Boolean registrado = false;
        TasaCambio tasaCambio = null;
        CrearTasaCambioResponseDto responseDto = new CrearTasaCambioResponseDto();
        ErrorResponseDto error = new ErrorResponseDto();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Timestamp ts = null;
        try {
            java.util.Date d = sdf.parse(request.getFecha());
            ts = new Timestamp(d.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        try {
            if(request!=null){
                tasaCambio = new TasaCambio();
                tasaCambio.setFecha(ts);
                tasaCambio.setCambioOficial(request.getCambioOficial());
                tasaCambio.setCambioCompra(request.getCambioCompra());
                tasaCambio.setCambioVenta(request.getCambioVenta());
                registrado = repository.registrarTasaCambio(tasaCambio);
                if(registrado){
                    responseDto.setError(error);
                }
            }
            if(!registrado){
                error.setCodError(260L);
                error.setDescError("No se puede crear la Tasa de Cambio.");
                responseDto.setError(error);
            }
            responseDto.setResultado(registrado);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            error.setCodError(270L);
            error.setDescError("No se puede crear la Tasa de Cambio.."+e.getMessage());
            responseDto.setError(error);
        }
        return responseDto;
    }

}
