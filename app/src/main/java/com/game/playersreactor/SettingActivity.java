package com.game.playersreactor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SwitchCompat;

public class SettingActivity extends AppCompatActivity {
    private Button backToHomepage;
    private Button gamesSelection;
    private Spinner spinnerDifficulty;
    private SwitchCompat soundBtn;
    public static final String mPrefs = "settPrefs";
    public static final String SOUND = "soundOn";
    public static final String DIFFICULTY = "0";
    private int difficulty;
    private boolean soundOnOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        backToHomepage = findViewById(R.id.back_to_homepage);
        backToHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                finish();
            }
        });
        gamesSelection = findViewById(R.id.games);
        gamesSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGameSelectionActivity();
            }
        });
        soundBtn = findViewById(R.id.switch1);
        soundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
        spinnerDifficulty = findViewById(R.id.difficulty);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.difficulty,
                R.layout.dropdwon_item);
        adapter.setDropDownViewResource(R.layout.dropdwon_item);
        spinnerDifficulty.setAdapter(adapter);
        loadData();
        updateData();
    }

    public void openGameSelectionActivity() {
        Intent gameIntent = new Intent(SettingActivity.this, GameSelection.class);
        startActivity(gameIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("settPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SOUND, soundBtn.isChecked());
        editor.putInt(DIFFICULTY, spinnerDifficulty.getSelectedItemPosition());
        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(mPrefs, MODE_PRIVATE);
        difficulty = sharedPreferences.getInt(DIFFICULTY, 0);
        soundOnOff = sharedPreferences.getBoolean(SOUND, true);
    }

    public void updateData() {
        soundBtn.setChecked(soundOnOff);
        spinnerDifficulty.setSelection(difficulty);
    }
}