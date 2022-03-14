package com.elthobhy.elearning.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.elthobhy.elearning.adapter.MaterialsAdapter
import com.elthobhy.elearning.databinding.ActivityMainBinding
import com.elthobhy.elearning.models.Material
import com.elthobhy.elearning.models.User
import com.elthobhy.elearning.presentation.content.ContentActivity
import com.elthobhy.elearning.presentation.user.UserActivity
import com.elthobhy.elearning.repository.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import disable
import enabled
import gone
import showDialogError
import visible

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var materialsAdapter: MaterialsAdapter
    private lateinit var userDatabase : DatabaseReference
    private lateinit var materialDatabase : DatabaseReference
    private var user : FirebaseUser? = null

    private var listener = object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            hideLoading()
            val user = snapshot.getValue(User::class.java)
            user?.let {
                binding.apply {
                    tvNameUser.text = it.nameUser
                    Glide.with(this@MainActivity)
                        .load(it.avatarUser)
                        .placeholder(android.R.color.darker_gray)
                        .into(ivUser)
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            hideLoading()
            Log.e("error", "onCancelled: ${error.message}", )
            showDialogError(this@MainActivity,error.message)
        }
    }

    private var materialListener = object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            hideLoading()
            if(snapshot.value != null){
                showData()
                val json = Gson().toJson(snapshot.value)
                val type = object: TypeToken<MutableList<Material>>(){}.type
                val materials = Gson().fromJson<MutableList<Material>>(json,type)

                materials?.let{
                    materialsAdapter.materials = it
                }
            }else{
                showEmptyData()
            }

        }

        override fun onCancelled(error: DatabaseError) {
            hideLoading()
            Log.e("error", "onCancelled: ${error.message}", )
            showDialogError(this@MainActivity,error.message)
        }
    }

    private fun showEmptyData() {
        binding.ivNoData.visible()
        binding.rvMaterials.gone()
        binding.etSearchMaterial.disable()
    }

    private fun showData() {
        binding.ivNoData.gone()
        binding.rvMaterials.visible()
        binding.etSearchMaterial.enabled()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init
        user = FirebaseAuth.getInstance().currentUser
        materialDatabase = FirebaseDatabase.getInstance("https://elearning-project-f0895-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("materials")
        userDatabase = FirebaseDatabase.getInstance("https://elearning-project-f0895-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users")
        materialsAdapter = MaterialsAdapter()

        //getDataMaterial() //dummy local
        getDataFirebase()
        onAction()
    }

    private fun getDataFirebase() {
        showLoading()
        userDatabase
            .child(user?.uid.toString())
            .addValueEventListener(listener)
        materialDatabase.addValueEventListener(materialListener)
        binding.rvMaterials.adapter = materialsAdapter
    }

    //dummy local
    /*private fun getDataMaterial() {
        showLoading()
        val materials = Repository.getMaterials(this)
        Log.e("getdata", "getDataMaterial: $materials", )
        Handler(Looper.getMainLooper()).postDelayed({
            hideLoading()
            materials?.let{
                materialsAdapter.materials = it
                if(intent != null){
                    val position = intent.getIntExtra(EXTRA_POSITION,0)
                    binding.rvMaterials.smoothScrollToPosition(position)
                }
            }
        },1200)

        binding.rvMaterials.adapter = materialsAdapter
    }*/
    

    private fun showLoading() {
        binding.swipeMain.isRefreshing = true
    }

    private fun hideLoading() {
        binding.swipeMain.isRefreshing = false
    }

    private fun onAction() {
        binding.apply {
            ivUser.setOnClickListener {
                startActivity(Intent(this@MainActivity, UserActivity::class.java))
            }
            etSearchMaterial.addTextChangedListener { 
                materialsAdapter.filter.filter(it.toString())
            }
            etSearchMaterial.setOnEditorActionListener { _, actionId, _ ->
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    val dataSearch = etSearchMaterial.text.toString().trim()
                    materialsAdapter.filter.filter(dataSearch)
                    return@setOnEditorActionListener  true
                }
                return@setOnEditorActionListener false
            }

            swipeMain.setOnRefreshListener {
                getDataFirebase()
                //dummy local
                //getDataMaterial()
            }
        }
        materialsAdapter.onClick { material, position ->
            val intent = Intent(this, ContentActivity::class.java)
            intent.putExtra(ContentActivity.EXTRA_MATERIAL,material)
            intent.putExtra(ContentActivity.EXTRA_POSITION, position)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        if(intent != null){
            val position = intent.getIntExtra(EXTRA_POSITION,0)
            binding.rvMaterials.smoothScrollToPosition(position)
        }
    }

    companion object{
        const val EXTRA_POSITION ="extra_position"
    }
}