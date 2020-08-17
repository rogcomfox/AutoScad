package com.nusantarian.autoscad.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.test.runner.AndroidJUnit4
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nusantarian.autoscad.R
import java.util.*

class ChangePassActivity : AppCompatActivity(), View.OnClickListener {
    private var et_password: EditText? = null
    private var et_confpass: EditText? = null
    private var mUser: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pass)
        val toolbar = findViewById<Toolbar?>(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar).setTitle(R.string.ubah_password)
        supportActionBar.setDisplayHomeAsUpEnabled(true)
        val mAuth = FirebaseAuth.getInstance()
        mUser = mAuth.currentUser
        et_password = findViewById(R.id.et_password)
        et_confpass = findViewById(R.id.et_confpass)
        findViewById<View?>(R.id.btn_change).setOnClickListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        return true
    }

    override fun onClick(view: View?) {
        if (view.getId() == R.id.btn_change) {
            val password = et_password.getText().toString()
            val confpass = et_confpass.getText().toString()
            if (TextUtils.isEmpty(password) && TextUtils.isEmpty(confpass)) {
                Toast.makeText(this, "Silahkan Masukkan Data Anda", Toast.LENGTH_SHORT).show()
            } else if (password.length < 6) {
                Toast.makeText(this, "Password Terlalu Pendek, Minimal 6 Karakter", Toast.LENGTH_SHORT).show()
            } else if (password != confpass) {
                Toast.makeText(this, "Password Tidak Cocok", Toast.LENGTH_SHORT).show()
            } else {
                mUser.updatePassword(password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@ChangePassActivity, "Password Berhasil Diganti", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@ChangePassActivity, "Gagal Mengganti Password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}