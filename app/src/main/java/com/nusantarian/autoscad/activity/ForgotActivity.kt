package com.nusantarian.autoscad.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.test.runner.AndroidJUnit4
import com.google.firebase.auth.FirebaseAuth
import com.nusantarian.autoscad.R
import java.util.*

class ForgotActivity : AppCompatActivity(), View.OnClickListener {
    private var mAuth: FirebaseAuth? = null
    private var et_email: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)
        mAuth = FirebaseAuth.getInstance()
        val toolbar = findViewById<Toolbar?>(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar).setTitle(R.string.lupa_password)
        supportActionBar.setDisplayHomeAsUpEnabled(true)
        findViewById<View?>(R.id.btn_reset).setOnClickListener(this)
        et_email = findViewById(R.id.et_email)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onClick(view: View?) {
        if (view.getId() == R.id.btn_reset) {
            val email = et_email.getText().toString().trim { it <= ' ' }
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Silahkan Masukkan Email Anda", Toast.LENGTH_SHORT).show()
            } else if (!email.matches(emailPattern)) {
                Toast.makeText(this, "Email Tidak Valid", Toast.LENGTH_SHORT).show()
            } else {
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val builder = AlertDialog.Builder(this@ForgotActivity)
                        builder.setMessage("Silahkan Cek Kotak Masuk Email Anda")
                        builder.setPositiveButton("Ok") { dialogInterface, i -> }
                    } else {
                        Toast.makeText(this@ForgotActivity, "Gagal Melakukan Reset Password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}