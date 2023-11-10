package com.example.cesar_p2_ap2.data.repository

import androidx.annotation.RequiresExtension
import com.example.cesar_p2_ap2.data.remote.api.SuplidorApi
import com.example.cesar_p2_ap2.data.remote.dto.SuplidorDto
import com.example.cesar_p2_ap2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SuplidorRepository @Inject constructor(
    private val api: SuplidorApi
) {

    fun getSuplidores(): Flow<Resource<List<SuplidorDto>?>> = flow {
        try {
            emit(Resource.Loading())

            val suplidores = api.getSuplidores()

            emit(Resource.Success(suplidores))
        } catch (e: HttpException) {

            emit(Resource.Error(e.message ?: "Error HTTP"))
        } catch (e: IOException) {

            emit(Resource.Error(e.message ?: "verificar internet"))
        }
    }
}