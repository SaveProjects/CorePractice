package fr.edminecoreteam.corepractice.listeners;

import fr.edminecoreteam.corepractice.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
                if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID))
                {
                    Location lobbySpawn = new Location(Bukkit.getWorld(core.getConfig().getString("Lobby.world")),
                            (float) core.getConfig().getDouble("Lobby.x")
                            , (float) core.getConfig().getDouble("Lobby.y")
                            , (float) core.getConfig().getDouble("Lobby.z")
                            , (float) core.getConfig().getDouble("Lobby.t")
                            , (float) core.getConfig().getDouble("Lobby.b"));
                    p.teleport(lobbySpawn);
                    ((Player) p).sendTitle("§c§lHop Hop Hop !", "§7Tu ne peux pas quitter la cité des dieux...");
                }
            }
        }
    }

    @EventHandler
    private void onDamageByAnother(EntityDamageByEntityEvent e)
    {
        if (e.getEntity() instanceof Player)
        {
            if (e.getDamager() instanceof Player)
            {
                Player p = ((Player) e.getEntity()).getPlayer();
                Player pA = (Player) e.getDamager();
                if (!core.getMatchOppenant().getMatchOppenant(p).equals(pA))
                {
                    e.setCancelled(true);
                }
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
        e.setCancelled(true);
    }
}
