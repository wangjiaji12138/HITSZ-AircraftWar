package edu.hitsz.dao;
import edu.hitsz.dao.Player;

import java.util.List;

public interface PlayerDao {
    List<Player> getAllPlayers();
    void doAdd(List<Player> playerList,Player player);
    List<Player> doDelete(List<Player> playerList,int playerId);

}
