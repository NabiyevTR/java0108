package ru.geekbrains.ntr_0108.players;

import ru.geekbrains.ntr_0108.DOTS;
import ru.geekbrains.ntr_0108.Map;

import java.util.Random;

public class AIIPlayer extends Player {

    static Map map;
    static int sizeX;
    static int sizeY;
    static int winSize;
    private final static int DELAY = 500;


    public AIIPlayer(String playerName) {
        super(playerName);
        map = Map.getInstance();
        sizeX = map.getSizeX();
        sizeY = map.getSizeY();
        winSize = map.getWinSize();
    }

    @Override
    public void nextTurn(int X, int Y) {

    }

    @Override
    public void nextTurn() {
        AiMove();
    }

    private void AiMove() {
        Random rnd = new Random();
        DOTS Ai_DOT = this.getDot();
        DOTS player_DOT = Ai_DOT == DOTS.CROSS ? DOTS.ZERO : DOTS.CROSS;

        int x, y;
        //блокировка ходов человека
        for (int v = 0; v < sizeY; v++) {
            for (int h = 0; h < sizeX; h++) {
                //анализ наличие поля для проверки
                if (h + winSize <= sizeX) {                           //по горизонтали
                    if (checkLineHorisont(v, h, player_DOT) == winSize - 1) {
                        if (MoveAiLineHorisont(v, h, Ai_DOT)) return;
                    }

                    if (v - winSize > -2) {                            //вверх по диагонали
                        if (checkDiaUp(v, h, player_DOT) == winSize - 1) {
                            if (MoveAiDiaUp(v, h, Ai_DOT)) return;
                        }
                    }
                    if (v + winSize <= sizeY) {                       //вниз по диагонали
                        if (checkDiaDown(v, h, player_DOT) == winSize - 1) {
                            if (MoveAiDiaDown(v, h, Ai_DOT)) return;
                        }
                    }
                }
                if (v + winSize <= sizeY) {                       //по вертикали
                    if (checkLineVertical(v, h, player_DOT) == winSize - 1) {
                        if (MoveAiLineVertical(v, h, Ai_DOT)) return;
                    }
                }
            }
        }
        //игра на победу
        for (int v = 0; v < sizeY; v++) {
            for (int h = 0; h < sizeX; h++) {
                //анализ наличие поля для проверки
                if (h + winSize <= sizeX) {                           //по горизонтали
                    if (checkLineHorisont(v, h, Ai_DOT) == winSize - 1) {
                        if (MoveAiLineHorisont(v, h, Ai_DOT)) return;
                    }

                    if (v - winSize > -2) {                            //вверх по диагонали
                        if (checkDiaUp(v, h, Ai_DOT) == winSize - 1) {
                            if (MoveAiDiaUp(v, h, Ai_DOT)) return;
                        }
                    }
                    if (v + winSize <= sizeY) {                       //вниз по диагонали
                        if (checkDiaDown(v, h, Ai_DOT) == winSize - 1) {
                            if (MoveAiDiaDown(v, h, Ai_DOT)) return;
                        }
                    }
                }
                if (v + winSize <= sizeY) {                       //по вертикалеи
                    if (checkLineVertical(v, h, Ai_DOT) == winSize - 1) {
                        if (MoveAiLineVertical(v, h, Ai_DOT)) return;
                    }
                }
            }
        }

        //случайный ход
        do {
            y = rnd.nextInt(sizeY);
            x = rnd.nextInt(sizeX);
        } while (!checkMove(y, x));
        // dotField(y, x, Ai_DOT);
        map.setDot(Ai_DOT, x, y);
    }

    //проверка заполнения выбранного для хода игроком
    private static boolean checkMove(int y, int x) {
        if (x < 0 || x >= sizeX || y < 0 || y >= sizeY) return false;
        else if (!(map.getCell(y, x) == DOTS.EMPTY)) return false;

        return true;
    }

    //ход компьютера по горизонтале
    private static boolean MoveAiLineHorisont(int v, int h, DOTS dot) {
        for (int j = h; j < winSize; j++) {
            if ((map.getCell(v, j) == DOTS.EMPTY)) {
                map.setDot(dot, j, v);
                return true;
            }
        }
        return false;
    }

    //ход компьютера по вертикале
    private static boolean MoveAiLineVertical(int v, int h, DOTS dot) {
        for (int i = v; i < winSize; i++) {
            if ((map.getCell(i, h) == DOTS.EMPTY)) {
                map.setDot(dot, h, i);
                return true;
            }
        }
        return false;
    }
    //проверка заполнения всей линии по диагонале вверх

    private static boolean MoveAiDiaUp(int v, int h, DOTS dot) {
        for (int i = 0, j = 0; j < winSize; i--, j++) {
            if ((map.getCell(v + i, h + j) == DOTS.EMPTY)) {
                map.setDot(dot, h + j, v + i);
                return true;
            }
        }
        return false;
    }
    //проверка заполнения всей линии по диагонале вниз

    private static boolean MoveAiDiaDown(int v, int h, DOTS dot) {

        for (int i = 0; i < winSize; i++) {
            if ((map.getCell(i + v, i + h) == DOTS.EMPTY)) {
                map.setDot(dot, i + h, i + v);
                return true;
            }
        }
        return false;
    }

    private static int checkDiaUp(int v, int h, DOTS dot) {
        int count = 0;
        for (int i = 0, j = 0; j < winSize; i--, j++) {
            if ((map.getCell(v + i, h + j) == dot)) count++;
        }
        return count;
    }
    //проверка заполнения всей линии по диагонале вниз

    private static int checkDiaDown(int v, int h, DOTS dot) {
        int count = 0;
        for (int i = 0; i < winSize; i++) {
            if ((map.getCell(i + v, i + h) == dot)) count++;
        }
        return count;
    }

    private static int checkLineHorisont(int v, int h, DOTS dot) {
        int count = 0;
        for (int j = h; j < winSize + h; j++) {
            if ((map.getCell(v, j) == dot)) count++;
        }
        return count;
    }

    //проверка заполнения всей линии по вертикале
    private static int checkLineVertical(int v, int h, DOTS dot) {
        int count = 0;
        for (int i = v; i < winSize + v; i++) {
            if ((map.getCell(i, h) == dot)) count++;
        }
        return count;
    }
}
