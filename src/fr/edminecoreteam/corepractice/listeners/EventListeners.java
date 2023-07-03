package fr.edminecoreteam.corepractice.listeners;

import fr.edminecoreteam.corepractice.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EventListeners implements Listener
{
    private static Core core = Core.getInstance();

    @EventHandler
    public void onDamage(EntityDamageEvent e)
    {
        if (e.getEntity() instanceof Player)
        {
            Player p = ((Player) e.getEntity()).getPlayer();
            if (core.getInLobby().contains(p) || core.getInEditor().contains(p))
            {
                e.setCancelled(true);
            }
        }
    }
}
