package ru.geekbrains.ntr_0108;

public enum DOTS {CROSS('X'), ZERO('O'), EMPTY('.');

    private final char symbol;

    DOTS(char symbol) {
        this.symbol = symbol;
    }
    public char getSymbol() {
        return this.symbol;
    }

}
