package com.example.sehatmentalku.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private lateinit var retrofitService: RetrofitService
    fun getRetrofitService(): RetrofitService {

        // Initialize ApiService if not initialized yet
        if (!::retrofitService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Env.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofitService = retrofit.create(RetrofitService::class.java)
        }

        return retrofitService
    }
}