package fr.edminecoreteam.corepractice.listeners;

import fr.edminecoreteam.corepractice.data.ranks.Rank;
import fr.edminecoreteam.corepractice.data.ranks.RankInfo;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerChatListener implements Listener
{
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        World world = p.getWorld();
        RankInfo rankInfo = new RankInfo(p);
        e.setCancelled(true);
        if (e.getMessage() != null) {

            if (rankInfo.getRankType().equalsIgnoreCase("static"))
            {
                if (rankInfo.getRankID() > 0)
                {
                    e.setFormat(Rank.powerToRank(rankInfo.getRankID()).getDisplayName() + p.getName() + Rank.powerToRank(rankInfo.getRankID()).getSuffix() + " §8» §f" + e.getMessage().replace("&", "§"));
                    for (Player players : world.getPlayers()) {
                        players.sendMessage(e.getFormat());
                    }
                }
                else if (rankInfo.getRankID() == 0)
                {
                    e.setFormat(Rank.powerToRank(rankInfo.getRankID()).getDisplayName() + p.getName() + " §8» §7" + e.getMessage());
                    for (Player players : world.getPlayers()) {
                        players.sendMessage(e.getFormat());
                    }
                }
            }
            if (rankInfo.getRankType().equalsIgnoreCase("tempo"))
            {
                if (rankInfo.getRankID() >= 3)
                {
                    e.setFormat(Rank.powerToRank(rankInfo.getRankID()).getDisplayName() + p.getName() + Rank.powerToRank(rankInfo.getRankID()).getSuffix() + " §8» §f" + e.getMessage());
                    for (Player players : world.getPlayers()) {
                        players.sendMessage(e.getFormat());
                    }
                }
                else if (rankInfo.getRankID() < 3)
                {
                    e.setFormat(Rank.powerToRank(rankInfo.getRankID()).getDisplayName() + p.getName() + Rank.powerToRank(rankInfo.getRankID()).getSuffix() + " §8» §7" + e.getMessage());
                    for (Player players : world.getPlayers()) {
                        players.sendMessage(e.getFormat());
                    }
                }
            }
            if (rankInfo.getRankType().equalsIgnoreCase("module"))
            {
                e.setFormat(Rank.powerToRank(rankInfo.getRankModule()).getDisplayName() + p.getName() + Rank.powerToRank(rankInfo.getRankModule()).getSuffix() + " §8» §f" + e.getMessage());
                for (Player players : world.getPlayers()) {
                    players.sendMessage(e.getFormat());
                }
            }
            if (rankInfo.getRankType().equalsIgnoreCase("staff"))
            {
                e.setFormat(Rank.powerToRank(rankInfo.getRankModule()).getDisplayName() + p.getName() + Rank.powerToRank(rankInfo.getRankModule()).getSuffix() + " §8» §f" + e.getMessage().replace("&", "§"));
                for (Player players : world.getPlayers()) {
                    players.sendMessage(e.getFormat());
                }
            }
        }
    }
}
