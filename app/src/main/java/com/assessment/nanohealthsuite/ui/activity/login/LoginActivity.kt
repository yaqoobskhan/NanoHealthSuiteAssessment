package com.assessment.nanohealthsuite.ui.activity.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.assessment.nanohealthsuite.MainActivity
import com.assessment.nanohealthsuite.NanoHealthApplication
import com.assessment.nanohealthsuite.databinding.ActivityLoginBinding
import com.assessment.nanohealthsuite.interfaces.ILogInHandler
import com.assessment.nanohealthsuite.service.model.LoginResponse
import com.assessment.nanohealthsuite.service.model.UserData
import com.assessment.nanohealthsuite.service.network.ApiHelper
import com.assessment.nanohealthsuite.service.network.RetrofitBuilder
import com.assessment.nanohealthsuite.ui.dialog.MessageAlertDialog
import com.assessment.nanohealthsuite.ui.dialog.ProgressLoadingDialog
import com.assessment.nanohealthsuite.utility.Constant
import com.assessment.nanohealthsuite.utility.Status
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity(), ILogInHandler {
    lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var progressDialog: ProgressLoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        progressDialog = ProgressLoadingDialog(this)
        setContentView(binding.root)
        supportActionBar!!.hide()

        setUpViewModel()
        setUpObservers()
        binding.btnLogin.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
        }
        viewModel.username = "mor_2314"
        viewModel.password = "83r5^_"
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        )[LoginViewModel::class.java]
        binding.viewModel = viewModel
        binding.handler = this
    }

    override fun onLogInClicked() {

        viewModel.performValidation().apply {
            when (this) {
                true -> {
                    lifecycleScope.launch {
                        loginUser()
                    }
                }
                false -> {}
            }
        }

    }

    private fun setUpObservers() {
        viewModel.getLogInResult().observe(this) { result ->
            when (result) {
                "Invalid username" -> {
                    binding.usernmae.error = "Please enter valid username/msisdn"
                    binding.usernmae.requestFocus()
                }
                "Invalid password" -> {
                    binding.password
                        .error = "Please enter password"
                    binding.password.requestFocus()
                }
                "" -> {
                    binding.usernmae.isErrorEnabled = false
                    binding.usernmae.error = null

                    binding.password.isErrorEnabled = false
                    binding.password.error = null
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loginUser() {
        progressDialog.showProgressDialog()
        val userData = UserData(viewModel.username, viewModel.password)

        lifecycleScope.launch(Dispatchers.IO) {
            val response = RetrofitBuilder.getTokenApiService()
                .getToken(
                    userData
                ).execute()
            if (response.isSuccessful) {
                try {
                    withContext(Dispatchers.Main)
                    {
                        progressDialog.cancelProgressDialog()
                        val token = response.body()?.token
                        NanoHealthApplication.sharedPreferences.edit().putString(
                            Constant.TOKEN,
                            token
                        ).apply()

                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                withContext(Dispatchers.Main)
                {
                    progressDialog.cancelProgressDialog()
                    MessageAlertDialog(
                        "Error",
                        "Invalid username/password"
                    ).show(supportFragmentManager, "DialogFragment")
                }
            }
        }


        /*   viewModel.userLogin(userData)
               .observe(this, androidx.lifecycle.Observer {
                   it?.let { resource ->
                       when (resource.status) {
                           Status.SUCCESS -> {
                               progressDialog.cancelProgressDialog()
                               when (resource.data) {
                                   null -> {
                                       MessageAlertDialog(
                                           "Error",
                                           "Invalid username/password"
                                       ).show(supportFragmentManager, "DialogFragment")
                                   }

                                   else ->
                                       try {
                                           val data = resource.data as LoginResponse

                                           NanoHealthApplication.sharedPreferences.edit().putString(
                                               Constant.TOKEN,
                                               data.token
                                           ).apply()
                                           startActivity(
                                               Intent(
                                                   this,
                                                   MainActivity::class.java
                                               )
                                           )
                                           finish()
                                       } catch (e: Exception) {
                                           e.printStackTrace()
                                       } finally {
                                       }
                               }
                           }
                           Status.ERROR -> {
                               progressDialog.cancelProgressDialog()
                               MessageAlertDialog("Alert!", resource.message.toString()).show(
                                   supportFragmentManager,
                                   "DialogFragment"
                               )
                           }
                           Status.LOADING -> {
                           }
                       }
                   }
               })*/
    }
}