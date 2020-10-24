package com.cesarcodecrafter.awsamplifyauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.activity_signup_confirmation.*

class ResetPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        supportActionBar?.hide()

        setListeners()
    }

    private fun setListeners() {
        reset_password_submit_button.setOnClickListener {
            if (reset_password_code_input.text == null && reset_password_password_input.text == null) {
                return@setOnClickListener
            }

            val password = reset_password_password_input.text.toString()
            val code = reset_password_code_input.text.toString()

            if (code.isNotEmpty() && password.isNotEmpty()) {
                resetPasswordConfirmation(code = code, password = password)
            }
        }
    }

    private fun resetPasswordConfirmation(code: String, password: String) {
        Amplify.Auth.confirmResetPassword(
            password,
            code,
            { openSignInApp() },
            { error -> showMessage(error.message) }
        )
    }

    private fun openSignInApp() {
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