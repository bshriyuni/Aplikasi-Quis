package com.example.tuprak31;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ImageView iv_profil;
    private EditText et_user;
    private Button btn_apply;
    User user;

    private ActivityResultLauncher<Intent> imageCaptureLauncher=registerForActivityResult(new
                    ActivityResultContracts.StartActivityForResult(),
            result -> {
                // untuk menghandle data yang dikmbalikan galeri
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        user.setImageuri(uri.toString());
                        iv_profil.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_profil = findViewById(R.id.iv_profil);
        et_user = findViewById(R.id.et_user);
        btn_apply = findViewById(R.id.btn_apply);

        // membuat user baru
        user = new User();
        user.setBestscore(0);

        iv_profil.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            imageCaptureLauncher.launch(Intent.createChooser(intent, "Pilih Gambar"));
        });

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setUsername(String.valueOf(et_user.getText().toString()));

                if (user.getUsername().isEmpty()) {
                    et_user.setError("Can't be empty");
                    Toast.makeText(getApplicationContext(), "Enter your Username!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
            }
        });
    }
}