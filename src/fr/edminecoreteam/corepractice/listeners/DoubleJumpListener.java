package fr.edminecoreteam.corepractice.listeners;

import fr.edminecoreteam.corepractice.Core;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class DoubleJumpListener implements Listener
{

    private static Core core = Core.getInstance();

    @EventHandler
    public void setVelocity(PlayerToggleFlightEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR || p.isFlying()) {
            return;
        }
        e.setCancelled(true);
        if (JoinEvent.getCanDoubleJump().contains(p)) {
            if (core.getInLobby().contains(p))
            {
                if (p.getPassenger() != null)
                {
                    Entity ent = p.getPassenger();
                    Bukkit.getWorld(ent.getWorld().getName()).playEffect(ent.getLocation(), Effect.EXPLOSION_LARGE, Integer.MIN_VALUE);
                    p.playSound(p.getLocation(), Sound.FIREWORK_BLAST2, 1.0f, 1.0f);
                    ent.setVelocity(ent.getLocation().getDirection().multiply(1.5).setY(1));
                }
                else
                {
                    Bukkit.getWorld(p.getWorld().getName()).playEffect(p.getLocation(), Effect.EXPLOSION_LARGE, Integer.MIN_VALUE);
                    p.playSound(p.getLocation(), Sound.FIREWORK_BLAST2, 1.0f, 1.0f);
                    p.setAllowFlight(false);
                    p.setFlying(false);
                    p.setVelocity(e.getPlayer().getLocation().getDirection().multiply(1.5).setY(1));
                    p.setAllowFlight(true);
                }
            }
        }
    }
}
