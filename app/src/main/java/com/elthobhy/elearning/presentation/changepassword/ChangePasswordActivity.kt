package com.elthobhy.elearning.presentation.changepassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.elthobhy.elearning.R
import com.elthobhy.elearning.databinding.ActivityChangePasswordBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import hideSoftKeyboard
import showDialogError
import showDialogLoading
import showDialogSuccess

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding
    private var currentUser: FirebaseUser? = null
    private lateinit var dialogLoading:AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init
        currentUser = FirebaseAuth.getInstance().currentUser
        dialogLoading = showDialogLoading(this)
        onAction()
    }

    private fun onAction() {
        binding.apply {
            btnChangePassword.setOnClickListener {
                val oldPass = etOldPassword.text.toString().trim()
                val newPass = etNewPassword.text.toString().trim()
                val confirmNewPass = etConfirmNewPassword.text.toString().trim()

                if(validationCheck(oldPass,newPass, confirmNewPass)){
                    hideSoftKeyboard(this@ChangePasswordActivity, binding.root)
                    changeToServer(oldPass, newPass)
                }
            }
            btnCloseChangePassword.setOnClickListener {
                finish()
            }
        }
    }

    private fun changeToServer(oldPass: String, newPass: String) {
        dialogLoading.show()
        currentUser?.let { mCurrentUser->
            val credential = EmailAuthProvider.getCredential(mCurrentUser.email.toString(), oldPass)

            mCurrentUser.reauthenticate(credential)
                .addOnSuccessListener {
                    mCurrentUser.updatePassword(newPass)
                        .addOnSuccessListener {
                            dialogLoading.dismiss()
                            val dialogSuccess = showDialogSuccess(this, getString(R.string.success_change_pass))
                            dialogSuccess.show()

                            Handler(Looper.getMainLooper()).postDelayed({
                                dialogSuccess.dismiss()
                                finish()
                            },1500)
                        }
                        .addOnFailureListener {
                            dialogLoading.dismiss()
                            showDialogError(this, it.message.toString())
                        }
                }
                .addOnFailureListener {
                    dialogLoading.dismiss()
                    showDialogError(this, it.message.toString())
                }
        }
    }

    private fun validationCheck(oldPass: String, newPass: String, confirmNewPass: String): Boolean {
        binding.apply {
            when{
                oldPass.isEmpty()->{
                    etOldPassword.error = getString(R.string.please_field_your_old_password)
                    etOldPassword.requestFocus()
                }
                newPass.isEmpty()->{
                    etNewPassword.error = getString(R.string.please_field_your_new_password)
                    etNewPassword.requestFocus()
                }
                confirmNewPass.isEmpty()->{
                    etConfirmNewPassword.error = getString(R.string.please_field_your_confirm_password)
                    etConfirmNewPassword.requestFocus()
                }
                oldPass.length < 8 ->{
                    etOldPassword.error = getString(R.string.please_input_your_old_password_more_than_8)
                    etOldPassword.requestFocus()
                }
                newPass.length < 8 ->{
                    etNewPassword.error = getString(R.string.please_input_your_new_password_more_than_8)
                    etNewPassword.requestFocus()
                }
                confirmNewPass.length < 8 ->{
                    etConfirmNewPassword.error = getString(R.string.please_input_your_confirm_new_password_more_than_8)
                    etConfirmNewPassword.requestFocus()
                }
                newPass != confirmNewPass ->{
                    etNewPassword.error = getString(R.string.your_password_didnt_match)
                    etNewPassword.requestFocus()
                    etConfirmNewPassword.error = getString(R.string.your_password_didnt_match)
                    etConfirmNewPassword.requestFocus()
                }
                else->{
                    return true
                }
            }
        }
        return false
    }
}