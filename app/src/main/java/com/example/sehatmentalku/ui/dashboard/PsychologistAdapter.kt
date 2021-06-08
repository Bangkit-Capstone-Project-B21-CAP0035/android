package com.example.sehatmentalku.ui.dashboard

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sehatmentalku.R
import com.example.sehatmentalku.data.model.Psychologist

class PsychologistAdapter (
    private val psychologistList: MutableList<Psychologist?>?
    ) : RecyclerView.Adapter<PsychologistAdapter.PsychologistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PsychologistViewHolder {
        val journalInflater = LayoutInflater.from(parent.context).inflate(
            R.layout.psychologist_item,
            parent, false)

        return PsychologistViewHolder(journalInflater)
    }

    override fun onBindViewHolder(holder: PsychologistViewHolder, position: Int) {
        val currentItem = psychologistList?.get(position)

        Glide.with(holder.img.context).load(currentItem?.imageUrl).into(holder.img)
        holder.name.text = currentItem?.name
    }

    override fun getItemCount() = psychologistList!!.size

    inner class PsychologistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener{
        val img: ImageView = itemView.findViewById(R.id.psych_img)
        val name: TextView = itemView.findViewById(R.id.psych_name)
//        val intent = Intent(itemView.context, JournalActivity::class.java).apply {}

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.d("TAG", "Kliked")
//            val position = absoluteAdapterPosition
//            if (position != RecyclerView.NO_POSITION) {
//                val currentItem = psychologistList?.get(position)
//                openDetail(currentItem!!)
//            }
        }

//        fun openDetail (psychologist: Psychologist) {
//            intent.putExtra("EXTRA_JOURNAL", psychologist)
//            ContextCompat.startActivity(itemView.context, intent, null)
//        }
    }
}