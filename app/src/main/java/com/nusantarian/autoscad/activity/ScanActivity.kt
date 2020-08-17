package com.nusantarian.autoscad.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.test.runner.AndroidJUnit4
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.nusantarian.autoscad.R
import com.squareup.picasso.Picasso
import java.util.*

class ScanActivity : AppCompatActivity(), View.OnClickListener {
    private var img_foto: ImageView? = null
    private var mStorage: StorageReference? = null
    private var uid: String? = null
    private val filepath: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        val toolbar = findViewById<Toolbar?>(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar).setTitle(R.string.mulai_scan)
        supportActionBar.setDisplayHomeAsUpEnabled(true)
        supportActionBar.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        val mUser = FirebaseAuth.getInstance().currentUser
        uid = Objects.requireNonNull(mUser).getUid()
        mStorage = FirebaseStorage.getInstance().reference
        img_foto = findViewById(R.id.img_foto)
        findViewById<View?>(R.id.btn_pick).setOnClickListener(this)
        findViewById<View?>(R.id.btn_process).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v.getId()) {
            R.id.btn_pick -> {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, REQUEST_CODE)
            }
            R.id.btn_process -> {
                val handler = Handler()
                handler.postDelayed({
                    startActivity(Intent(this@ScanActivity, ResultActivity::class.java))
                    finish()
                }, 3000)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val handler = Handler()
            handler.postDelayed({
                startActivity(Intent(this@ScanActivity, ResultActivity::class.java))
                finish()
            }, 1500)

//            Uri uri = data.getData();
//            final StorageReference filepath = mStorage.child("Photos").child(uid);
//            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(ScanActivity.this, "Uploading Finished...", Toast.LENGTH_SHORT).show();
//                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            Picasso.get().load(uri).fit().centerCrop().into(img_foto);
//                        }
//                    });
//                }
//            });
        }
    }

    companion object {
        const val REQUEST_CODE = 1
    }
}