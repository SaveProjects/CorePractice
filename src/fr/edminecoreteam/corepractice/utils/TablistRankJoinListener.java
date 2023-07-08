package fr.edminecoreteam.corepractice.utils;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.data.ranks.Rank;
import fr.edminecoreteam.corepractice.data.ranks.RankInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TablistRankJoinListener implements Listener
{
    private static Core api = Core.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        refreshTablist(p);
        checkrankName(p);
    }

    private void refreshTablist(Player p) {

        new BukkitRunnable() {
            int t = 0;
            public void run() {

                if (!p.isOnline()) { cancel(); }

                RankInfo rankInfo = new RankInfo(p);

                if (rankInfo.getRankType().equalsIgnoreCase("static"))
                {
                    TeamsTagsManager.setNameTag(p, Rank.powerToRank(rankInfo.getRankID()).getOrderRank(), Rank.powerToRank(rankInfo.getRankID()).getDisplayName(), Rank.powerToRank(rankInfo.getRankID()).getSuffix());
                }
                if (rankInfo.getRankType().equalsIgnoreCase("tempo"))
                {
                    TeamsTagsManager.setNameTag(p, Rank.powerToRank(rankInfo.getRankID()).getOrderRank(), Rank.powerToRank(rankInfo.getRankID()).getDisplayName(), Rank.powerToRank(rankInfo.getRankID()).getSuffix());
                }
                if (rankInfo.getRankType().equalsIgnoreCase("module"))
                {
                    TeamsTagsManager.setNameTag(p, Rank.powerToRank(rankInfo.getRankModule()).getOrderRank(), Rank.powerToRank(rankInfo.getRankModule()).getDisplayName(), Rank.powerToRank(rankInfo.getRankModule()).getSuffix());
                }
                if (rankInfo.getRankType().equalsIgnoreCase("staff"))
                {
                    TeamsTagsManager.setNameTag(p, Rank.powerToRank(rankInfo.getRankModule()).getOrderRank(), Rank.powerToRank(rankInfo.getRankModule()).getDisplayName(), Rank.powerToRank(rankInfo.getRankModule()).getSuffix());
                }

                ++t;
                if (t == 50) {
                    run();
                }
            }
        }.runTaskTimer((Plugin)api, 0L, 50L);

    }

    private void checkrankName(Player p)
    {
        RankInfo rankInfo = new RankInfo(p);

        if (rankInfo.getRankType().equalsIgnoreCase("static"))
        {
            if (rankInfo.getRankID() == 0)
            {
                rankInfo.updateRankName("aucun");
            }
            if (rankInfo.getRankID() == 6)
            {
                rankInfo.updateRankName("STREAMER");
            }
            if (rankInfo.getRankID() == 7)
            {
                rankInfo.updateRankName("YOUTUBER");
            }
            if (rankInfo.getRankID() == 8)
            {
                rankInfo.updateRankName("FAMOUS");
            }
        }
        if (rankInfo.getRankType().equalsIgnoreCase("tempo"))
        {
            if (rankInfo.getRankID() == 1)
            {
                rankInfo.updateRankName("VIP");
            }
            if (rankInfo.getRankID() == 2)
            {
                rankInfo.updateRankName("SUPER-VIP");
            }
            if (rankInfo.getRankID() == 3)
            {
                rankInfo.updateRankName("SUPREME");
            }
            if (rankInfo.getRankID() == 4)
            {
                rankInfo.updateRankName("ULTRA");
            }
            if (rankInfo.getRankID() == 5)
            {
                rankInfo.updateRankName("ELITE");
            }
        }
        if (rankInfo.getRankType().equalsIgnoreCase("module"))
        {
            if (rankInfo.getRankModule() == 8)
            {
                rankInfo.updateRankName("FRIEND");
            }
        }
        if (rankInfo.getRankType().equalsIgnoreCase("staff"))
        {
            if (rankInfo.getRankModule() == 10)
            {
                rankInfo.updateRankName("STAFF");
            }
            if (rankInfo.getRankModule() == 11)
            {
                rankInfo.updateRankName("BUILDER");
            }
            if (rankInfo.getRankModule() == 12)
            {
                rankInfo.updateRankName("HELPER");
            }
            if (rankInfo.getRankModule() == 13)
            {
                rankInfo.updateRankName("MOD");
            }
            if (rankInfo.getRankModule() == 14)
            {
                rankInfo.updateRankName("DEV");
            }
            if (rankInfo.getRankModule() == 15)
            {
                rankInfo.updateRankName("RESP");
            }
            if (rankInfo.getRankModule() == 16)
            {
                rankInfo.updateRankName("ADMIN");
            }
        }
    }
}
