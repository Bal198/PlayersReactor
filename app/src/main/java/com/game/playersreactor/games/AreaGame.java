package com.game.playersreactor.games;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.game.playersreactor.GameActivity;
import com.game.playersreactor.R;

import java.util.*;

public class AreaGame extends MyFragment {

    public TextView areaTxt1, areaTxt2;
    public CountDownTimer t1;
    public List<String> country;
    public int[] area;
    public int rightValue;
    public Handler handler;
    public int leftValue;
    private Random random = new Random();
    private int difficulty;
    private ArrayList<Integer> colorList;
    private Runnable timer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_area_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        difficulty = GameActivity.difficulty;
        areaTxt1 = view.findViewById(R.id.area_txt);
        areaTxt2 = view.findViewById(R.id.area_txt2);
        country = new ArrayList<>();
        country = Arrays.asList(getResources().getStringArray(R.array.country));
        area = getResources().getIntArray(R.array.area);

        String color[] = getContext().getResources().getStringArray(R.array.colors);
        colorList = new ArrayList<Integer>();

        for (int i = 0; i < color.length; i++) {
            colorList.add(Color.parseColor(color[i]));
        }

        exp1 = view.findViewById(R.id.explanation1);
        exp2 = view.findViewById(R.id.explanation2);

        setAllInvisible();
        showGameName();
    }

    @SuppressLint("ResourceAsColor")
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


    private void setAllInvisible() {
        ((GameActivity) getActivity()).personalTxt1.setVisibility(View.INVISIBLE);
        ((GameActivity) getActivity()).personalTxt2.setVisibility(View.INVISIBLE);
        areaTxt2.setVisibility(View.INVISIBLE);
        areaTxt1.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean check() {
        if (area[leftValue] <= area[rightValue]) {
            return true;
        } else {
            return false;
        }
    }

    @Override
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
                        time[0] = 1800;
                        break;
                    }
                    case 2: {
                        time[0] = 1000;
                        break;
                    }
                    default: {
                        time[0] = 2200;
                        break;
                    }
                }


                t1 = new CountDownTimer(100000000, time[0]) {
                    @Override
                    public void onTick(long l) {
                        int c = random.nextInt(colorList.size());
                        rightValue = random.nextInt(country.size());
                        leftValue = random.nextInt(country.size());
                        areaTxt2.setText(String.format("%s < %s", country.get(leftValue), country.get(rightValue)));
                        areaTxt1.setText(String.format("%s < %s", country.get(leftValue), country.get(rightValue)));
                        areaTxt2.setTextSize(24);
                        areaTxt1.setTextSize(24);
                        areaTxt2.setTextColor(colorList.get(c));
                        areaTxt1.setTextColor(colorList.get(c));
                        areaTxt2.setVisibility(View.VISIBLE);
                        areaTxt1.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFinish() {
                    }
                }.start();
            }
        }.start();
    }

    @Override
    public void showExplanation() {
        ((GameActivity) (getActivity())).personalTxt1.setText("Tap when area is right");
        ((GameActivity) getActivity()).personalTxt2.setText("Tap when area is right");

        ((GameActivity) getActivity()).personalTxt1.setVisibility(View.VISIBLE);
        ((GameActivity) getActivity()).personalTxt2.setVisibility(View.VISIBLE);
    }

    @Override
    public void stop() {
        if(t1 != null) {
            t1.cancel();
        }
        t1 = null;
    }
}