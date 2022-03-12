package com.elthobhy.elearning.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elthobhy.elearning.R
import com.elthobhy.elearning.databinding.ActivityLoginBinding
import com.elthobhy.elearning.presentation.forgotpassword.ForgotPasswordActivity
import com.elthobhy.elearning.presentation.main.MainActivity
import com.elthobhy.elearning.presentation.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onAction()
    }

    private fun onAction() {
        binding.apply {
            btnLogin.setOnClickListener {
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
            btnRegister.setOnClickListener {
                startActivity(Intent(applicationContext, RegisterActivity::class.java))
            }
            btnForgotPasswordLogin.setOnClickListener {
                startActivity(Intent(applicationContext, ForgotPasswordActivity::class.java))
            }
        }
    }
}