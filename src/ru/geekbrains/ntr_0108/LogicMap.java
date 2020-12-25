package ru.geekbrains.ntr_0108;

public class LogicMap {
    private static LogicMap instance;

    private static int SIZE = 5;

    private DOTS map[][];

    public static LogicMap getInstance() {
        if (instance == null) {
            instance = new LogicMap();
        }
        return instance;
    }

    public static LogicMap getInstance(int size) {
        if (instance == null) {
            SIZE = size;
            instance = new LogicMap();
        }
        return instance;
    }

    private LogicMap() {
        this.map = new DOTS[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
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

    public int getSize() {
        return SIZE;
    }

    public DOTS getCell(int i, int j) {
        return map[i][j];
    }

    public void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " | ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " | ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j].getSymbol() + " | ");
            }
            System.out.println();
        }
        System.out.println();
    }

    DOTS[][] getMap() {
        return map;
    }

    public boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOTS.EMPTY) return false;
            }
        }
        return true;
    }

    public boolean hasInRow(DOTS dot, int dotsToWin) {

        String winCombination = MapHelpers.winCombination(dot, dotsToWin);

        // Проверка по горизонталям
        for (int i = 0; i < SIZE; i++) {
            if (MapHelpers.rowToString(map[i]).contains(winCombination)) {
                return true;
            }
        }

        // Проверка по вертикалям
        for (int i = 0; i < SIZE; i++) {
            DOTS[] column = new DOTS[SIZE];
            for (int j = 0; j < SIZE; j++) {
                column[j] = map[j][i];
            }
            if (MapHelpers.rowToString(column).contains(winCombination)) {
                return true;
            }
        }

        // Проверка по главным диогоналям
        for (int i = 0; i < SIZE - dotsToWin + 1; i++) {
            DOTS[] diagonal = new DOTS[SIZE - i];
            for (int j = 0; j < SIZE - i; j++) {
                diagonal[j] = map[i + j][j];
            }
            if (MapHelpers.rowToString(diagonal).contains(winCombination)) {
                return true;
            }
        }

        for (int i = 1; i < SIZE - dotsToWin + 1; i++) {
            DOTS[] diagonal = new DOTS[SIZE - i];
            for (int j = 0; j < SIZE - i; j++) {
                diagonal[j] = map[j][i + j];
            }
            if (MapHelpers.rowToString(diagonal).contains(winCombination)) {
                return true;
            }
        }

        // Проверка по второстепенным диогоналям
        for (int i = dotsToWin - 1; i < SIZE; i++) {
            DOTS[] diagonal = new DOTS[i + 1];
            for (int j = 0; j < i + 1; j++) {
                diagonal[j] = map[j][i - j];
            }
            if (MapHelpers.rowToString(diagonal).contains(winCombination)) {
                return true;
            }
        }

        for (int i = 1; i < SIZE - dotsToWin + 1; i++) {
            DOTS[] diagonal = new DOTS[SIZE - i];
            for (int j = 0; j < SIZE - i; j++) {
                diagonal[j] = map[j + i ][SIZE - j -1];
            }
            if (MapHelpers.rowToString(diagonal).contains(winCombination)) {
                return true;
            }
        }
        return false;
    }
}
