package com.nusantarian.autoscad.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nusantarian.autoscad.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ScanActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_foto;
    final static int REQUEST_CODE = 1;
    private StorageReference mStorage;
    private String uid, filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.mulai_scan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = Objects.requireNonNull(mUser).getUid();
        mStorage = FirebaseStorage.getInstance().getReference();
        img_foto = findViewById(R.id.img_foto);
        findViewById(R.id.btn_pick).setOnClickListener(this);
        findViewById(R.id.btn_process).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pick:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.btn_process:
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(ScanActivity.this, ResultActivity.class));
                        finish();
                    }
                }, 3000);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(ScanActivity.this, ResultActivity.class));
                    finish();
                }
            }, 1500);

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
}
