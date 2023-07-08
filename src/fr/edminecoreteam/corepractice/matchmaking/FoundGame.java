package fr.edminecoreteam.corepractice.matchmaking;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.kits.LoadKits;
import fr.edminecoreteam.corepractice.matchduels.GameListeners;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class FoundGame
{
    private static Core core = Core.getInstance();

    public static void start()
    {
        new BukkitRunnable() {
            int t = 0;
            public void run() {


                for (String gameMode : core.getConfig().getConfigurationSection("kits.1vs1").getKeys(false))
                {
                    GameCheck gameCheck = core.getGameCheck();
                    WhatIsGame gameIs = core.getGameIs();
                    List<Player> waitlist = new ArrayList<Player>();
                    for (Player pGame : core.getInWaiting())
                    {
                        if (gameCheck.getGame(pGame) != null && gameCheck.getGame(pGame).equalsIgnoreCase(gameMode))
                        {
                            waitlist.add(pGame);
                        }
                    }
                    if (waitlist.size() > 1)
                    {
                        List<Player> unrankedWait = new ArrayList<Player>();
                        List<Player> rankedWait = new ArrayList<Player>();
                        for (Player filtreP : waitlist)
                        {
                            if (gameIs.getGameIs(filtreP).equalsIgnoreCase("unranked"))
                            {
                                unrankedWait.add(filtreP);
                            }
                            if (gameIs.getGameIs(filtreP).equalsIgnoreCase("ranked"))
                            {
                                rankedWait.add(filtreP);
                            }
                        }

                        if (unrankedWait.size() > 1)
                        {
                            List<Player> unrankedGameList = unrankedWait.subList(0, 2);
                            Player pUnranked1 = unrankedWait.get(0);
                            Player pUnranked2 = unrankedWait.get(1);
                            pUnranked1.getInventory().clear();
                            pUnranked2.getInventory().clear();
                            pUnranked1.sendMessage("§aJoueur trouvé ! §fVotre match contre §b" + pUnranked2.getName() + "§f va commencer...");
                            pUnranked2.sendMessage("§aJoueur trouvé ! §fVotre match contre §b" + pUnranked1.getName() + "§f va commencer...");

                            GameListeners.startGame(pUnranked1, pUnranked2);
                        }

                        if (rankedWait.size() > 1)
                        {
                            List<Player> rankedGameList = rankedWait;
                            Player pRanked1 = rankedWait.get(0);

                            for (Player filtreP : rankedGameList)
                            {
                                if (filtreP != pRanked1)
                                {
                                    int eloP1 = core.getPlayerEloDataManager().getData(pRanked1.getUniqueId());
                                    int eloP2 = core.getPlayerEloDataManager().getData(filtreP.getUniqueId());

                                    if (eloP1 >= eloP2 - 200 && eloP1 <= eloP2 + 200) {
                                        Player pRranked2 = filtreP;
                                        pRanked1.getInventory().clear();
                                        pRranked2.getInventory().clear();
                                        pRanked1.sendMessage("§aJoueur trouvé ! §fVotre match contre §b" + pRranked2.getName() + "§f va commencer...");
                                        pRranked2.sendMessage("§aJoueur trouvé ! §fVotre match contre §b" + pRanked1.getName() + "§f va commencer...");

                                        GameListeners.startGame(pRanked1, pRranked2);
                                    } else {
                                        System.out.println("int1 ne satisfait aucune des conditions spécifiées.");
                                        if (eloP1 >= eloP2 - 800 && eloP1 <= eloP2 + 800) {
                                            Player pRranked2 = filtreP;
                                            pRanked1.getInventory().clear();
                                            pRranked2.getInventory().clear();
                                            pRanked1.sendMessage("§aJoueur trouvé ! §fVotre match contre §b" + pRranked2.getName() + "§f va commencer...");
                                            pRranked2.sendMessage("§aJoueur trouvé ! §fVotre match contre §b" + pRanked1.getName() + "§f va commencer...");

                                            GameListeners.startGame(pRanked1, pRranked2);
                                        }
                                        else
                                        {
                                            if (eloP1 >= eloP2 - 1500 && eloP1 <= eloP2 + 1500) {
                                                Player pRranked2 = filtreP;
                                                pRanked1.getInventory().clear();
                                                pRranked2.getInventory().clear();
                                                pRanked1.sendMessage("§aJoueur trouvé ! §fVotre match contre §b" + pRranked2.getName() + "§f va commencer...");
                                                pRranked2.sendMessage("§aJoueur trouvé ! §fVotre match contre §b" + pRanked1.getName() + "§f va commencer...");

                                                GameListeners.startGame(pRanked1, pRranked2);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                ++t;
                if (t == 1) {
                    t = 0;
                }
            }
        }.runTaskTimer((Plugin)core, 0L, 40L);
    }
}
