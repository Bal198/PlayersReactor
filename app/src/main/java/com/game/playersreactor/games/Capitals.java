package com.game.playersreactor.games;

import android.annotation.SuppressLint;
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

public class Capitals extends MyFragment {
    public TextView capitalTxt1, capitalTxt2, countryTxt1, countryTxt2;
    public List<String> country;
    public List<String> capital;
    public int rightValue;
    public int leftValue;
    private final Random random = new Random();
    public ScheduledExecutorService service;
    public ScheduledFuture<?> future;
    public Runnable runnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_capitals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        service = Executors.newScheduledThreadPool(1);
        capitalTxt1 = view.findViewById(R.id.txt11);
        capitalTxt2 = view.findViewById(R.id.txt21);
        countryTxt1 = view.findViewById(R.id.txt1);
        countryTxt2 = view.findViewById(R.id.txt2);
        country = new ArrayList<>();
        country = Arrays.asList(getResources().getStringArray(R.array.country));
        capital = new ArrayList<>();
        capital = Arrays.asList(getResources().getStringArray(R.array.capitals));
        exp1 = view.findViewById(R.id.explanation1);
        exp2 = view.findViewById(R.id.explanation2);
        runnable = () -> {
            //in questo modo gli dico fare queste operazione sul main thread
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    int c;
                    rightValue = random.nextInt(country.size());
                    int n = random.nextInt(4);
                    if (rightValue < country.size() - 4) {
                        leftValue = rightValue + n;
                    } else {
                        leftValue = random.nextInt(capital.size());
                    }
                    String left = String.format("%s", capital.get(leftValue));
                    String right = String.format("%s", country.get(rightValue));
                    capitalTxt2.setText(left);
                    capitalTxt1.setText(left);
                    countryTxt1.setText(right);
                    countryTxt2.setText(right);
                    countryTxt1.setTextSize(26);
                    countryTxt2.setTextSize(26);
                    capitalTxt2.setTextSize(24);
                    capitalTxt1.setTextSize(24);
                    c = getRandomColor();
                    capitalTxt2.setTextColor(c);
                    capitalTxt1.setTextColor(c);
                    c = getRandomColor();
                    countryTxt1.setTextColor(c);
                    countryTxt2.setTextColor(c);
                    capitalTxt2.setVisibility(View.VISIBLE);
                    capitalTxt1.setVisibility(View.VISIBLE);
                    countryTxt1.setVisibility(View.VISIBLE);
                    countryTxt2.setVisibility(View.VISIBLE);
                    if (getActivity() != null) {
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

    private void setAllInvisible() {
        ((GameActivity) requireActivity()).personalTxt1.setVisibility(View.INVISIBLE);
        ((GameActivity) requireActivity()).personalTxt2.setVisibility(View.INVISIBLE);
        if (capitalTxt1 != null && capitalTxt2 != null) {
            capitalTxt2.setVisibility(View.INVISIBLE);
            capitalTxt1.setVisibility(View.INVISIBLE);
            countryTxt1.setVisibility(View.INVISIBLE);
            countryTxt2.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean check() {
        return leftValue == rightValue;
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
                if (getActivity() != null) {
                    String s = GameActivity.explanation.get(2);
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