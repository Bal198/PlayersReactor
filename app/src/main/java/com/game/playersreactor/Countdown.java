package com.game.playersreactor;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Countdown extends AppCompatActivity {
    private CountDownTimer timer;
    private TextView mTextField;
    private TextView mTextField2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mTextField = findViewById(R.id.countdown_txt);
        mTextField2 = findViewById(R.id.countdown_txt2);
        timer = new CountDownTimer(3500, 1000) {
            @Override
            public void onTick(long l) {
                long m = l / 1000;
                mTextField.setText(String.format("%d", (int) m));
                mTextField2.setText(String.format("%d", (int) m));
            }

            @Override
            public void onFinish() {
                openGameActivity();
            }
        }.start();
    }

    public void openGameActivity() {
        Intent gameIntent = new Intent(this, GameActivity.class);
        startActivity(gameIntent);
    }

    @Override
    public void finish() {
        openGameActivity();
    }
}