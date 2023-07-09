package com.assessment.nanohealthsuite.service.model

import com.google.gson.annotations.SerializedName

class ProductDetailResponse(
    @field:
    SerializedName("id") var id: Int? = null,
    @field:SerializedName("title") var title: String? = null,
    @field:SerializedName("price") var price: Double? = null,
    @field:SerializedName("description") var description: String? = null,
    @field:SerializedName("category") var category: String? = null,
    @field:SerializedName("image") var image: String? = null,
    @field:SerializedName("rating") var rating: Rating? = Rating()
)
