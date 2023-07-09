package fr.edminecoreteam.corepractice.matchduels;

import fr.edminecoreteam.corepractice.Core;
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

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GameListeners implements Listener
{
    private static Core core = Core.getInstance();
    public static void startGame(Player p1, Player p2)
    {
        core.getInWaiting().remove(p1);
        core.getInWaiting().remove(p2);
        core.getInLobby().remove(p1);
        core.getInLobby().remove(p2);

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
                    core.getGameMap().mettreAJourValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "", newKillStreak);
                    core.getGameMap().mettreAJourValeurPourJoueur(pDeath, core.getGameType().getTypeGame(pVictory) + "_actualwinstreak", 0);
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
                    core.getGameMap().mettreAJourValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_ranked_lose", newRankedLose);

                    int newKillStreak = core.getGameMap().rechercherValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_actualwinstreak") + 1;
                    core.getGameMap().mettreAJourValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_actualwinstreak", newKillStreak);
                    core.getGameMap().mettreAJourValeurPourJoueur(pDeath, core.getGameType().getTypeGame(pDeath) + "_actualwinstreak", 0);

                    int newEloWin = core.getGameMap().rechercherValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_elo") + eloWin;
                    core.getGameMap().mettreAJourValeurPourJoueur(pVictory, core.getGameType().getTypeGame(pVictory) + "_elo", newEloWin);

                    int newEloLose = core.getGameMap().rechercherValeurPourJoueur(pDeath, core.getGameType().getTypeGame(pDeath) + "_elo") - eloLose;
                    core.getGameMap().mettreAJourValeurPourJoueur(pDeath, core.getGameType().getTypeGame(pDeath) + "_elo", newEloLose);
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