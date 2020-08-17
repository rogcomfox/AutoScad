package com.nusantarian.autoscad.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.test.runner.AndroidJUnit4
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.nusantarian.autoscad.R
import java.util.*

class EditProfileActivity : AppCompatActivity(), View.OnClickListener {
    private var mAuth: FirebaseAuth? = null
    private val mDatabase: DatabaseReference? = null
    private val mStorage: StorageReference? = null
    private var nama: String? = null
    private var phone: String? = null
    private var email: String? = null
    private val uid: String? = null
    private var et_nama: EditText? = null
    private var et_phone: EditText? = null
    private var et_email: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        val toolbar = findViewById<Toolbar?>(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar).setTitle(R.string.pengaturan_profil)
        supportActionBar.setDisplayHomeAsUpEnabled(true)
        mAuth = FirebaseAuth.getInstance()
        et_nama = findViewById(R.id.et_nama)
        et_email = findViewById(R.id.et_email)
        et_phone = findViewById(R.id.et_phone)
        findViewById<View?>(R.id.btn_change).setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.editprofile_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_changepass) {
            startActivity(Intent(this@EditProfileActivity, ChangePassActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View?) {
        if (view.getId() == R.id.btn_change) {
            nama = et_nama.getText().toString()
            email = et_email.getText().toString()
            phone = et_phone.getText().toString()
        }
    }
}