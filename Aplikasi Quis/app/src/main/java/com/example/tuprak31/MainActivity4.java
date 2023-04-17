package com.example.tuprak31;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity4 extends AppCompatActivity {
    private TextView tv_gg, tv_score, tv_bscore;
    private Button btn_back;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        tv_gg = findViewById(R.id.tv_gg);
        tv_bscore = findViewById(R.id.tv_bscore);
        tv_score = findViewById(R.id.tv_score);
        btn_back = findViewById(R.id.btn_back);

        //mengambil skor dri hlmn sblmny
        int bscore = getIntent().getIntExtra("score", 0);
        user = getIntent().getParcelableExtra("user");
        // jika score skrg lbih bsr dripd best score sblumnya maka best score user dengan yang baru
        if (bscore > user.getBestscore()) {
            user.setBestscore(bscore);
        }
        tv_gg.setText("GGWP " + user.getUsername());
        tv_bscore.setText(String.valueOf(user.getBestscore()));
        tv_score.setText(String.valueOf(bscore));

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK, new Intent().putExtra("userResult", user));
                finish();
            }
        });
    }
}