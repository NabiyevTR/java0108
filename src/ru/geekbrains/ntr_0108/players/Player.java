package ru.geekbrains.ntr_0108.players;

import ru.geekbrains.ntr_0108.DOTS;

public abstract class Player implements IPlayer {
    String playerName;
    DOTS dot;

    public Player(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String getName() {
        return playerName;
    }

    @Override
    public DOTS getDot() {
        return dot;
    }

    @Override
    public void setDot(DOTS dot) {
        this.dot = dot;
    }
}
