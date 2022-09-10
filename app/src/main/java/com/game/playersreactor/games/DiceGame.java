package com.game.playersreactor.games;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.game.playersreactor.GameActivity;
import com.game.playersreactor.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DiceGame extends MyFragment {
    public List<ImageView> dice;
    private Random random = new Random();
    private int redTot, greenTot, nums[] = new int[6];
    private ArrayList<Integer> diceImg;
    public ScheduledExecutorService service;
    public ScheduledFuture<?> future;
    public Runnable runnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dice_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        service = Executors.newScheduledThreadPool(1);
        dice = new ArrayList();
        dice.add(getActivity().findViewById(R.id.dice1));
        dice.add(getActivity().findViewById(R.id.dice2));
        dice.add(getActivity().findViewById(R.id.dice3));
        dice.add(getActivity().findViewById(R.id.dice4));
        dice.add(getActivity().findViewById(R.id.dice5));
        dice.add(getActivity().findViewById(R.id.dice6));
        exp1 = view.findViewById(R.id.explanation1);
        exp2 = view.findViewById(R.id.explanation2);
        diceImg = new ArrayList<>();
        diceImg.add(R.drawable.g1);
        diceImg.add(R.drawable.g2);
        diceImg.add(R.drawable.g3);
        diceImg.add(R.drawable.g4);
        diceImg.add(R.drawable.g5);
        diceImg.add(R.drawable.g6);
        diceImg.add(R.drawable.r1);
        diceImg.add(R.drawable.r2);
        diceImg.add(R.drawable.r3);
        diceImg.add(R.drawable.r4);
        diceImg.add(R.drawable.r5);
        diceImg.add(R.drawable.r6);
        runnable = () -> {
            //in questo modo gli dico fare queste operazione sul main thread
            new Handler(Looper.getMainLooper()).post(() -> {
                int numDice;
                int numImg;
                numDice = random.nextInt(6);
                numImg = random.nextInt(12);
                if (dice.get(numDice).getVisibility() == View.INVISIBLE) {
                    dice.get(numDice).setVisibility(View.VISIBLE);
                }
                dice.get(numDice).setImageResource(diceImg.get(numImg));
                nums[numDice] = numImg;
                if (getActivity() != null) {
                    ((GameActivity) getActivity()).setClickalbleBtn(true);
                }
            });
        };
        setAllInvisible();
        getSpeed();
        showGameName();
        startGame();
    }

    private void setAllInvisible() {
        ((GameActivity) getActivity()).personalTxt1.setVisibility(View.INVISIBLE);
        ((GameActivity) getActivity()).personalTxt2.setVisibility(View.INVISIBLE);
        for (ImageView d : dice) {
            d.setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    public void showGameName() {
        exp2.setVisibility(View.VISIBLE);
        exp1.setVisibility(View.VISIBLE);
        if (getActivity() != null) {
            ((GameActivity) getActivity()).setClickalbleBtn(false);
        }
        service.schedule(new Runnable() {
            @Override
            public void run() {
                exp2.setVisibility(View.INVISIBLE);
                exp1.setVisibility(View.INVISIBLE);
                showExplanation();
            }
        }, 1500, TimeUnit.MILLISECONDS);
    }

    public void showExplanation() {
        //in questo modo gli dico fare queste operazione sul main thread
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    String s = GameActivity.explanation.get(3);
                    ((GameActivity) getActivity()).personalTxt1.setText(s);
                    ((GameActivity) getActivity()).personalTxt2.setText(s);
                    ((GameActivity) getActivity()).personalTxt1.setVisibility(View.VISIBLE);
                    ((GameActivity) getActivity()).personalTxt2.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void startGame() {
        future = service.scheduleAtFixedRate(runnable, 1500, speed, TimeUnit.MILLISECONDS);
    }

    public void resume() {
        stop();
        showExplanation();
        future = service.scheduleAtFixedRate(runnable, 500, speed, TimeUnit.MILLISECONDS);
    }

    @Override
    public void stop() {
        future.cancel(false);
    }

    public boolean check() {
        for (int i : nums) {
            if (i > 5) {
                redTot += (i - 6);
            } else {
                greenTot += i;
            }
        }
        return redTot == greenTot;
    }
}