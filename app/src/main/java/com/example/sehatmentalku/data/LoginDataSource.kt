package com.example.sehatmentalku.data

import android.util.Log
import android.widget.Toast
import com.example.sehatmentalku.data.model.LoggedInUser
import com.example.sehatmentalku.data.model.LoginRequest
import com.example.sehatmentalku.data.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
//    private lateinit var sessionManager: SessionManager
    private lateinit var retrofitClient: RetrofitClient

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            Log.d("TAG", "Masuk sini")
            // TODO: handle loggedInUser authentication
            retrofitClient = RetrofitClient()
//            sessionManager = SessionManager(this)

            Log.d("TAG", "Masuk sini 2" + username + password)

            retrofitClient.getRetrofitService().login(LoginRequest(username, password))
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        // Error logging in
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        val loginResponse = response.body()

                        Log.d("TAG", "Login sukses = " + response)
                        Log.d("TAG", "Login sukses2 = " + loginResponse?.message)

//                        if (loginResponse?.statusCode == 200 && loginResponse.user != null) {
//                            sessionManager.saveAuthToken(loginResponse.authToken)
//                        } else {
//                            // Error logging in
//                        }
                    }
                })
            Log.d("TAG", "Masuk sini 4")
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            Log.d("TAG", "Masuk error 4" + e)
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}