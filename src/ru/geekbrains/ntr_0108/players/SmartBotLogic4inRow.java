package ru.geekbrains.ntr_0108.players;

import ru.geekbrains.ntr_0104.AIHelpers;
import ru.geekbrains.ntr_0104.DOTS;
import ru.geekbrains.ntr_0104.Map;
import ru.geekbrains.ntr_0104.MapHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SmartBotLogic4inRow {

    private static final int DOTS_TO_WIN = 4;
    private static final boolean PRINT_VALUE_MAP = false;

    private Map gameMap;
    private static DOTS[][] botMap;
    private static int size;
    private DOTS dot;
    private int bestX;
    private int bestY;

    public SmartBotLogic4inRow(Map gameMap, DOTS dot) {
        this.gameMap = gameMap;
        this.size = gameMap.getSize();
        createBotMap();
        ValueMoveMap vMMap = new ValueMoveMap();
        bestX = vMMap.getBestMove()[0];
        bestY = vMMap.getBestMove()[1];
        if (PRINT_VALUE_MAP) vMMap.printValueMap();


    }

    public int getBestX() {
        return bestX;
    }

    public int getBestY() {
        return bestY;
    }

    public void createBotMap() {
        boolean clone = false;
        botMap = new DOTS[size][size];

        if (dot == DOTS.CROSS) clone = true;

        for (int i = 0; i < gameMap.getSize(); i++) {
            for (int j = 0; j < gameMap.getSize(); j++) {
                if (clone) {
                    botMap[i][j] = gameMap.getCell(i, j);
                } else {
                    if (gameMap.getCell(i, j) == DOTS.EMPTY) {
                        botMap[i][j] = DOTS.EMPTY;
                    } else {
                        botMap[i][j] = gameMap.getCell(i, j) == DOTS.CROSS ? DOTS.ZERO : DOTS.CROSS;
                    }
                }
            }
        }
    }

    private class ValueMoveMap {
        int[][] vmMap;
        List<Row> rowsList;

        public ValueMoveMap() {
            vmMap = new int[size][size];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (!(botMap[i][j] == DOTS.EMPTY)) {
                        vmMap[i][j] = -10000;
                    }
                }
            }


            Rows rows = new Rows();
            rowsList = rows.getRows();

            //Победный ход
            List<String> winMoves = new ArrayList<>();
            winMoves.add(".XXX");
            winMoves.add("X.XX");
            winMoves.add("XX.X");
            winMoves.add("XXX.");

            putValues(winMoves, 1000);

            //Не дать победить
            List<String> resistMoves = new ArrayList<>();
            resistMoves.add(".OOO");
            resistMoves.add("O.OO");
            resistMoves.add("OO.O");
            resistMoves.add("OOO.");

            putValues(resistMoves, 500);

            List<String> resistMoves2 = new ArrayList<>();
            resistMoves2.add(".OO..");
            resistMoves2.add("..OO.");

            putValues(resistMoves2, 50);


            //Два в ряд
            List<String> twoInRowMoves = new ArrayList<>();
            twoInRowMoves.add("XX..");
            twoInRowMoves.add(".XX.");
            twoInRowMoves.add(".XX");
            twoInRowMoves.add("X..X");
            twoInRowMoves.add("X.X.");
            twoInRowMoves.add(".X.X");

            putValues(twoInRowMoves, 10);

            //Один
            List<String> oneRowMoves = new ArrayList<>();
            oneRowMoves.add("X...");
            oneRowMoves.add(".X..");
            oneRowMoves.add("..X.");
            oneRowMoves.add("...X");

            putValues(oneRowMoves, 1);

        }

        private void putValues(List<String> patterns, int score) {
            for (Row row : rowsList) {
                for (String pattern : patterns) {
                    int index = row.stringRow.indexOf(pattern);
                    if (index == -1) continue;
                    for (int i = 0; i < pattern.length(); i++) {
                        if (pattern.charAt(i) == '.') {
                            vmMap[row.yRow[i + index]][row.xRow[i + index]] += score;
                        }
                    }
                }
            }
        }

        public void printValueMap() {
            for (int i = 0; i <= size; i++) {
                System.out.print(i + " | ");
            }
            System.out.println();
            for (int i = 0; i < size; i++) {
                System.out.print((i + 1) + " | ");
                for (int j = 0; j < size; j++) {
                    System.out.print(vmMap[i][j] + " | ");
                }
                System.out.println();
            }
            System.out.println();
        }


        public Integer[] getBestMove() {
            int max = vmMap[0][0];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (vmMap[i][j] > max) {
                        max = vmMap[i][j];
                    }
                }
            }

            List<Integer[]> maxValueCords = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (max == vmMap[i][j]) {
                        maxValueCords.add(new Integer[]{j, i});
                    }
                }
            }

            Random random = new Random();
            random.nextInt(maxValueCords.size());
            return maxValueCords.get(random.nextInt(maxValueCords.size()));
        }
    }

    private class Rows {

        private List<Row> rows;

        public Rows() {
            rows = new ArrayList<>();

            //Сохраняем горизонтали
            for (int i = 0; i < size; i++) {
                Row row = new Row();
                row.stringRow = MapHelpers.rowToString(botMap[i]);
                row.dRow = new DOTS[size];
                row.xRow = new int[size];
                row.yRow = new int[size];

                for (int j = 0; j < size; j++) {
                    row.xRow[j] = j;
                    row.yRow[j] = i;
                    row.dRow[j] = botMap[i][j];
                }
                row.stringRow = AIHelpers.rowToString(row.dRow);
                rows.add(row);
            }

            // Сохраняем вертикали
            for (int i = 0; i < size; i++) {
                Row row = new Row();
                row.dRow = new DOTS[size];
                row.xRow = new int[size];
                row.yRow = new int[size];


                for (int j = 0; j < size; j++) {
                    row.xRow[j] = i;
                    row.yRow[j] = j;
                    row.dRow[j] = botMap[j][i];
                }
                row.stringRow = AIHelpers.rowToString(row.dRow);
                rows.add(row);
            }

            // Сохраняем главные диоганали
            for (int i = 0; i < size - DOTS_TO_WIN + 1; i++) {
                Row row = new Row();
                row.dRow = new DOTS[size - i];
                row.xRow = new int[size - i];
                row.yRow = new int[size - i];

                for (int j = 0; j < size - i; j++) {
                    row.xRow[j] = j;
                    row.yRow[j] = i + j;
                    row.dRow[j] = botMap[i + j][j];
                }
                row.stringRow = AIHelpers.rowToString(row.dRow);
                rows.add(row);
            }

            for (int i = 1; i < size - DOTS_TO_WIN + 1; i++) {
                Row row = new Row();
                row.dRow = new DOTS[size - i];
                row.xRow = new int[size - i];
                row.yRow = new int[size - i];

                for (int j = 0; j < size - i; j++) {
                    row.xRow[j] = i + j;
                    row.yRow[j] = j;
                    //row.dRow[j] = botMap[i + j][j];
                    row.dRow[j] = botMap[j][j + i];
                }
                row.stringRow = AIHelpers.rowToString(row.dRow);
                rows.add(row);
            }

            // Сохраняем второстепенные
            for (int i = DOTS_TO_WIN - 1; i < size; i++) {
                Row row = new Row();
                row.dRow = new DOTS[i + 1];
                row.xRow = new int[i + 1];
                row.yRow = new int[i + 1];

                for (int j = 0; j < i + 1; j++) {
                    row.xRow[j] = i - j;
                    row.yRow[j] = j;
                    row.dRow[j] = botMap[j][i - j];
                }
                row.stringRow = AIHelpers.rowToString(row.dRow);
                rows.add(row);
            }

            for (int i = 1; i < size - DOTS_TO_WIN + 1; i++) {
                Row row = new Row();
                row.dRow = new DOTS[size - i];
                row.xRow = new int[size - i];
                row.yRow = new int[size - i];

                for (int j = 0; j < size - i; j++) {
                    row.xRow[j] = size - j - 1;
                    row.yRow[j] = j + i;
                    row.dRow[j] = botMap[j + i][size - j - 1];
                }
                row.stringRow = AIHelpers.rowToString(row.dRow);
                rows.add(row);
            }
        }

        public List<Row> getRows() {
            return rows;
        }

    }

    private class Row {
        String stringRow;
        DOTS[] dRow;
        int[] xRow;
        int[] yRow;
    }

}
