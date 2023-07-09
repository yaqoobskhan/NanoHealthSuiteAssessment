package com.assessment.nanohealthsuite.service.network

import com.assessment.nanohealthsuite.service.model.UserData


class ApiHelper(private val apiService: ApiService) : BaseDataSource() {

    fun getToken(
        userData: UserData
    ) =
        apiService.getToken(userData)

    suspend fun getProducts() =
        apiService.getProducts()

    suspend fun getProductDetail() =
        apiService.getProductDetail()


}