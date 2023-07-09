package com.assessment.nanohealthsuite.ui.activity.login

import android.text.Editable
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.assessment.nanohealthsuite.service.model.UserData
import com.assessment.nanohealthsuite.service.network.NetworkResource
import com.assessment.nanohealthsuite.service.repository.RepositoryLogin
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val repository: RepositoryLogin) : ViewModel() {

    var username: String = ""
    var password: String = ""
    private val logInResult = MutableLiveData<String>()
    fun getLogInResult(): LiveData<String> = logInResult

    fun userLogin(userData: UserData) = liveData(Dispatchers.IO) {
        emit(NetworkResource.loading(data = null))
        try {
            emit(NetworkResource.success(data = repository.getToken(userData)))
        } catch (exception: Exception) {
            emit(
                NetworkResource.error(
                    data = null,
                    message = exception.message ?: "Error Occurred!"
                )
            )
        }
    }

    fun performValidation(): Boolean {

        if (username.isBlank()) {
            logInResult.value = "Invalid username"
            return false
        }

        if (password.isBlank()) {
            logInResult.value = "Invalid password"
            return false
        }

        logInResult.value = "Valid credentials"
        return true
    }

    fun onTextChangeUsername(editable: Editable?) {
        when (editable.toString().length) {
            in 1..Integer.MAX_VALUE -> logInResult.value = ""
        }
    }

    fun onTextChangePassword(editable: Editable?) {
        when (editable.toString().length) {
            in 1..Integer.MAX_VALUE -> logInResult.value = ""
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}