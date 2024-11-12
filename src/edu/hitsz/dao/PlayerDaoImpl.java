package edu.hitsz.dao;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.*;
public class PlayerDaoImpl implements PlayerDao{
    private List<Player> players;

    public PlayerDaoImpl(){
        players = new ArrayList<Player>();
    }

    @Override
    public List<Player> doDelete(List<Player> playerList,int playerId) {
        for (Player player : playerList) {
            if (player.getPlayerId() == playerId) {
                players.remove(player);
                System.out.println("Delete player: ID [" + playerId + "]");
                break;
            }
            else{continue;}
        }
        for(Player player : playerList){
            if(player.getPlayerId()>playerId){
                player.setPlayerId(player.getPlayerId()-1);
            }
        }
        return playerList;
    }
    @Override
    public List<Player> getAllPlayers() {
        return players;
    }

    //新增
    @Override
    public void doAdd(List<Player> playerList,Player player) {
        players.add(player);
    }
    //排行榜功能
    public List<Player> sortScore(List<Player> playerList) {
        PlayerDaoImpl Impl = new PlayerDaoImpl();
        for(Player player : playerList){
            Impl.doAdd(Impl.getAllPlayers(),player);
        }
        if(Impl.getAllPlayers().size()>1){
            Collections.sort(Impl.getAllPlayers(), new PlayerScoreComparator());
        }
        return Impl.getAllPlayers();
    }

    public void writeFile(String filePath,String content){
        try {
            Path path = Paths.get(filePath);
            Files.write(path,(content+System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);


        }catch(IOException e){
            System.err.println("读取文件时出现异常：" + e.getMessage());
            // 在这里处理文件读取过程中的异常
        }
    }
    public void rewritePlayerToFile(String filePath,List<Player> playerList){
        try {
            Path path = Paths.get(filePath);
            Files.write(path,"".getBytes(),StandardOpenOption.TRUNCATE_EXISTING);
            for(Player player :playerList){
                Files.write(path,(player.recordToString()+System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
            }

        }catch(IOException e){
            System.err.println("读取文件时出现异常：" + e.getMessage());
            // 在这里处理文件读取过程中的异常
        }
    }
    //规范化时间
    public String CurrentTime(){
        String currentTime = LocalDateTime.now().toString();
        LocalDateTime dateTime = LocalDateTime.parse(currentTime);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        currentTime = dateTime.format(formatter);
        return currentTime;
    }
    public void PrintScore(List<Player> players){
        int position=1;
        for (Player player : players) {
            System.out.println("第"+position+"名："+player.getPlayerName() + "，分数为" + player.getPlayerScore()+","+player.getPlayTime());
            position++;
        }
    }
    public List<Player> getPlayersFromFile(PlayerDaoImpl Impl,String filePath){
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("文件不存在，已创建新文件：" + filePath);
            } catch (IOException e) {
                System.err.println("创建新文件时出现异常：" + e.getMessage());
            }
        }
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
            int order = 0;
            for (String line : lines) {
                String[] parts = line.split(" ");// 使用空格分割
                Impl.doAdd(Impl.getAllPlayers(),new Player(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        Integer.parseInt(parts[2]),
                        parts[3]+" "+parts[4]));
            }
        }catch(IOException e) {
            System.err.println("读取文件时出现异常：" + e.getMessage());
            // 在这里处理文件读取过程中的异常
        }
        return Impl.getAllPlayers();
    }
}

