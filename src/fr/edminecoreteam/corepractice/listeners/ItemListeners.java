package fr.edminecoreteam.corepractice.listeners;

import fr.edminecoreteam.corepractice.Core;
import fr.edminecoreteam.corepractice.gui.UnrankedGui;
import fr.edminecoreteam.corepractice.matchmaking.GameCheck;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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
        if (core.getInLobby().contains(p) || core.getInEditor().contains(p))
        {
            e.setCancelled(true);
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
        if (it.getType() == Material.BED && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lQuitter §7• Clique") && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK || a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK)) {
            e.setCancelled(true);
            GameCheck gameCheck = core.getGameCheck();
            gameCheck.removeSerchGame(p);
            core.getInWaiting().remove(p);
            p.sendMessage("§cRecherche annulée...");
            getLobbyItems(p);
        }
        if (it.getType() == Material.IRON_SWORD && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§f§lUnranked §7• Clique") && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK || a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK)) {
            e.setCancelled(true);
            UnrankedGui.gui(p);
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

            ItemStack ffa = new ItemStack(Material.GOLD_AXE, 1);
            ItemMeta ffaM = ffa.getItemMeta();
            ffaM.setDisplayName("§e§lFFA §7• Clique");
            ffa.setItemMeta(ffaM);
            p.getInventory().setItem(3, ffa);

            ItemStack party = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta partyM = (SkullMeta) party.getItemMeta();
            partyM.setDisplayName("§a§lCréer une partie §7• Clique");
            partyM.setOwner(p.getName());
            party.setItemMeta(partyM);
            p.getInventory().setItem(5, party);

            ItemStack settings = new ItemStack(Material.REDSTONE_COMPARATOR, 1);
            ItemMeta settingsM = settings.getItemMeta();
            settingsM.setDisplayName("§9§lRéglages §7• Clique");
            settings.setItemMeta(settingsM);
            p.getInventory().setItem(6, settings);

            ItemStack kitEditor = new ItemStack(Material.BOOK, 1);
            ItemMeta kitEditorM = kitEditor.getItemMeta();
            kitEditorM.setDisplayName("§d§lKit Editor §7• Clique");
            kitEditor.setItemMeta(kitEditorM);
            p.getInventory().setItem(8, kitEditor);
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
}
