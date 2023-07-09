package com.assessment.nanohealthsuite.service.model

import com.google.gson.annotations.SerializedName


data class UserData(

    @field:SerializedName("username")
    val username: String? = "",

    @field:SerializedName("password")
    val password: String? = "",

)






