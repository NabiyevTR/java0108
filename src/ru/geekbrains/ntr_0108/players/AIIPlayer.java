package ru.geekbrains.ntr_0108.players;

import ru.geekbrains.ntr_0104.Map;

import java.util.Random;

public class AIIPlayer extends Player {

    private static final int TIME_DELAY = 500;
    private static final int LEVEL = 1;

    public AIIPlayer(String playerName) {
        super(playerName);
    }

    @Override
    public void nextTurn() {
        System.out.printf("Ходит %s\n", playerName);
        switch (LEVEL) {
            case 0:
                dummyBot();
                break;
            case 1:
                smartBot();
                break;
        }
    }


    private void dummyBot() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(Map.getInstance().getSize());
            y = random.nextInt(Map.getInstance().getSize());
        } while (!Map.getInstance().isCellValid(x, y));
        Map.getInstance().setDot(dot, x, y);
        try {
            Thread.sleep(TIME_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void smartBot() {
        SmartBotLogic4inRow smartBot = new SmartBotLogic4inRow(Map.getInstance(), dot);
        Map.getInstance().setDot(dot, smartBot.getBestX(), smartBot.getBestY());
        try {
            Thread.sleep(TIME_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s ходил X = %d, Y = %d\n" , playerName, smartBot.getBestX()+1, smartBot.getBestY()+1 );
    }


}
