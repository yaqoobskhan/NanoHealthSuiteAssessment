package com.assessment.nanohealthsuite.service.repository

import com.assessment.nanohealthsuite.service.model.UserData
import com.assessment.nanohealthsuite.service.network.ApiHelper

class RepositoryLogin(private val apiHelper: ApiHelper) {

suspend fun getToken(userData: UserData) = apiHelper.getToken(userData)

}