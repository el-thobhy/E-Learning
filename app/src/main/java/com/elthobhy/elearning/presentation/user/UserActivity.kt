package com.elthobhy.elearning.presentation.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.ACTION_LOCALE_SETTINGS
import android.util.Log
import com.bumptech.glide.Glide
import com.elthobhy.elearning.R
import com.elthobhy.elearning.databinding.ActivityUserBinding
import com.elthobhy.elearning.models.User
import com.elthobhy.elearning.presentation.changepassword.ChangePasswordActivity
import com.elthobhy.elearning.presentation.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import showDialogError

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userDatabase : DatabaseReference
    private var user : FirebaseUser? = null

    private var listener = object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            hideLoading()
            val user = snapshot.getValue(User::class.java)
            user?.let {
                binding.apply {
                    tvNameUser.text = it.nameUser
                    Glide.with(this@UserActivity)
                        .load(it.avatarUser)
                        .placeholder(android.R.color.darker_gray)
                        .into(ivUser)
                    tvEmailUser.text = it.emailUser
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            hideLoading()
            Log.e("error", "onCancelled: ${error.message}", )
            showDialogError(this@UserActivity, error.message)
        }
    }

    private fun hideLoading() {
        binding.swipeUser.isRefreshing = false
    }
    private fun showLoading() {
        binding.swipeUser.isRefreshing = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Init
        user = FirebaseAuth.getInstance().currentUser
        userDatabase = FirebaseDatabase.getInstance("https://elearning-project-f0895-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users")
        firebaseAuth = FirebaseAuth.getInstance()
        getDataFirebase()
        onAction()

    }

    private fun getDataFirebase() {
        showLoading()
        userDatabase
            .child(user?.uid.toString())
            .addValueEventListener(listener)
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
                firebaseAuth.signOut()
                startActivity(Intent(this@UserActivity, LoginActivity::class.java))
                finishAffinity()
            }
            swipeUser.setOnRefreshListener {
                getDataFirebase()
            }
        }
    }
}