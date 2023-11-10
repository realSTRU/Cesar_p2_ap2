package com.example.cesar_p2_ap2.data.remote.dto

import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//Dto de gastos modelado para hacer la ui propuesta para el examen.
@JsonClass(generateAdapter = true)
data class GastoDto (

    @PrimaryKey
    @Json(name = "idGasto")
    val idGasto : Int?,
    @Json(name = "fecha")
    val fecha : String,
    @Json(name = "idSuplidor")
    val idSuplidor : Int,
    @Json(name = "suplidor")
    val suplidor : String,
    @Json(name = "concepto")
    val concepto : String,
    @Json(name="ncf")
    val ncf : String,
    @Json(name="itbis")
    val itbis : Double,
    @Json(name = "monto")
    val monto : Double
)