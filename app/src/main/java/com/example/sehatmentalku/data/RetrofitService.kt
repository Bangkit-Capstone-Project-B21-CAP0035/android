package com.example.sehatmentalku.data

import com.example.sehatmentalku.data.model.LoginRequest
import com.example.sehatmentalku.data.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitService {
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}