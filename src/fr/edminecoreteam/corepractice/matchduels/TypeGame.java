package fr.edminecoreteam.corepractice.matchduels;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class TypeGame
{
    private HashMap<Player, String> typeGame;

    public TypeGame() {
        typeGame = new HashMap<>();
    }

    public void putInGame(Player player, String gameType) { typeGame.put(player, gameType); }

    public String getTypeGame(Player player) {
        return typeGame.get(player);
    }

    public void removeFromTypeGame(Player player) { typeGame.remove(player); }

    public HashMap<Player, String> getTypeGame() { return typeGame; }

    public int getListWhereGame(String game)
    {
        int i = 0;

        for (String sGame : typeGame.values())
        {
            if (sGame == game)
            {
                i++;
            }
        }

        return i;
    }
}
