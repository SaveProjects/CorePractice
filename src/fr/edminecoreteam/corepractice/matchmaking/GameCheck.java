package fr.edminecoreteam.corepractice.matchmaking;

import fr.edminecoreteam.corepractice.Core;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public int getListWhereGame(String game)
    {
        int i = 0;

        for (String sGame : gameCheck.values())
        {
            if (sGame == game)
            {
                i++;
            }
        }

        return i;
    }

    public List<Player> getInWaiting()
    {
        List<Player> list = new ArrayList<Player>();
        for (Player pS : Core.getInstance().getInLobby())
        {
            if (gameCheck.get(pS) != null)
            {
                list.add(pS);
            }
        }
        return list;
    }
}
