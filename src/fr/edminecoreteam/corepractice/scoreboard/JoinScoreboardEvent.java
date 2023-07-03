package fr.edminecoreteam.corepractice.scoreboard;

import fr.edminecoreteam.corepractice.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinScoreboardEvent implements Listener
{
    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Core.getInstance().getScoreboardManager().onLogin(p);
    }
}