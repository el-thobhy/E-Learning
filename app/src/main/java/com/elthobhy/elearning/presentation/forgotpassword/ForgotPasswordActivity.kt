package com.elthobhy.elearning.presentation.forgotpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.elthobhy.elearning.R
import com.elthobhy.elearning.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onAction()
    }

    private fun onAction() {
        binding.apply {
            btnCloseForgotPassword.setOnClickListener {
                finish()
            }
            btnForgotPassword.setOnClickListener {
                Toast.makeText(this@ForgotPasswordActivity, "Forgot password", Toast.LENGTH_LONG).show()
            }
        }
    }
}