package ru.geekbrains.ntr_0108;

public class Map {
    private static Map instance;

    private final static int MAXSIZE_X = 10;
    private final static int MAXSIZE_Y = 10;
    private static int roundSizeX = MAXSIZE_X;
    private static int roundSizeY = MAXSIZE_Y;

    private static int winSize = 3;

    private static DOTS map[][];

    public static Map getInstance() {
        if (instance == null) {
            instance = new Map();
        }
        return instance;
    }

    private Map() {
        this.map = new DOTS[MAXSIZE_Y][MAXSIZE_X];
        clear();
    }

    public void clear() {
        for (int i = 0; i < MAXSIZE_Y; i++) {
            for (int j = 0; j < MAXSIZE_X; j++) {
                map[i][j] = DOTS.EMPTY;
            }
        }
    }

    public boolean isCellValid(int x, int y) {
        return map[y][x] == DOTS.EMPTY;
    }

    public void setDot(DOTS dot, int x, int y) {
        map[y][x] = dot;
    }

    public void setSize(int roundSizeX, int roundSizeY) {
        if (roundSizeX < MAXSIZE_X) {
            this.roundSizeX = roundSizeX;
        }
        if (roundSizeY < MAXSIZE_Y) {
            this.roundSizeY = roundSizeY;
        }
    }

    public static void setWinSize(int winSize) {
        Map.winSize = winSize;
    }

    public static int getWinSize() {
        return winSize;
    }

    public int getSizeX() {
        return roundSizeX;
    }

    public int getSizeY() {
        return roundSizeY;
    }

    public DOTS getCell(int i, int j) {
        return map[i][j];
    }

    public boolean isFull() {
        for (int i = 0; i < roundSizeY; i++) {
            for (int j = 0; j < roundSizeX; j++) {
                if (map[i][j] == DOTS.EMPTY) return false;
            }
        }
        return true;
    }

    public boolean checkWin(DOTS dot) {
        for (int v = 0; v < roundSizeY; v++) {
            for (int h = 0; h < roundSizeX; h++) {
                //анализ наличие поля для проверки
                if (h + winSize <= roundSizeX) {
                    //по горизонтале
                    if (checkLineHorisont(v, h, dot) >= winSize) return true;

                    if (v - winSize > -2) {                            //вверх по диагонале
                        if (checkDiaUp(v, h, dot) >= winSize) return true;
                    }
                    if (v + winSize <= roundSizeY) {                       //вниз по диагонале
                        if (checkDiaDown(v, h, dot) >= winSize) return true;
                    }
                }
                if (v + winSize <= roundSizeY) {                       //по вертикале
                    if (checkLineVertical(v, h, dot) >= winSize) return true;
                }
            }
        }
        return false;
    }

    //проверка заполнения всей линии по диагонале вверх

    private static int checkDiaUp(int v, int h, DOTS dot) {
        int count = 0;
        for (int i = 0, j = 0; j < winSize; i--, j++) {
            if ((map[v + i][h + j] == dot)) count++;
        }
        return count;
    }
    //проверка заполнения всей линии по диагонале вниз

    private static int checkDiaDown(int v, int h, DOTS dot) {
        int count = 0;
        for (int i = 0; i < winSize; i++) {
            if ((map[i + v][i + h] == dot)) count++;
        }
        return count;
    }

    private static int checkLineHorisont(int v, int h, DOTS dot) {
        int count = 0;
        for (int j = h; j < winSize + h; j++) {
            if ((map[v][j] == dot)) count++;
        }
        return count;
    }

    //проверка заполнения всей линии по вертикале
    private static int checkLineVertical(int v, int h, DOTS dot) {
        int count = 0;
        for (int i = v; i < winSize + v; i++) {
            if ((map[i][h] == dot)) count++;
        }
        return count;
    }

}
