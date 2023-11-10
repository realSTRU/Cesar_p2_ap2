package com.example.cesar_p2_ap2.di

import com.example.cesar_p2_ap2.data.remote.api.GastoApi
import com.example.cesar_p2_ap2.data.remote.api.SuplidorApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }


    @Provides
    @Singleton
    fun provideGastoApi(moshi: Moshi): GastoApi {
        return Retrofit.Builder()
            .baseUrl("https://sag-api.azurewebsites.net/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GastoApi::class.java)
    }
    @Provides
    @Singleton
    fun provideSupliersApi(moshi: Moshi): SuplidorApi {
        return Retrofit.Builder()
            .baseUrl("https://sag-api.azurewebsites.net/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(SuplidorApi::class.java)
    }

}

//AppModule for provide all my tools to cosuming on UI development.