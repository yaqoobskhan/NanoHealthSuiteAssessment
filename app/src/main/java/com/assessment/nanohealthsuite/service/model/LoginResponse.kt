package com.assessment.nanohealthsuite.service.model

import com.google.gson.annotations.SerializedName


data class LoginResponse(
    @SerializedName("token")
    var token: String?
)