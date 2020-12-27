package ru.geekbrains.ntr_0108;

import ru.geekbrains.ntr_0108.gui.GameWindow;
import ru.geekbrains.ntr_0108.players.AIIPlayer;
import ru.geekbrains.ntr_0108.players.HumanPlayer;
import ru.geekbrains.ntr_0108.players.IPlayer;

import javax.swing.*;
import java.awt.event.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class GameController {

    public static Map map;
    public static int sizeX;
    public static int sizeY;
    public static int cellWidth;
    public static int cellHeight;
    public static int winLen;
    public static boolean gameOver = false;
    public static IPlayer player1;
    public static IPlayer player2;
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
                        System.out.println("2");
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
        }
        if (!isHumanVsHumanMode) {
            gameWindow.getField().repaint();
            turnsCounter++;
            if (playerWin(player2)) {
                showMessageDialog(gameWindow, "Выиграл " + player2.getName() + "!");
                gameOver = true;
            }
        }
    }

    public static boolean playerWin(IPlayer player) {
        return map.hasInRow(player.getDot(), winLen);
    }




 /*   public static void ticTacToeGame(IPlayer player1, IPlayer player2) {

        int turnsCounter = 0;
        map = Map.getInstance(SIZE);
        map.printMap();
        player1.setDot(DOTS.CROSS);
        player2.setDot(DOTS.ZERO);

        while (!gameOver){
            if (turnsCounter % 2 == 0) {
                gameTurn(player1);
            } else {
                gameTurn(player2);
            }
            turnsCounter++;
        }
    }

    public static void gameTurn(IPlayer player) {
        player.nextTurn();
        map.printMap();
        if (playerWin(player)) {
            System.out.printf("Выиграл %s\n", player.getName());
            gameOver = true;
        } else if (map.isFull()) {
            System.out.println("Ничья!");
            gameOver = true;
        }

    }

    public static boolean playerWin (IPlayer player) {
        return map.hasInRow(player.getDot(), DOTS_TO_WIN);
    } */
}
