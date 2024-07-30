package com.example.jetpackcomposebase.network

import com.example.jetpackcomposebase.ui.dashboard.model.MovieCharacter
import com.example.jetpackcomposebase.ui.login.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import javax.inject.Singleton

/**
 * Interface used for api
 */
@Singleton
interface ApiInterface {

    @GET("characters")
    suspend fun getCharacter(): List<MovieCharacter>

    @FormUrlEncoded
    @POST("login")
    suspend fun callLoginApi(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Response<ResponseData<LoginResponse>>


}
