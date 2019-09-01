package com.nusantarian.autoscad.Activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nusantarian.autoscad.R;

import java.util.Objects;

public class ChangePassActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText et_password, et_confpass;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.ubah_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        et_password = findViewById(R.id.et_password);
        et_confpass = findViewById(R.id.et_confpass);
        findViewById(R.id.btn_change).setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_change){
            String password = et_password.getText().toString();
            String confpass = et_confpass.getText().toString();

            if (TextUtils.isEmpty(password) && TextUtils.isEmpty(confpass)){
                Toast.makeText(this, "Silahkan Masukkan Data Anda", Toast.LENGTH_SHORT).show();
            }
            else if (password.length() < 6){
                Toast.makeText(this, "Password Terlalu Pendek, Minimal 6 Karakter", Toast.LENGTH_SHORT).show();
            }
            else if (!password.equals(confpass)){
                Toast.makeText(this, "Password Tidak Cocok", Toast.LENGTH_SHORT).show();
            }else {
                mUser.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ChangePassActivity.this, "Password Berhasil Diganti", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ChangePassActivity.this, "Gagal Mengganti Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
}
