package com.nusantarian.autoscad.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nusantarian.autoscad.R;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_email, tv_name, tv_phone, tv_uid;
    private FirebaseAuth mAuth;
    private String uid, name, email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String url = "https://firebasestorage.googleapis.com/v0/b/autoscad-2bb87.appspot.com/o/blank-profile-picture-973460_960_720.png?alt=media&token=47a9d648-00dc-4793-afd2-e30375d35186";
        uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        CircleImageView img_profile = findViewById(R.id.img_profile);
        Glide.with(getApplicationContext()).load(url).into(img_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.profil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_email = findViewById(R.id.tv_email);
        tv_name = findViewById(R.id.tv_name);
        tv_phone = findViewById(R.id.tv_phone);
        tv_uid = findViewById(R.id.tv_uid);
        findViewById(R.id.btn_edit).setOnClickListener(this);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    name = Objects.requireNonNull(dataSnapshot.child("Nama").getValue()).toString();
                    email = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
                    phone = Objects.requireNonNull(dataSnapshot.child("Nomor Telepon").getValue()).toString();

                    tv_name.setText(name);
                    tv_uid.setText(uid);
                    tv_email.setText(email);
                    tv_phone.setText(phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_edit){
            startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
        }
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }
}
