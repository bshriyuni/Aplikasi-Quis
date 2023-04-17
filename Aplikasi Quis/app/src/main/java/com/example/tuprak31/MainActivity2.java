package com.example.tuprak31;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    private ImageView iv_profil;
    private TextView tv_nama, tv_bscore;
    private Button btn_play;
    User user;

    private ActivityResultLauncher<Intent> actLauncher=registerForActivityResult(new
                    ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    user = result.getData().getParcelableExtra("userResult");
                    tv_bscore.setText(String.valueOf("Best Score : " + user.getBestscore()));
                    btn_play.setText("PLAY AGAIN!");
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        iv_profil = findViewById(R.id.iv_profil);
        tv_nama = findViewById(R.id.tv_nama);
        tv_bscore = findViewById(R.id.tv_bscore);
        btn_play = findViewById(R.id.btn_play);

        user = getIntent().getParcelableExtra("user");
        tv_nama.setText(user.getUsername());


        if (user.getImageuri() != null) {
            iv_profil.setImageURI(Uri.parse(user.getImageuri()));
        }
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                intent.putExtra("user", user);
                actLauncher.launch(intent);
            }
        });
    }
}