package com.game.playersreactor;

import androidx.fragment.app.Fragment;
import com.game.playersreactor.games.DiceGame;

public class GameList {
    public boolean inList = true;
    public Fragment game;
    public int seq;

    public GameList(Fragment game, int seq) {
        this.game = game;
        this.inList = MainActivity.selected[seq];
        this.seq = seq;
    }
}
