package ru.geekbrains.ntr_0108;

import ru.geekbrains.ntr_0108.gui.GameWindow;
import ru.geekbrains.ntr_0108.players.AIIPlayer;
import ru.geekbrains.ntr_0108.players.HumanPlayer;
import ru.geekbrains.ntr_0108.players.IPlayer;

import javax.swing.*;
import java.awt.event.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class GameController {

    private static Map map;
    private static int sizeX;
    private static int sizeY;
    private static int cellWidth;
    private static int cellHeight;
    private static int winLen;
    private static boolean gameOver = false;
    private static IPlayer player1;
    private static IPlayer player2;
    private static GameWindow gameWindow;
    private static int turnsCounter = 0;
    private static boolean isHumanVsHumanMode = true;


    public GameController() {
        gameWindow = new GameWindow();
        map = Map.getInstance();

        gameWindow.getStartNewGameWindow().getBtnStartGame().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                turnsCounter = 0;
                gameOver = false;
                map.clear();
            }
        });

        gameWindow.getStartNewGameWindow().addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                sizeX = gameWindow.getFieldSizeX();
                sizeY = gameWindow.getFieldSizeY();
                isHumanVsHumanMode = gameWindow.isHumanVsHumanMode();
                winLen = gameWindow.getWinLen();
                cellHeight = gameWindow.getField().getCellHeight();
                cellWidth = gameWindow.getField().getCellWidth();

                map.setSize(sizeX, sizeY);
                map.setWinSize(winLen);

                player1 = new HumanPlayer("Игрок 1");
                if (gameWindow.isHumanVsHumanMode()) {
                    player2 = new HumanPlayer("Игрок 2");
                } else {
                    player2 = new AIIPlayer("SkyNet");
                }
                player1.setDot(DOTS.CROSS);
                player2.setDot(DOTS.ZERO);
            }
        });

        gameWindow.getField().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                int cellX = e.getX() / cellWidth;
                int cellY = e.getY() / cellHeight;

                if (map.isCellValid(cellX, cellY) && !gameOver) {
                    if (turnsCounter % 2 == 0) {
                        gameTurn(player1, cellX, cellY);

                    } else {
                        gameTurn(player2, cellX, cellY);
                    }
                    turnsCounter++;
                }
            }
        });
    }

    public static void gameTurn(IPlayer player, int cellX, int cellY) {

        player.nextTurn(cellX, cellY);
        gameWindow.getField().repaint();


        if (playerWin(player)) {
            showMessageDialog(gameWindow, "Выиграл " + player.getName() + "!");
            gameOver = true;
            return;
        }
        if (map.isFull()) {
            showMessageDialog(gameWindow, "Ничья!");
            gameOver = true;
            return;
        }

        if (!isHumanVsHumanMode) {
            player2.nextTurn();
            gameWindow.getField().repaint();
            turnsCounter++;
            if (playerWin(player2)) {
                showMessageDialog(gameWindow, "Выиграл " + player2.getName() + "!");
                gameOver = true;
                return;
            }
            if (map.isFull()) {
                showMessageDialog(gameWindow, "Ничья!");
                gameOver = true;
                return;
            }
        }
    }

    public static boolean playerWin(IPlayer player) {
        return map.checkWin(player.getDot());
    }
}
