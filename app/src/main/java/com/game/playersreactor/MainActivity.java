package com.game.playersreactor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

import static com.game.playersreactor.GameActivity.TOTGAMES;

public class MainActivity extends AppCompatActivity {
    private Button settingButton;
    private Button aboutMeButton;
    private Button playButton;
    private RadioGroup radioGroup;
    public static final String SHARED_PREF = "settPrefs";
    public static final String NUMPLAYERS = "num_players";
    private int numPlayers;
    public static boolean[] selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        selected = new boolean[TOTGAMES];
        for(int i = 0 ; i<TOTGAMES; i++){
            selected[i] = true;
        }

        radioGroup = findViewById(R.id.radio_group);
        settingButton = findViewById(R.id.setting_button);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettingActivity();
            }
        });

        aboutMeButton = findViewById(R.id.about_me_button);
        aboutMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAboutMeActivity();
            }
        });

        playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                updateData();
                openCountdownActivity();
            }
        });
        loadData();
        updateData();
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NUMPLAYERS, radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId())));
        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        numPlayers = sharedPreferences.getInt(NUMPLAYERS, 0);
    }

    public void updateData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        int i = sharedPreferences.getInt(NUMPLAYERS, 0);
        ((RadioButton) ((RadioGroup) findViewById(R.id.radio_group)).getChildAt(i)).setChecked(true);
    }

    public void openSettingActivity() {
        Intent settingIntent = new Intent(this, SettingActivity.class);
        startActivity(settingIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void openAboutMeActivity() {
        Intent intent = new Intent(this, AboutMe.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openCountdownActivity() {
        Intent countdownIntent = new Intent(this, Countdown.class);
        startActivity(countdownIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
