package fr.edminecoreteam.corepractice.listeners;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.gui.ProfileGui;
import fr.edminecoreteam.corepractice.gui.RankedGui;
import fr.edminecoreteam.corepractice.gui.UnrankedGui;
import fr.edminecoreteam.corepractice.kits.LoadKits;
import fr.edminecoreteam.corepractice.matchduels.GameListeners;
import fr.edminecoreteam.corepractice.matchmaking.GameCheck;
import fr.edminecoreteam.corepractice.matchmaking.WhatIsGame;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemListeners implements Listener
{

    private static Core core = Core.getInstance();

    @EventHandler
    public void onDrop(PlayerDropItemEvent e)
    {
        Player p = e.getPlayer();
        if (core.getInLobby().contains(p) || core.getInEditor().contains(p) || core.getInPreDuel().contains(p) || core.getInEndDuel().contains(p))
        {
            e.setCancelled(true);
        }
        if (core.getInDuel().contains(p))
        {
            if (e.getItemDrop().getItemStack().getType() == Material.BOWL || e.getItemDrop().getItemStack().getType() == Material.POTION)
            {
                e.getItemDrop().remove();
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        Player p = (Player) e.getWhoClicked();
        if (core.getInLobby().contains(p))
        {
            e.setCancelled(true);
            ItemStack it = e.getCurrentItem();
            if (it == null) {
                return;
            }
            if (it.getType() == Material.BED && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§lQuitter §7• Clique")) {
                e.setCancelled(true);
                GameCheck gameCheck = core.getGameCheck();
                gameCheck.removeSerchGame(p);
                core.getInWaiting().remove(p);
                p.sendMessage("§cRecherche annulée...");
                getLobbyItems(p);
            }
            if (it.getType() == Material.IRON_SWORD && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§f§lUnranked §7• Clique")) {
                e.setCancelled(true);
                UnrankedGui.gui(p);
            }
            if (it.getType() == Material.DIAMOND_SWORD && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§b§lRanked §7• Clique")) {
                e.setCancelled(true);
                if (core.getUnrankedPlayedDataManager().getData(p.getUniqueId()) >= 20)
                {
                    RankedGui.gui(p);
                }
                else if (core.getUnrankedPlayedDataManager().getData(p.getUniqueId()) < 20)
                {
                    int needMatch = 20 - core.getUnrankedPlayedDataManager().getData(p.getUniqueId());

                    p.sendMessage("§cErreur, avant de jouer en Ranked, veuillez faire encore " + needMatch + " matchs Unranked.");
                }
            }
            if (it.getType() == Material.SKULL_ITEM && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§lProfil §7• Clique")) {
                e.setCancelled(true);
                ProfileGui.gui(p);
            }
        }
        if (core.getInPreDuel().contains(p) || core.getInDuel().contains(p))
        {
            ItemStack it = e.getCurrentItem();
            if (it == null) {
                return;
            }
            if (it.getType() == Material.BOOK && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§d§lKit par défaut §7• Clique")) {
                e.setCancelled(true);
                LoadKits pKit = new LoadKits(p);

                if (core.getGameCheck().getGame(p) != null)
                {
                    pKit.equipUnrankedDefaultKit(core.getGameCheck().getGame(p));
                }
                else if (core.getGameCheck().getGame(p) == null)
                {
                    if (core.getGameType().getTypeGame(p) != null)
                    {
                        pKit.equipUnrankedDefaultKit(core.getGameType().getTypeGame(p));
                    }
                    else if (core.getGameType().getTypeGame(p) == null)
                    {
                        p.sendMessage("§cErreur vous n'êtes pas en jeu pour faire cela...");
                    }
                }
            }
        }
        if (core.getInEndDuel().contains(p))
        {
            ItemStack it = e.getCurrentItem();
            if (it == null) {
                return;
            }
            if (it.getType() == Material.BED && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§lQuitter §7• Clique")) {
                e.setCancelled(true);
                GameListeners.leaveGame(p);
            }
            if (it.getType() == Material.PAPER && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§d§lRejouer §7• Clique")) {
                e.setCancelled(true);
                GameListeners.replayGame(p);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        ItemStack it = e.getItem();
        if (it == null) {
            return;
        }
        if (core.getInLobby().contains(p))
        {
            if (it.getType() == Material.BED && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lQuitter §7• Clique") && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK || a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK)) {
                e.setCancelled(true);
                GameCheck gameCheck = core.getGameCheck();
                gameCheck.removeSerchGame(p);

                WhatIsGame gameIs = core.getGameIs();
                gameIs.removeGameIs(p);

                core.getInWaiting().remove(p);
                p.sendMessage("§cRecherche annulée...");
                getLobbyItems(p);
            }
            if (it.getType() == Material.IRON_SWORD && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§f§lUnranked §7• Clique") && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK || a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK)) {
                e.setCancelled(true);
                UnrankedGui.gui(p);
            }
            if (it.getType() == Material.DIAMOND_SWORD && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§b§lRanked §7• Clique") && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK || a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK)) {
                e.setCancelled(true);
                if (core.getUnrankedPlayedDataManager().getData(p.getUniqueId()) >= 20)
                {
                    RankedGui.gui(p);
                }
                else if (core.getUnrankedPlayedDataManager().getData(p.getUniqueId()) < 20)
                {
                    int needMatch = 20 - core.getUnrankedPlayedDataManager().getData(p.getUniqueId());

                    p.sendMessage("§cErreur, avant de jouer en Ranked, veuillez faire encore " + needMatch + " matchs Unranked.");
                }
            }
            if (it.getType() == Material.SKULL_ITEM && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lProfil §7• Clique") && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK || a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK)) {
                e.setCancelled(true);
                ProfileGui.gui(p);
            }
        }
        if (core.getInPreDuel().contains(p) || core.getInDuel().contains(p))
        {
            if (it.getType() == Material.BOOK && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§d§lKit par défaut §7• Clique") && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK || a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK)) {
                e.setCancelled(true);
                LoadKits pKit = new LoadKits(p);

                if (core.getGameCheck().getGame(p) != null)
                {
                    p.getInventory().clear();
                    pKit.equipUnrankedDefaultKit(core.getGameCheck().getGame(p));
                }
                else if (core.getGameCheck().getGame(p) == null)
                {
                    if (core.getGameType().getTypeGame(p) != null)
                    {
                        p.getInventory().clear();
                        pKit.equipUnrankedDefaultKit(core.getGameType().getTypeGame(p));
                    }
                    else if (core.getGameType().getTypeGame(p) == null)
                    {
                        p.sendMessage("§cErreur vous n'êtes pas en jeu pour faire cela...");
                    }
                }
            }
        }
        if (core.getInEndDuel().contains(p))
        {
            if (it.getType() == Material.BED && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lQuitter §7• Clique") && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK || a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK)) {
                e.setCancelled(true);
                GameListeners.leaveGame(p);
            }
            if (it.getType() == Material.PAPER && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§d§lRejouer §7• Clique") && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK || a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK)) {
                e.setCancelled(true);
                GameListeners.replayGame(p);
            }
        }
    }

    public static void getLobbyItems(Player p)
    {
        p.getInventory().clear();
        Bukkit.getScheduler().runTaskLater(core, () -> {
            // Code à exécuter après 1 seconde
            // Par exemple, envoi d'un message à un joueur

            p.getInventory().setHelmet(null);
            p.getInventory().setChestplate(null);
            p.getInventory().setLeggings(null);
            p.getInventory().setBoots(null);

            p.setAllowFlight(false);
            p.setFlying(false);

            if (JoinEvent.getCanDoubleJump().contains(p)) {
                p.setAllowFlight(true);
                p.setFlying(false);
            }
            else {
                p.setAllowFlight(false);
                p.setFlying(false);
            }

            ItemStack unranked = new ItemStack(Material.IRON_SWORD, 1);
            ItemMeta unrankedM = unranked.getItemMeta();
            unrankedM.setDisplayName("§f§lUnranked §7• Clique");
            unranked.setItemMeta(unrankedM);
            p.getInventory().setItem(0, unranked);

            ItemStack ranked = new ItemStack(Material.DIAMOND_SWORD, 1);
            ItemMeta rankedM = ranked.getItemMeta();
            rankedM.setDisplayName("§b§lRanked §7• Clique");
            ranked.setItemMeta(rankedM);
            p.getInventory().setItem(1, ranked);

            ItemStack kitEditor = new ItemStack(Material.BOOK, 1);
            ItemMeta kitEditorM = kitEditor.getItemMeta();
            kitEditorM.setDisplayName("§d§lKit Editor §7• Clique");
            kitEditor.setItemMeta(kitEditorM);
            p.getInventory().setItem(2, kitEditor);

            ItemStack ffa = new ItemStack(Material.GOLD_AXE, 1);
            ItemMeta ffaM = ffa.getItemMeta();
            ffaM.setDisplayName("§e§lFFA §7• Clique");
            ffa.setItemMeta(ffaM);
            p.getInventory().setItem(4, ffa);

            ItemStack leaderBoard = new ItemStack(Material.EMERALD, 1);
            ItemMeta leaderBoardM = leaderBoard.getItemMeta();
            leaderBoardM.setDisplayName("§a§lLeader-Board §7• Clique");
            leaderBoard.setItemMeta(leaderBoardM);
            p.getInventory().setItem(6, leaderBoard);

            ItemStack events = new ItemStack(Material.COMMAND_MINECART, 1);
            ItemMeta eventsM = events.getItemMeta();
            eventsM.setDisplayName("§6§lÉvénements §7• Clique");
            events.setItemMeta(eventsM);
            p.getInventory().setItem(7, events);

            ItemStack profile = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta profileM = (SkullMeta) profile.getItemMeta();
            profileM.setDisplayName("§c§lProfil §7• Clique");
            profileM.setOwner(p.getName());
            profile.setItemMeta(profileM);
            p.getInventory().setItem(8, profile);

        }, 3);
    }


    public static void foundGameItems(Player p)
    {
        p.getInventory().clear();

        ItemStack leave = new ItemStack(Material.BED, 1);
        ItemMeta leaveM = leave.getItemMeta();
        leaveM.setDisplayName("§c§lQuitter §7• Clique");
        leave.setItemMeta(leaveM);
        p.getInventory().setItem(8, leave);
    }

    public static void getEndUnrankedItems(Player p)
    {
        p.getInventory().clear();
        Bukkit.getScheduler().runTaskLater(core, () -> {
            // Code à exécuter après 1 seconde
            // Par exemple, envoi d'un message à un joueur

            p.getInventory().setHelmet(null);
            p.getInventory().setChestplate(null);
            p.getInventory().setLeggings(null);
            p.getInventory().setBoots(null);

            ItemStack replay = new ItemStack(Material.PAPER, 1);
            ItemMeta replayM = replay.getItemMeta();
            replayM.setDisplayName("§d§lRejouer §7• Clique");
            replay.setItemMeta(replayM);
            p.getInventory().setItem(0, replay);

            ItemStack leave = new ItemStack(Material.BED, 1);
            ItemMeta leaveM = leave.getItemMeta();
            leaveM.setDisplayName("§c§lQuitter §7• Clique");
            leave.setItemMeta(leaveM);
            p.getInventory().setItem(8, leave);
        }, 5);
    }

    public static void getStartedKit(Player p)
    {
        p.getInventory().clear();

        ItemStack defaultKit = new ItemStack(Material.BOOK, 1);
        ItemMeta defaultKitM = defaultKit.getItemMeta();
        defaultKitM.setDisplayName("§d§lKit par défaut §7• Clique");
        defaultKit.setItemMeta(defaultKitM);
        p.getInventory().setItem(8, defaultKit);
    }
}
