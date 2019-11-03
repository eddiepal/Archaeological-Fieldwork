package com.example.archaeological_fieldwork.activities

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.archaeological_fieldwork.models.HillfortModel
import kotlinx.android.synthetic.main.card_hillfort.view.*
import com.example.archaeological_fieldwork.R
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.card_hillfort.view.description
import kotlinx.android.synthetic.main.card_hillfort.view.hillfortName

interface HillfortListener {
  fun onPlacemarkClick(hillfort: HillfortModel)
}

class HillfortAdapter constructor(private var hillforts: List<HillfortModel>,
                                  private val listener: HillfortListener) : RecyclerView.Adapter<HillfortAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_hillfort, parent, false))
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val hillfort = hillforts[holder.adapterPosition]
    holder.bind(hillfort, listener)
  }

  override fun getItemCount(): Int = hillforts.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(hillfort: HillfortModel, listener: HillfortListener) {
      itemView.hillfortName.text = hillfort.name
      itemView.description.text = hillfort.description
      itemView.visitedCheckBox?.isChecked = hillfort.visited
      itemView.setOnClickListener { listener.onPlacemarkClick(hillfort) }
    }
  }
}