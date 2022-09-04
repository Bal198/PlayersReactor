package com.game.playersreactor;

import androidx.fragment.app.Fragment;
import com.game.playersreactor.games.DiceGame;

/*vediamo se usarlo o no come lista dei giochi*/

public class GameList {
    public boolean inList = true;
    public Fragment game;
    public int sequenceNumber;

    public GameList(Fragment game, boolean inList, int sequenceNumber) {
        this.game = game;
        this.inList = inList;
        this.sequenceNumber = sequenceNumber;
    }



}
