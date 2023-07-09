package fr.edminecoreteam.corepractice.listeners;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.matchduels.GameListeners;
import fr.edminecoreteam.corepractice.matchmaking.WhatIsGame;
import fr.edminecoreteam.corepractice.utils.MessagesListeners;
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

import java.util.Random;

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

                pVictory.sendTitle("§a§lVictoire !", "§7C'était moins une !");
                pVictory.playSound(pVictory.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 1.0f);

                core.getInDuel().remove(pVictory);
                core.getInEndDuel().add(pVictory);

                WhatIsGame gameIs = core.getGameIs();
                int eloWin = 0;
                eloWin = generateRandomNumber(7, 10);

                int eloLose = 0;
                eloLose = generateRandomNumber(10, 15);

                if (gameIs.getGameIs(pVictory).equalsIgnoreCase("unranked"))
                {
                    MessagesListeners.endOfflineMessage(pVictory, p.getName(), 0, convertTime(core.getTime(pVictory)));
                    core.getUnrankedPlayedDataManager().addData(pVictory.getUniqueId(), 1);
                    core.getUnrankedPlayedDataManager().addData(p.getUniqueId(), 1);

                    core.getUnrankedWinDataManager().addData(pVictory.getUniqueId(), 1);
                    core.getUnrankedLoseDataManager().addData(p.getUniqueId(), 1);

                    int newUnrankedWin = core.getGameMap().rechercherValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_unranked_win") + 1;
                    core.getGameMap().mettreAJourValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_unranked_win", newUnrankedWin);

                    int newKillStreak = core.getGameMap().rechercherValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_actualwinstreak") + 1;
                    core.getGameMap().mettreAJourValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_actualwinstreak", newKillStreak);
                    core.getGameMap().mettreAJourValeurPourJoueur(p, core.getGameType().getTypeGame(pVictory) + "_actualwinstreak", 0);

                    if (newKillStreak > core.getGameMap().rechercherValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_bestwinstreak"))
                    {
                        core.getGameMap().mettreAJourValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_bestwinstreak", newKillStreak);
                    }
                }

                if (gameIs.getGameIs(pVictory).equalsIgnoreCase("ranked"))
                {
                    MessagesListeners.endOfflineMessage(pVictory, p.getName(), eloWin, convertTime(core.getTime(pVictory)));
                    core.getRankedPlayedDataManager().addData(pVictory.getUniqueId(), 1);
                    core.getRankedPlayedDataManager().addData(p.getUniqueId(), 1);

                    core.getRankedWinDataManager().addData(pVictory.getUniqueId(), 1);
                    core.getRankedLoseDataManager().addData(p.getUniqueId(), 1);

                    core.getPlayerEloDataManager().addData(pVictory.getUniqueId(), eloWin);
                    core.getPlayerEloDataManager().removeData(p.getUniqueId(), eloLose);

                    int newRankedWin = core.getGameMap().rechercherValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_ranked_win") + 1;
                    core.getGameMap().mettreAJourValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_ranked_win", newRankedWin);

                    int newRankedLose = core.getGameMap().rechercherValeurPourJoueur(p, core.getGameType().getTypeGame(p) + "_ranked_lose") + 1;
                    core.getGameMap().mettreAJourValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_ranked_lose", newRankedLose);

                    int newKillStreak = core.getGameMap().rechercherValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_actualwinstreak") + 1;
                    core.getGameMap().mettreAJourValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_actualwinstreak", newKillStreak);
                    core.getGameMap().mettreAJourValeurPourJoueur(p, core.getGameType().getTypeGame(p) + "_actualwinstreak", 0);

                    int newEloWin = core.getGameMap().rechercherValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_elo") + eloWin;
                    core.getGameMap().mettreAJourValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_elo", newEloWin);

                    int newEloLose = core.getGameMap().rechercherValeurPourJoueur(p, core.getGameType().getTypeGame(p) + "_elo") - eloLose;
                    core.getGameMap().mettreAJourValeurPourJoueur(p, core.getGameType().getTypeGame(p) + "_elo", newEloLose);

                    if (newKillStreak > core.getGameMap().rechercherValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_bestwinstreak"))
                    {
                        core.getGameMap().mettreAJourValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_bestwinstreak", newKillStreak);
                    }
                }

                gameIs.removeGameIs(pVictory);
                gameIs.removeGameIs(p);

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

    private int generateRandomNumber(int minValue, int maxValue) {
        Random random = new Random();
        return random.nextInt((maxValue - minValue) + 1) + minValue;
    }

    private String convertTime(int timeInSeconds)
    {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02dm %02ds", minutes, seconds);
    }
}
