package com.game.playersreactor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.game.playersreactor.games.*;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.game.playersreactor.MainActivity.NUMPLAYERS;
import static com.game.playersreactor.SettingActivity.DIFFICULTY;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String mPrefs = "settPrefs";
    public static final int TOTGAMES = 7;
    public static int difficulty;
    private static boolean sound;
    private static int numPlayers;
    public List<String> shoutVictory;
    public List<String> shoutLoss;
    public static List<String> explanation;
    public Player player1 = new Player(),
            player2 = new Player(),
            player3 = new Player(),
            player4 = new Player();
    public static ArrayList<GameList> modeList;
    public Button player1btn;
    public Button player2btn;
    //public Button player3btn;
    //public Button player4btn;
    public TextView player1Score, player2Score, player3Score, player4Score, personalTxt1, personalTxt2;
    Random random = new Random();
    private int gameCount = 0;
    private int currentFragment = 0;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences sharedPreferences = getSharedPreferences(mPrefs, MODE_PRIVATE);
        numPlayers = sharedPreferences.getInt(NUMPLAYERS, 0);
        difficulty = sharedPreferences.getInt(DIFFICULTY, 0);
        switch (numPlayers) {
            /*per ora faccia solo al versione con due giocatori*/
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
            default: {
                setContentView(R.layout.activity_two_players);
            }
        }

        modeList = new ArrayList<>();
        modeList.add(new GameList(new AreaGame(), 0));
        modeList.add(new GameList(new FiveDifferent(), 4));
        modeList.add(new GameList(new DiceGame(), 3));
        modeList.add(new GameList(new ColorNames(), 5));
        modeList.add(new GameList(new Population(), 1));
        modeList.add(new GameList(new Capitals(), 2));
        modeList.add(new GameList(new TicTacToe(), 6));
        Collections.shuffle(modeList);

        shoutVictory = new ArrayList<>();
        shoutVictory = Arrays.asList(getResources().getStringArray(R.array.shout_victory));
        shoutLoss = new ArrayList<>();
        shoutLoss = Arrays.asList(getResources().getStringArray(R.array.shout_loss));
        explanation = new ArrayList<>();
        explanation = Arrays.asList(getResources().getStringArray(R.array.explanation));
        player2btn = findViewById(R.id.player_two_btn);
        player2btn.setOnClickListener(this);
        player2Score = findViewById(R.id.score_player_two);
        player2Score.setText(String.format("%d", player2.getScore()));

        /*player3btn = findViewById(R.id.player_three_btn);
        player3btn.setOnClickListener(this);
        player3Score = findViewById(R.id.score_player_three);
        player3Score.setText(String.format("%d", player3.getScore()));

        player4btn = findViewById(R.id.player_four_btn);
        player4btn.setOnClickListener(this);
        player4Score = findViewById(R.id.score_player_four);
        player4Score.setText(String.format("%d", player4.getScore()));*/
        player1btn = findViewById(R.id.player_one_btn);
        player1btn.setOnClickListener(this);
        player1Score = findViewById(R.id.score_player_one);
        player1Score.setText(String.format("%d", player1.getScore()));
        (personalTxt1 = findViewById(R.id.personal_txt1)).setVisibility(View.INVISIBLE);
        (personalTxt2 = findViewById(R.id.personal_txt2)).setVisibility(View.INVISIBLE);

        callFragment();
    }

    public void callFragment() {
        if (currentFragment >= modeList.size()) {
            replaceFragment(new ShoutFinalVictory());
        } else {
            if (modeList.get(currentFragment).inList) {
                replaceFragment(modeList.get(currentFragment).game);
            } else {
                currentFragment++;
                callFragment();
            }
        }
        currentFragment++;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_game, fragment);
        fragmentTransaction.commit();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        setClickalbleBtn(false);
        MyFragment fragment = (MyFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_game);
        assert fragment != null;
        fragment.stop();
        boolean check = fragment.check();
        gameCount++;
        switch (view.getId()) {
           /* case R.id.player_four_btn: {
                if (check) {
                    player4.addScore();
                } else {
                    player4.subScore();
                }
                break;
            }
            case R.id.player_three_btn: {
                if (check) {
                    player3.addScore();
                } else {
                    player3.subScore();
                }
                break;
            }*/
            case R.id.player_two_btn: {
                if (check) {
                    player2.addScore();
                } else {
                    player2.subScore();
                }
                break;
            }
            case R.id.player_one_btn: {
                if (check) {
                    player1.addScore();
                } else {
                    player1.subScore();
                }
                break;
            }
        }
        updateScore();
        if (check) {
            shoutVictory(view.getId());
        } else {
            shoutLoss(view.getId());
        }
    }

    public void setClickalbleBtn(boolean b) {
        player1btn.setClickable(b);
        player2btn.setClickable(b);
        //player3btn.setClickable(b);
        //player4btn.setClickable(b);
    }

    public void updateScore() {
        player1Score.setText(String.format("%d", player1.getScore()));
        player2Score.setText(String.format("%d", player2.getScore()));
        //Commentate via in quanto generavano nullPointerException
//        player3Score.setText(String.format("%d", player3.getScore()));
//        player4Score.setText(String.format("%d", player4.getScore()));
    }

    @SuppressLint({"ResourceAsColor", "NonConstantResourceId"})
    public void shoutVictory(int id) {
        int n = random.nextInt(10);
        MyFragment fragment = (MyFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_game);
        switch (id) {
            /*case R.id.player_four_btn: {
                personalTxt2.setText(shoutVictory.get(n));
                player4btn.setBackgroundResource(R.drawable.player_win_btn);
                break;
            }
            case R.id.player_three_btn: {
                personalTxt1.setText(shoutVictory.get(n));
                player3btn.setBackgroundResource(R.drawable.player_win_btn);
                break;
            }*/
            case R.id.player_two_btn: {
                personalTxt1.setText(shoutVictory.get(n));
                player2btn.setBackgroundResource(R.drawable.player_win_btn);
                break;
            }
            case R.id.player_one_btn: {
                personalTxt2.setText(shoutVictory.get(n));
                player1btn.setBackgroundResource(R.drawable.player_win_btn);
                break;
            }
        }
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.schedule(() -> {
            player1btn.setBackgroundResource(R.drawable.player_btn);
            player2btn.setBackgroundResource(R.drawable.player_btn);
            //player3btn.setBackgroundResource(R.drawable.player_btn);
            //player4btn.setBackgroundResource(R.drawable.player_btn);
            if (gameCount >= 3) {
                gameCount = 0;
                getSupportFragmentManager().findFragmentById(R.id.fragment_game).onDestroy();
                callFragment();
            } else {
                assert fragment != null;
                fragment.resume();
            }
        }, 1, TimeUnit.SECONDS);
    }

    @SuppressLint({"ResourceAsColor", "NonConstantResourceId"})
    public void shoutLoss(int id) {
        int n = random.nextInt(10);
        MyFragment fragment = (MyFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_game);
        switch (id) {
            /*case R.id.player_four_btn: {
                personalTxt2.setText(shoutLoss.get(n));
                player4btn.setBackgroundResource(R.drawable.player_loss_btn);
                break;
            }
            case R.id.player_three_btn: {
                personalTxt1.setText(shoutLoss.get(n));
                player3btn.setBackgroundResource(R.drawable.player_loss_btn);
                break;
            }*/
            case R.id.player_two_btn: {
                personalTxt1.setText(shoutLoss.get(n));
                player2btn.setBackgroundResource(R.drawable.player_loss_btn);
                break;
            }
            case R.id.player_one_btn: {
                personalTxt2.setText(shoutLoss.get(n));
                player1btn.setBackgroundResource(R.drawable.player_loss_btn);
                break;
            }
        }
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.schedule(() -> {
            player1btn.setBackgroundResource(R.drawable.player_btn);
            player2btn.setBackgroundResource(R.drawable.player_btn);
            //player3btn.setBackgroundResource(R.drawable.player_btn);
            //player4btn.setBackgroundResource(R.drawable.player_btn);
            if (gameCount >= 3) {
                gameCount = 0;
                getSupportFragmentManager().findFragmentById(R.id.fragment_game).onDestroy();
                callFragment();
            } else {
                assert fragment != null;
                fragment.resume();
            }
        }, 1, TimeUnit.SECONDS);
    }

    @Override
    public void onBackPressed() {
        openMainActivity();
    }

    public void openMainActivity() {
        Intent gameIntent = new Intent(this, MainActivity.class);
        startActivity(gameIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}