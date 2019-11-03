package com.example.archaeological_fieldwork

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.archaeological_fieldwork.activities.HillfortActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import kotlinx.android.synthetic.main.activity_auth.*

const val RC_SIGN_IN = 100
class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(
                    listOf(
                        AuthUI.IdpConfig.EmailBuilder().build()
                    )
                )
                .setLogo(R.drawable.splash_logo)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        message_textview.text = "Signing in"

        if(requestCode == RC_SIGN_IN)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                startActivity(Intent(this, HillfortActivity::class.java))
            }
            else
            {
                val response = IdpResponse.fromResultIntent(data)

                if(response == null)
                {
                    message_textview.text = "Sign in has been cancelled"
                }

                val errorMessage = response?.error?.localizedMessage
                message_textview.text = errorMessage
            }
        }
    }
}
