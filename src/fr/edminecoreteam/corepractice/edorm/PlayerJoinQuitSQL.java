package fr.edminecoreteam.corepractice.edorm;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.data.PracticeData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitSQL implements Listener
{
    private static final Core core = Core.getInstance();

    @EventHandler
    private void onJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        PracticeData data = new PracticeData(p);

        data.createData();
        data.updatePlayerName();


    }

    @EventHandler
    private void onQuit(PlayerQuitEvent e)
    {

    }

}
