package com.assessment.nanohealthsuite.service.network

import com.assessment.nanohealthsuite.service.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("/auth/login")
    fun getToken(
        @Body userData: UserData
    ): Call<LoginResponse>

    @GET("/products")
    suspend fun getProducts(
    ): List<Product>

    @GET("/products/1")
    suspend fun getProductDetail(
    ): Product

}