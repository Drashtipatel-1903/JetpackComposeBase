package com.example.jetpackcomposebase.ui.login.repository

import com.example.jetpackcomposebase.base.BaseRepository
import com.example.jetpackcomposebase.network.ApiInterface
import com.example.jetpackcomposebase.network.ResponseData
import com.example.jetpackcomposebase.network.ResponseHandler
import com.example.jetpackcomposebase.ui.login.model.LoginResponse
import javax.inject.Inject

open class LoginRepository @Inject constructor(private val apiInterface: ApiInterface) :
    BaseRepository() {

    open suspend fun callLoginAPI(
        email: String,
        password: String
    ): ResponseHandler<ResponseData<LoginResponse>?> {
        return makeAPICall {
            apiInterface.callLoginApi(email, password)
        }
    }
}

