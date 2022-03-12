package com.elthobhy.elearning.presentation.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.elthobhy.elearning.R
import com.elthobhy.elearning.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onAction()
    }

    private fun onAction() {
        binding.apply {
            btnCloseRegister.setOnClickListener {
                finish()
            }
            btnRegister.setOnClickListener {
                Toast.makeText(this@RegisterActivity, "success",Toast.LENGTH_LONG).show()
            }
        }
    }
}