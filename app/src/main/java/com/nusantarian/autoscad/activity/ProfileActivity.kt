package com.nusantarian.autoscad.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.test.runner.AndroidJUnit4
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nusantarian.autoscad.R
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class ProfileActivity : AppCompatActivity(), View.OnClickListener {
    private var tv_email: TextView? = null
    private var tv_name: TextView? = null
    private var tv_phone: TextView? = null
    private var tv_uid: TextView? = null
    private var mAuth: FirebaseAuth? = null
    private var uid: String? = null
    private var name: String? = null
    private var email: String? = null
    private var phone: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val url = "https://firebasestorage.googleapis.com/v0/b/autoscad-2bb87.appspot.com/o/blank-profile-picture-973460_960_720.png?alt=media&token=47a9d648-00dc-4793-afd2-e30375d35186"
        uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid()
        mAuth = FirebaseAuth.getInstance()
        val mDatabase = FirebaseDatabase.getInstance().reference.child("Users").child(uid)
        val img_profile = findViewById<CircleImageView?>(R.id.img_profile)
        Glide.with(applicationContext).load(url).into(img_profile)
        val toolbar = findViewById<Toolbar?>(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar).setTitle(R.string.profil)
        supportActionBar.setDisplayHomeAsUpEnabled(true)
        tv_email = findViewById(R.id.tv_email)
        tv_name = findViewById(R.id.tv_name)
        tv_phone = findViewById(R.id.tv_phone)
        tv_uid = findViewById(R.id.tv_uid)
        findViewById<View?>(R.id.btn_edit).setOnClickListener(this)
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    name = Objects.requireNonNull(dataSnapshot.child("Nama").value).toString()
                    email = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail()
                    phone = Objects.requireNonNull(dataSnapshot.child("Nomor Telepon").value).toString()
                    tv_name.setText(name)
                    tv_uid.setText(uid)
                    tv_email.setText(email)
                    tv_phone.setText(phone)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onClick(view: View?) {
        if (view.getId() == R.id.btn_edit) {
            startActivity(Intent(this@ProfileActivity, EditProfileActivity::class.java))
        }
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}