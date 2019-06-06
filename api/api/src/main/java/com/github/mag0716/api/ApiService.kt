package com.github.mag0716.api

import com.github.mag0716.api.response.DataResponse
import com.github.mag0716.api.response.DetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/data")
    suspend fun data(): List<DataResponse>

    @GET("/data/{id}")
    suspend fun detail(@Path("id") id: Int): DetailResponse
}