package com.assessment.nanohealthsuite.service.repository

import com.assessment.nanohealthsuite.service.network.ApiHelper

class RepositoryProduct(private val apiHelper: ApiHelper) {

    suspend fun getProductList() = apiHelper.getProducts()
    suspend fun getProductDetail() = apiHelper.getProductDetail()

}