package com.nusantarian.autoscad.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nusantarian.autoscad.R;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText et_email, et_name, et_phone, et_password, et_confpass;
    private String uid, name, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        et_email = findViewById(R.id.et_email);
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        et_confpass = findViewById(R.id.et_confpass);
        findViewById(R.id.btn_register).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_register){
            String email = et_email.getText().toString().trim();
            name = et_name.getText().toString();
            phone = et_phone.getText().toString();
            String password = et_password.getText().toString();
            String confpass = et_confpass.getText().toString();
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (TextUtils.isEmpty(email)&&TextUtils.isEmpty(name)&&TextUtils.isEmpty(phone)&&TextUtils.isEmpty(password)&&TextUtils.isEmpty(confpass)){
                Toast.makeText(this, "Silahkan Isi Data Diri Anda", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(email)){
                Toast.makeText(this, "Silahkan Isi Email Anda", Toast.LENGTH_SHORT).show();
            }
            else if (!email.matches(emailPattern)){
                Toast.makeText(this, "Email Tidak Valid", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(name)){
                Toast.makeText(this, "Silahkan Isi Nama Anda", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(phone)){
                Toast.makeText(this, "Silahkan Isi Nomor Telepon Anda", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(password)){
                Toast.makeText(this, "Silahkan Isi Password Anda", Toast.LENGTH_SHORT).show();
            }
            else if (password.length() < 6){
                Toast.makeText(this, "Password Terlalu Pendek, Minimal 6 Karakter", Toast.LENGTH_SHORT).show();
            }
            else if (!password.equals(confpass)){
                Toast.makeText(this, "Password Tidak Sama", Toast.LENGTH_SHORT).show();
            }else {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                            mDatabase.child(uid).child("Nama").setValue(name);
                            mDatabase.child(uid).child("Nomor Telepon").setValue(phone);
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            Toast.makeText(RegisterActivity.this, "Silahkan Login Terlebih Dahulu!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RegisterActivity.this, "Gagal Melakukan Pendaftaran", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
}
