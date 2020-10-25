package com.cesarcodecrafter.awsamplifypredictions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_task_retrospective.*

class TaskRetrospective : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_retrospective)

        supportActionBar?.hide()

        setListeners()
    }

    private fun setListeners() {
        add_retro_submit_button.setOnClickListener {
            if (task_retrospective.text.isNotEmpty()) {
                processRetrospective(task_retrospective.text.toString())
            }
        }
    }

    private fun processRetrospective(retroMessage: String) {
        Amplify.Predictions.interpret(
            retroMessage,
            { result -> Handler(Looper.getMainLooper()).post { showAppFeedback(result.sentiment!!.value.toString()) } },
            { error -> showMessage("Interpret failed: ${error.message}") }
        )
    }

    private fun showAppFeedback(feedbackSentiment: String) {
        if (feedbackSentiment == "POSITIVE") {
            feedback_app_textview.text = getString(R.string.congrats_text)
        } else if (feedbackSentiment == "NEGATIVE") {
            feedback_app_textview.text = getString(R.string.supportive_text)
        }
    }

    private fun showMessage(message: String?) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG)
                .show()
        }
    }
}