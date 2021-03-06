package com.example.sehatmentalku.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sehatmentalku.R
import com.example.sehatmentalku.data.model.Journal
import com.example.sehatmentalku.ui.journal.JournalActivity

class JournalAdapter(
    private val journalList: MutableList<Journal?>?
    ) : RecyclerView.Adapter<JournalAdapter.JournalViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
        val journalInflater = LayoutInflater.from(parent.context).inflate(R.layout.journal_item,
        parent, false)

        return JournalViewHolder(journalInflater)
    }

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
        val currentItem = journalList?.get(position)

        Glide.with(holder.img.context).load(currentItem?.imageUrl).into(holder.img)
        holder.tanggal.text = currentItem?.tanggal
        holder.story.text = currentItem?.story
        holder.prediction.text = if (currentItem?.prediction == 1) "Happy" else "Not Happy"
    }

    override fun getItemCount() = journalList!!.size

    inner class JournalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        val img: ImageView = itemView.findViewById(R.id.journal_img)
        val tanggal: TextView = itemView.findViewById(R.id.journal_tanggal)
        val story: TextView = itemView.findViewById(R.id.journal_story)
        val prediction: TextView = itemView.findViewById(R.id.journal_prediction)
        val intent = Intent(itemView.context, JournalActivity::class.java).apply {}

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val currentItem = journalList?.get(position)
                openDetail(currentItem!!)
            }
        }

        fun openDetail (journal: Journal) {
            intent.putExtra("EXTRA_JOURNAL", journal)
            startActivity(itemView.context, intent, null)
        }
    }
}