package com.game.playersreactor.games;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.game.playersreactor.GameActivity;

public abstract class MyFragment extends Fragment {
    public TextView exp1, exp2;
    private int difficulty;
    public int speed;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        difficulty = GameActivity.difficulty;
    }
    public void getSpeed() {
        switch (difficulty) {
            case 1: {
                speed = 1000;
                break;
            }
            case 2: {
                speed = 500;
                break;
            }
            default: {
                speed = 1200;
                break;
            }
        }
    }

    public abstract boolean check(); //controlla la risposta del giocatore dopo il click
    public abstract void startGame(); //inizia il gioco
    public abstract void showExplanation(); //mostra il titolo per gioco
    public abstract void stop(); //fine gioco
    public abstract void resume();
}
