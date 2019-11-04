package com.example.archaeological_fieldwork.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import com.example.archaeological_fieldwork.helpers.readImageFromPath
import com.example.archaeological_fieldwork.main.MainApp
import kotlinx.android.synthetic.main.activity_main.*


import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

import com.example.archaeological_fieldwork.R
import com.example.archaeological_fieldwork.helpers.readImage
import com.example.archaeological_fieldwork.helpers.showImagePicker
import com.example.archaeological_fieldwork.models.HillfortModel
import com.example.archaeological_fieldwork.models.Location
import org.jetbrains.anko.intentFor

import android.app.DatePickerDialog
import android.icu.util.Calendar

import android.widget.Toast

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfort = HillfortModel()
    lateinit var app: MainApp
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Hillfort Activity started..")

        app = application as MainApp
        var edit = false

        if (intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            hillfortName.setText(hillfort.name)
            description.setText(hillfort.description)
            dateTextView.text = hillfort.date
            visitedCheckBox.isChecked = (hillfort.visited)
            hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
            btnAdd.setText(R.string.save_hillfort)
        }

        btnAdd.setOnClickListener() {
            hillfort.name = hillfortName.text.toString()
            hillfort.description = description.text.toString()
            hillfort.date = dateTextView.text.toString()
            hillfort.visited = visitedCheckBox.isChecked
            if (hillfort.name.isEmpty()) {
                toast(R.string.enter_hillfort_name)
            } else {
                if (edit) {
                    app.hillforts.update(hillfort.copy())
                } else {
                    app.hillforts.create(hillfort.copy())
                }
            }
            info("add Button Pressed: $hillfortName")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

        hillfortLocation.setOnClickListener {
            info("Set Location Pressed")
        }

        hillfortLocation.setOnClickListener {
            val location = Location(52.5120189, -6.2852437, 15f)
            if (hillfort.zoom != 0f) {
                location.lat =  hillfort.lat
                location.lng = hillfort.lng
                location.zoom = hillfort.zoom
            }
            startActivityForResult(intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    hillfort.image = data.getData().toString()
                    hillfortImage.setImageBitmap(readImage(this, resultCode, data))
                    //chooseImage.setText(R.string.change_hillfort_image)
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras?.getParcelable<Location>("location")!!
                    hillfort.lat = location.lat
                    hillfort.lng = location.lng
                    hillfort.zoom = location.zoom
                }
            }
        }
    }

    //Reference: https://tutorial.eyehunts.com/android/android-date-picker-dialog-example-kotlin/
    @RequiresApi(Build.VERSION_CODES.N)
    fun clickDataPicker(view: View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // Display Selected date in Toast
            var dateText = ("""Date Visited: $dayOfMonth - ${monthOfYear + 1} - $year""")
            dateTextView.text = dateText.toString()

        }, year, month, day)
        dpd.show()
    }
}

/*    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sign_out -> signOut()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun signOut(): Boolean {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }
        return true
    }*/
