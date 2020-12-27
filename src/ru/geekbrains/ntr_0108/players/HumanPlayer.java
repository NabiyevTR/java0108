package ru.geekbrains.ntr_0108.players;

import ru.geekbrains.ntr_0108.Map;

public class HumanPlayer extends Player {

    public HumanPlayer(String playerName) {
        super(playerName);
    }

    @Override
    public void nextTurn() {

    }

    @Override
    public void nextTurn(int x, int y) {
        Map.getInstance().setDot(dot,x,y);
    }

}
