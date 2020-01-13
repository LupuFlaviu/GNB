package com.example.gnb.api

import com.example.gnb.api.model.Conversion
import com.example.gnb.api.model.Transaction
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Interface that defines all the API calls
 */
interface WebService {

    @Headers("Accept: application/json")
    @GET("rates.json")
    suspend fun getConversions(): Response<List<Conversion>>

    @Headers("Accept: application/json")
    @GET("transactions.json")
    suspend fun getTransactions(): Response<List<Transaction>>
}