package com.assessment.nanohealthsuite.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assessment.nanohealthsuite.service.network.ApiHelper
import com.assessment.nanohealthsuite.service.repository.RepositoryProduct

class HomeViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(RepositoryProduct(apiHelper)) as T
    }
}
