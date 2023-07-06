package fr.edminecoreteam.corepractice.matchduels;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.kits.LoadKits;
import fr.edminecoreteam.corepractice.matchmaking.GameCheck;
import org.bukkit.entity.Player;

public class GameListeners
{
    private static Core core = Core.getInstance();
    public static void startGame(Player p1, Player p2)
    {
        GameCheck gameCheck = core.getGameCheck();
        core.getInWaiting().remove(p1);
        core.getInWaiting().remove(p2);
        core.getInLobby().remove(p1);
        core.getInLobby().remove(p2);

        LoadKits p1Kit = new LoadKits(p1);
        LoadKits p2Kit = new LoadKits(p2);
        p1Kit.equipUnrankedDefaultKit(gameCheck.getGame(p1));
        p2Kit.equipUnrankedDefaultKit(gameCheck.getGame(p2));
        gameCheck.removeSerchGame(p1);
        gameCheck.removeSerchGame(p2);
        core.getInDuel().add(p1);
        core.getInDuel().add(p2);
    }
}
