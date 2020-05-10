package com.example.newsx.model

import com.example.newsx.data.BASE_API_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServiceBuilder {

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(BASE_API_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(NewsAPIService::class.java)

    fun buildService(): NewsAPIService {
        return  api
    }

}