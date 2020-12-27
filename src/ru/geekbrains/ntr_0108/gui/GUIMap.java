package ru.geekbrains.ntr_0108.gui;

import ru.geekbrains.ntr_0108.DOTS;
import ru.geekbrains.ntr_0108.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIMap extends JPanel {
    static final int MODE_H_V_A = 0;
    static final int MODE_H_V_H = 1;
    int[][] field;
    int fieldSizeX;
    int fieldSizeY;
    int winLen;
    int cellHeight;
    int cellWidth;
    boolean isInitialized = false;

    GUIMap() {
        setBackground(Color.ORANGE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    boolean startNewGame(int mode, int fieldSizeX, int fieldSizeY, int winLen) {
        System.out.println(mode + " " + fieldSizeX + " " + fieldSizeY + " " + winLen);
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        this.winLen = winLen;
        field = new int[fieldSizeY][fieldSizeX];
        isInitialized = true;
        repaint();
        return true;
    }

    public void render(Graphics g) {
        if (!isInitialized) return;

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        cellWidth = panelWidth / fieldSizeX;
        cellHeight = panelHeight / fieldSizeY;

        // рисуем горизонтальные полоски
        for (int i = 0; i < fieldSizeY; i++) {
            int y = i * cellHeight;
            g.drawLine(0, y, panelWidth, y);
        }

        // рисуем вертикальные полоски
        for (int i = 0; i < fieldSizeX; i++) {
            int x = i * cellWidth;
            g.drawLine(x, 0, x, panelHeight);
        }
        Map map = Map.getInstance();

        // рисуем крестики и нолики
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                if (map.getCell(i, j) == DOTS.CROSS) {
                    int y = i * cellWidth;
                    int x = j * cellHeight;

                    g.setColor(Color.BLUE);
                    g.drawLine((int) (x + 0.1 * cellWidth), (int) (y + 0.1 * cellHeight),
                            (int) (x + 0.9 * cellWidth), (int) (y + 0.9 * cellHeight));
                    g.drawLine((int) (x + 0.1 * cellWidth), (int) (y + 0.9 * cellHeight),
                            (int) (x + 0.9 * cellWidth), (int) (y + 0.1 * cellHeight));
                }
                if (map.getCell(i, j) == DOTS.ZERO) {
                    int y = i * cellWidth;
                    int x = j * cellHeight;
                    g.setColor(Color.RED);
                    g.drawOval((int) (x + 0.1 * cellWidth), (int) (y + 0.1 * cellHeight),
                            (int) (0.8 * cellWidth), (int) (0.8 * cellHeight));
                }
            }
        }
        g.setColor(Color.BLACK);
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public int getCellWidth() {
        return cellWidth;
    }
}
