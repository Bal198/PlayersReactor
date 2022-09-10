package com.game.playersreactor;

import androidx.fragment.app.Fragment;
import com.game.playersreactor.games.DiceGame;

/*vediamo se usarlo o no come lista dei giochi*/

public class GameList {
    public boolean inList = true;
    public Fragment game;

    public GameList(Fragment game, boolean inList) {
        this.game = game;
        this.inList = inList;
    }

}
