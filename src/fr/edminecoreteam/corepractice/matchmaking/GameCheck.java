package fr.edminecoreteam.corepractice.matchmaking;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class GameCheck
{
    private HashMap<Player, String> gameCheck;

    public GameCheck() {
        gameCheck = new HashMap<>();
    }

    public void searchGame(Player player, String gameType) { gameCheck.put(player, gameType); }

    public String getGame(Player player) {
        return gameCheck.get(player);
    }

    public void removeSerchGame(Player player) { gameCheck.remove(player); }

    public HashMap<Player, String> getGame() { return gameCheck; }
}
