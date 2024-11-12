package edu.hitsz.dao;

public class Player {
    private int playerId;
    private String playerName;
    private int score;
    private String playTime;
    public Player(int playerId,String playerName,int score,String playTime) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.score = score;
        this.playTime = playTime;
    }
    //编号
    public int getPlayerId() {
        return playerId;
    }
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
    //名称
    public String getPlayerName() {
        return playerName;
    }
    //分数
    public int getPlayerScore() {
        return score;
    }

    public String recordToString() {
        return playerId + " " + playerName + " " + score+" "+playTime;
    }
    //时间
    public String getPlayTime() {
        return playTime;
    }
}
