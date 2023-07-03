package fr.edminecoreteam.corepractice.scoreboard;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import fr.edminecoreteam.corepractice.Core;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.Listener;

public class LeaveScoreboardEvent implements Listener
{
    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        Core.getInstance().getScoreboardManager().onLogout(p);
    }
}