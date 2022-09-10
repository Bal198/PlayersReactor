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

public class FiveDifferent extends MyFragment {
    public List<ImageView> position;
    private Random random = new Random();
    private int[] nums = new int[6];
    private ArrayList<Integer> image;
    public ScheduledExecutorService service;
    public ScheduledFuture<?> future;
    public Runnable runnable;
    public Runnable runnable1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_five_different, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        service = Executors.newScheduledThreadPool(1);
        position = new ArrayList();
        position.add(getActivity().findViewById(R.id.dice1));
        position.add(getActivity().findViewById(R.id.dice2));
        position.add(getActivity().findViewById(R.id.dice3));
        position.add(getActivity().findViewById(R.id.dice4));
        position.add(getActivity().findViewById(R.id.dice5));
        position.add(getActivity().findViewById(R.id.dice6));
        exp1 = view.findViewById(R.id.explanation1);
        exp2 = view.findViewById(R.id.explanation2);
        image = new ArrayList<>();
        image.add(R.drawable.a0);
        image.add(R.drawable.a1);
        image.add(R.drawable.a2);
        image.add(R.drawable.a3);
        image.add(R.drawable.a4);
        image.add(R.drawable.a5);
        image.add(R.drawable.a6);

        runnable = () -> {
            //in questo modo gli dico fare queste operazione sul main thread
            new Handler(Looper.getMainLooper()).post(() -> {
                int numDice;
                int numImg;
                numDice = random.nextInt(6);
                numImg = random.nextInt(7);
                position.get(numDice).setImageResource(image.get(numImg));
                nums[numDice] = numImg;
                if (getActivity() != null) {
                    ((GameActivity) getActivity()).setClickalbleBtn(true);
                }
            });
        };

        runnable1 = () ->{
            new Handler(Looper.getMainLooper()).post(() -> {
                int n;
                for (int i = 0; i < 6; i++) {
                    n = random.nextInt(7);
                    position.get(i).setImageResource(image.get(n));
                    position.get(i).setVisibility(View.VISIBLE);
                    nums[i] = n;
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
        for (ImageView d : position) {
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
                    String s = GameActivity.explanation.get(4);
                    ((GameActivity) getActivity()).personalTxt1.setText(s);
                    ((GameActivity) getActivity()).personalTxt2.setText(s);
                    ((GameActivity) getActivity()).personalTxt1.setVisibility(View.VISIBLE);
                    ((GameActivity) getActivity()).personalTxt2.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void startGame() {
        service.schedule(runnable1, 1500, TimeUnit.MILLISECONDS);
        future = service.scheduleAtFixedRate(runnable, 1500, 5000, TimeUnit.MILLISECONDS);
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
        int copie = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (i == j) {
                    copie += 1;
                }
            }
        }
        return copie == 1;
    }
}