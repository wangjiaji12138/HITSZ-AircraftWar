package edu.hitsz.dao;

import java.util.Comparator;

public class PlayerScoreComparator implements Comparator<Player> {
    @Override
    public int compare(Player player1, Player player2) {
        // 按照分数从高到低排序
        return player2.getPlayerScore() - player1.getPlayerScore();
    }
}
