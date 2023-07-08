package fr.edminecoreteam.corepractice.matchmaking;

import fr.edminecoreteam.corepractice.Core;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameCheck
{
    private HashMap<Player, String> gameCheck;
    private static final Core core = Core.getInstance();

    public GameCheck() {
        gameCheck = new HashMap<>();
    }

    public void searchGame(Player player, String gameType) { gameCheck.put(player, gameType); }

    public String getGame(Player player) {
        return gameCheck.get(player);
    }

    public void removeSerchGame(Player player) { gameCheck.remove(player); }

    public HashMap<Player, String> getGame() { return gameCheck; }

    public int getListWhereGame(String game, String type)
    {
        int i = 0;

        if (type.equalsIgnoreCase("unranked"))
        {
            for (Player pGame : getInWaiting())
            {
                if (getGame(pGame).equalsIgnoreCase(game))
                {
                    if (Core.getInstance().getGameIs().getGameIs(pGame).equalsIgnoreCase(type))
                    {
                        i++;
                    }
                }
            }
        }
        if (type.equalsIgnoreCase("ranked"))
        {
            for (Player pGame : getInWaiting())
            {
                if (getGame(pGame).equalsIgnoreCase(game))
                {
                    if (Core.getInstance().getGameIs().getGameIs(pGame).equalsIgnoreCase(type))
                    {
                        i++;
                    }
                }
            }
        }


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
