package com.example.sehatmentalku.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sehatmentalku.R
import com.example.sehatmentalku.data.RetrofitClient
import com.example.sehatmentalku.data.SessionManager
import com.example.sehatmentalku.data.model.Journal
import com.example.sehatmentalku.data.model.JournalResponse
import com.example.sehatmentalku.ui.journal.AddJournalActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var sessionManager: SessionManager
    private lateinit var retrofitClient: RetrofitClient
    private lateinit var loading: ProgressBar

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loading = view.findViewById(R.id.loading)
        val btnAdd = view.findViewById<Button>(R.id.btn_add)
        btnAdd.setOnClickListener{
            startActivity(Intent(view.context, AddJournalActivity::class.java).apply {})
        }
        loading.visibility = View.VISIBLE
        // Get data journal
        getJournal(view)
    }

    private fun getJournal(view: View) {
        try {
            retrofitClient = RetrofitClient()
            sessionManager = SessionManager(requireContext())

            retrofitClient.getRetrofitService().getJournal(sessionManager.fetchAuthToken()!!)
                .enqueue(object : Callback<JournalResponse> {
                    override fun onFailure(call: Call<JournalResponse>, t: Throwable) {
                        // Error logging in
                        Log.d("TAG", "onFailure Journal = " + t)
                    }

                    override fun onResponse(call: Call<JournalResponse>, response: Response<JournalResponse>) {
                        loading.visibility = View.INVISIBLE
                        val journalResponse = response.body()
                        Log.d("TAG", "Ini response Plain = " + journalResponse.toString())

                        if (response.code() == 200 && journalResponse?.data?.journals != null) {
                            Log.d("Tag", "Ini isi journalnya string = " + journalResponse.data?.journals?.get(0)?.story)
                            val recyclerView: RecyclerView = view.findViewById(R.id.journal_recycler_view)

                            recyclerView.adapter = JournalAdapter(journalResponse.data?.journals)
                            recyclerView.layoutManager = LinearLayoutManager(context)
                            recyclerView.setHasFixedSize(true)
                        } else {
                            try {
                                val jObjError = JSONObject(response.errorBody()!!.string())

                                if (jObjError.has("errors")) {
                                    val errorName = jObjError.getJSONObject("errors").names()[0].toString()
                                    Toast.makeText(
                                        context,
                                        jObjError.getJSONObject("errors").getJSONArray(errorName).getString(0),
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        jObjError.getString("message"),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                })
        } catch (e: Throwable) {
            Log.d("TAG", "get Journal error = " + e)
        }
    }
}