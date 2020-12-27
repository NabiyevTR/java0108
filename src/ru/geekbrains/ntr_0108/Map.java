package ru.geekbrains.ntr_0108;

public class Map {
    private static Map instance;
    //Todo check for maxsize
    private final static int MAXSIZE = 10;
    private static int roundSize = MAXSIZE;

    private DOTS map[][];

    public static Map getInstance() {
        if (instance == null) {
            instance = new Map();
        }
        return instance;
    }

    private Map() {
        this.map = new DOTS[MAXSIZE][MAXSIZE];
        clear();
    }

    public void clear() {
        for (int i = 0; i < MAXSIZE; i++) {
            for (int j = 0; j < MAXSIZE; j++) {
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

    public void setSize(int roundSize) {
        if (roundSize <= MAXSIZE) {
            this.roundSize = roundSize;
        }
    }

    public int getSize() {
        return roundSize;
    }

    public DOTS getCell(int i, int j) {
        return map[i][j];
    }

    public void printMap() {
        for (int i = 0; i <= roundSize; i++) {
            System.out.print(i + " | ");
        }
        System.out.println();
        for (int i = 0; i < roundSize; i++) {
            System.out.print((i + 1) + " | ");
            for (int j = 0; j < roundSize; j++) {
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
        for (int i = 0; i < roundSize; i++) {
            for (int j = 0; j < roundSize; j++) {
                if (map[i][j] == DOTS.EMPTY) return false;
            }
        }
        return true;
    }

    public boolean hasInRow(DOTS dot, int dotsToWin) {

        if (dotsToWin > roundSize) return false;

        //ToDO зачем?
        String winCombination = MapHelpers.winCombination(dot, dotsToWin);


        // Проверка по горизонталям
        for (int i = 0; i < roundSize; i++) {
            if (MapHelpers.rowToString(map[i]).contains(winCombination)) {
                return true;
            }
        }

        // Проверка по вертикалям
        for (int i = 0; i < roundSize; i++) {
            DOTS[] column = new DOTS[roundSize];
            for (int j = 0; j < roundSize; j++) {
                column[j] = map[j][i];
            }
            if (MapHelpers.rowToString(column).contains(winCombination)) {
                return true;
            }
        }

        // Проверка по главным диогоналям
        for (int i = 0; i < roundSize - dotsToWin + 1; i++) {
            DOTS[] diagonal = new DOTS[roundSize - i];
            for (int j = 0; j < roundSize - i; j++) {
                diagonal[j] = map[i + j][j];
            }
            if (MapHelpers.rowToString(diagonal).contains(winCombination)) {
                return true;
            }
        }

        for (int i = 1; i < roundSize - dotsToWin + 1; i++) {
            DOTS[] diagonal = new DOTS[roundSize - i];
            for (int j = 0; j < roundSize - i; j++) {
                diagonal[j] = map[j][i + j];
            }
            if (MapHelpers.rowToString(diagonal).contains(winCombination)) {
                return true;
            }
        }

        // Проверка по второстепенным диогоналям
        for (int i = dotsToWin - 1; i < roundSize; i++) {
            DOTS[] diagonal = new DOTS[i + 1];
            for (int j = 0; j < i + 1; j++) {
                diagonal[j] = map[j][i - j];
            }
            if (MapHelpers.rowToString(diagonal).contains(winCombination)) {
                return true;
            }
        }

        for (int i = 1; i < roundSize - dotsToWin + 1; i++) {
            DOTS[] diagonal = new DOTS[roundSize - i];
            for (int j = 0; j < roundSize - i; j++) {
                diagonal[j] = map[j + i][roundSize - j - 1];
            }
            if (MapHelpers.rowToString(diagonal).contains(winCombination)) {
                return true;
            }
        }
        return false;
    }

}
