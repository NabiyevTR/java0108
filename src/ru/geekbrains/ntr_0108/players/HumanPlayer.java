package ru.geekbrains.ntr_0108.players;

import ru.geekbrains.ntr_0108.LogicMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HumanPlayer extends Player {

    public HumanPlayer(String playerName) {
        super(playerName);
    }

    @Override
    public void nextTurn() {
        System.out.printf("Ходит %s\n", playerName);
        int x;
        int y;
        do {
            x = getCord('X');
            y = getCord('Y');
        } while (!Map.getInstance().isCellValid(x, y));

        Map.getInstance().setDot(dot,x,y);
    }

    public static int getCord(char axisName) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int axisValue;

        while (true) {
            System.out.printf("Введите координату %c (от 1 до %d):\n", axisName, Map.getInstance().getSize());
            try {
                axisValue = Integer.parseInt(reader.readLine());
                if (axisValue < 0) throw new NumberFormatException();
                return axisValue - 1;
            } catch (IOException e) {
                continue;
            } catch (NumberFormatException e) {
                System.out.printf("Нужно ввести число от от 1 до %d\n", Map.getInstance().getSize());
                continue;
            }
        }
    }
}
