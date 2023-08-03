package fr.edminecoreteam.corepractice.matchduels;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.data.hashmap.game.GameMap;
import fr.edminecoreteam.corepractice.listeners.ItemListeners;
import fr.edminecoreteam.corepractice.matchmaking.RankedMatchMaking;
import fr.edminecoreteam.corepractice.matchmaking.UnrankedMatchMaking;
import fr.edminecoreteam.corepractice.matchmaking.WhatIsGame;
import fr.edminecoreteam.corepractice.utils.LoadWorld;
import fr.edminecoreteam.corepractice.utils.MessagesListeners;
import fr.edminecoreteam.corepractice.utils.UnloadWorld;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GameListeners implements Listener
{
    private static Core core = Core.getInstance();
    public static void startSoloGame(Player p1, Player p2)
    {
        core.getInWaiting().remove(p1);
        core.getInWaiting().remove(p2);
        core.getInLobby().remove(p1);
        core.getInLobby().remove(p2);
        core.resetTime(p1);
        core.resetTime(p2);

        int randomID = ThreadLocalRandom.current().nextInt(100000000, 999999999);

        core.getMatchOppenant().putMatchOppenant(p1, p2);
        core.getMatchOppenant().putMatchOppenant(p2, p1);

        core.getGameType().putInGame(p1, core.getGameCheck().getGame(p1));
        core.getGameType().putInGame(p2, core.getGameCheck().getGame(p2));

        core.getGameID().putGameID(p1, randomID);
        core.getGameID().putGameID(p2, randomID);

        core.getInPreDuel().add(p1);
        core.getInPreDuel().add(p2);

        String world = LoadWorld.getRandomSubfolderName("gameTemplate/");
        LoadWorld.createGameWorld(world, core.getGameID().getIDString(p1));
        core.getWorldName().putWorldName(p1, world);
        core.getWorldName().putWorldName(p2, world);

        Location p1Spawn = new Location(Bukkit.getWorld(core.getGameID().getIDString(p1)),
                (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p1) + ".team1.x")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p1) + ".team1.y")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p1) + ".team1.z")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p1) + ".team1.t")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p1) + ".team1.b"));

        Location p2Spawn = new Location(Bukkit.getWorld(core.getGameID().getIDString(p2)),
                (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p2) + ".team2.x")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p2) + ".team2.y")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p2) + ".team2.z")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p2) + ".team2.t")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p2) + ".team2.b"));

        p1.teleport(p1Spawn);
        p2.teleport(p2Spawn);


        p1.setGameMode(GameMode.SURVIVAL);
        p2.setGameMode(GameMode.SURVIVAL);

        p1.setAllowFlight(false);
        p1.setFlying(false);

        p2.setAllowFlight(false);
        p2.setFlying(false);

        p1.setHealth(20);
        p2.setHealth(20);

        ItemListeners.getStartedKit(p1);
        ItemListeners.getStartedKit(p2);

        core.getGameCheck().removeSerchGame(p1);
        core.getGameCheck().removeSerchGame(p2);

        new BukkitRunnable() {
            int t = 8;
            public void run() {

                if (t == 5)
                {
                    p1.sendTitle("§a§l" + t, "§7préparez-vous.");
                    p1.playSound(p1.getLocation(), Sound.NOTE_PLING, 1.0f, 1.5f);

                    p2.sendTitle("§a§l" + t, "§7préparez-vous.");
                    p2.playSound(p2.getLocation(), Sound.NOTE_PLING, 1.0f, 1.5f);
                }
                if (t == 4)
                {
                    p1.sendTitle("§6§l" + t, "");
                    p1.playSound(p1.getLocation(), Sound.NOTE_PLING, 1.0f, 1.2f);

                    p2.sendTitle("§6§l" + t, "");
                    p2.playSound(p2.getLocation(), Sound.NOTE_PLING, 1.0f, 1.2f);
                }
                if (t == 3)
                {
                    p1.sendTitle("§e§l" + t, "");
                    p1.playSound(p1.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);

                    p2.sendTitle("§e§l" + t, "");
                    p2.playSound(p2.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);
                }
                if (t == 2)
                {
                    p1.sendTitle("§c§l" + t, "");
                    p1.playSound(p1.getLocation(), Sound.NOTE_PLING, 1.0f, 0.7f);

                    p2.sendTitle("§c§l" + t, "");
                    p2.playSound(p2.getLocation(), Sound.NOTE_PLING, 1.0f, 0.7f);
                }
                if (t == 1)
                {
                    p1.sendTitle("§4§l" + t, "");
                    p1.playSound(p1.getLocation(), Sound.NOTE_PLING, 1.0f, 0.5f);

                    p2.sendTitle("§4§l" + t, "");
                    p2.playSound(p2.getLocation(), Sound.NOTE_PLING, 1.0f, 0.5f);
                }

                --t;
                if (t == 0) {
                    core.getInPreDuel().remove(p1);
                    core.getInPreDuel().remove(p2);
                    core.getInDuel().add(p1);
                    core.getInDuel().add(p2);

                    p1.sendTitle("§r", "§r");
                    p2.sendTitle("§r", "§r");
                    p1.sendMessage("§6§l➡ §e§lLancement du jeu !");
                    p2.sendMessage("§6§l➡ §e§lLancement du jeu !");
                    p1.playSound(p1.getLocation(), Sound.ENDERDRAGON_GROWL, 0.7f, 1.0f);
                    p2.playSound(p2.getLocation(), Sound.ENDERDRAGON_GROWL, 0.7f, 1.0f);
                    startTimingMatch(p1, p2);
                    cancel();
                }
            }
        }.runTaskTimer((Plugin)core, 0L, 20L);
    }

    public static void startDuoGame(Player p1, Player p2, Player p3, Player p4)
    {
        core.getInWaiting().remove(p1);
        core.getInWaiting().remove(p2);
        core.getInWaiting().remove(p3);
        core.getInWaiting().remove(p4);
        core.getInLobby().remove(p1);
        core.getInLobby().remove(p2);
        core.getInLobby().remove(p3);
        core.getInLobby().remove(p4);
        core.resetTime(p1);
        core.resetTime(p2);
        core.resetTime(p3);
        core.resetTime(p4);

        int randomID = ThreadLocalRandom.current().nextInt(100000000, 999999999);

        MatchDuoOppenant matchDuoOppenant = core.getMatchDuoOppenant();
        HashMap<Player, String> gameValues = new HashMap<>();

        gameValues.put(p1, "red");
        gameValues.put(p2, "red");
        gameValues.put(p3, "blue");
        gameValues.put(p4, "blue");

        matchDuoOppenant.createDuoGame(randomID, gameValues);

        core.getGameType().putInGame(p1, core.getGameCheck().getGame(p1));
        core.getGameType().putInGame(p2, core.getGameCheck().getGame(p2));
        core.getGameType().putInGame(p3, core.getGameCheck().getGame(p3));
        core.getGameType().putInGame(p4, core.getGameCheck().getGame(p4));

        core.getGameID().putGameID(p1, randomID);
        core.getGameID().putGameID(p2, randomID);
        core.getGameID().putGameID(p3, randomID);
        core.getGameID().putGameID(p4, randomID);

        core.getDuoGestion().putDuoGestion(p1, p2);
        core.getDuoGestion().putDuoGestion(p2, p1);

        core.getDuoGestion().putDuoGestion(p3, p4);
        core.getDuoGestion().putDuoGestion(p4, p3);

        core.getInPreDuel().add(p1);
        core.getInPreDuel().add(p2);

        core.getInPreDuel().add(p3);
        core.getInPreDuel().add(p4);

        String world = LoadWorld.getRandomSubfolderName("gameTemplate/");
        LoadWorld.createGameWorld(world, core.getGameID().getIDString(p1));
        core.getWorldName().putWorldName(p1, world);
        core.getWorldName().putWorldName(p2, world);

        core.getWorldName().putWorldName(p3, world);
        core.getWorldName().putWorldName(p4, world);

        Location p1Spawn = new Location(Bukkit.getWorld(core.getGameID().getIDString(p1)),
                (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p1) + ".team1.x")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p1) + ".team1.y")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p1) + ".team1.z")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p1) + ".team1.t")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p1) + ".team1.b"));

        Location p2Spawn = new Location(Bukkit.getWorld(core.getGameID().getIDString(p1)),
                (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p1) + ".team2.x")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p1) + ".team2.y")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p1) + ".team2.z")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p1) + ".team2.t")
                , (float) core.getConfig().getDouble("Arenas." + core.getWorldName().getWorldName(p1) + ".team2.b"));

        p1.teleport(p1Spawn);
        p2.teleport(p1Spawn);

        p3.teleport(p2Spawn);
        p4.teleport(p2Spawn);


        p1.setGameMode(GameMode.SURVIVAL);
        p2.setGameMode(GameMode.SURVIVAL);

        p3.setGameMode(GameMode.SURVIVAL);
        p4.setGameMode(GameMode.SURVIVAL);

        p1.setAllowFlight(false);
        p1.setFlying(false);

        p2.setAllowFlight(false);
        p2.setFlying(false);

        p3.setAllowFlight(false);
        p3.setFlying(false);

        p4.setAllowFlight(false);
        p4.setFlying(false);

        p1.setHealth(20);
        p2.setHealth(20);

        p3.setHealth(20);
        p4.setHealth(20);

        ItemListeners.getStartedKit(p1);
        ItemListeners.getStartedKit(p2);
        ItemListeners.getStartedKit(p3);
        ItemListeners.getStartedKit(p4);

        core.getGameCheck().removeSerchGame(p1);
        core.getGameCheck().removeSerchGame(p2);
        core.getGameCheck().removeSerchGame(p3);
        core.getGameCheck().removeSerchGame(p4);

        new BukkitRunnable() {
            int t = 8;
            public void run() {

                if (t == 5)
                {
                    p1.sendTitle("§a§l" + t, "§7préparez-vous.");
                    p1.playSound(p1.getLocation(), Sound.NOTE_PLING, 1.0f, 1.5f);

                    p2.sendTitle("§a§l" + t, "§7préparez-vous.");
                    p2.playSound(p2.getLocation(), Sound.NOTE_PLING, 1.0f, 1.5f);

                    p3.sendTitle("§a§l" + t, "§7préparez-vous.");
                    p3.playSound(p3.getLocation(), Sound.NOTE_PLING, 1.0f, 1.5f);

                    p4.sendTitle("§a§l" + t, "§7préparez-vous.");
                    p4.playSound(p4.getLocation(), Sound.NOTE_PLING, 1.0f, 1.5f);
                }
                if (t == 4)
                {
                    p1.sendTitle("§6§l" + t, "");
                    p1.playSound(p1.getLocation(), Sound.NOTE_PLING, 1.0f, 1.2f);

                    p2.sendTitle("§6§l" + t, "");
                    p2.playSound(p2.getLocation(), Sound.NOTE_PLING, 1.0f, 1.2f);

                    p3.sendTitle("§6§l" + t, "");
                    p3.playSound(p3.getLocation(), Sound.NOTE_PLING, 1.0f, 1.2f);

                    p4.sendTitle("§6§l" + t, "");
                    p4.playSound(p4.getLocation(), Sound.NOTE_PLING, 1.0f, 1.2f);
                }
                if (t == 3)
                {
                    p1.sendTitle("§e§l" + t, "");
                    p1.playSound(p1.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);

                    p2.sendTitle("§e§l" + t, "");
                    p2.playSound(p2.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);

                    p3.sendTitle("§e§l" + t, "");
                    p3.playSound(p3.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);

                    p4.sendTitle("§e§l" + t, "");
                    p4.playSound(p4.getLocation(), Sound.NOTE_PLING, 1.0f, 1.0f);
                }
                if (t == 2)
                {
                    p1.sendTitle("§c§l" + t, "");
                    p1.playSound(p1.getLocation(), Sound.NOTE_PLING, 1.0f, 0.7f);

                    p2.sendTitle("§c§l" + t, "");
                    p2.playSound(p2.getLocation(), Sound.NOTE_PLING, 1.0f, 0.7f);

                    p3.sendTitle("§c§l" + t, "");
                    p3.playSound(p1.getLocation(), Sound.NOTE_PLING, 1.0f, 0.7f);

                    p4.sendTitle("§c§l" + t, "");
                    p4.playSound(p2.getLocation(), Sound.NOTE_PLING, 1.0f, 0.7f);
                }
                if (t == 1)
                {
                    p1.sendTitle("§4§l" + t, "");
                    p1.playSound(p1.getLocation(), Sound.NOTE_PLING, 1.0f, 0.5f);

                    p2.sendTitle("§4§l" + t, "");
                    p2.playSound(p2.getLocation(), Sound.NOTE_PLING, 1.0f, 0.5f);

                    p3.sendTitle("§4§l" + t, "");
                    p3.playSound(p3.getLocation(), Sound.NOTE_PLING, 1.0f, 0.5f);

                    p4.sendTitle("§4§l" + t, "");
                    p4.playSound(p4.getLocation(), Sound.NOTE_PLING, 1.0f, 0.5f);
                }

                --t;
                if (t == 0) {
                    core.getInPreDuel().remove(p1);
                    core.getInPreDuel().remove(p2);
                    core.getInPreDuel().remove(p3);
                    core.getInPreDuel().remove(p4);
                    core.getInDuel().add(p1);
                    core.getInDuel().add(p2);
                    core.getInDuel().add(p3);
                    core.getInDuel().add(p4);

                    p1.sendTitle("§r", "§r");
                    p2.sendTitle("§r", "§r");
                    p3.sendTitle("§r", "§r");
                    p4.sendTitle("§r", "§r");
                    p1.sendMessage("§6§l➡ §e§lLancement du jeu !");
                    p2.sendMessage("§6§l➡ §e§lLancement du jeu !");
                    p3.sendMessage("§6§l➡ §e§lLancement du jeu !");
                    p4.sendMessage("§6§l➡ §e§lLancement du jeu !");
                    p1.playSound(p1.getLocation(), Sound.ENDERDRAGON_GROWL, 0.7f, 1.0f);
                    p2.playSound(p2.getLocation(), Sound.ENDERDRAGON_GROWL, 0.7f, 1.0f);
                    p3.playSound(p3.getLocation(), Sound.ENDERDRAGON_GROWL, 0.7f, 1.0f);
                    p4.playSound(p4.getLocation(), Sound.ENDERDRAGON_GROWL, 0.7f, 1.0f);
                    startTimingMatchDuo(p1, p2, p3, p4);
                    cancel();
                }
            }
        }.runTaskTimer((Plugin)core, 0L, 20L);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e)
    {
        Player pDeathEvent = (Player) e.getEntity();
        Location pDeathLoc = pDeathEvent.getLocation();
        World pDeathWorld = pDeathEvent.getWorld();
        String worldName = core.getWorldName().getWorldName(pDeathEvent);
        String gameID = core.getGameID().getIDString(pDeathEvent);
        if (core.getGameID().getGameID(pDeathEvent) != null)
        {
            if (core.getInDuel().contains(pDeathEvent))
            {
                if (core.getIfSoloOrDuo().getIfSoloOrDuo(pDeathEvent).equalsIgnoreCase("solo"))
                {
                    e.setDeathMessage(null);
                    e.getDrops().clear();
                    e.getEntity().spigot().respawn();

                    Player pVictory = core.getMatchOppenant().getMatchOppenant(pDeathEvent);
                    Player pDeath = core.getMatchOppenant().getMatchOppenant(pVictory);

                    pDeath.sendTitle("§c§lDéfaite...", "§7Peut-être une prochaine fois.");
                    pDeath.playSound(pVictory.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);

                    pVictory.sendTitle("§a§lVictoire !", "§7C'était moins une !");
                    pVictory.playSound(pVictory.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 1.0f);

                    core.getInDuel().remove(pVictory);
                    core.getInDuel().remove(pDeath);

                    core.getInEndDuel().add(pVictory);
                    core.getInEndDuel().add(pDeath);


                    WhatIsGame gameIs = core.getGameIs();
                    int eloWin = 0;
                    eloWin = generateRandomNumber(7, 10);

                    int eloLose = 0;
                    eloLose = generateRandomNumber(10, 15);


                    if (gameIs.getGameIs(pVictory).equalsIgnoreCase("unranked"))
                    {
                        MessagesListeners.endMessage(pVictory, pDeath, 0, 0, convertTime(core.getTime(pVictory)));
                        core.getUnrankedPlayedDataManager().addData(pVictory.getUniqueId(), 1);
                        core.getUnrankedPlayedDataManager().addData(pDeath.getUniqueId(), 1);

                        core.getUnrankedWinDataManager().addData(pVictory.getUniqueId(), 1);
                        core.getUnrankedLoseDataManager().addData(pDeath.getUniqueId(), 1);

                        int newUnrankedWin = core.getGameMap().rechercherValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_unranked_win") + 1;
                        core.getGameMap().mettreAJourValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_unranked_win", newUnrankedWin);

                        int newKillStreak = core.getGameMap().rechercherValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_actualwinstreak") + 1;
                        core.getGameMap().mettreAJourValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_actualwinstreak", newKillStreak);
                        core.getGameMap().mettreAJourValeurPourJoueur(pDeath, core.getGameType().getTypeGame(pDeath) + "_actualwinstreak", 0);

                        if (newKillStreak > core.getGameMap().rechercherValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_bestwinstreak"))
                        {
                            core.getGameMap().mettreAJourValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_bestwinstreak", newKillStreak);
                        }
                    }

                    if (gameIs.getGameIs(pVictory).equalsIgnoreCase("ranked"))
                    {
                        MessagesListeners.endMessage(pVictory, pDeath, eloWin, eloLose, convertTime(core.getTime(pVictory)));
                        core.getRankedPlayedDataManager().addData(pVictory.getUniqueId(), 1);
                        core.getRankedPlayedDataManager().addData(pDeath.getUniqueId(), 1);

                        core.getRankedWinDataManager().addData(pVictory.getUniqueId(), 1);
                        core.getRankedLoseDataManager().addData(pDeath.getUniqueId(), 1);

                        core.getPlayerEloDataManager().addData(pVictory.getUniqueId(), eloWin);
                        core.getPlayerEloDataManager().removeData(pDeath.getUniqueId(), eloLose);

                        int newRankedWin = core.getGameMap().rechercherValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_ranked_win") + 1;
                        core.getGameMap().mettreAJourValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_ranked_win", newRankedWin);

                        int newRankedLose = core.getGameMap().rechercherValeurPourJoueur(pDeath, core.getGameType().getTypeGame(pDeath) + "_ranked_lose") + 1;
                        core.getGameMap().mettreAJourValeurPourJoueur(pDeath, core.getGameType().getTypeGame(pDeath) + "_ranked_lose", newRankedLose);

                        int newKillStreak = core.getGameMap().rechercherValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_actualwinstreak") + 1;
                        core.getGameMap().mettreAJourValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_actualwinstreak", newKillStreak);
                        core.getGameMap().mettreAJourValeurPourJoueur(pDeath, core.getGameType().getTypeGame(pDeath) + "_actualwinstreak", 0);

                        int newEloWin = core.getGameMap().rechercherValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_elo") + eloWin;
                        core.getGameMap().mettreAJourValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_elo", newEloWin);

                        int newEloLose = core.getGameMap().rechercherValeurPourJoueur(pDeath, core.getGameType().getTypeGame(pDeath) + "_elo") - eloLose;
                        core.getGameMap().mettreAJourValeurPourJoueur(pDeath, core.getGameType().getTypeGame(pDeath) + "_elo", newEloLose);

                        if (newKillStreak > core.getGameMap().rechercherValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_bestwinstreak"))
                        {
                            core.getGameMap().mettreAJourValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_bestwinstreak", newKillStreak);
                        }
                    }

                    endGame(pVictory);

                    pDeath.teleport(pDeathLoc);
                    endGame(pDeath);

                    Bukkit.getScheduler().runTaskLater(core, () -> {

                        for (Player pWorld : pDeathWorld.getPlayers())
                        {
                            leaveGame(pWorld);
                        }

                        UnloadWorld.deleteWorld(gameID);
                    }, 100);
                }
                if (core.getIfSoloOrDuo().getIfSoloOrDuo(pDeathEvent).equalsIgnoreCase("duo"))
                {
                    e.setDeathMessage(null);
                    e.getDrops().clear();
                    e.getEntity().spigot().respawn();
                    if (core.getMatchDuoOppenant().isInTeam(core.getGameID().getGameID(pDeathEvent), pDeathEvent, "red"))
                    {
                        core.getMatchDuoOppenant().updateTeam(core.getGameID().getGameID(pDeathEvent), pDeathEvent, "redspec");
                    }
                    if (core.getMatchDuoOppenant().isInTeam(core.getGameID().getGameID(pDeathEvent), pDeathEvent, "blue"))
                    {
                        core.getMatchDuoOppenant().updateTeam(core.getGameID().getGameID(pDeathEvent), pDeathEvent, "bluespec");
                    }


                    if (core.getMatchDuoOppenant().getTeam(core.getGameID().getGameID(pDeathEvent), "red") == null || core.getMatchDuoOppenant().getTeam(core.getGameID().getGameID(pDeathEvent), "blue") == null)
                    {
                        if (core.getMatchDuoOppenant().getTeam(core.getGameID().getGameID(pDeathEvent), "red") != null)
                        {
                            HashMap<Player, String> getTeam = core.getMatchDuoOppenant().getHashMapForTeam(core.getGameID().getGameID(pDeathEvent));

                            List<Player> winTeam = new ArrayList<Player>();
                            List<Player> loseTeam = new ArrayList<Player>();

                            for (Player pS : getTeam.keySet())
                            {
                                if (core.getMatchDuoOppenant().isInTeam(core.getGameID().getGameID(pDeathEvent), pS, "red") || core.getMatchDuoOppenant().isInTeam(core.getGameID().getGameID(pDeathEvent), pS, "redspec"))
                                {
                                    winTeam.add(pS);
                                }
                                if (core.getMatchDuoOppenant().isInTeam(core.getGameID().getGameID(pDeathEvent), pS, "blue") || core.getMatchDuoOppenant().isInTeam(core.getGameID().getGameID(pDeathEvent), pS, "bluespec"))
                                {
                                    loseTeam.add(pS);
                                }
                            }

                            for (Player winner : winTeam)
                            {
                                winner.sendTitle("§a§lVictoire !", "§7C'était moins une !");
                                winner.playSound(winner.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 1.0f);

                                core.getInDuel().remove(winner);

                                core.getInEndDuel().add(winner);

                                core.getDuoGestion().removeDuoGestion(winner);


                                WhatIsGame gameIs = core.getGameIs();
                                int eloWin = 0;
                                eloWin = generateRandomNumber(7, 10);


                                if (gameIs.getGameIs(winner).equalsIgnoreCase("unranked"))
                                {
                                    core.getUnrankedPlayedDataManager().addData(winner.getUniqueId(), 1);

                                    core.getUnrankedWinDataManager().addData(winner.getUniqueId(), 1);

                                    int newUnrankedWin = core.getGameMap().rechercherValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_unranked_win") + 1;
                                    core.getGameMap().mettreAJourValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_unranked_win", newUnrankedWin);

                                    int newKillStreak = core.getGameMap().rechercherValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_actualwinstreak") + 1;
                                    core.getGameMap().mettreAJourValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_actualwinstreak", newKillStreak);

                                    if (newKillStreak > core.getGameMap().rechercherValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_bestwinstreak"))
                                    {
                                        core.getGameMap().mettreAJourValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_bestwinstreak", newKillStreak);
                                    }
                                }

                                if (gameIs.getGameIs(winner).equalsIgnoreCase("ranked"))
                                {
                                    core.getRankedPlayedDataManager().addData(winner.getUniqueId(), 1);

                                    core.getRankedWinDataManager().addData(winner.getUniqueId(), 1);

                                    core.getPlayerEloDataManager().addData(winner.getUniqueId(), eloWin);

                                    int newRankedWin = core.getGameMap().rechercherValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_ranked_win") + 1;
                                    core.getGameMap().mettreAJourValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_ranked_win", newRankedWin);


                                    int newKillStreak = core.getGameMap().rechercherValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_actualwinstreak") + 1;
                                    core.getGameMap().mettreAJourValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_actualwinstreak", newKillStreak);

                                    int newEloWin = core.getGameMap().rechercherValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_elo") + eloWin;
                                    core.getGameMap().mettreAJourValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_elo", newEloWin);

                                    if (newKillStreak > core.getGameMap().rechercherValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_bestwinstreak"))
                                    {
                                        core.getGameMap().mettreAJourValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_bestwinstreak", newKillStreak);
                                    }
                                }

                                endGame(winner);
                            }

                            for (Player loser : loseTeam)
                            {
                                loser.sendTitle("§c§lDéfaite...", "§7Peut-être une prochaine fois.");
                                loser.playSound(loser.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 1.0f);

                                core.getInDuel().remove(loser);

                                core.getInEndDuel().add(loser);

                                core.getDuoGestion().removeDuoGestion(loser);


                                WhatIsGame gameIs = core.getGameIs();

                                int eloLose = 0;
                                eloLose = generateRandomNumber(10, 15);


                                if (gameIs.getGameIs(loser).equalsIgnoreCase("unranked"))
                                {
                                    core.getUnrankedPlayedDataManager().addData(loser.getUniqueId(), 1);
                                    core.getUnrankedLoseDataManager().addData(loser.getUniqueId(), 1);
                                    core.getGameMap().mettreAJourValeurPourJoueur(loser, core.getGameType().getTypeGame(loser) + "_actualwinstreak", 0);
                                }

                                if (gameIs.getGameIs(loser).equalsIgnoreCase("ranked"))
                                {
                                    core.getRankedPlayedDataManager().addData(loser.getUniqueId(), 1);
                                    core.getRankedLoseDataManager().addData(loser.getUniqueId(), 1);
                                    core.getPlayerEloDataManager().removeData(loser.getUniqueId(), eloLose);

                                    int newRankedLose = core.getGameMap().rechercherValeurPourJoueur(loser, core.getGameType().getTypeGame(loser) + "_ranked_lose") + 1;
                                    core.getGameMap().mettreAJourValeurPourJoueur(loser, core.getGameType().getTypeGame(loser) + "_ranked_lose", newRankedLose);
                                    core.getGameMap().mettreAJourValeurPourJoueur(loser, core.getGameType().getTypeGame(loser) + "_actualwinstreak", 0);

                                    int newEloLose = core.getGameMap().rechercherValeurPourJoueur(loser, core.getGameType().getTypeGame(loser) + "_elo") - eloLose;
                                    core.getGameMap().mettreAJourValeurPourJoueur(loser, core.getGameType().getTypeGame(loser) + "_elo", newEloLose);
                                }

                                endGame(loser);
                            }

                            Bukkit.getScheduler().runTaskLater(core, () -> {

                                for (Player pWorld : pDeathWorld.getPlayers())
                                {
                                    leaveGame(pWorld);
                                }

                                UnloadWorld.deleteWorld(gameID);
                            }, 100);
                        }
                        if (core.getMatchDuoOppenant().getTeam(core.getGameID().getGameID(pDeathEvent), "blue") != null)
                        {
                            HashMap<Player, String> getTeam = core.getMatchDuoOppenant().getHashMapForTeam(core.getGameID().getGameID(pDeathEvent));

                            List<Player> winTeam = new ArrayList<Player>();
                            List<Player> loseTeam = new ArrayList<Player>();

                            for (Player pS : getTeam.keySet())
                            {
                                if (core.getMatchDuoOppenant().isInTeam(core.getGameID().getGameID(pDeathEvent), pS, "red") || core.getMatchDuoOppenant().isInTeam(core.getGameID().getGameID(pDeathEvent), pS, "redspec"))
                                {
                                    loseTeam.add(pS);
                                }
                                if (core.getMatchDuoOppenant().isInTeam(core.getGameID().getGameID(pDeathEvent), pS, "blue") || core.getMatchDuoOppenant().isInTeam(core.getGameID().getGameID(pDeathEvent), pS, "bluespec"))
                                {
                                    winTeam.add(pS);
                                }
                            }

                            for (Player winner : winTeam)
                            {
                                winner.sendTitle("§a§lVictoire !", "§7C'était moins une !");
                                winner.playSound(winner.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 1.0f);

                                core.getInDuel().remove(winner);

                                core.getInEndDuel().add(winner);


                                WhatIsGame gameIs = core.getGameIs();
                                int eloWin = 0;
                                eloWin = generateRandomNumber(7, 10);


                                if (gameIs.getGameIs(winner).equalsIgnoreCase("unranked"))
                                {
                                    core.getUnrankedPlayedDataManager().addData(winner.getUniqueId(), 1);

                                    core.getUnrankedWinDataManager().addData(winner.getUniqueId(), 1);

                                    int newUnrankedWin = core.getGameMap().rechercherValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_unranked_win") + 1;
                                    core.getGameMap().mettreAJourValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_unranked_win", newUnrankedWin);

                                    int newKillStreak = core.getGameMap().rechercherValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_actualwinstreak") + 1;
                                    core.getGameMap().mettreAJourValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_actualwinstreak", newKillStreak);

                                    if (newKillStreak > core.getGameMap().rechercherValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_bestwinstreak"))
                                    {
                                        core.getGameMap().mettreAJourValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_bestwinstreak", newKillStreak);
                                    }
                                }

                                if (gameIs.getGameIs(winner).equalsIgnoreCase("ranked"))
                                {
                                    core.getRankedPlayedDataManager().addData(winner.getUniqueId(), 1);

                                    core.getRankedWinDataManager().addData(winner.getUniqueId(), 1);

                                    core.getPlayerEloDataManager().addData(winner.getUniqueId(), eloWin);

                                    int newRankedWin = core.getGameMap().rechercherValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_ranked_win") + 1;
                                    core.getGameMap().mettreAJourValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_ranked_win", newRankedWin);


                                    int newKillStreak = core.getGameMap().rechercherValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_actualwinstreak") + 1;
                                    core.getGameMap().mettreAJourValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_actualwinstreak", newKillStreak);

                                    int newEloWin = core.getGameMap().rechercherValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_elo") + eloWin;
                                    core.getGameMap().mettreAJourValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_elo", newEloWin);

                                    if (newKillStreak > core.getGameMap().rechercherValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_bestwinstreak"))
                                    {
                                        core.getGameMap().mettreAJourValeurPourJoueur(winner, core.getGameType().getTypeGame(winner) + "_bestwinstreak", newKillStreak);
                                    }
                                }

                                endGame(winner);
                            }

                            for (Player loser : loseTeam)
                            {
                                loser.sendTitle("§c§lDéfaite...", "§7Peut-être une prochaine fois.");
                                loser.playSound(loser.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 1.0f);

                                core.getInDuel().remove(loser);

                                core.getInEndDuel().add(loser);


                                WhatIsGame gameIs = core.getGameIs();

                                int eloLose = 0;
                                eloLose = generateRandomNumber(10, 15);


                                if (gameIs.getGameIs(loser).equalsIgnoreCase("unranked"))
                                {
                                    core.getUnrankedPlayedDataManager().addData(loser.getUniqueId(), 1);
                                    core.getUnrankedLoseDataManager().addData(loser.getUniqueId(), 1);
                                    core.getGameMap().mettreAJourValeurPourJoueur(loser, core.getGameType().getTypeGame(loser) + "_actualwinstreak", 0);
                                }

                                if (gameIs.getGameIs(loser).equalsIgnoreCase("ranked"))
                                {
                                    core.getRankedPlayedDataManager().addData(loser.getUniqueId(), 1);
                                    core.getRankedLoseDataManager().addData(loser.getUniqueId(), 1);
                                    core.getPlayerEloDataManager().removeData(loser.getUniqueId(), eloLose);

                                    int newRankedLose = core.getGameMap().rechercherValeurPourJoueur(loser, core.getGameType().getTypeGame(loser) + "_ranked_lose") + 1;
                                    core.getGameMap().mettreAJourValeurPourJoueur(loser, core.getGameType().getTypeGame(loser) + "_ranked_lose", newRankedLose);
                                    core.getGameMap().mettreAJourValeurPourJoueur(loser, core.getGameType().getTypeGame(loser) + "_actualwinstreak", 0);

                                    int newEloLose = core.getGameMap().rechercherValeurPourJoueur(loser, core.getGameType().getTypeGame(loser) + "_elo") - eloLose;
                                    core.getGameMap().mettreAJourValeurPourJoueur(loser, core.getGameType().getTypeGame(loser) + "_elo", newEloLose);
                                }

                                endGame(loser);
                            }

                            Bukkit.getScheduler().runTaskLater(core, () -> {

                                core.getMatchDuoOppenant().removeGameTeams(core.getGameID().getGameID(pDeathEvent));
                                for (Player pWorld : pDeathWorld.getPlayers())
                                {
                                    leaveGame(pWorld);
                                }

                                UnloadWorld.deleteWorld(gameID);
                            }, 100);
                        }
                    }
                    else
                    {
                        WhatIsGame gameIs = core.getGameIs();
                        pDeathEvent.sendTitle("§c§lVous êtes mort !", "§7Veuillez attendre la fin du match...");
                        pDeathEvent.playSound(pDeathEvent.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);

                        pDeathEvent.teleport(pDeathLoc);
                        endGameSpec(pDeathEvent);
                    }
                }
            }
        }
    }

    public static void leaveGame(Player p)
    {
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

        WhatIsGame gameIs = core.getGameIs();

        gameIs.removeGameIs(p);

        p.setGameMode(GameMode.ADVENTURE);
        p.setFoodLevel(20);
        p.setHealth(20);
        p.teleport(lobbySpawn);
        p.setFireTicks(0);

        core.resetTime(p);

        ItemListeners.getLobbyItems(p);
    }

    public static void leaveGroupGame(Player p)
    {
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

        WhatIsGame gameIs = core.getGameIs();

        gameIs.removeGameIs(p);

        p.setGameMode(GameMode.ADVENTURE);
        p.setFoodLevel(20);
        p.setHealth(20);
        p.teleport(lobbySpawn);
        p.setFireTicks(0);

        core.resetTime(p);

        ItemListeners.getLobbyItems(p);
    }

    public static void replayGame(Player p)
    {
        if (core.getWorldName().getWorldName(p) != null)
        {
            core.getWorldName().removeWorldName(p);
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
        p.setHealth(20);
        p.teleport(lobbySpawn);
        p.setFireTicks(0);

        core.resetTime(p);
        WhatIsGame gameIs = core.getGameIs();

        if (gameIs.getGameIs(p).equalsIgnoreCase("unranked"))
        {
            UnrankedMatchMaking matchMaking = new UnrankedMatchMaking(p);

            matchMaking.start(core.getGameType().getTypeGame(p));
            ItemListeners.foundGameItems(p);

            if (core.getGameType().getTypeGame(p) != null)
            {
                core.getGameType().removeFromTypeGame(p);
            }
        }

        if (gameIs.getGameIs(p).equalsIgnoreCase("ranked"))
        {
            RankedMatchMaking matchMaking = new RankedMatchMaking(p);

            matchMaking.start(core.getGameType().getTypeGame(p));
            ItemListeners.foundGameItems(p);

            if (core.getGameType().getTypeGame(p) != null)
            {
                core.getGameType().removeFromTypeGame(p);
            }
        }
    }

    public static void endGame(Player p)
    {
        Bukkit.getScheduler().runTaskLater(core, () -> {

            for (PotionEffect effect : p.getActivePotionEffects ())
            {
                p.removePotionEffect(effect.getType());
            }
            core.getBlocsToWorld().clearPlayerBlocks(p);
            p.setFireTicks(0);
            p.getInventory().setHelmet(null);
            p.getInventory().setChestplate(null);
            p.getInventory().setLeggings(null);
            p.getInventory().setBoots(null);

            p.setAllowFlight(true);
            p.setFlying(true);
            p.setGameMode(GameMode.ADVENTURE);
            p.setHealth(20);
            p.setFoodLevel(20);
            ItemListeners.getEndUnrankedItems(p);

        }, 3);
    }

    public static void endGameSpec(Player p)
    {
        Bukkit.getScheduler().runTaskLater(core, () -> {

            for (PotionEffect effect : p.getActivePotionEffects ())
            {
                p.removePotionEffect(effect.getType());
            }
            core.getBlocsToWorld().clearPlayerBlocks(p);
            p.setFireTicks(0);
            p.getInventory().setHelmet(null);
            p.getInventory().setChestplate(null);
            p.getInventory().setLeggings(null);
            p.getInventory().setBoots(null);

            p.setAllowFlight(true);
            p.setFlying(true);
            p.setGameMode(GameMode.SPECTATOR);
            p.setHealth(20);
            p.setFoodLevel(20);
            ItemListeners.getEndUnrankedItems(p);
        }, 3);
    }

    private static void startTimingMatch(Player p1, Player p2)
    {
        new BukkitRunnable() {
            int t = 0;
            public void run() {

                if (core.getInDuel().contains(p1))
                {
                    core.addTime(p1, 1);
                }
                else
                {
                    cancel();
                }

                if (core.getInDuel().contains(p2))
                {
                    core.addTime(p2, 1);
                }
                else
                {
                    cancel();
                }

                ++t;
            }
        }.runTaskTimer((Plugin)core, 0L, 20L);
    }

    private static void startTimingMatchDuo(Player p1, Player p2, Player p3, Player p4)
    {

        List<Player> gameList = new ArrayList<Player>();

        gameList.add(p1);
        gameList.add(p2);
        gameList.add(p3);
        gameList.add(p4);
        new BukkitRunnable() {
            int t = 0;
            public void run() {

                if (!core.getInDuel().contains(p1) && !core.getInDuel().contains(p2))
                {
                    cancel();
                }

                if (!core.getInDuel().contains(p3) && !core.getInDuel().contains(p4))
                {
                    cancel();
                }

                for (Player p : gameList)
                {
                    core.addTime(p, 1);
                }

                ++t;
            }
        }.runTaskTimer((Plugin)core, 0L, 20L);
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