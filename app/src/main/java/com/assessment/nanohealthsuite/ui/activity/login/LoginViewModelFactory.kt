package com.assessment.nanohealthsuite.ui.activity.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assessment.nanohealthsuite.service.network.ApiHelper
import com.assessment.nanohealthsuite.service.repository.RepositoryLogin

class LoginViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(RepositoryLogin(apiHelper)) as T
    }
}
