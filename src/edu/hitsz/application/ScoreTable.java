package edu.hitsz.application;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import edu.hitsz.dao.Player;
import edu.hitsz.dao.PlayerDaoImpl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ScoreTable {
    private JPanel MainPanel;
    private JPanel TopPanel;
    private JPanel BottomPanel;
    private JLabel head;
    private JScrollPane scoreScroll;
    private JTable scoreList;
    private JButton deleteButton;
    private JButton returnBotton;
    private JLabel mode;
    private String modeText;
    PlayerDaoImpl Impl = new PlayerDaoImpl();
    String relativePath = System.getProperty("user.dir");
    String filePath = relativePath;
    String[] columnName = {"排名","姓名","成绩","游戏时间"};
    List<Player> playerlist;
    List<Player> sortedplayerlist;

    public  ScoreTable(String modeText){
        this.modeText = modeText;
        filePath = relativePath+"/"+modeText+"ModeScore.txt";
        playerlist = Impl.getPlayersFromFile(Impl,filePath);
        sortedplayerlist = Impl.sortScore(Impl.getAllPlayers());
        mode.setText(modeText);
        String[][] tableData = new String[sortedplayerlist.size()][4];
        for(int i=0;i<sortedplayerlist.size();i++){
            tableData[i][0]= Integer.toString(i+1);
            tableData[i][1]= sortedplayerlist.get(i).getPlayerName();
            tableData[i][2]= Integer.toString(sortedplayerlist.get(i).getPlayerScore());
            tableData[i][3]= sortedplayerlist.get(i).getPlayTime();
        }
        DefaultTableModel model = new DefaultTableModel(tableData,columnName);

    //JTable并不存储自己的数据，而是从表格模型那里获取它的数据
        scoreList.setModel(model);
        scoreScroll.setViewportView(scoreList);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = scoreList.getSelectedRow();
                if(row != -1){
                    model.removeRow(row);
                }
                int id = sortedplayerlist.get(row).getPlayerId();
                playerlist = Impl.doDelete(playerlist,id);
                Impl.rewritePlayerToFile(filePath,playerlist);
                sortedplayerlist = Impl.sortScore(Impl.getAllPlayers());
                String[][] tableData = new String[sortedplayerlist.size()][4];
                for(int i=0;i<sortedplayerlist.size();i++){
                    tableData[i][0]= Integer.toString(i+1);
                    tableData[i][1]= sortedplayerlist.get(i).getPlayerName();
                    tableData[i][2]= Integer.toString(sortedplayerlist.get(i).getPlayerScore());
                    tableData[i][3]= sortedplayerlist.get(i).getPlayTime();
                }
                DefaultTableModel model = new DefaultTableModel(tableData,columnName);

                //JTable并不存储自己的数据，而是从表格模型那里获取它的数据
                scoreList.setModel(model);
                scoreScroll.setViewportView(scoreList);
            }
        });
        returnBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.cardLayout.first(Main.cardPanel);
            }
        });
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }
}
