package com.example.sehatmentalku.ui.journal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sehatmentalku.R
import com.example.sehatmentalku.data.RetrofitClient
import com.example.sehatmentalku.data.SessionManager
import com.example.sehatmentalku.data.model.Journal
import com.example.sehatmentalku.data.model.JournalResponse
import com.example.sehatmentalku.ui.home.HomeActivity
import com.example.sehatmentalku.ui.home.JournalAdapter
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JournalActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var retrofitClient: RetrofitClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal)
    }

    fun deleteJournal(view: View) {
        val journal = intent.getSerializableExtra("EXTRA_JOURNAL") as? Journal
        try {
            retrofitClient = RetrofitClient()
            sessionManager = SessionManager(view.context)

            retrofitClient.getRetrofitService().deleteJournal(sessionManager.fetchAuthToken()!!, journal?.id!!)
                .enqueue(object : Callback<JournalResponse> {
                    override fun onFailure(call: Call<JournalResponse>, t: Throwable) {
                        // Error logging in
                        Log.d("TAG", "onFailure Delete Journal = " + t)
                    }

                    override fun onResponse(call: Call<JournalResponse>, response: Response<JournalResponse>) {
                        val journalResponse = response.body()

                        if (response.code() == 200 && journalResponse?.message != null) {
                            // Toast sukses
                            Toast.makeText(
                                view.context,
                                journalResponse.message,
                                Toast.LENGTH_LONG
                            ).show()

                            // Intent balik ke home
                            startActivity(Intent(view.context, HomeActivity::class.java).apply {})
                        } else {
                            try {
                                val jObjError = JSONObject(response.errorBody()!!.string())

                                if (jObjError.has("errors")) {
                                    val errorName = jObjError.getJSONObject("errors").names()[0].toString()
                                    Toast.makeText(
                                        view.context,
                                        jObjError.getJSONObject("errors").getJSONArray(errorName).getString(0),
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        view.context,
                                        jObjError.getString("message"),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(view.context, e.message, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                })
        } catch (e: Throwable) {
            Log.d("TAG", "delete Journal error = " + e)
        }
    }
}