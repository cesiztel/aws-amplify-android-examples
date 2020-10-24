package com.cesarcodecrafter.awsamplifyauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_sigin.*

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sigin)

        supportActionBar?.hide()

        setListeners()
    }

    private fun setListeners() {
        signin_submit_button.setOnClickListener {
            if (signin_email_input.text == null || signin_password_input.text == null) {
                return@setOnClickListener
            }

            val email = signin_email_input.text.toString()
            val password = signin_password_input.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                signIn(email = email, password = password)
            }
        }

        signin_signup_button.setOnClickListener {
            openSignUp()
        }

        signin_forgotpassword_button.setOnClickListener {
            openForgotPassword()
        }
    }

    private fun openSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun openForgotPassword() {
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }

    private fun signIn(email: String, password: String) {
        Amplify.Auth.signIn(
                email,
                password,
                { result -> if (result.isSignInComplete) openMainAppScreen() else showMessage("Sign in not complete") },
                { error -> showMessage(error.message) }
        )
    }

    private fun openMainAppScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun showMessage(message: String?) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG)
                    .show()
        }
    }
}