package com.example.archaeological_fieldwork.views.hillfort

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import com.example.archaeological_fieldwork.helpers.checkLocationPermissions
import com.example.archaeological_fieldwork.helpers.createDefaultLocationRequest
import com.example.archaeological_fieldwork.helpers.isPermissionGranted
import com.example.archaeological_fieldwork.helpers.showImagePicker
import com.example.archaeological_fieldwork.models.HillfortModel
import com.example.archaeological_fieldwork.models.Location
import com.example.archaeological_fieldwork.views.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HillfortPresenter(view: BaseView) : BasePresenter(view) {

  var map: GoogleMap? = null
  var hillfort = HillfortModel()
  var defaultLocation = Location(52.245696, -7.139102, 15f)
  var edit = false;
  var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)
  val locationRequest = createDefaultLocationRequest()

  init {
    if (view.intent.hasExtra("hillfort_edit")) {
      edit = true
      hillfort = view.intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
      view.showHillfort(hillfort)
    } else {
      if (checkLocationPermissions(view)) {
        doSetCurrentLocation()
      }
    }
  }

  @SuppressLint("MissingPermission")
  fun doSetCurrentLocation() {
    Log.d("addcrash", locationService.lastLocation.toString())
    locationService.lastLocation.addOnSuccessListener {
      locationUpdate(Location(it.latitude, it.longitude))
    }
  }

  @SuppressLint("MissingPermission")
  fun doResartLocationUpdates() {
    var locationCallback = object : LocationCallback() {
      override fun onLocationResult(locationResult: LocationResult?) {
        if (locationResult != null && locationResult.locations != null) {
          val l = locationResult.locations.last()
          locationUpdate(Location(l.latitude, l.longitude))
        }
      }
    }
    if (!edit) {
      locationService.requestLocationUpdates(locationRequest, locationCallback, null)
    }
  }


  override fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    if (isPermissionGranted(requestCode, grantResults)) {
      doSetCurrentLocation()
    } else {
      locationUpdate(defaultLocation)
    }
  }

  fun doConfigureMap(m: GoogleMap) {
    map = m
    locationUpdate(hillfort.location)
  }

  fun locationUpdate(location: Location) {
    hillfort.location = location
    hillfort.location.zoom = 15f
    map?.clear()
    val options = MarkerOptions().title(hillfort.title).position(LatLng(hillfort.location.lat, hillfort.location.lng))
    map?.addMarker(options)
    map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(hillfort.location.lat, hillfort.location.lng), hillfort.location.zoom))
    view?.showLocation(hillfort.location)
  }


  fun doAddOrSave(title: String, description: String) {
    hillfort.title = title
    hillfort.description = description
    doAsync {
      if (edit) {
        app.hillforts.update(hillfort)
      } else {
        app.hillforts.create(hillfort)
      }
      uiThread {
        view?.finish()
      }
    }
  }

  fun doCancel() {
    view?.finish()
  }

  fun doDelete() {
    doAsync {
      app.hillforts.delete(hillfort)
      uiThread {
        view?.finish()
      }
    }
  }

  fun doSelectImage() {
    view?.let {
      showImagePicker(view!!, IMAGE_REQUEST)
    }
  }

  fun doSetLocation() {
    view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", Location(hillfort.location.lat, hillfort.location.lng, hillfort.location.zoom))
  }

  override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    when (requestCode) {
      IMAGE_REQUEST -> {
        hillfort.image = data.data.toString()
        view?.showHillfort(hillfort)
      }
      LOCATION_REQUEST -> {
        val location = data.extras?.getParcelable<Location>("location")!!
        hillfort.location = location
        locationUpdate(location)
      }
    }
  }
}