package com.example.sehatmentalku.data

import com.example.sehatmentalku.data.model.JournalRequest
import com.example.sehatmentalku.data.model.JournalResponse
import com.example.sehatmentalku.data.model.LoginRequest
import com.example.sehatmentalku.data.model.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("journal")
    fun getJournal(@Header ("Authorization") auth: String): Call<JournalResponse>

    @POST("journal/delete/{id}")
    fun deleteJournal(@Header ("Authorization") auth: String,
                      @Path ("id") id: Int): Call<JournalResponse>

    @POST("journal")
    fun saveJournal(@Header ("Authorization") auth: String,
                    @Body request: JournalRequest): Call<JournalResponse>

    @POST("journal/update/{id}")
    fun updateJournal(@Header ("Authorization") auth: String,
                    @Body request: JournalRequest,
                    @Path ("id") id: Int): Call<JournalResponse>
}