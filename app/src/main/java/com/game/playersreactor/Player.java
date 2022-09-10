package com.game.playersreactor;

public class Player {
    private int score = 0;

    public int getScore() {
        return score;
    }

    public void addScore() {
        this.score++;
    }

    public void subScore() {
        this.score--;
    }
}