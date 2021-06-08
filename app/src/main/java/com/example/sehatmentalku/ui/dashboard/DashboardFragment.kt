package com.example.sehatmentalku.ui.dashboard

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
import com.example.sehatmentalku.data.model.JournalResponse
import com.example.sehatmentalku.ui.journal.AddJournalActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment() {

    private lateinit var sessionManager: SessionManager
    private lateinit var retrofitClient: RetrofitClient
    private lateinit var loading: ProgressBar

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loading = view.findViewById(R.id.loading)
        loading.visibility = View.VISIBLE

        // Get data journal
        getPsychologist(view)
    }

    private fun getPsychologist(view: View) {
        try {
            retrofitClient = RetrofitClient()
            sessionManager = SessionManager(requireContext())

            retrofitClient.getRetrofitService().getPsychologist(sessionManager.fetchAuthToken()!!)
                .enqueue(object : Callback<JournalResponse> {
                    override fun onFailure(call: Call<JournalResponse>, t: Throwable) {
                        // Error logging in
                        Log.d("TAG", "onFailure Journal = " + t)
                    }

                    override fun onResponse(call: Call<JournalResponse>, response: Response<JournalResponse>) {
                        loading.visibility = View.INVISIBLE
                        val psychologistResponse = response.body()
                        Log.d("TAG", "Ini response Plain = " + psychologistResponse.toString())

                        if (response.code() == 200 && psychologistResponse?.data?.psychologists != null) {
                            Log.d("Tag", "Ini isi psikolognya string = " + psychologistResponse.data?.psychologists?.get(0)?.name)
                            val recyclerView: RecyclerView = view.findViewById(R.id.psychologist_recycler_view)

                            recyclerView.adapter = PsychologistAdapter(psychologistResponse.data?.psychologists)
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
            Log.d("TAG", "get Psychologist error = " + e)
        }
    }
}