package fr.edminecoreteam.corepractice.data.ranks;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public enum Rank
{
    JOUEUR("JOUEUR", 0, 0, "§l§6Rank", "§7Joueur", "§7", ""),
    VIP("VIP", 1, 1, "§k§6Rank", "§fVIP", "§F§lVIP §7", ""),
    SUPERVIP("SUPERVIP", 2, 2, "§i§6Rank", "§eSUPER-VIP", "§e§lSUPER-VIP §7", ""),
    SUPREME("SUPREME", 3, 3, "§f§6Rank", "§aSUPREME", "§a§lSUPREME §7", " §f§l┃ §e\u272f"),
    ULTRA("ULTRA", 4, 4, "§e§6Rank", "§bULTRA", "§b§lULTRA §7", " §f§l┃ §e\u272f"),
    ELITE("ELITE", 5, 5, "§d§6Rank", "§3ELITE", "§3§lELITE §7", " §f§l┃ §e\u272f"),
    STREAMER("STREAMER", 6, 6, "§c§4Rank", "§6STREAMER", "§5§lSTREAMER §7", " §f§l┃ §e\u272f"),
    YOUTUBER("YOUTUBER", 7, 7, "§c§6§6Rank", "§6YOUTUBER", "§6§lYOUTUBER §7", " §f§l┃ §e\u272f"),
    FAMOUS("FAMOUS", 8, 8, "§b§7Rank", "§6FAMOUS", "§6§lFAMOUS §7", " §f§l┃ §e┃"),
    FRIEND("FRIEND", 9, 9, "§a§8Rank", "§dFRIEND", "§d§lFRIEND §7", " §f§l┃ §c┃"),
    STAFF("STAFF", 10, 10, "§4§9Rank", "§eSTAFF", "§e§lSTAFF §7", " §f§l┃ §e┃"),
    BUILDER("BUILDER", 11, 11, "§7§9§1Rank", "§aBUILD", "§a§lBUILD §7", " §f§l┃ §e\u272f"),
    HELPER("HELPER", 12, 12, "§6§9§2Rank", "§bHELPER", "§b§lHELPER §7", " §f§l┃ §e\u272f"),
    MODERATEUR("MODERATEUR", 13, 13, "§5§9§3Rank", "§9MOD", "§9§lMOD §7", " §f§l┃ §e\u272f"),
    DEVELOPPEUR("DEVELOPPEUR", 14, 14, "§3§9§4Rank", "§5DEV", "§5§lDEV §7", " §f§l┃ §e\u272f"),
    RESPONSABLE("RESPONSABLE", 15, 15, "§2§9§5Rank", "§4RESP", "§4§lRESP §7", " §f§l┃ §e\u272f"),
    ADMINISTRATEUR("ADMINISTRATEUR", 16, 16, "§1§9§6Rank", "§cADMIN", "§c§lADMIN §7", " §f§l┃ §e\u272f");

    private int power;
    private String name;
    private String orderRank;
    private String displayName;
    private String suffix;
    public static Map<Integer, Rank> ranks;

    static {
        Rank.ranks = new HashMap<Integer, Rank>();
        Rank[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            Rank rank = values[i];
            Rank.ranks.put(rank.getPower(), rank);
        }
    }

    private Rank(String rank, int ordinal, int power, String orderRank, String name, String displayName, String Suffix) {
        this.power = power;
        this.name = name;
        this.orderRank = orderRank;
        this.displayName = displayName;
        this.suffix = Suffix;
    }

    public String getName() {
        return this.name;
    }

    public static Rank powerToRank(int power) {
        return Rank.ranks.get(power);
    }

    public int getPower() {
        return this.power;
    }

    public String getOrderRank() {
        return this.orderRank;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public int getRankID(Player p)
    {
        new RankInfo(p);
        RankInfo rankInfo = new RankInfo(p);
        return rankInfo.getRankID();
    }

    public int getModuleID(Player p)
    {
        new RankInfo(p);
        RankInfo rankInfo = new RankInfo(p);
        return rankInfo.getRankModule();
    }
}
