package com.game.playersreactor;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static com.game.playersreactor.MainActivity.NUMPLAYERS;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int TOTGAMES = 1;
    private static boolean sound;
    private static int difficulty;
    private static int numPlayers;
    private static ArrayList<Integer> gamesSelected;
    public Player player1 = new Player(),
            player2 = new Player(),
            player3 = new Player(),
            player4 = new Player();
    private Button player1btn, player2btn, player3btn, player4btn;
    private TextView player1Score, player2Score, player3Score,player4Score;


    public static final String mPrefs = "settPrefs";

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences sharedPreferences = getSharedPreferences(mPrefs, MODE_PRIVATE);
        numPlayers = sharedPreferences.getInt(NUMPLAYERS, 0);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        switch (numPlayers){
           /* case 0:
            {
                setContentView(R.layout.activity_two_players);
                break;
            }
            case 1:
            {
                setContentView(R.layout.activity_two_players);
                break;
            }*/
            default: {setContentView(R.layout.activity_two_players);
            }
        }

        player2btn = findViewById(R.id.player_two_btn);
        player2btn.setOnClickListener(this);
        player2Score = findViewById(R.id.score_player_two);
        player2Score.setText(String.format("%d", player2.getScore()));


    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            /*case R.id.player_four_btn:{
                player4.addScore();
                break;
            }
            case R.id.player_three_btn:{
                player3.addScore();
                break;
            }*/
            case R.id.player_two_btn:{
                player2.addScore();
                break;
            }/*
            case R.id.player_one_btn:{
                player1.addScore();
                break;
            }*/
        }
        updateScore();

    }

    @SuppressLint("DefaultLocale")
    public void updateScore(){
        /*player1Score.setText(String.format("%d", player1.getScore()));*/
        player2Score.setText(String.format("%d", player2.getScore()));
       /* player3Score.setText(String.format("%d", player3.getScore()));
        player4Score.setText(String.format("%d", player4.getScore()));*/
    }
}