package fr.edminecoreteam.corepractice.listeners;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.data.ranks.RankInfo;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class JoinEvent implements Listener
{

    private static Core core = Core.getInstance();

    public static List<Player> candoublejump;
    public JoinEvent() { candoublejump = new ArrayList<Player>(); }
    public static List<Player> getCanDoubleJump() { return JoinEvent.candoublejump; }

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        RankInfo rankInfo  = new RankInfo(p);
        if (!core.getInLobby().contains(p))
        {
            core.getInLobby().add(p);
        }

        if (rankInfo.getRankID() >= 3) { getCanDoubleJump().add(p); }
        if (rankInfo.getRankModule() >= 10) { getCanDoubleJump().add(p); }

        Location lobbySpawn = new Location(Bukkit.getWorld(core.getConfig().getString("Lobby.world")),
                (float) core.getConfig().getDouble("Lobby.x")
                , (float) core.getConfig().getDouble("Lobby.y")
                , (float) core.getConfig().getDouble("Lobby.z")
                , (float) core.getConfig().getDouble("Lobby.t")
                , (float) core.getConfig().getDouble("Lobby.b"));

        for (PotionEffect effect : p.getActivePotionEffects ())
        {
            p.removePotionEffect(effect.getType());
        }
        p.setGameMode(GameMode.ADVENTURE);
        p.setFoodLevel(20);
        p.teleport(lobbySpawn);
        p.setFireTicks(0);
        ItemListeners.getLobbyItems(p);
        p.sendTitle("§e§lPractice", "§7Par Edmine Network.");
        e.setJoinMessage(null);
    }
}
