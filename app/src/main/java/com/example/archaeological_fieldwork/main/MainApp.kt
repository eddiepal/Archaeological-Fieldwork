package com.example.archaeological_fieldwork.main

import android.app.Application
import com.example.archaeological_fieldwork.models.HillfortMemStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

  val hillforts = HillfortMemStore()

  override fun onCreate() {
    super.onCreate()
    info("Hillfort started")
  }
}