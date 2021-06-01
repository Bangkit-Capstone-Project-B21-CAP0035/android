package com.example.sehatmentalku.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sehatmentalku.R
import com.example.sehatmentalku.data.RetrofitClient
import com.example.sehatmentalku.data.SessionManager
import com.example.sehatmentalku.data.model.LoginRequest
import com.example.sehatmentalku.data.model.LoginResponse
import com.example.sehatmentalku.ui.home.HomeActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var retrofitClient: RetrofitClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)

        Log.d("TAG", "ini button = " + login)
        Log.d("TAG", "ini loading = " + loading)

        password.apply {
            login.setOnClickListener {
                Log.d("TAG", "Ini terclick")
                loading.visibility = View.VISIBLE
                login(username.text.toString(), password.text.toString())
            }
        }
    }

    private fun login(username: String, password: String) {
        try {
            retrofitClient = RetrofitClient()
            sessionManager = SessionManager(this)
            val intent = Intent(this, HomeActivity::class.java).apply {}

            retrofitClient.getRetrofitService().login(LoginRequest(username, password))
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        // Error logging in
                        Log.d("TAG", "Masuk eerror kah ?")
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        val loginResponse = response.body()

                        Log.d("TAG", "Login sukses = " + response)

                        val loading = findViewById<ProgressBar>(R.id.loading)
                        loading.visibility = View.INVISIBLE

                        if (response.code() == 200 && loginResponse?.data?.apiToken != null) {
                            sessionManager.saveAuthToken(loginResponse?.data?.apiToken!!)
                            startActivity(intent)
                        } else {
                            try {
                                val jObjError = JSONObject(response.errorBody()!!.string())

                                if (jObjError.has("errors")) {
                                    val errorName = jObjError.getJSONObject("errors").names()[0].toString()
                                    Toast.makeText(
                                        applicationContext,
                                        jObjError.getJSONObject("errors").getJSONArray(errorName).getString(0),
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        applicationContext,
                                        jObjError.getString("message"),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                })
        } catch (e: Throwable) {
            Log.d("TAG", "Login error = " + e)
        }
    }
}