package com.nusantarian.autoscad.Activity;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nusantarian.autoscad.R;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        findViewById(R.id.img_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_back){
            onBackPressed();
        }
    }
}
