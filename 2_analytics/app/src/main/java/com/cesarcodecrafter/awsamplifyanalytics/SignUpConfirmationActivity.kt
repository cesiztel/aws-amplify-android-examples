package com.cesarcodecrafter.awsamplifyanalytics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_signup_confirmation.*

class SignUpConfirmationActivity : AppCompatActivity() {

    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_confirmation)

        supportActionBar?.hide()

        email = intent.getStringExtra(EXTRA_EMAIL)

        setListeners()
    }

    private fun setListeners() {
        signup_confirmation_submit_button.setOnClickListener {
            if (signup_confirmation_code_input.text == null) {
                return@setOnClickListener
            }

            val code = signup_confirmation_code_input.text.toString()

            if (code.isNotEmpty() && email != null) {
                signUpConfirmation(code = code)
            }
        }
    }

    private fun signUpConfirmation(code: String) {
        email?.let {confirmation_email ->
            Amplify.Auth.confirmSignUp(
                confirmation_email,
                code,
                { openSignInScreen() },
                { error -> showMessage(error.message) }
            )
        }
    }

    private fun openSignInScreen() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

    private fun showMessage(message: String?) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG)
                .show()
        }
    }
}