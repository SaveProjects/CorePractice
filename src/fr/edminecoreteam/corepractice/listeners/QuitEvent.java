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

        p.getInventory().clear();
        e.setQuitMessage(null);
    }
}
