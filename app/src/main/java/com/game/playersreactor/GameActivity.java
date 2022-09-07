package com.game.playersreactor;

import android.annotation.SuppressLint;
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
import com.game.playersreactor.games.AreaGame;
import com.game.playersreactor.games.DiceGame;
import com.game.playersreactor.games.MyFragment;
import com.game.playersreactor.games.ShoutFinalVictory;

import java.util.*;

import static com.game.playersreactor.MainActivity.NUMPLAYERS;
import static com.game.playersreactor.SettingActivity.DIFFICULTY;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String mPrefs = "settPrefs";
    private static final int TOTGAMES = 1;
    public static int difficulty;
    private static boolean sound;
    private static int numPlayers;
    public List<String> gameName;
    public List<String> gameExplanation;
    public List<String> shoutVictory;
    public List<String> shoutLoss;
    public static List<String> explanation;
    public Player player1 = new Player(),
            player2 = new Player(),
            player3 = new Player(),
            player4 = new Player();
    public ArrayList<GameList> modeList;
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
        modeList.add(new GameList(new DiceGame(), true, 0));
        modeList.add(new GameList(new AreaGame(), true, 1));

        //gameName = new ArrayList<>();
        //gameName = Arrays.asList(getResources().getStringArray(R.array.game_name));
        //gameExplanation = new ArrayList<>();
        //gameExplanation = Arrays.asList(getResources().getStringArray(R.array.game_explanation));

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

        //callFragment();
        replaceFragment(new AreaGame());
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
        if (gameCount >= 3) {
            gameCount = 0;
            // getSupportFragmentManager().findFragmentById(R.id.fragment_game).onDestroy();
            //  callFragment();
        }
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

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                player1btn.setBackgroundResource(R.drawable.player_btn);
                player2btn.setBackgroundResource(R.drawable.player_btn);
                //player3btn.setBackgroundResource(R.drawable.player_btn);
                //player4btn.setBackgroundResource(R.drawable.player_btn);

                assert fragment != null;
                fragment.showExplanation();
                fragment.startGame();
            }
        }, 1000);

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

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                player1btn.setBackgroundResource(R.drawable.player_btn);
                player2btn.setBackgroundResource(R.drawable.player_btn);
                //player3btn.setBackgroundResource(R.drawable.player_btn);
                //player4btn.setBackgroundResource(R.drawable.player_btn);

                assert fragment != null;
                fragment.showExplanation();
                fragment.startGame();
            }
        }, 1000);
    }

}