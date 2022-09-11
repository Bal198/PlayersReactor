package com.game.playersreactor;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.ToggleButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;
import java.util.List;

import static com.game.playersreactor.MainActivity.SHARED_PREF;
import static com.game.playersreactor.SettingActivity.mPrefs;
// TODO: 04/09/2022 Da inserire una lista di check box per selezionare i vari giochi; DA FARE PER ULTIMO

public class GameSelection extends AppCompatActivity implements View.OnClickListener {
    public SwitchCompat btn0, btn1, btn2, btn3, btn4, btn5, btn6;
    public List<SwitchCompat> btns;
    public boolean[] value;
    public String[] name = {"area", "five", "dice", "color", "population", "capitals"};
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);

        Button backToSettings = findViewById(R.id.back_to_settings);
        backToSettings.setOnClickListener(v -> {
            saveData();
            finish();
        });

        btn0 = findViewById(R.id.box0);
        btn0.setOnClickListener(this);
        btn1 = findViewById(R.id.box1);
        btn1.setOnClickListener(this);
        btn2 = findViewById(R.id.box2);
        btn2.setOnClickListener(this);
        btn3 = findViewById(R.id.box3);
        btn3.setOnClickListener(this);
        btn4 = findViewById(R.id.box4);
        btn4.setOnClickListener(this);
        btn5 = findViewById(R.id.box5);
        btn5.setOnClickListener(this);
        btn6 = findViewById(R.id.box6);
        btn6.setOnClickListener(this);

        btns = new ArrayList<>();
        btns.add(btn0);
        btns.add(btn1);
        btns.add(btn2);
        btns.add(btn3);
        btns.add(btn4);
        btns.add(btn5);

        value = new boolean[GameActivity.TOTGAMES];

        loadData();
        updateData();
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < GameActivity.TOTGAMES; i++) {
            editor.putBoolean(name[i], btns.get(i).isChecked());
        }
        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(mPrefs, MODE_PRIVATE);
        for (int i = 0; i < GameActivity.TOTGAMES; i++) {
            value[i] = sharedPreferences.getBoolean(name[i], true);
        }
    }

    public void updateData() {
        for (int i = 0; i < GameActivity.TOTGAMES; i++) {
            btns.get(i).setChecked(value[i]);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        for (int i = 0; i < GameActivity.TOTGAMES; i++) {
            //MainActivity.gList.get(i).setInList(btns.get(i).isChecked());
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}