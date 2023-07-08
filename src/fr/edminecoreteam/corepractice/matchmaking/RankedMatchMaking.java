package fr.edminecoreteam.corepractice.matchmaking;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.listeners.ItemListeners;
import org.bukkit.entity.Player;

public class RankedMatchMaking
{
    private Player p;

    private static Core core = Core.getInstance();

    public RankedMatchMaking(Player p)
    {
        this.p = p;
    }

    public void start(String game)
    {
        GameCheck gameCheck = core.getGameCheck();
        WhatIsGame gameIs = core.getGameIs();

        if (gameCheck.getGame(p) == null)
        {
            core.getInWaiting().add(p);
            gameIs.removeGameIs(p);
            gameIs.putGameIs(p, "ranked");
            gameCheck.searchGame(p, game);

            p.sendMessage("§fRecherche d'une partie en cours sur le mode §e§l" + game + "§f...");
            ItemListeners.foundGameItems(p);
        }
        else
        {
            p.sendMessage("§cAction impossible, vous êtes déjà dans une file d'attente...");
        }
    }
}
