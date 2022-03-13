package com.elthobhy.elearning.presentation.forgotpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import androidx.appcompat.app.AlertDialog
import com.elthobhy.elearning.R
import com.elthobhy.elearning.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import hideSoftKeyboard
import showDialogError
import showDialogLoading
import showDialogSuccess

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dialogLoading: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Init
        firebaseAuth = FirebaseAuth.getInstance()
        dialogLoading = showDialogLoading(this)
        onAction()
    }

    private fun onAction() {
        binding.apply {
            btnCloseForgotPassword.setOnClickListener {
                finish()
            }
            btnForgotPassword.setOnClickListener {
                val email = etEmailForgotPassword.text.toString().trim()
                if(checkValidation(email)){
                    hideSoftKeyboard(this@ForgotPasswordActivity,binding.root)
                    forgotPasswordToServer(email)
                }
            }
        }
    }

    private fun forgotPasswordToServer(email: String) {
        dialogLoading.show()
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                dialogLoading.dismiss()
                val successDialog = showDialogSuccess(this,getString(R.string.success_forgot_pass))
                successDialog.show()

                Handler(Looper.getMainLooper()).postDelayed({
                    successDialog.dismiss()
                    finish()
                },1500)
            }
            .addOnFailureListener {
                dialogLoading.dismiss()
                showDialogError(this,it.message.toString())
            }
    }

    private fun checkValidation(email: String): Boolean {
        binding.apply {
            when{
                email.isEmpty()->{
                    etEmailForgotPassword.error = getString(R.string.please_field_your_email)
                    etEmailForgotPassword.requestFocus()
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()->{
                    etEmailForgotPassword.error = getString(R.string.please_use_valid_email)
                    etEmailForgotPassword.requestFocus()
                }
                else->{
                    return true
                }
            }
        }
        return false
    }
}