package com.cesarcodecrafter.awsamplifyanalytics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_signup.*

const val EXTRA_EMAIL = "com.cesarcodecrafter.awsamplifyanalytics.EMAIL"

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportActionBar?.hide()

        setListeners()
    }

    private fun setListeners() {
        signup_submit_button.setOnClickListener {
            if (signup_email_input.text == null || signup_password_input.text == null ||
                signup_username_input.text == null
            ) {
                return@setOnClickListener
            }

            val email = signup_email_input.text.toString()
            val password = signup_password_input.text.toString()
            val userName = signup_username_input.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && userName.isNotEmpty()) {
                signUp(email = email, password = password, userName = userName)
            }
        }
    }

    private fun signUp(email: String, password: String, userName: String) {
        Amplify.Auth.signUp(
            email,
            password,
            AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.preferredUsername(), userName).build(),
            { goToSignUpConfirmation(email) },
            { showMessage("Sign up failed") }
        )
    }

    private fun goToSignUpConfirmation(email: String) {
        val intent = Intent(this, SignUpConfirmationActivity::class.java).apply {
            putExtra(EXTRA_EMAIL, email)
        }
        startActivity(intent)
    }

    private fun showMessage(message: String?) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG)
                .show()
        }
    }
}