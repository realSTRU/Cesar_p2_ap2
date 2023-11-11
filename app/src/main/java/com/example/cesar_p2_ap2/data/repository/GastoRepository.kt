package com.example.cesar_p2_ap2.data.repository

import com.example.cesar_p2_ap2.data.remote.api.GastoApi
import com.example.cesar_p2_ap2.data.remote.dto.GastoDto
import com.example.cesar_p2_ap2.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import retrofit2.HttpException
import java.io.IOException


class GastoRepository @Inject constructor(private val api:GastoApi){
    fun getGastos() : Flow<Resource<List<GastoDto>>> = flow{
        try{
            emit(Resource.Loading())
            val gastos = api.getGastos()
            emit(Resource.Success(gastos))
        }catch (e: IOException){
            emit(Resource.Error(e.message ?: "Verificar Conexion"))
        }catch (e: HttpException)
        {
            emit(Resource.Error(e.message()?:"Error HTTP"))
        }
    }

    fun getGastoById(id: Int): Flow<Resource<GastoDto>> = flow {
        try {
            emit(Resource.Loading())
            val gasto = api.getGastoById(id)
            emit(Resource.Success(gasto))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP"))
        } catch (e: IOException) {
            emit(Resource.Error(e.message ?: "Verificar Conexion"))
        }
    }

    suspend fun deleteGasto(id: Int) {
        api.deleteGasto(id)
    }

    suspend fun postGasto(gasto: GastoDto) : GastoDto?{
        return try {
            withContext(Dispatchers.IO) {
                val response = api.postGasto(gasto)
                println("Lo hice!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
                if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun putGasto(gasto : GastoDto, id: Int) {
        api.putGasto(gasto,id)
    }

}