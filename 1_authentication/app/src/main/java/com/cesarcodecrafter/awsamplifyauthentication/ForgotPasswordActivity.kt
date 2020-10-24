package com.cesarcodecrafter.awsamplifyauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_sigin.*

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        supportActionBar?.hide()

        setListeners()
    }

    private fun setListeners() {
        forgot_password_submit_button.setOnClickListener {
            if (forgot_password_email_input.text == null) {
                return@setOnClickListener
            }

            val email = forgot_password_email_input.text.toString()

            if (email.isNotEmpty()) {
                setConfirmationResetPassword(email = email)
            }
        }
    }

    private fun setConfirmationResetPassword(email: String) {
        Amplify.Auth.resetPassword(
            email,
            { openConfirmationResetPassword(email) },
            { error -> showMessage(error.toString()) }
        )
    }

    private fun openConfirmationResetPassword(email: String) {
        val intent = Intent(this, ResetPasswordActivity::class.java)
        startActivity(intent)
    }

    private fun showMessage(message: String?) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG)
                .show()
        }
    }
}