package com.elthobhy.elearning.presentation.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.elthobhy.elearning.R
import com.elthobhy.elearning.databinding.ActivityRegisterBinding
import com.elthobhy.elearning.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import hideSoftKeyboard
import showDialogError
import showDialogLoading
import showDialogSuccess

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userDatabase : DatabaseReference
    private lateinit var dialogLoading : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Init
        firebaseAuth = FirebaseAuth.getInstance()
        userDatabase = FirebaseDatabase
            .getInstance("https://elearning-project-f0895-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("users")
        dialogLoading = showDialogLoading(this)
        onAction()
    }

    private fun onAction() {
        binding.apply {
            btnCloseRegister.setOnClickListener {
                finish()
            }
            btnRegister.setOnClickListener {
                val name = etNameRegister.text.toString().trim()
                val email = etEmailRegister.text.toString().trim()
                val pass = etPasswordRegister.text.toString().trim()
                val passConfirm = etConfirmPasswordRegister.text.toString().trim()

                if(checkValidation(name, email, pass, passConfirm)){
                    hideSoftKeyboard(this@RegisterActivity,binding.root)
                    registerToServer(name,email,pass)
                }
            }
        }
    }

    private fun registerToServer(name: String, email: String, pass: String) {
        dialogLoading.show()
        firebaseAuth.createUserWithEmailAndPassword(email,pass)
            .addOnSuccessListener {
                val uid = firebaseAuth.uid
                val imageUser = "https://ui-avatars.com/api/?background=218B5E&color=fff&size=100&rounded=true&name=$name"
                val user = User(
                    nameUser = name,
                    avatarUser = imageUser,
                    emailUser = email,
                    uidUser = uid
                )
                userDatabase
                    .child(uid.toString())
                    .setValue(user)
                    .addOnSuccessListener {
                        dialogLoading.dismiss()
                        val dialogSuccess = showDialogSuccess(this,getString(R.string.success))
                        dialogSuccess.show()

                        Handler(Looper.getMainLooper()).postDelayed({
                            dialogSuccess.dismiss()
                            finish()
                        },1500)
                    }
                    .addOnFailureListener {
                        dialogLoading.dismiss()
                        showDialogError(this,it.message.toString())
                    }
            }
            .addOnFailureListener {
                dialogLoading.dismiss()
                showDialogError(this, it.message.toString())
            }
    }

    private fun checkValidation(
        name: String,
        email: String,
        pass: String,
        passConfirm: String
    ): Boolean {
        binding.apply {
            when{
                name.isEmpty()->{
                    etNameRegister.error = getString(R.string.please_field_your_name)
                    etNameRegister.requestFocus()
                }
                email.isEmpty()->{
                    etEmailRegister.error = getString(R.string.please_field_your_email)
                    etEmailRegister.requestFocus()
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()->{
                    etEmailRegister.error = getString(R.string.please_use_valid_email)
                    etEmailRegister.requestFocus()
                }
                pass.isEmpty()->{
                    etPasswordRegister.error = getString(R.string.please_field_your_password)
                    etPasswordRegister.requestFocus()
                }
                pass.length < 8 ->{
                    etPasswordRegister.error = getString(R.string.please_field_your_password_more_than_8)
                    etPasswordRegister.requestFocus()
                }
                passConfirm.isEmpty()->{
                    etConfirmPasswordRegister.error = getString(R.string.please_field_your_confirm_password)
                    etConfirmPasswordRegister.requestFocus()
                }
                passConfirm.length<8->{
                    etConfirmPasswordRegister.error = getString(R.string.please_field_your_confirm_password_more_than_8)
                    etConfirmPasswordRegister.requestFocus()
                }
                pass != passConfirm ->{
                    etConfirmPasswordRegister.error = getString(R.string.your_password_didnt_match)
                    etPasswordRegister.error = getString(R.string.your_password_didnt_match)
                }else->{
                    return true
                }

            }
        }
        return false
    }
}