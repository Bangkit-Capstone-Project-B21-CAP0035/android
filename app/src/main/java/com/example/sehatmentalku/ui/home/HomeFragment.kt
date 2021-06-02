package com.example.sehatmentalku.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sehatmentalku.R
import com.example.sehatmentalku.data.model.JournalItem

class HomeFragment : Fragment() {

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

        val recyclerView: RecyclerView = view.findViewById(R.id.journal_recycler_view)
        val exampleList = generateDummy(100)

        recyclerView.adapter = JournalAdapter(exampleList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
    }

    private fun generateDummy(size: Int): List<JournalItem> {
        val list = ArrayList<JournalItem>()

        for (i in 0 until size) {
            val item = JournalItem("https://media.suara.com/pictures/480x260/2019/12/26/49091-gambar.jpg",
                "tanggal sekarang","cerita saya " + i)
            list += item
        }
        return list
    }
}