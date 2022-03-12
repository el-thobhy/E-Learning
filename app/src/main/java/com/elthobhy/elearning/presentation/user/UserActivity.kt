package com.elthobhy.elearning.presentation.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.ACTION_LOCALE_SETTINGS
import com.elthobhy.elearning.R
import com.elthobhy.elearning.databinding.ActivityUserBinding
import com.elthobhy.elearning.presentation.changepassword.ChangePasswordActivity
import com.elthobhy.elearning.presentation.login.LoginActivity

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onAction()
    }

    private fun onAction() {
        binding.apply {
            btnClose.setOnClickListener {
                finish()
            }
            btnChangeLanguage.setOnClickListener {
                startActivity(Intent(ACTION_LOCALE_SETTINGS))
            }
            btnChangePassword.setOnClickListener {
                startActivity(Intent(this@UserActivity,ChangePasswordActivity::class.java))
            }
            btnLogoutUser.setOnClickListener {
                startActivity(Intent(this@UserActivity, LoginActivity::class.java))
                finishAffinity()
            }
        }
    }
}