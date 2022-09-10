package com.game.playersreactor.games;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.game.playersreactor.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* Schermata finale per mostrare il vincitore finale*/
public class ShoutFinalVictory extends Fragment {

    private String p1Won = "Player 1 won!!!";
    private String p2Won = "Player 2 won!!!";
    public TextView textP1, textP2;
    public Random random;
    public List<String> shoutVictory;
    public List<String> shoutLoss;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shout_final_victory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textP1 = view.findViewById(R.id.pl1);
        textP2 = view.findViewById(R.id.pl2);
        random = new Random();
        shoutVictory = new ArrayList<>();
        shoutVictory = Arrays.asList(getResources().getStringArray(R.array.shout_victory));
        shoutLoss = new ArrayList<>();
        shoutLoss = Arrays.asList(getResources().getStringArray(R.array.shout_loss));
        checkVictory();
    }

    public void checkVictory() {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                int p1, p2;
                p1 = ((GameActivity) getActivity()).player1.getScore();
                p2 = ((GameActivity) getActivity()).player2.getScore();
                if (p1 > p2) {
                    textP1.setText(String.format("%s", p1Won));
                    textP2.setText(String.format("%s", p1Won));
                    shoutVictory(0);

                } else if (p1 == p2) {
                    textP1.setText("Tie!!");
                    textP2.setText("Tie!!");
                    shoutVictory(2);
                }
                else {
                    textP1.setText(String.format("%s", p2Won));
                    textP2.setText(String.format("%s", p2Won));
                    shoutVictory(1);
                }
            }
        });



        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.schedule(() -> {
            ((GameActivity)getActivity()).player1btn.setBackgroundResource(R.drawable.player_btn);
            ((GameActivity)getActivity()).player2btn.setBackgroundResource(R.drawable.player_btn);
            ((GameActivity)getActivity()).openMainActivity();

        }, 3000, TimeUnit.MILLISECONDS);

    }

    public void shoutVictory(int id) {
        int n = random.nextInt(10);

        switch (id) {
            case 2: {
                ((GameActivity)getActivity()).personalTxt2.setText("Try again!");
                ((GameActivity)getActivity()).personalTxt1.setText("Try again!");
                break;
            }
            case 1: {
                ((GameActivity)getActivity()).personalTxt1.setText(shoutVictory.get(n));
                ((GameActivity)getActivity()).personalTxt2.setText(shoutLoss.get(n));
                ((GameActivity)getActivity()).player2btn.setBackgroundResource(R.drawable.player_win_btn);
                break;
            }
            case 0: {
                ((GameActivity)getActivity()).personalTxt2.setText(shoutVictory.get(n));
                ((GameActivity)getActivity()).personalTxt1.setText(shoutLoss.get(n));
                ((GameActivity)getActivity()).player1btn.setBackgroundResource(R.drawable.player_win_btn);
                break;
            }
        }
    }



}