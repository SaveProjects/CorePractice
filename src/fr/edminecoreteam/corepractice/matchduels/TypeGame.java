package fr.edminecoreteam.corepractice.matchduels;

import fr.edminecoreteam.corepractice.Core;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public int getListWhereGame(String game, String type, String soloOrDuo)
    {
        int i = 0;

        for (Player p : Core.getInstance().getInDuel())
        {
            if (getTypeGame(p).equalsIgnoreCase(game))
            {
                if (Core.getInstance().getGameIs().getGameIs(p).equalsIgnoreCase(type))
                {
                    if (Core.getInstance().getIfSoloOrDuo().getIfSoloOrDuo(p).equalsIgnoreCase(soloOrDuo))
                    {
                        i++;
                    }
                }
            }
        }

        return i;
    }
}
