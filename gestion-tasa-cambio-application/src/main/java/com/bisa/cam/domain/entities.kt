package com.bisa.cam.domain

import java.sql.Timestamp


data class TasaCambio(
    var id: Long?=null,
    var canal: String?=null,
    var fecha: Timestamp?=null,
    var moneda: String?=null,
    var cambioOficial: Double?=null,
    var cambioCompra: Double?=null,
    var cambioVenta: Double?=null,
    var estado: String?=null
)


data class Cuenta(
    var id: Long?=null,
    var nroCuenta: Long?=null,
    var tipoCuenta: String?=null,
    var moneda: String?=null,
    var saldo: Double?=null,
    var idCliente: Long?=null,
    var fechaApertura: Timestamp?=null,
    var lugarApertura: String?=null,
    var estado: String?=null

)
