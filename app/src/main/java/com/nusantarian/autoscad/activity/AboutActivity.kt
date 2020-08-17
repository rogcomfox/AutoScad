package com.nusantarian.autoscad.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.test.runner.AndroidJUnit4
import com.nusantarian.autoscad.R

class AboutActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        findViewById<View?>(R.id.img_back).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view.getId() == R.id.img_back) {
            onBackPressed()
        }
    }
}