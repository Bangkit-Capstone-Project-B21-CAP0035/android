package com.example.sehatmentalku.ui.journal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.sehatmentalku.R
import com.example.sehatmentalku.data.RetrofitClient
import com.example.sehatmentalku.data.SessionManager
import com.example.sehatmentalku.data.model.Journal
import com.example.sehatmentalku.data.model.JournalResponse
import com.example.sehatmentalku.ui.home.HomeActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JournalActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var retrofitClient: RetrofitClient
    private lateinit var journal: Journal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal)

        journal = intent.getSerializableExtra("EXTRA_JOURNAL") as Journal

        Glide.with(findViewById<ImageView>(R.id.journal_image).context)
            .load(journal.imageUrl).into(findViewById(R.id.journal_image))
        findViewById<TextView>(R.id.journal_story).text = journal.story
    }

    fun editJournal(view: View) {
        val intent = Intent(view.context, EditJournalActivity::class.java).apply {}
        intent.putExtra("EXTRA_JOURNAL", journal)
        startActivity(intent)
    }

    fun deleteJournal(view: View) {
        try {
            retrofitClient = RetrofitClient()
            sessionManager = SessionManager(view.context)

            retrofitClient.getRetrofitService().deleteJournal(sessionManager.fetchAuthToken()!!, journal.id!!)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}