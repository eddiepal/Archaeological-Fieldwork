package com.example.archaeological_fieldwork.models.mem

import com.example.archaeological_fieldwork.models.HillfortModel
import com.example.archaeological_fieldwork.models.HillfortStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
  return lastId++
}

class HillfortMemStore : HillfortStore, AnkoLogger {

  val hillforts = ArrayList<HillfortModel>()

  override fun delete(hillfort: HillfortModel) {
    hillforts.remove(hillfort)
  }

  override fun findById(id:Long) : HillfortModel? {
    val foundHillfort: HillfortModel? = hillforts.find { it.id == id }
    return foundHillfort
  }

  override fun findAll(): List<HillfortModel> {
    return hillforts
  }

  override fun create(hillfort: HillfortModel) {
    hillfort.id = getId()
    hillforts.add(hillfort)
    logAll()
  }

  override fun update(hillfort: HillfortModel) {
    var foundHillfort: HillfortModel? = hillforts.find { p -> p.id == hillfort.id }
    if (foundHillfort != null) {
      foundHillfort.name = hillfort.name
      foundHillfort.description = hillfort.description
      foundHillfort.image = hillfort.image
      foundHillfort.visited = hillfort.visited
      foundHillfort.date = hillfort.date
/*      foundHillfort.lat = hillfort.lat
      foundHillfort.lng = hillfort.lng
      foundHillfort.zoom = hillfort.zoom*/
    }
  }

  internal fun logAll() {
    hillforts.forEach { info("${it}") }
  }
}