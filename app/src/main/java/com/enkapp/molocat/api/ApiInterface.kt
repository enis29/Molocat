package com.enkapp.molocat.api

import com.enkapp.molocat.model.Breed
import com.enkapp.molocat.model.BreedShort
import com.enkapp.molocat.model.Detail
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/v1/breeds")
    fun getBreeds() : Deferred<Response<List<Breed>>>

    @GET("/v1/images/search")
    fun getBreedById(@Query("breed_id") id: String, @Query("limit") limit: Int) : Deferred<Response<List<Detail>>>
}