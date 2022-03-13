package com.elthobhy.elearning.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AlertDialog
import com.elthobhy.elearning.R
import com.elthobhy.elearning.databinding.ActivityLoginBinding
import com.elthobhy.elearning.presentation.forgotpassword.ForgotPasswordActivity
import com.elthobhy.elearning.presentation.main.MainActivity
import com.elthobhy.elearning.presentation.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import hideSoftKeyboard
import showDialogError
import showDialogLoading

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dialogLoading: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Init
        firebaseAuth = FirebaseAuth.getInstance()
        dialogLoading = showDialogLoading(this)
        onAction()
    }

    private fun onAction() {
        binding.apply {
            btnLogin.setOnClickListener {
                val email = binding.etEmailLogin.text.toString().trim()
                val password = binding.etPasswordLogin.text.toString().trim()

                if(checkValidation(email, password)){
                    hideSoftKeyboard(this@LoginActivity,binding.root)
                    loginToServer(email,password)
                }
            }
            btnRegister.setOnClickListener {
                startActivity(Intent(applicationContext, RegisterActivity::class.java))
            }
            btnForgotPasswordLogin.setOnClickListener {
                startActivity(Intent(applicationContext, ForgotPasswordActivity::class.java))
            }
        }
    }

    private fun loginToServer(email: String, password: String) {
        dialogLoading.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                dialogLoading.dismiss()
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
            }
            .addOnFailureListener {
                dialogLoading.dismiss()
                showDialogError(this,it.message.toString())
            }

    }

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        currentUser?.let {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }
    }

    private fun checkValidation(email: String, password: String): Boolean {
        binding.apply {
            when{
                email.isEmpty()->{
                    etEmailLogin.error = getString(R.string.please_field_your_email)
                    etEmailLogin.requestFocus()
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()->{
                    etEmailLogin.error = getString(R.string.please_use_valid_email)
                    etEmailLogin.requestFocus()
                }
                password.isEmpty()->{
                    etPasswordLogin.error = getString(R.string.please_field_your_password)
                    etPasswordLogin.requestFocus()
                }
                password.length < 8 ->{
                    etPasswordLogin.error = getString(R.string.please_field_your_password_more_than_8)
                    etPasswordLogin.requestFocus()
                }
                else -> {
                    return true
                }
            }
        }
        return false
    }
}