package com.example.archaeological_fieldwork.models.room

import android.content.Context
import androidx.room.Room
import com.example.archaeological_fieldwork.models.HillfortModel
import com.example.archaeological_fieldwork.models.HillfortStore

class HillfortStoreRoom(val context: Context) : HillfortStore {

  var dao: HillfortDao

  init {
    val database = Room.databaseBuilder(context, Database::class.java, "room_sample.db")
        .fallbackToDestructiveMigration()
        .build()
    dao = database.hillfortDao()
  }

  override fun findAll(): List<HillfortModel> {
    return dao.findAll()
  }

  override fun findById(id: Long): HillfortModel? {
    return dao.findById(id)
  }

  override fun create(hillfort: HillfortModel) {
    dao.create(hillfort)
  }

  override fun update(hillfort: HillfortModel) {
    dao.update(hillfort)
  }

  override fun delete(hillfort: HillfortModel) {
    dao.deleteHillfort(hillfort)
  }

  override fun clear() {
  }
}