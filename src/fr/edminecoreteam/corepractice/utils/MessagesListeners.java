package fr.edminecoreteam.corepractice.utils;

import fr.edminecoreteam.corepractice.Core;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class MessagesListeners
{
    private final static Core core = Core.getInstance();

    public static void gameMessage(Player p1, Player p2, String message)
    {

    }

    public static void endMessage(Player p1, Player p2, int elo1, int elo2, String time)
    {
        if (core.getGameIs().getGameIs(p1).equalsIgnoreCase("unranked"))
        {
            p1.sendMessage("");
            p1.sendMessage("  §d§lCompte rendu:");
            p1.sendMessage("   §f• §7Victoire: §a" + p1.getName());
            p1.sendMessage("   §f• §7Défaite: §c" + p2.getName());
            p1.sendMessage("");
            p1.sendMessage("   §f• §7Durée: §b" + time);
            p1.sendMessage("");
            p1.sendMessage(" §8➡ §fVisionnez vos statistiques sur votre profil.");
            p1.sendMessage("");

            p2.sendMessage("");
            p2.sendMessage("  §d§lCompte rendu:");
            p2.sendMessage("   §f• §7Victoire: §a" + p1.getName());
            p2.sendMessage("   §f• §7Défaite: §c" + p2.getName());
            p2.sendMessage("");
            p2.sendMessage("   §f• §7Durée: §b" + time);
            p2.sendMessage("");
            p2.sendMessage(" §8➡ §fVisionnez vos statistiques sur votre profil.");
            p2.sendMessage("");
        }
        else if (core.getGameIs().getGameIs(p1).equalsIgnoreCase("ranked"))
        {
            p1.sendMessage("");
            p1.sendMessage("  §d§lCompte rendu:");
            p1.sendMessage("   §f• §7Victoire: §a" + p1.getName());
            p1.sendMessage("   §f• §7Défaite: §c" + p2.getName());
            p1.sendMessage("");
            p1.sendMessage("   §f• §7Durée: §b" + time);
            p1.sendMessage("   §f• §7Elo remporté: §e+" + elo1);
            p1.sendMessage("");
            p1.sendMessage(" §8➡ §fVisionnez vos statistiques sur votre profil.");
            p1.sendMessage("");

            p2.sendMessage("");
            p2.sendMessage("  §d§lCompte rendu:");
            p2.sendMessage("   §f• §7Victoire: §a" + p1.getName());
            p2.sendMessage("   §f• §7Défaite: §c" + p2.getName());
            p2.sendMessage("");
            p2.sendMessage("   §f• §7Durée: §b" + time);
            p2.sendMessage("   §f• §7Elo perdu: §c-" + elo2);
            p2.sendMessage("");
            p2.sendMessage(" §8➡ §fVisionnez vos statistiques sur votre profil.");
            p2.sendMessage("");
        }
    }

    public static void endOfflineMessage(Player p1, String p2, int elo1, String time)
    {
        if (core.getGameIs().getGameIs(p1).equalsIgnoreCase("unranked"))
        {
            p1.sendMessage("");
            p1.sendMessage("  §d§lCompte rendu:");
            p1.sendMessage("   §f• §7Victoire: §a" + p1.getName());
            p1.sendMessage("   §f• §7Défaite: §c" + p2);
            p1.sendMessage("");
            p1.sendMessage("   §f• §7Durée: §b" + time);
            p1.sendMessage("");
            p1.sendMessage(" §8➡ §fVisionnez vos statistiques sur votre profil.");
            p1.sendMessage("");
        }
        else if (core.getGameIs().getGameIs(p1).equalsIgnoreCase("ranked"))
        {
            p1.sendMessage("");
            p1.sendMessage("  §d§lCompte rendu:");
            p1.sendMessage("   §f• §7Victoire: §a" + p1.getName());
            p1.sendMessage("   §f• §7Défaite: §c" + p2);
            p1.sendMessage("");
            p1.sendMessage("   §f• §7Durée: §b" + time);
            p1.sendMessage("   §f• §7Elo remporté: §e+" + elo1);
            p1.sendMessage("");
            p1.sendMessage(" §8➡ §fVisionnez vos statistiques sur votre profil.");
            p1.sendMessage("");
        }
    }
}
