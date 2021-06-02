package com.example.sehatmentalku.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sehatmentalku.R
import com.example.sehatmentalku.data.model.Journal
import com.example.sehatmentalku.data.model.JournalItem

class JournalAdapter(private val journalList: MutableList<Journal?>?) : RecyclerView.Adapter<JournalAdapter.JournalViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
        val journalInflater = LayoutInflater.from(parent.context).inflate(R.layout.journal_item,
        parent, false)

        return JournalViewHolder(journalInflater)
    }

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
        val currentItem = journalList?.get(position)
        Log.d("LOG", "ini currentItem = " + currentItem.toString())

        //currentItem.image
        Glide.with(holder.img.context).load("https://i.stack.imgur.com/eJbuH.png?s=128").into(holder.img)
        holder.tanggal.text = currentItem?.createdAt
        holder.story.text = currentItem?.story
    }

    override fun getItemCount() = journalList!!.size

    class JournalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.journal_img)
        val tanggal: TextView = itemView.findViewById(R.id.journal_tanggal)
        val story: TextView = itemView.findViewById(R.id.journal_story)
    }
}