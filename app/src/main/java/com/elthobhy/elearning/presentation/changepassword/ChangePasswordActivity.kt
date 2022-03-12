package com.elthobhy.elearning.presentation.changepassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.elthobhy.elearning.R
import com.elthobhy.elearning.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onAction()
    }

    private fun onAction() {
        binding.apply {
            btnChangePassword.setOnClickListener {
                Toast.makeText(this@ChangePasswordActivity, "Change Password", Toast.LENGTH_LONG).show()
            }
            btnCloseChangePassword.setOnClickListener {
                finish()
            }
        }
    }
}