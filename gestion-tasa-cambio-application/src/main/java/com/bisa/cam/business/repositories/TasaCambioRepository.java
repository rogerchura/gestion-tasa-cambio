package com.bisa.cam.business.repositories;

import com.bisa.cam.domain.Cuenta;
import com.bisa.cam.domain.TasaCambio;

import java.sql.Date;


public interface TasaCambioRepository {


    TasaCambio consultarTasaCambio(Date fecha, String moneda) throws Exception;

    Boolean registrarTasaCambio(TasaCambio tasa) throws Exception;

}
