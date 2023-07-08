package fr.edminecoreteam.corepractice.matchmaking;

import fr.edminecoreteam.corepractice.Core;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WhatIsGame
{
    private HashMap<Player, String> gameIs;

    public WhatIsGame() {
        gameIs = new HashMap<>();
    }

    public void putGameIs(Player player, String gameType) { gameIs.put(player, gameType); }

    public String getGameIs(Player player) {
        return gameIs.get(player);
    }

    public void removeGameIs(Player player) { gameIs.remove(player); }

    public HashMap<Player, String> getGameIs() { return gameIs; }

    public int getListWhereGameIs(String game)
    {
        int i = 0;

        for (String sGame : gameIs.values())
        {
            if (sGame == game)
            {
                i++;
            }
        }

        return i;
    }

    public List<Player> getInGameIs()
    {
        List<Player> list = new ArrayList<Player>();
        for (Player pS : Core.getInstance().getInLobby())
        {
            if (gameIs.get(pS) != null)
            {
                list.add(pS);
            }
        }
        return list;
    }
}
