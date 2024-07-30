package com.example.jetpackcomposebase.ui.login.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposebase.base.ViewModelBase
import com.example.jetpackcomposebase.network.ResponseData
import com.example.jetpackcomposebase.network.ResponseHandler
import com.example.jetpackcomposebase.ui.login.model.LoginResponse
import com.example.jetpackcomposebase.ui.login.repository.LoginRepository
import com.example.jetpackcomposebase.utils.SentryService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
) : ViewModelBase() {

    private val _email = MutableStateFlow("eve.holt@reqres.in")
    val email = _email as StateFlow<String>

    private val _password = MutableStateFlow("cityslicka")
    val password = _password as StateFlow<String>

    private val _loginResponse =
        MutableStateFlow<ResponseHandler<ResponseData<LoginResponse>?>>(ResponseHandler.Empty)
    val loginResponse = _loginResponse as StateFlow<ResponseHandler<ResponseData<LoginResponse>?>>

    fun setUserName(userName: String) {
        _email.value = userName
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun callLoginAPI() {
        viewModelScope.launch {
            try {
                _loginResponse.value = ResponseHandler.Loading
                _loginResponse.value = repository.callLoginAPI(email.value, password.value)
                SentryService.captureEvent(
                    "Login Successful",
                    tagKey = "navigation",
                    tagValue = "login"
                ) // Capture exception with Sentry
            } catch (e: Exception) {
                SentryService.captureException(e) // Capture exception with Sentry
                _loginResponse.value = ResponseHandler.OnError(e.message ?: "Unknown error")
            }

        }
    }
}
