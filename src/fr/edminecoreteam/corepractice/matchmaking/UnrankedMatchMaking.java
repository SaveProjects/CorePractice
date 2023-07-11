package fr.edminecoreteam.corepractice.matchmaking;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.listeners.ItemListeners;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class UnrankedMatchMaking
{

    private Player p;

    private static Core core = Core.getInstance();

    public UnrankedMatchMaking(Player p)
    {
        this.p = p;
    }

    public void start(String game)
    {
        GameCheck gameCheck = core.getGameCheck();
        WhatIsGame gameIs = core.getGameIs();

        if (gameCheck.getGame(p) == null)
        {
            core.getInWaiting().add(p);
            gameIs.removeGameIs(p);
            gameIs.putGameIs(p, "unranked");
            gameCheck.searchGame(p, game);

            p.sendMessage("§fRecherche d'une partie en cours sur le mode §e§l" + game + "§f...");
            ItemListeners.foundGameItems(p);
            startTimingMatch(p);
        }
        else
        {
            p.sendMessage("§cAction impossible, vous êtes déjà dans une file d'attente...");
        }
    }



    private void startTimingMatch(Player p)
    {
        new BukkitRunnable() {
            int t = 0;
            public void run() {

                if (core.getInWaiting().contains(p))
                {
                    core.addTime(p, 1);
                }
                else
                {
                    core.resetTime(p);
                    cancel();
                }

                ++t;
            }
        }.runTaskTimer((Plugin)core, 0L, 20L);
    }
}
