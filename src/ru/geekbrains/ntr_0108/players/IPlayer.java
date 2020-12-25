package ru.geekbrains.ntr_0108.players;

import ru.geekbrains.ntr_0108.DOTS;

public interface IPlayer {

    public void nextTurn();

    public String getName();

    public DOTS getDot();

    public void setDot(DOTS dot);
}
