package com.assessment.nanohealthsuite.ui.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.assessment.nanohealthsuite.service.model.Product
import com.assessment.nanohealthsuite.service.network.NetworkResource
import com.assessment.nanohealthsuite.service.repository.RepositoryProduct
import kotlinx.coroutines.Dispatchers

class ProductDetailViewModel(
    private val repository: RepositoryProduct
) :
    ViewModel() {
    var data: LiveData<Product> = MutableLiveData()
    fun getProductDetail() =
        liveData(Dispatchers.IO) {
            emit(NetworkResource.loading(data = null))
            try {
                emit(
                    NetworkResource.success(
                        data = repository.getProductDetail(
                        )
                    )
                )
            } catch (exception: Exception) {
                emit(
                    NetworkResource.error(
                        data = null,
                        message = exception.message ?: "Error Occurred!"
                    )
                )
            }
        }
}