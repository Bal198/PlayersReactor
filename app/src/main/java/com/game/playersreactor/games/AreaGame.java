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

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class AreaGame extends MyFragment {

    public TextView areaTxt1, areaTxt2;
    public List<String> country;
    public int[] area;
    public int rightValue;
    public int leftValue;
    private final Random random = new Random();
    public ScheduledExecutorService service;
    public ScheduledFuture<?> future;
    public Runnable runnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_area_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        service = Executors.newScheduledThreadPool(1);
        areaTxt1 = view.findViewById(R.id.area_txt);
        areaTxt2 = view.findViewById(R.id.area_txt2);
        country = new ArrayList<>();
        country = Arrays.asList(getResources().getStringArray(R.array.country));
        area = getResources().getIntArray(R.array.area);

        exp1 = view.findViewById(R.id.explanation1);
        exp2 = view.findViewById(R.id.explanation2);

        runnable = () -> {
            //in questo modo gli dico fare queste operazione sul main thread
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    int c = getRandomColor();
                    rightValue = random.nextInt(country.size());
                    leftValue = random.nextInt(country.size());
                    areaTxt2.setText(String.format("%s < %s", country.get(leftValue), country.get(rightValue)));
                    areaTxt1.setText(String.format("%s < %s", country.get(leftValue), country.get(rightValue)));
                    areaTxt2.setTextSize(24);
                    areaTxt1.setTextSize(24);
                    areaTxt2.setTextColor(c);
                    areaTxt1.setTextColor(c);
                    areaTxt2.setVisibility(View.VISIBLE);
                    areaTxt1.setVisibility(View.VISIBLE);
                    if(getActivity() != null) {
                        ((GameActivity) getActivity()).setClickalbleBtn(true);
                    }
                }
            });
        };

        setAllInvisible();
        getSpeed();
        showGameName();
        startGame();
    }

    @SuppressLint("ResourceAsColor")
    public synchronized void showGameName() {
        exp2.setVisibility(View.VISIBLE);
        exp1.setVisibility(View.VISIBLE);
        if(getActivity() != null) {
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
        future = service.scheduleAtFixedRate(runnable, 1500, speed, TimeUnit.MILLISECONDS);
    }

    @Override
    public synchronized void showExplanation() {
        //in questo modo gli dico fare queste operazione sul main thread
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if(getActivity() != null){
                String s = GameActivity.explanation.get(0);
                ((GameActivity) getActivity()).personalTxt1.setText(s);
                ((GameActivity) getActivity()).personalTxt2.setText(s);

                ((GameActivity) getActivity()).personalTxt1.setVisibility(View.VISIBLE);
                ((GameActivity) getActivity()).personalTxt2.setVisibility(View.VISIBLE);
            }}
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