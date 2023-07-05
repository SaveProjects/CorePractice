package fr.edminecoreteam.corepractice.listeners;

import fr.edminecoreteam.corepractice.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener
{
    private static Core core = Core.getInstance();

    @EventHandler
    public void onQuit(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();

        if (core.getInLobby().contains(p))
        {
            core.getInLobby().remove(p);
        }
        if (core.getInEditor().contains(p))
        {
            core.getInEditor().remove(p);
        }
        if (core.getInWaiting().contains(p))
        {
            core.getInWaiting().remove(p);
            if (core.getGameCheck().getGame(p) != null)
            {
                core.getGameCheck().removeSerchGame(p);
            }
        }
        if (core.getInDuel().contains(p))
        {
            core.getInDuel().remove(p);
        }

        p.getInventory().clear();
        e.setQuitMessage(null);
    }
}
