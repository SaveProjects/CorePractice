package fr.edminecoreteam.corepractice.matchduels;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class GameID
{
    private HashMap<Player, Integer> gameID;

    public GameID() {
        gameID = new HashMap<>();
    }

    public void putGameID(Player player, Integer id) { gameID.put(player, id); }

    public Integer getGameID(Player player) {
        return gameID.get(player);
    }

    public void removeFromGameID(Player player) { gameID.remove(player); }

    public HashMap<Player, Integer> getGameID() { return gameID; }
}
