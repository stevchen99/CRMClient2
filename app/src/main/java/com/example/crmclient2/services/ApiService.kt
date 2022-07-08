package com.example.crmclient2.services

import com.example.crmclient2.model.TheClient
import com.example.crmclient2.model.TheHisto
import com.example.crmclient2.model.TheHistoBack
import com.example.crmclient2.model.TheTaches
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @GET("GetClient.php")
    fun getClient(): Call<List<TheClient>>

    @GET("GetTaches.php")
    fun getTaches(): Call<List<TheTaches>>

    @GET("HistoRead.php")
    fun getHisto(): Call<List<TheHisto>>

    @Headers("Content-Type: application/json")
    @POST("HistoCreateUpdJob.php")
    fun postHisto(@Body userData: TheHistoBack): Call<TheHistoBack>

    companion object {

        var BASE_URL = "https://le-esp.fr/CRM/"

        fun create() : ApiService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiService::class.java)

        }
    }
}