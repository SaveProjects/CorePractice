package fr.edminecoreteam.corepractice.listeners;

import fr.edminecoreteam.corepractice.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class EventListeners implements Listener
{
    private static Core core = Core.getInstance();

    @EventHandler
    private void onDamage(EntityDamageEvent e)
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

    @EventHandler
    private void noHungerBarChange(FoodLevelChangeEvent e)
    {
        if (e.getEntity() instanceof Player)
        {
            Player p = ((Player) e.getEntity()).getPlayer();
            if (core.getInLobby().contains(p) || core.getInEditor().contains(p))
            {
                e.setFoodLevel(20);
            }
        }
    }

    @EventHandler
    private void weatherChangeEvent(WeatherChangeEvent e)
    {
        Core.getPlugin().getServer().dispatchCommand(Core.getPlugin().getServer().getConsoleSender(), "weather clear");
    }
}
