package com.game.playersreactor.games;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.game.playersreactor.GameActivity;
import com.game.playersreactor.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ColorNames extends MyFragment {
    public TextView colorTxt1, colorTxt2;
    public List<Integer> colors;
    public List<String> colorName;
    private final Random random = new Random();
    public ScheduledExecutorService service;
    public ScheduledFuture<?> future;
    public Runnable runnable;
    int c;
    int n;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_color_names, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        service = Executors.newScheduledThreadPool(1);

        colorTxt1 = view.findViewById(R.id.txt1);
        colorTxt2 = view.findViewById(R.id.txt2);

        colors = new ArrayList<>();
        colors.add(Integer.valueOf(Color.rgb(0, 0, 0)));
        colors.add(Integer.valueOf(Color.rgb(255, 255, 255)));
        colors.add(Integer.valueOf(Color.rgb(255, 0, 0)));
        colors.add(Integer.valueOf(Color.rgb(0, 255, 0)));
        colors.add(Integer.valueOf(Color.rgb(255, 255, 0)));
        colors.add(Integer.valueOf(Color.rgb(0, 0, 255)));
        colors.add(Integer.valueOf(Color.rgb(0, 255, 255)));
        colors.add(Integer.valueOf(Color.rgb(255, 0, 255)));

        colorName = new ArrayList<>();
        colorName.add("Black");
        colorName.add("White");
        colorName.add("Red");
        colorName.add("Green");
        colorName.add("Yellow");
        colorName.add("Blue");
        colorName.add("Cyan");
        colorName.add("Magenta");

        exp1 = view.findViewById(R.id.explanation1);
        exp2 = view.findViewById(R.id.explanation2);

        runnable = () -> {
            //in questo modo gli dico fare queste operazione sul main thread
            new Handler(Looper.getMainLooper()).post(() -> {
                c = random.nextInt(colors.size());
                if (c < colors.size() - 3) {
                    n = random.nextInt(3) + c;
                } else {
                    n = random.nextInt(colorName.size());
                }
                colorTxt2.setText(String.format("%s", colorName.get(n)));
                colorTxt1.setText(String.format("%s", colorName.get(n)));
                colorTxt2.setTextSize(45);
                colorTxt1.setTextSize(45);
                colorTxt2.setTextColor(colors.get(c));
                colorTxt1.setTextColor(colors.get(c));
                colorTxt2.setVisibility(View.VISIBLE);
                colorTxt1.setVisibility(View.VISIBLE);
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

    @SuppressLint("ResourceAsColor")
    public void showGameName() {
        exp2.setVisibility(View.VISIBLE);
        exp1.setVisibility(View.VISIBLE);
        if (getActivity() != null) {
            ((GameActivity) getActivity()).setClickalbleBtn(false);
        }
        service.schedule(() -> {
            exp2.setVisibility(View.INVISIBLE);
            exp1.setVisibility(View.INVISIBLE);
            showExplanation();
        }, 1500, TimeUnit.MILLISECONDS);
    }

    private void setAllInvisible() {
        ((GameActivity) requireActivity()).personalTxt1.setVisibility(View.INVISIBLE);
        ((GameActivity) requireActivity()).personalTxt2.setVisibility(View.INVISIBLE);
        colorTxt2.setVisibility(View.INVISIBLE);
        colorTxt1.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean check() {
        return c == n;
    }

    @Override
    public void startGame() {
        future = service.scheduleAtFixedRate(runnable, 1500, speed, TimeUnit.MILLISECONDS);
    }

    @Override
    public void showExplanation() {
        //in questo modo gli dico fare queste operazione sul main thread
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    String s = GameActivity.explanation.get(5);
                    ((GameActivity) getActivity()).personalTxt1.setText(s);
                    ((GameActivity) getActivity()).personalTxt2.setText(s);
                    ((GameActivity) getActivity()).personalTxt1.setVisibility(View.VISIBLE);
                    ((GameActivity) getActivity()).personalTxt2.setVisibility(View.VISIBLE);
                }
            }
        });
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
}