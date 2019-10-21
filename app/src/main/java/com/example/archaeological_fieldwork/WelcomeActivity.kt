package com.example.archaeological_fieldwork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.lang.Exception

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        var background = object : Thread()
        {
            override fun run()
            {
                try
                {
                    Thread.sleep(5000)
                    val intent = Intent(baseContext, MainActivity::class.java)
                } catch (e: Exception)
                {
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}
