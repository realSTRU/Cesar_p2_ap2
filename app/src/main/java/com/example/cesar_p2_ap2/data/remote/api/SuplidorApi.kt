package com.example.cesar_p2_ap2.data.remote.api

import com.example.cesar_p2_ap2.data.remote.dto.SuplidorDto
import retrofit2.http.GET

interface SuplidorApi {
    @GET("api/SuplidoresGastos")
    suspend fun getSuplidores(): List<SuplidorDto>
}