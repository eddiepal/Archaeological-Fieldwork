package com.example.archaeological_fieldwork.main

import android.app.Application
import com.example.archaeological_fieldwork.models.HillfortStore
import com.example.archaeological_fieldwork.models.room.HillfortStoreRoom
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

  lateinit var hillforts: HillfortStore

  override fun onCreate() {
    super.onCreate()
    hillforts = HillfortStoreRoom(applicationContext)
    info("Hillfort started")
  }
}