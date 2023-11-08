package com.example.cesar_p2_ap2.data.remote.api

import com.example.cesar_p2_ap2.data.remote.dto.GastoDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

//API for comsuming from https://sag-api.azurewebsites.net/swagger/index.html

interface GastoApi {
    @GET("api/Gastos")
    suspend fun getGastos(): List<GastoDto>
    @GET("api/Gastos/{id}")
    suspend fun getGastoById(@Path("idGasto") idGasto: Int) : GastoDto
    @POST("api/Gastos")
    suspend fun postGasto(@Body gato : GastoDto) : GastoDto
    @PUT("api/Gastos/{id}")
    suspend fun postGasto(@Body gato : GastoDto, @Path("idGasto") idGasto: Int) : GastoDto
    @DELETE("api/Gastos/{id}")
    suspend fun deleteGasto(@Path("idGasto") idGasto: Int) : GastoDto
}