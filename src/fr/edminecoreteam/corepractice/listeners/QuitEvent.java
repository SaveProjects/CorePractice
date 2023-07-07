package fr.edminecoreteam.corepractice.listeners;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.matchduels.GameListeners;
import fr.edminecoreteam.corepractice.utils.UnloadWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
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

        if (JoinEvent.getCanDoubleJump().contains(p))
        {
            JoinEvent.getCanDoubleJump().remove(p);
        }

        if (core.getGameID().getGameID(p) != null)
        {
            if (core.getInDuel().contains(p) || core.getInPreDuel().contains(p))
            {
                Player pVictory = core.getMatchOppenant().getMatchOppenant(p);

                World pVictoryWorld = pVictory.getWorld();
                String worldName = core.getWorldName().getWorldName(pVictory);
                String gameID = core.getGameID().getIDString(pVictory);

                pVictory.sendTitle("§a§lVictoire !", "§7C'étais moins une !");
                pVictory.playSound(pVictory.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 1.0f);

                core.getInDuel().remove(pVictory);
                core.getInEndDuel().add(pVictory);

                GameListeners.endGame(pVictory);

                Bukkit.getScheduler().runTaskLater(core, () -> {

                    for (Player pWorld : pVictoryWorld.getPlayers())
                    {
                        GameListeners.leaveGame(pWorld);
                    }

                    UnloadWorld.deleteWorld(gameID);
                }, 100);

                if (core.getWorldName().getWorldName(p) != null)
                {
                    core.getWorldName().removeWorldName(p);
                }
                if (core.getGameType().getTypeGame(p) != null)
                {
                    core.getGameType().removeFromTypeGame(p);
                }
                if (core.getGameID().getGameID(p) != null)
                {
                    core.getGameID().removeFromGameID(p);
                }

                if (core.getInEndDuel().contains(p))
                {
                    core.getInEndDuel().remove(p);
                }
                if (!core.getInLobby().contains(p))
                {
                    core.getInLobby().add(p);
                }
            }
        }

        p.getInventory().clear();
        e.setQuitMessage(null);
    }
}
