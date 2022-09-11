package com.game.playersreactor.games;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

public class TicTacToe extends MyFragment {
    public ImageView[][] position = new ImageView[3][3];
    public ImageView base;
    private Random random = new Random();
    private int[][] nums = new int[3][3];
    private ArrayList<Integer> img;
    public ScheduledExecutorService service;
    public ScheduledFuture<?> future;
    public Runnable runnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tic_tac_toe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        service = Executors.newScheduledThreadPool(1);

        position[0][0] = getActivity().findViewById(R.id.ttt0);
        position[0][1] = getActivity().findViewById(R.id.ttt1);
        position[0][2] = getActivity().findViewById(R.id.ttt3);
        position[1][0] = getActivity().findViewById(R.id.ttt4);
        position[1][1] = getActivity().findViewById(R.id.ttt5);
        position[1][2] = getActivity().findViewById(R.id.ttt6);
        position[2][0] = getActivity().findViewById(R.id.ttt7);
        position[2][1] = getActivity().findViewById(R.id.ttt8);
        position[2][2] = getActivity().findViewById(R.id.ttt9);
        base = getActivity().findViewById(R.id.imageView);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                nums[i][j] = 2;
        }
        exp1 = view.findViewById(R.id.explanation1);
        exp2 = view.findViewById(R.id.explanation2);
        img = new ArrayList<>();
        img.add(R.drawable.o);
        img.add(R.drawable.x);
        runnable = () -> {
            //in questo modo gli dico fare queste operazione sul main thread
            new Handler(Looper.getMainLooper()).post(() -> {
                base.setVisibility(View.VISIBLE);
                int r, c;
                int numImg;
                r = random.nextInt(3);
                c = random.nextInt(3);
                numImg = random.nextInt(2);
                if (position[r][c].getVisibility() == View.INVISIBLE) {
                    position[r][c].setVisibility(View.VISIBLE);
                }
                position[r][c].setImageResource(img.get(numImg));
                nums[r][c] = numImg;

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
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                position[i][j].setVisibility(View.INVISIBLE);
        }
        base.setVisibility(View.INVISIBLE);
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
                base.setVisibility(View.VISIBLE);
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
                    String s = GameActivity.explanation.get(6);
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
        setAllInvisible();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                nums[i][j] = 2;
        }
        future = service.scheduleAtFixedRate(runnable, 500, speed, TimeUnit.MILLISECONDS);
    }

    @Override
    public void stop() {
        future.cancel(false);
    }

    public boolean check() {
        for (int i = 0; i < 3; i++) {
            if (nums[i][0] == nums[i][1] && nums[i][1] == nums[i][2]) {
                if (nums[i][0] == 1 || nums[i][0] == 0) {
                    return true;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            if (nums[0][i] == nums[1][i] && nums[1][i] == nums[2][1]) {
                if (nums[0][i] == 1 || nums[0][i] == 0) {
                    return true;
                }
            }
        }

        if (nums[0][0] == nums[1][1] && nums[1][1] == nums[2][2]) {
            if (nums[0][0] == 1 || nums[0][0] == 0) {
                return true;
            }
        }

        if (nums[0][2] == nums[1][1] && nums[1][1] == nums[2][0]) {
            if (nums[0][2] == 1 || nums[0][2] == 0) {
                return true;
            }
        }
        return false;
    }
}

