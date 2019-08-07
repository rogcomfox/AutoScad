package com.nusantarian.autoscad.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.nusantarian.autoscad.R;

import java.util.Objects;

public class ForgotActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.lupa_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.btn_reset).setOnClickListener(this);
        et_email = findViewById(R.id.et_email);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_reset){
            String email = et_email.getText().toString().trim();
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (TextUtils.isEmpty(email)){
                Toast.makeText(this, "Silahkan Masukkan Email Anda", Toast.LENGTH_SHORT).show();
            }
            else if (!email.matches(emailPattern)){
                Toast.makeText(this, "Email Tidak Valid", Toast.LENGTH_SHORT).show();
            }else {
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            AlertDialog.Builder builder = new AlertDialog.Builder(ForgotActivity.this);
                            builder.setMessage("Silahkan Cek Kotak Masuk Email Anda");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                        }else {
                            Toast.makeText(ForgotActivity.this, "Gagal Melakukan Reset Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
}
