package ru.geekbrains.ntr_0108;

public class MapHelpers {
    public static String rowToString(DOTS[] dots) {
        StringBuilder sb = new StringBuilder();
        for (DOTS dot : dots) {
            sb.append(dot.getSymbol());
        }
        return sb.toString();
    }

    public static String winCombination(DOTS dot, int amount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            sb.append(dot.getSymbol());
        }
        return sb.toString();
    }
}
