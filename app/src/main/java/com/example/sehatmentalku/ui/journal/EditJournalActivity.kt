package com.example.sehatmentalku.ui.journal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.sehatmentalku.R
import com.example.sehatmentalku.data.RetrofitClient
import com.example.sehatmentalku.data.SessionManager
import com.example.sehatmentalku.data.model.Journal
import com.example.sehatmentalku.data.model.JournalRequest
import com.example.sehatmentalku.data.model.JournalResponse
import com.example.sehatmentalku.ui.home.HomeActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditJournalActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var retrofitClient: RetrofitClient
    private lateinit var journal: Journal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_journal)
        supportActionBar?.hide()

        journal = intent.getSerializableExtra("EXTRA_JOURNAL") as Journal
        Glide.with(findViewById<ImageView>(R.id.journal_img).context)
            .load("https://i.stack.imgur.com/eJbuH.png?s=128").into(findViewById(R.id.journal_img))
        findViewById<EditText>(R.id.input_story).setText(journal.story)
    }

    fun updateJournal(view: View) {
        try {
            retrofitClient = RetrofitClient()
            sessionManager = SessionManager(this)

            val intent = Intent(this, HomeActivity::class.java).apply {}
            val story = findViewById<EditText>(R.id.input_story).text

            retrofitClient.getRetrofitService().updateJournal(sessionManager.fetchAuthToken()!!, JournalRequest(story.toString()), journal.id!!)
                .enqueue(object : Callback<JournalResponse> {
                    override fun onFailure(call: Call<JournalResponse>, t: Throwable) {
                        // Error logging in
                        Log.d("TAG", "onFailure updateJournal" + t)
                    }

                    override fun onResponse(call: Call<JournalResponse>, response: Response<JournalResponse>) {
                        val journalResponse = response.body()

                        if (response.code() == 200 && journalResponse?.message != null) {
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
            Log.d("TAG", "Update Journal error = " + e)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}