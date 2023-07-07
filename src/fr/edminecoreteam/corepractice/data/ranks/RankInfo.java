package fr.edminecoreteam.corepractice.data.ranks;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class RankInfo
{
    private Map<Player, RankInfo> rankInfo;
    private Player p;
    private RankData rankData;;

    public RankInfo(Player p) {
        this.p = p;
        this.rankInfo = new HashMap<Player, RankInfo>();
        this.rankData = new RankData(p);
        this.rankInfo.put(p, this);
    }

    public RankInfo getAccountInfos(Player player) { return rankInfo.get(player); }

    public String getPlayerName() { return p.getName(); }

    public boolean hasRank() { return rankData.hasRank(); }

    public void updateRankName(String name) { rankData.updateRankName(name); }

    public int getRankID() { return rankData.getRankID(); }

    public int getRankModule() { return rankData.getRankModule(); }

    public String getPurchaseDate() { return rankData.getPurchaseDate(); }

    public String getDeadLineDate() { return rankData.getDeadLineDate(); }

    public String getRankType() { return rankData.getRankType(); }
}
