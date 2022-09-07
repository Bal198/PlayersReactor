package com.game.playersreactor.games;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.game.playersreactor.GameActivity;
import com.game.playersreactor.R;

import java.util.*;

import static java.util.Objects.*;

public class AreaGame extends MyFragment {

    public TextView areaTxt1, areaTxt2;
    public List<String> country;
    public int[] area;
    public int rightValue;
    public int leftValue;
    private Random random = new Random();
    private int difficulty;
    private ArrayList<Integer> colorList;
    public Timer timer;
    private TimerTask task;
    public int speed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_area_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        timer = new Timer();
        difficulty = GameActivity.difficulty;
        areaTxt1 = view.findViewById(R.id.area_txt);
        areaTxt2 = view.findViewById(R.id.area_txt2);
        country = new ArrayList<>();
        country = Arrays.asList(getResources().getStringArray(R.array.country));
        area = getResources().getIntArray(R.array.area);

        String[] color = requireContext().getResources().getStringArray(R.array.colors);
        colorList = new ArrayList<>();

        for (int i = 0; i < color.length; i++) {
            colorList.add(Color.parseColor(color[i]));
        }

        exp1 = view.findViewById(R.id.explanation1);
        exp2 = view.findViewById(R.id.explanation2);

        task = new TimerTask() {
            @Override
            public void run() {
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
        };

        setAllInvisible();
        getSpeed();
        showGameName();
    }

    public void getSpeed() {
        switch (difficulty) {
            case 1: {
                speed = 1800;
                break;
            }
            case 2: {
                speed = 1000;
                break;
            }
            default: {
                speed = 2200;
                break;
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    public void showGameName() {
        exp2.setVisibility(View.VISIBLE);
        exp1.setVisibility(View.VISIBLE);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                exp2.setVisibility(View.INVISIBLE);
                exp1.setVisibility(View.INVISIBLE);
                showExplanation();
            }
        }, 1500);

    }


    private void setAllInvisible() {
        ((GameActivity) requireActivity()).personalTxt1.setVisibility(View.INVISIBLE);
        ((GameActivity) requireActivity()).personalTxt2.setVisibility(View.INVISIBLE);
        areaTxt2.setVisibility(View.INVISIBLE);
        areaTxt1.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean check() {
        return area[leftValue] <= area[rightValue];
    }

    @Override
    public void startGame() {
        if (timer != null) {
            timer.schedule(task, 1500, speed);
        }
    }

    @Override
    public void showExplanation() {
        ((GameActivity) (requireActivity())).personalTxt1.setText("Tap when area is right");
        ((GameActivity) requireActivity()).personalTxt2.setText("Tap when area is right");

        ((GameActivity) requireActivity()).personalTxt1.setVisibility(View.VISIBLE);
        ((GameActivity) requireActivity()).personalTxt2.setVisibility(View.VISIBLE);
    }

    public void resume() {
        timer = new Timer();
        timer.schedule(task, 1500, speed);
    }

    @Override
    public void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }
}