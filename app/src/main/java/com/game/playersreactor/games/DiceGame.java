package com.game.playersreactor.games;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.game.playersreactor.GameActivity;
import com.game.playersreactor.R;

import java.util.*;

import static java.lang.Thread.sleep;

public class DiceGame extends MyFragment {

    public List<ImageView> dice;
    public CountDownTimer t1;
    private Random random = new Random();
    private int redTot, greenTot, nums[] = new int[6];
    private int difficulty;
    private GameActivity gameActivity = ((GameActivity) getActivity());
    private ArrayList<Integer> diceImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dice_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dice = new ArrayList();
        dice.add(getActivity().findViewById(R.id.dice1));
        dice.add(getActivity().findViewById(R.id.dice2));
        dice.add(getActivity().findViewById(R.id.dice3));
        dice.add(getActivity().findViewById(R.id.dice4));
        dice.add(getActivity().findViewById(R.id.dice5));
        dice.add(getActivity().findViewById(R.id.dice6));

        exp1 = view.findViewById(R.id.explanation1);
        exp2 = view.findViewById(R.id.explanation2);

        difficulty = GameActivity.difficulty;

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

        setAllInvisible();
        showGameName();

    }

    @SuppressLint("SetTextI18n")
    public void showExplanation() {

        ((GameActivity) (getActivity())).personalTxt1.setText("Tap when sum of both colors is equal");
        ((GameActivity) getActivity()).personalTxt2.setText("Tap when sum of both colors is equal");

        ((GameActivity) getActivity()).personalTxt1.setVisibility(View.VISIBLE);
        ((GameActivity) getActivity()).personalTxt2.setVisibility(View.VISIBLE);

    }


    private void setAllInvisible() {
        ((GameActivity) getActivity()).personalTxt1.setVisibility(View.INVISIBLE);
        ((GameActivity) getActivity()).personalTxt2.setVisibility(View.INVISIBLE);
        for (ImageView d : dice) {
            d.setVisibility(View.INVISIBLE);
        }
    }

    public void startGame() {
        final int[] time = new int[1];

        new CountDownTimer(1000, 600) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                switch (difficulty) {
                    case 1: {
                        time[0] = 1200;
                        break;
                    }
                    case 2: {
                        time[0] = 750;
                        break;
                    }
                    default: {
                        time[0] = 1800;
                        break;
                    }
                }


                t1 = new CountDownTimer(100000000, time[0]) {
                    @Override
                    public void onTick(long l) {
                        int numDice;
                        int numImg;
                        numDice = random.nextInt(6);
                        numImg = random.nextInt(12);
                        if (dice.get(numDice).getVisibility() == View.INVISIBLE) {
                            dice.get(numDice).setVisibility(View.VISIBLE);
                        }
                        dice.get(numDice).setImageResource(diceImg.get(numImg));

                        nums[numDice] = numImg;
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
            }
        }.start();


    }

    public void stop(){
        t1.cancel();
    }

    @Override
    public void resume() {

    }

    public boolean check() {
        for (int i : nums) {
            if (i > 5) {
                redTot += (i - 6);
            } else {
                greenTot += i;
            }
        }

        if (redTot == greenTot) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    public void showGameName() {
        exp2.setVisibility(View.VISIBLE);
        exp1.setVisibility(View.VISIBLE);

        CountDownTimer timer = new CountDownTimer(1800, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                exp2.setVisibility(View.INVISIBLE);
                exp1.setVisibility(View.INVISIBLE);
                try {
                    showExplanation();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startGame();
            }
        }.start();

    }

}