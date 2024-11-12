package edu.hitsz.application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StartMenu {
    private JButton easyMode;
    private JButton commonMode;
    private JButton difficultMode;
    private JPanel musicChoose;
    private JPanel modeChoose;
    private JButton MusicBotton;
    private JLabel MusicChoose;
    private JPanel MainPanel;
    private  Game game;
    private boolean ifMusicOn = false;
    public StartMenu() {
        easyMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game = new EasyGame();
                Main.cardPanel.add(game);
                Main.cardLayout.last(Main.cardPanel);
                if(ifMusicOn ==true){
                    game.ifMusicOn = true;
                }
                game.action();
            }
        });
        commonMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game = new CommonGame();
                Main.cardPanel.add(game);
                Main.cardLayout.last(Main.cardPanel);
                if(ifMusicOn ==true){
                    game.ifMusicOn = true;
                }
                game.action();
            }
        });
        difficultMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game = new DifficultGame();
                Main.cardPanel.add(game);
                Main.cardLayout.last(Main.cardPanel);
                if(ifMusicOn == true){
                    game.ifMusicOn = true;
                }
                game.action();
            }
        });
        MusicBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(MusicBotton.getText()=="关"){
                    MusicBotton.setText("开");
                    ifMusicOn = true;
                }
                else{
                    MusicBotton.setText("关");
                    ifMusicOn = false;
                }
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("StartMenu");
        frame.setContentPane(new StartMenu().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }
}
