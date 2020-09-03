package com.example.archaeological_fieldwork.views.hillfortlist

import com.example.archaeological_fieldwork.models.HillfortModel
import com.example.archaeological_fieldwork.views.BasePresenter
import com.example.archaeological_fieldwork.views.BaseView
import com.example.archaeological_fieldwork.views.VIEW
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HillfortListPresenter(view: BaseView) : BasePresenter(view) {

  fun doAddHillfort() {
    view?.navigateTo(VIEW.HILLFORT)
  }

  fun doEditHillfort(hillfort: HillfortModel) {
    view?.navigateTo(VIEW.HILLFORT, 0, "hillfort_edit", hillfort)
  }

  fun doShowHillfortsMap() {
    view?.navigateTo(VIEW.MAPS)
  }

  fun loadHillforts() {
    doAsync {
      val hillforts = app.hillforts.findAll()
      uiThread {
        view?.showHillforts(hillforts)
      }
    }
  }

  fun doLogout() {
    view?.navigateTo(VIEW.LOGIN)
  }
}