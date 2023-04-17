package com.example.tuprak31;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity3 extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_pertanyaan, tv_nomorsoal;
    private Button btn_answ1, btn_answ2, btn_answ3, btn_answ4;
    int jumlahpertanyaan = Question.Pertanyaan.length;
    int jawaban = 0;
    int hscore = 0;
    User user;

    private ActivityResultLauncher<Intent> actLauncher=registerForActivityResult(new
                    ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                     untuk menghancurkan hlamn ini dn kmbali ke halamn smblmnya
                    user = result.getData().getParcelableExtra("userResult");
                    setResult(RESULT_OK, new Intent().putExtra("userResult", user));
                    finish();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tv_pertanyaan = findViewById(R.id.tv_pertanyaan);
        btn_answ1 = findViewById(R.id.btn_answ1);
        btn_answ2 = findViewById(R.id.btn_answ2);
        btn_answ3 = findViewById(R.id.btn_answ3);
        btn_answ4 = findViewById(R.id.btn_answ4);
        tv_nomorsoal = findViewById(R.id.tv_nomorsoal);

        btn_answ1.setOnClickListener(this);
        btn_answ2.setOnClickListener(this);
        btn_answ3.setOnClickListener(this);
        btn_answ4.setOnClickListener(this);

        user = getIntent().getParcelableExtra("user");
        pertanyaanbaru();
    }

    void pertanyaanbaru() {
        if (jawaban < jumlahpertanyaan){
            tv_nomorsoal.setText(String.format("Question %d of %d", jawaban +1, jumlahpertanyaan));

            tv_pertanyaan.setText(Question.Pertanyaan[jawaban]);
            btn_answ1.setText(Question.pilihan[jawaban][0]);
            btn_answ2.setText(Question.pilihan[jawaban][1]);
            btn_answ3.setText(Question.pilihan[jawaban][2]);
            btn_answ4.setText(Question.pilihan[jawaban][3]);

            btn_answ1.setBackgroundColor(getResources().getColor(R.color.white));
            btn_answ2.setBackgroundColor(getResources().getColor(R.color.white));
            btn_answ3.setBackgroundColor(getResources().getColor(R.color.white));
            btn_answ4.setBackgroundColor(getResources().getColor(R.color.white));
        }
        else {
            Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
            intent.putExtra("user", user);
            intent.putExtra("score", hscore);
            actLauncher.launch(intent);
        }
    }

    @Override
    public void onClick(View view) {
        //untuk semua fungsi button
        Button button = (Button) view;
        String selectedAns = ((Button) view).getText().toString().trim();
        if (selectedAns.equalsIgnoreCase(Question.jawabanBenar[jawaban])) {
            button.setBackgroundColor(getResources().getColor(R.color.ijo));
            // score trtmbah
            hscore += 100;
        } else {
            button.setBackgroundColor(getResources().getColor(R.color.red));
        }
        // membuat delay 1 detik sblum pidh ke soal slnjtnya
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jawaban++;
                pertanyaanbaru();
            }
        }, 1000);
    }
}